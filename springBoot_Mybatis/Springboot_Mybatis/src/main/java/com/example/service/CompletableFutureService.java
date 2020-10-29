package com.example.service;

import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


//localhost:8080/CompletableFutureController/getInfo/1
@Service
public class CompletableFutureService {
    @Autowired
    UserMapper userMapper;
    
    public  String getInfo(int userId) {

        // 1 查询数据字节长度 byteLength
        Integer byteLength = userMapper.getInfo1(userId);
        // 2 数据分割 task
        // 根据字符数分割，以n个字符位单位,每个线程，要取的字符数
        Integer  characterLength = 3;
        if (byteLength != null && characterLength != 0) {
            // 数据 总共字符数
            Integer num= byteLength/3;

            //异步数量 num1
            Integer num1 = (num%characterLength == 0) ? (num/characterLength) : (num/characterLength +1);
            //线程池
            ExecutorService executor = Executors.newFixedThreadPool(num1);
            // 需要开启的线程数量 数组
            List<Integer> listTask = new ArrayList<>();
            for (int i = 0; i <num1-1 ; i++) {
                listTask.add(i);
            }
            // 3 启动 异步
            List<CompletableFuture<String>> CompletableFutureList = listTask.stream().
                    map(i -> CompletableFuture.supplyAsync(() ->
                            userMapper.getInfoByBate( userId,  i*characterLength+1,characterLength), executor)).
                    collect(Collectors.<CompletableFuture<String>>toList());
            //阻塞，等待结果
            CompletableFutureList.forEach(CompletableFuture::join);
            // 4 转换获取数据结果
            List<String> listStr = CompletableFutureList.stream().map((completableFuture)->{
                try {
                    return completableFuture.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
            System.out.println(listStr.toString());
            String str1 = String.join("", listStr);
            return str1;
        }

        return "helle";
    }

//    public <T> CompletableFuture<List<T>> allOf(List<CompletableFuture<T>> futuresList) {
//        CompletableFuture<Void> allFuturesResult =
//                CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[futuresList.size()]));
//        return allFuturesResult.thenApply(v ->
//                futuresList.stream().
//                        map(future -> future.join()).
//                        collect(Collectors.<T>toList())
//        );
//    }
}



