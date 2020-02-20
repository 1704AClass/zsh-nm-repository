package com.ningmeng.manage_course.dao;

import com.ningmeng.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by 赵晟晖 on 2020/2/20.
 */
@Mapper
public interface TeachplanMapper {
    public TeachplanNode selectList(String courseId);
}
