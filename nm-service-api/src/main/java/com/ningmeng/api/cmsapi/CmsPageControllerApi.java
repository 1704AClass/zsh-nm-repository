package com.ningmeng.api.cmsapi;

import com.ningmeng.framework.domain.cms.CmsPage;
import com.ningmeng.framework.domain.request.QueryPageRequest;
import com.ningmeng.framework.domain.respose.CmsPageResult;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * Created by 赵晟晖 on 2020/2/12.
 * 约束controller 对外调用
 */
@Api(value = "cms页面管理",description = "cms页面管理接口，提供页面的增删改查")
public interface CmsPageControllerApi {
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "页 码",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="size",value = "每页记录 数",required=true,paramType="path",dataType="int")
    })
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) ;
    @ApiOperation("添加页面")
    public CmsPageResult add(CmsPage cmsPage);

@ApiOperation("根据id查询页面")
    public CmsPage findById(String id);
    @ApiOperation("修改页面") public CmsPageResult edit(String id,CmsPage cmsPage);
    @ApiOperation("通过ID删除页面") public ResponseResult delete(String id);

    @ApiOperation("发布页面") public ResponseResult post(String pageId);






}