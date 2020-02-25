package com.ningmeng.api.filesystemapi;

import com.ningmeng.framework.domain.filesystem.response.UploadFileResult;
import com.ningmeng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by 赵晟晖 on 2020/2/25.
 */
@Api("文件系统服务接口")
public interface fileSystem {

    /*** 上传文件
     *
     * * @param multipartFile 文件
     * * @param filetag 文件标签
     * * @param businesskey 业务key
     * * @param metedata 元信息,json格式 * @return */
    @ApiOperation("上传文件")
    public UploadFileResult upload(MultipartFile multipartFile, String filetag, String businesskey, String metadata);
    @ApiOperation("添加课程图片") public ResponseResult addCoursePic(String courseId, String pic);

}
