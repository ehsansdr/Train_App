package com.example.trainproject.base.Controller;

import com.example.trainproject.base.Service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/exchange")
@RequiredArgsConstructor
public class ExchangeRateController {

  private final ExchangeRateService exchangeRateService;

//  @GetMapping("/{from}/{to}")
//  public Double getExchangeRate(@PathVariable("from") String fromCurrency,@PathVariable("to") String toCurrency) {
//    return  exchangeRateService.getExchangeRate(fromCurrency, toCurrency);
//  }
}
