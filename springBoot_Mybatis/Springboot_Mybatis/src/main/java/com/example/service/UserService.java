package com.example.service;

import com.example.entity.InfoDescEntity;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 15:23
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    int number = 0;
    String str ="返回值";
    public User Sel(int id){
        return userMapper.Sel(id);
    }

    public InfoDescEntity getInfo(int i) {
        return userMapper.getInfo(i);
    }




//  异步获取数据
    public String  getInfo1(int userId) throws ExecutionException, InterruptedException {

        // 1.获取数据字符长度 utf-8
        Integer length = userMapper.getInfo1(userId);

        // 2.数据分割
        if(length != null&& length != 0){
            if (length / 1024 ==0){
                 number = length/1024;
            }else {
                number = length/1024 +1;
            }
        }

        // 3.根据分割数目，开启异步(1)
        if(length != null&& length != 0){
            CompletableFuture<String> future4 = CompletableFuture.supplyAsync(() -> {
                String str = userMapper.getInfoByBate( userId, 1 ,2);
                return str;
            }).thenCombineAsync(
                    CompletableFuture.supplyAsync(() -> {
                        String str = userMapper.getInfoByBate( userId, 3 ,100);
                        return str;
                        //合并
                    }),(s, s2) -> {
                        return s+s2;
                    }
            );


            // 3.开启异步(2)
            CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
                String str = userMapper.getInfoByBate( userId, 1 ,2);
                return str;
            });
            CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
                String str = userMapper.getInfoByBate( userId, 3 ,20);
                return str;
            });

            CompletableFuture<String> comFuture = future1.thenCombine(future2, (s, i) -> (s + i));

            // 3.根据分割数目，开启异步(3)
            Map<String, String> resultMap = new HashMap<>(10);
            CompletableFuture<String> string1 = CompletableFuture.supplyAsync(() -> {
                String str1 = userMapper.getInfoByBate( userId, 1 ,3);
                return str1;
            });

            CompletableFuture<String> string2 = CompletableFuture.supplyAsync(() -> {
                String str1 = userMapper.getInfoByBate( userId, 4 ,3);
                return str1;
            });

            CompletableFuture<String> string3 = CompletableFuture.supplyAsync(() -> {
                String str1 = userMapper.getInfoByBate( userId, 7 ,10);

                return str1;
            });

            CompletableFuture.allOf(string1, string2, string3).join();

            resultMap.put("string1", string1.get());
            resultMap.put("string2", string2.get());
            resultMap.put("string3", string3.get());
            System.out.print(resultMap.toString());







            try {
                str = future4.get();
                str = comFuture.get();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }



        // 4.合并数据


        return str;
    }



}
