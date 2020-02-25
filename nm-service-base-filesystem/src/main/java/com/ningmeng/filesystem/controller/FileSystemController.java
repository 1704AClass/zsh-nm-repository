package com.ningmeng.filesystem.controller;

import com.ningmeng.api.filesystemapi.fileSystem;
import com.ningmeng.filesystem.service.FileSystemService;
import com.ningmeng.framework.domain.filesystem.response.UploadFileResult;
import com.ningmeng.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/filesystem")
public class FileSystemController implements fileSystem {
    @Autowired
FileSystemService fileSystemService;

@Override
@PostMapping("/upload")
public UploadFileResult upload(@RequestParam("file") MultipartFile file,
                               @RequestParam(value = "filetag", required = true) String filetag,
                               @RequestParam(value = "businesskey", required = false) String businesskey,
                               @RequestParam(value = "metedata", required = false) String metadata) {
    return fileSystemService.upload(file,filetag,businesskey,metadata);
}

    @Override
    @PostMapping("/coursepic/add")
    public ResponseResult addCoursePic(@RequestParam("courseId") String courseId,
                                       @RequestParam("pic") String pic) {
        // /保存课程图片
        return fileSystemService.saveCoursePic(courseId,pic); }

}