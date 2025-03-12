package com.example.trainproject.base.Controller;

import com.example.trainproject.base.Controller.FeignService.ExchangeRateClient;
import com.example.trainproject.base.Service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/exchange")
@RequiredArgsConstructor
public class ExchangeRateController {

  private final ExchangeRateService exchangeRateService;

  @GetMapping("/{from}/{to}")
  public Double getExchangeRate(String fromCurrency, String toCurrency) {
    return  exchangeRateService.getExchangeRate(fromCurrency, toCurrency);
  }
}
