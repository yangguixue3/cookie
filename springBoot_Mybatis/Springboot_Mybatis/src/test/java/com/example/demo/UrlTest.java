package com.example.demo;

import com.example.DemoApplication;
import com.example.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URL;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UrlTest {

    @LocalServerPort
    private int port =8080;

    private URL base;

    @Autowired
    private TestRestTemplate restTemplate;


    /**
     * 向"/test"地址发送请求，并打印返回结果
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        String url = String.format("http://localhost:8080/testBoot/getUser/1");

        this.base = new URL(url);
    }

    @Test

    public void getUserByIdTest() throws Exception {

        ResponseEntity<String> response = this.restTemplate.getForEntity(
                "http://localhost:8080/testBoot/getUser/1", String.class, "");
        System.out.println(String.format("测试结果为：%s", response.getBody().toString()));
    }



}
