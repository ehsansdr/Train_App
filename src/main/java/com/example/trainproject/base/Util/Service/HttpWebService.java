package com.example.trainproject.base.Util.Service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

// you can not use any name that you want in the name attribute of the @FeignClient
@FeignClient(name = "httpbin-client" , url = "http://localhost:80")
public interface HttpWebService {

    @GetMapping("/get")
    Map<String, Object> getRequest(); // Handle GET requests

    @PostMapping("/post")
    Map<String, Object> postRequest(@RequestBody Map<String, Object> body); // Handle POST requests
}
