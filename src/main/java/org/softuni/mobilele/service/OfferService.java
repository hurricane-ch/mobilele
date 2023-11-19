package org.softuni.mobilele.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.softuni.mobilele.model.dto.CreateOfferDTO;
import org.softuni.mobilele.model.dto.OfferDetailDTO;
import org.softuni.mobilele.model.dto.OfferSummaryDTO;
import org.softuni.mobilele.model.entity.ModelEntity;
import org.softuni.mobilele.model.entity.OfferEntity;
import org.softuni.mobilele.model.entity.UserEntity;
import org.softuni.mobilele.model.entity.UserRoleEntity;
import org.softuni.mobilele.model.enums.UserRoleEnum;
import org.softuni.mobilele.repository.ModelRepository;
import org.softuni.mobilele.repository.OfferRepository;
import org.softuni.mobilele.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class OfferService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final ModelRepository modelRepository;

    public UUID createOffer(CreateOfferDTO createOfferDTO, UserDetails seller) {

        OfferEntity newOffer = map(createOfferDTO);
        ModelEntity modelEntity = modelRepository.findById(createOfferDTO.modelId()).orElseThrow(() ->
                new IllegalArgumentException("Model with id " + createOfferDTO.modelId() + " not found!"));
        UserEntity sellerEntity = userRepository.findByEmail(seller.getUsername()).orElseThrow(() ->
                new IllegalArgumentException("User with email " + seller.getUsername() + " not found!"));

        newOffer.setModel(modelEntity);
        newOffer.setSeller(sellerEntity);

        newOffer = offerRepository.save(newOffer);

        return newOffer.getUuid();
    }

    public Page<OfferSummaryDTO> getAllOffers(Pageable pageable) {
        return offerRepository
                .findAll(pageable)
                .map(OfferService::mapAsSummary);
    }
    public Optional<OfferDetailDTO> getOfferDetail(UUID offerUUID, UserDetails viewer) {
        return offerRepository
                .findByUuid(offerUUID)
                .map(o -> this.mapAsDetails(o, viewer));
    }

    @Transactional
    public void deleteOffer(UUID offerUUID) {
        offerRepository.deleteByUuid(offerUUID);
    }

    private OfferDetailDTO mapAsDetails(OfferEntity offerEntity, UserDetails viewer) {

        return new OfferDetailDTO(
                offerEntity.getUuid().toString(),
                offerEntity.getModel().getBrand().getName(),
                offerEntity.getModel().getName(),
                offerEntity.getYear(),
                offerEntity.getMileage(),
                offerEntity.getPrice(),
                offerEntity.getEngine(),
                offerEntity.getTransmission(),
                offerEntity.getImageUrl(),
                offerEntity.getSeller().getFirstName(),
                isOwner(offerEntity, viewer != null ? viewer.getUsername() : null));
    }

    public boolean isOwner(UUID uuid, String userName) {
        return isOwner(
                offerRepository.findByUuid(uuid).orElse(null),
                userName
        );
    }

    private boolean isOwner(OfferEntity offerEntity, String userName) {
        if (offerEntity == null || userName == null) {
            // anonymous users own no offers
            // missing offers are meaningless
            return false;
        }

        UserEntity viewerEntity =
                userRepository
                        .findByEmail(userName)
                        .orElseThrow(() -> new IllegalArgumentException("Unknown user..."));

        if (isAdmin(viewerEntity)) {
            // all admins own all offers
            return true;
        }

        return Objects.equals(
                offerEntity.getSeller().getId(),
                viewerEntity.getId());
    }

    private boolean isAdmin(UserEntity userEntity) {
        return userEntity
                .getRoles()
                .stream()
                .map(UserRoleEntity::getRole)
                .anyMatch(r -> UserRoleEnum.ADMIN == r);
    }

    private static OfferSummaryDTO mapAsSummary(OfferEntity offerEntity) {
        return new OfferSummaryDTO(
                offerEntity.getUuid().toString(),
                offerEntity.getModel().getBrand().getName(),
                offerEntity.getModel().getName(),
                offerEntity.getYear(),
                offerEntity.getMileage(),
                offerEntity.getPrice(),
                offerEntity.getEngine(),
                offerEntity.getTransmission(),
                offerEntity.getImageUrl());
    }

    private OfferEntity map(CreateOfferDTO createOfferDTO) {
        OfferEntity newOffer = new OfferEntity();

        newOffer.setUuid(UUID.randomUUID());
        newOffer.setDescription(createOfferDTO.description());
        newOffer.setEngine(createOfferDTO.engine());
        newOffer.setTransmission(createOfferDTO.transmission());
        newOffer.setImageUrl(createOfferDTO.imageUrl());
        newOffer.setMileage(createOfferDTO.mileage());
        newOffer.setPrice(BigDecimal.valueOf(createOfferDTO.price()));
        newOffer.setYear(createOfferDTO.year());

        return newOffer;

    }
}