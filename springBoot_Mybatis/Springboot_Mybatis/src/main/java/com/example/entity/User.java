package com.example.entity;

import lombok.Data;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 14:39
 */
@Data
public class User {

    /**
     * 分类id
     */
    private int catId;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 父分类id
     */
    private Long parentCid;
    /**
     * 层级
     */
    private Integer catLevel;
    /**
     * 是否显示[0-不显示，1显示]
     */
    private Integer showStatus;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 图标地址
     */
    private String icon;
    /**
     * 计量单位
     */
    private String productUnit;
    /**
     * 商品数量
     */
    private Integer productCount;
}
