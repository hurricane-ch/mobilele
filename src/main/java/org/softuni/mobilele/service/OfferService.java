package org.softuni.mobilele.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.softuni.mobilele.model.dto.CreateOfferDTO;
import org.softuni.mobilele.model.dto.OfferDetailDTO;
import org.softuni.mobilele.model.dto.OfferSummaryDTO;
import org.softuni.mobilele.model.entity.ModelEntity;
import org.softuni.mobilele.model.entity.OfferEntity;
import org.softuni.mobilele.repository.ModelRepository;
import org.softuni.mobilele.repository.OfferRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class OfferService {

    private final OfferRepository offerRepository;
    private final ModelRepository modelRepository;

    public UUID createOffer(CreateOfferDTO createOfferDTO) {

        OfferEntity newOffer = map(createOfferDTO);
        ModelEntity modelEntity = modelRepository.findById(createOfferDTO.modelId()).orElseThrow(() ->
                new IllegalArgumentException("Model with id " + createOfferDTO.modelId() + " not found!"));

        newOffer.setModel(modelEntity);

        newOffer = offerRepository.save(newOffer);

        return newOffer.getUuid();
    }

    public Page<OfferSummaryDTO> getAllOffers(Pageable pageable) {
        return offerRepository
                .findAll(pageable)
                .map(OfferService::mapAsSummary);
    }
    public Optional<OfferDetailDTO> getOfferDetail(UUID offerUUID) {
        return offerRepository
                .findByUuid(offerUUID)
                .map(OfferService::mapAsDetails);
    }

    @Transactional
    public void deleteOffer(UUID offerUUID) {
        offerRepository.deleteByUuid(offerUUID);
    }

    private static OfferDetailDTO mapAsDetails(OfferEntity offerEntity) {
        // TODO: reuse
        return new OfferDetailDTO(
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