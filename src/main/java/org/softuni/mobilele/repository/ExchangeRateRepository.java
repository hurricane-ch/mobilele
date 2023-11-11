package org.softuni.mobilele.repository;

import org.softuni.mobilele.model.entity.ExchangeRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, Long > {
    Optional<ExchangeRateEntity> findByCurrency(String currency);
}
