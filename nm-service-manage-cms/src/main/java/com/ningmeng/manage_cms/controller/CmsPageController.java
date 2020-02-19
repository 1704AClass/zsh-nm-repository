package com.ningmeng.manage_cms.controller;

import com.ningmeng.api.cmsapi.CmsPageControllerApi;
import com.ningmeng.framework.domain.cms.CmsPage;
import com.ningmeng.framework.domain.request.QueryPageRequest;
import com.ningmeng.framework.domain.respose.CmsPageResult;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.ResponseResult;
import com.ningmeng.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 赵晟晖 on 2020/2/12.
 */
@RestController
public class CmsPageController implements CmsPageControllerApi {
    @Autowired
    PageService pageService;
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest) {
     /*   //暂时采用测试数据，测试接口是否可以正常运行
       QueryResult queryResult = new QueryResult();
         queryResult.setTotal(2);
        // 静态数据列表
         List list = new ArrayList();
         CmsPage cmsPage = new CmsPage();
         cmsPage.setPageName("测试页面");
        list.add(cmsPage);
        queryResult.setList(list);
         QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);*/
        return pageService.findList(page,size,queryPageRequest);
    }
//添加
    @Override
    @PostMapping("/add")
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        return pageService.add(cmsPage);
    }

    @Override @GetMapping("/get/{id}")
    public CmsPage findById(@PathVariable("id") String id) {
        return pageService.getById(id);
    }
    @Override @PutMapping("/edit/{id}")
    //这里使用put方法，http 方法中put表示更新
     public CmsPageResult edit(@PathVariable("id") String id,
    @RequestBody CmsPage cmsPage) {
        return pageService.update(id,cmsPage);
    }

    @DeleteMapping("/del/{id}")
    //使用http的delete方法完成岗位操作
     public ResponseResult delete(@PathVariable("id") String id) {
    return pageService.delete(id);
    }

    @Override
    @PostMapping("/postPage/{pageId}")
    public ResponseResult post(@PathVariable("pageId") String pageId) {
        return pageService.postPage(pageId);
    }

}
