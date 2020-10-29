package com.example.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysUser {
    private String id;
    private String username;
    private String password;
    private String email;
    private String mobile;
    private String status;
    private String create_user_id;
    private Date create_time;
}
