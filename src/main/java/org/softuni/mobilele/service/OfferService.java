package org.softuni.mobilele.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.softuni.mobilele.model.dto.CreateOfferDTO;
import org.softuni.mobilele.model.entity.ModelEntity;
import org.softuni.mobilele.model.entity.OfferEntity;
import org.softuni.mobilele.repository.ModelRepository;
import org.softuni.mobilele.repository.OfferRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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