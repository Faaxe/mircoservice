package com.faxe.dubbo.course.service;

import com.faxe.dubbo.course.dao.CourseDao;
import com.faxe.dubbo.course.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务provider
 *
 * @author ZhouXiang
 * @create 2018-08-25 23:53
 **/
@Service
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
