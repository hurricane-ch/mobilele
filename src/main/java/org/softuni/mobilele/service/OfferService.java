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
        OfferEntity offer = new OfferEntity();

        offer.setUuid(UUID.randomUUID());
        offer.setEngine(createOfferDTO.engine());
        offer.setImageUrl(createOfferDTO.imageUrl());
        offer.setMileage(createOfferDTO.mileage());
        offer.setPrice(BigDecimal.valueOf(createOfferDTO.price()));
        offer.setYear(createOfferDTO.year());
        offer.setTransmission(createOfferDTO.transmission());
        offer.setDescription(createOfferDTO.description());

        return offer;
    }
}
