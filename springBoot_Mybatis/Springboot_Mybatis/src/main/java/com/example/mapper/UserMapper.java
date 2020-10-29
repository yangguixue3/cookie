package com.example.mapper;

import com.example.entity.InfoDescEntity;
import com.example.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 15:20
 */
@Repository
public interface UserMapper {

    User Sel(int id);
    InfoDescEntity getInfo(int id);

    Integer getInfo1(int id);


    String getInfoByBate(int userId, int startFromByteNumber);
    String getInfoByBate(int userId, int startFromByteNumber,int NumberOfByteIntervals );

    User getUser(int id);
}
