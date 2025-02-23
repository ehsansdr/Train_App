package com.example.trainproject.Util.Service;

import com.example.trainproject.Model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "jsonPlaceholderClient", url = "https://jsonplaceholder.typicode.com")
public interface JsonPlaceholderClient {

    // paste the real url if the url will not be existed you will get feign.RetryableException
    @GetMapping("/users/{id}")
    User getUser(@PathVariable("id") Long id);
    // if you give the non matching return type upi will get feign.RetryableException

//    @GetMapping("/posts")
//    List<Post> getPosts();


}
