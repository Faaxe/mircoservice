package com.faxe.dubbo.course.entity;

import lombok.Data;

/**
 * 实现crud的实体均需要继承该类
 *
 * @author ZhouXiang
 * @create 2018-08-25 23:30
 **/
@Data
public class CrudEntity {
    //时间戳
    private Long createTime;

    private Long updateTime;
}
