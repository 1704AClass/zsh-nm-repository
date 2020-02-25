package com.ningmeng.api.courseapi;


import com.ningmeng.framework.domain.course.Teachplan;
import com.ningmeng.framework.domain.course.ext.TeachplanNode;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.ResponseResult;
import io.swagger.annotations.ApiOperation;

public interface CourseControllerApi {
    @ApiOperation("课程计划查询")
    public TeachplanNode findTeachplanList(String courseId);
    @ApiOperation("添加课程计划") public ResponseResult addTeachplan(Teachplan teachplan);
//查询课程列表
@ApiOperation("查询我的课程列表") public QueryResponseResult findCourseList(int page, int pagesize, String companyId);

}