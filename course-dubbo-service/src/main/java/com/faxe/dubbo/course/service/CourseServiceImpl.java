package com.faxe.dubbo.course.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.faxe.dubbo.course.dao.CourseDao;
import com.faxe.dubbo.course.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 服务provider
 *
 * @service 为 dubbo 注解
 * application.yml 中  scan: com.faxe.dubbo.course.service 会扫描这个注解
 *
 * @author ZhouXiang
 * @create 2018-08-25 23:53
 **/
@Service(version = "V1.0.0")
public class CourseServiceImpl implements CourseService<String> {
    @Autowired
    private CourseDao courseDao;

    @Override
    public Course getCourseById(String id) {
        return courseDao.getOne(id);
    }

    @Override
    public String saveCourse(Course entity) {
        return courseDao.save(entity).getId();
    }

    @Override
    public List<Course> findAll() {
        return courseDao.findAll();
    }
}
