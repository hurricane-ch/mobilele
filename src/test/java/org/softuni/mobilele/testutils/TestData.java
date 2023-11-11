package org.softuni.mobilele.testutils;

import org.softuni.mobilele.model.entity.ExchangeRateEntity;
import org.softuni.mobilele.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TestData {

  @Autowired
  private ExchangeRateRepository exchangeRateRepository;

  public void createExchangeRate(String currency, BigDecimal rate) {
    ExchangeRateEntity exchangeRate = new ExchangeRateEntity();

    exchangeRate.setCurrency(currency);
    exchangeRate.setRate(rate);

    exchangeRateRepository.save(exchangeRate);
  }

  public void cleanAllTestData() {
    exchangeRateRepository.deleteAll();
  }
}
