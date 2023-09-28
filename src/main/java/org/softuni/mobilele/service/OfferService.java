package org.softuni.mobilele.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.softuni.mobilele.model.dto.CreateOfferDTO;
import org.softuni.mobilele.repository.OfferRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class OfferService {

    private final OfferRepository offerRepository;

    public UUID createOffer(CreateOfferDTO createOfferDTO) {
        //todo - create offer
        throw new UnsupportedOperationException("Coming soon!");
    }

}
