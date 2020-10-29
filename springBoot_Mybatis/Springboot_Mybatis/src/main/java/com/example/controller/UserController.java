package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 14:42
 */

@RestController
@RequestMapping("/testBoot")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("getUser/{id}")
    public String GetUser(@PathVariable int id){
        return userService.Sel(id).toString();
    }

    @RequestMapping("/getInfo/{id}")
    public String getInfo(@PathVariable int id){
        return userService.getInfo(id).toString();
    }

    @RequestMapping("/getInfo1/{id}")
    public String getInfo1(@PathVariable int id) throws ExecutionException, InterruptedException {
        System.out.print(userService.getInfo1(id));
        return userService.getInfo1(id);
    }
}
