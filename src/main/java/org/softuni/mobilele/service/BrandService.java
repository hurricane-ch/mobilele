package org.softuni.mobilele.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.softuni.mobilele.model.dto.BrandDTO;
import org.softuni.mobilele.model.dto.ModelDTO;
import org.softuni.mobilele.repository.BrandRepository;
import org.softuni.mobilele.repository.ModelRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class BrandService {

    private final ModelRepository modelRepository;
    private final BrandRepository brandRepository;

    public List<BrandDTO> getAllBrands() {

        return brandRepository.findAll().stream()
                .map(brand -> new BrandDTO(
                        brand.getBrand(),
                        modelRepository.findAllByBrandId(brand.getId()).stream()
                                .map(model -> new ModelDTO(model.getId(), model.getName()))
                                .sorted(Comparator.comparing(ModelDTO::name))
                                .collect(Collectors.toList())
                ))
                .sorted(Comparator.comparing(BrandDTO::name))
                .collect(Collectors.toList());
    }
}