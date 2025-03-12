package com.example.trainproject.base.Controller.FeignService;

import com.example.trainproject.base.Dto.ExchangeRateResponce;
import java.math.BigDecimal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "exchange-rate-client", url = "https://api.exchangerate-api.com/v4")
public interface ExchangeRateClient {

  @GetMapping("/latest/{currency}")
  ExchangeRateResponce getExchangeRates(@PathVariable String currency);
}
