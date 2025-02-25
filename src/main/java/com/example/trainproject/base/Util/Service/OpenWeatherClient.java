package com.example.trainproject.base.Util.Service;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "openWeatherClient", url = "http://api.openweathermap.org/data/2.5")
public interface OpenWeatherClient {
}
