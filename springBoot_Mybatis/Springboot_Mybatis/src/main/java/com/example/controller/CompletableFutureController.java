package com.example.controller;

import com.example.service.CompletableFutureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

//localhost:8080/CompletableFutureController/getInfo/1
@RestController
@RequestMapping("/CompletableFutureController")
public class CompletableFutureController {
    @Autowired
    private CompletableFutureService completableFutureService;

    @RequestMapping("/getInfo/{id}")
    public String getInfo1(@PathVariable int id) throws ExecutionException, InterruptedException {
        System.out.print(completableFutureService.getInfo(id));
        return completableFutureService.getInfo(id);
    }

}
