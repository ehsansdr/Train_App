package com.example.trainproject.base.Service;

import com.example.trainproject.base.Controller.FeignService.ExchangeRateClient;
import com.example.trainproject.base.Dto.ExchangeRateResponce;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

   private final ExchangeRateClient exchangeRateClient;

  public Double getExchangeRate(String fromCurrency, String toCurrency) {
    ExchangeRateResponce response = exchangeRateClient.getExchangeRates(fromCurrency);
    return response.getRates().get(toCurrency);
  }


}
