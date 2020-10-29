package com.example.demo;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void test1(){
        String name = getUser(1);
        Assert.assertNotNull(name);
        Assert.assertEquals("图书、音像、电子书刊", name);

    }

    private  String getUser(int id){
        User user = userMapper.Sel(1);
        return user.getName();

    }
}
