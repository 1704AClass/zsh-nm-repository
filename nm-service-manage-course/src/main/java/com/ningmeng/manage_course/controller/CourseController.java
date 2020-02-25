package com.ningmeng.manage_course.controller;

import com.ningmeng.api.courseapi.CourseControllerApi;
import com.ningmeng.framework.domain.course.Teachplan;
import com.ningmeng.framework.domain.course.ext.TeachplanNode;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.ResponseResult;
import com.ningmeng.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController implements CourseControllerApi {
    @Autowired
    CourseService courseService;
    //查询课程计划
@Override
@GetMapping("/teachplan/list/{courseId}")
public TeachplanNode findTeachplanList(String courseId) {
    return courseService.findTeachplanList(courseId);
}


//添加课程计划
@Override
@PostMapping("/teachplan/add")
public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) {
    return courseService.addTeachplan(teachplan);
}



//分页查询
    @Override
    @GetMapping("/findCourseList/{page}/{pagesize}")
    public QueryResponseResult findCourseList(@PathVariable("page") int page,@PathVariable("pagesize") int pagesize, String companyId) {
        return courseService.findCoursePage(page,pagesize,companyId);
    }


}