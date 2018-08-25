package com.faxe.dubbo.course.service;

import com.faxe.dubbo.course.entity.Course;

import java.util.List;

/**
 * 课程服务接口
 * PK为主键id
 *
 * @author ZhouXiang
 * @create 2018-08-25 22:48
 **/
public interface CourseService<PK> {

    Course getCourseById(PK id);

    PK saveCourse(Course entity);

    List<Course> findAll();
}
