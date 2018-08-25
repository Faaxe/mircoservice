package com.faxe.dubbo.course.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 课程实体
 *
 * @author ZhouXiang
 * @create 2018-08-25 22:51
 **/
@Data
@Entity
@Table(name = "tbl_course")
public class Course extends CrudEntity{
    private String id;

    private String courseName;

    private String courseDescription;

    private String teacher;
}
