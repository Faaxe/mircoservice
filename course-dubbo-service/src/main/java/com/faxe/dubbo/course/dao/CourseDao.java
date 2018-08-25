package com.faxe.dubbo.course.dao;

import com.faxe.dubbo.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * dao 接口 继承 JpaRepository
 *
 * @author ZhouXiang
 * @create 2018-08-25 23:25
 **/
@Repository
public interface CourseDao extends JpaRepository<Course, String> {
}
