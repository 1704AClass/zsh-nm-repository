package com.ningmeng.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.ningmeng.framework.domain.cms.CmsPage;
import com.ningmeng.framework.domain.cms.response.CmsCode;
import com.ningmeng.framework.domain.request.QueryPageRequest;
import com.ningmeng.framework.domain.respose.CmsPageResult;
import com.ningmeng.framework.exception.ExceptionCast;
import com.ningmeng.framework.model.response.CommonCode;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.QueryResult;
import com.ningmeng.framework.model.response.ResponseResult;
import com.ningmeng.manage_cms.dao.CmsPageRepository;
import com.ningmeng.manage_cms.config.RabbitmqConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by 赵晟晖 on 2020/2/13.
 */
@Service
public class PageService {

    @Autowired
    CmsPageRepository cmsPageRepository;
    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 页面列表分页查询
     *
     * @param page             当前页码
     * @param size             页面显示个数
     * @param queryPageRequest 查询条件
     * @return 页面列表
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }
        if (page <= 0) {
            page = 1;
        }
        page = page - 1;//为了适应mongodb的接口将页码减1
        if (size <= 0) {
            size = 20;
        }
//分页对象
        Pageable pageable = new PageRequest(page, size);
        //分页查询
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        QueryResult<CmsPage> cmsPageQueryResult = new QueryResult<CmsPage>();
        cmsPageQueryResult.setList(all.getContent());
        cmsPageQueryResult.setTotal((int) all.getTotalElements());
//返回结果
        return new QueryResponseResult(CommonCode.SUCCESS, cmsPageQueryResult);
    }

    /**
     * 页面列表分页查询
     * * @param page 当前页码
     * * @param size 页面显示个数
     * * @param queryPageRequest 查询条件
     * * @return 页面列表
     */
    public QueryResponseResult findLists(int page, int size, QueryPageRequest queryPageRequest) {
        //条件匹配器
        // 页面名称模糊查询，需要自定义字符串的匹配器实现模糊查询
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        // 条件值
        CmsPage cmsPage = new CmsPage();
        // 站点ID
        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        // 页面别名
        if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        // 创建条件实例
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        // 页码
        page = page - 1;
        // 分页对象
        Pageable pageable = new PageRequest(page, size);
        // 分页查询
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        QueryResult<CmsPage> cmsPageQueryResult = new QueryResult<CmsPage>();
        cmsPageQueryResult.setList(all.getContent());
        cmsPageQueryResult.setTotal(all.getTotalElements());
        // 返回结果
        return new QueryResponseResult(CommonCode.SUCCESS, cmsPageQueryResult);
    }

    //添加
    public CmsPageResult add(CmsPage cmsPage) {
        //校验页面是否存在，根据页面名称、站点Id、页面webpath查询
        CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (cmsPage1 != null) {
            //校验页面是否存在，已存在则抛出异常
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        if (cmsPage1 == null) {
            cmsPage.setPageId(null);
            //添加页面主键由spring data 自动生成
            cmsPageRepository.save(cmsPage);
            //返回结果
            CmsPageResult cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, cmsPage);
            return cmsPageResult;
        }
        return new CmsPageResult(CommonCode.FAIL, null);
    }


    //根据id查询页面
    public CmsPage getById(String id) {

        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        // 返回空
        return null;
    }


    //更新页面信息
    public CmsPageResult update(String id, CmsPage cmsPage) {
// 根据id查询页面信息
        CmsPage one = this.getById(id);
        if (one != null) {
// 更新模板id
            one.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            one.setSiteId(cmsPage.getSiteId());
            // 更新页面别名
            one.setPageAliase(cmsPage.getPageAliase());
            // 更新页面名称
            one.setPageName(cmsPage.getPageName());
            // 更新访问路径
            one.setPageWebPath(cmsPage.getPageWebPath());
            // 更新物理路径
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            // 执行更新
            CmsPage save = cmsPageRepository.save(one);
            if (save != null) {
                // 返回成功
                CmsPageResult cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, save);
                return cmsPageResult;
            }
        }
        // 返回失败
        return new CmsPageResult(CommonCode.FAIL, null);
    }

    //删除页面
    public ResponseResult delete(String id) {
        CmsPage one = this.getById(id);
        if (one != null) {
// 删除页面
            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }








    //生成页面
//页面发布
    public ResponseResult postPage(String pageId) {
//执行静态化
        boolean flag=createhtml();
//保存静态化文件
//发送消息
        sendPostPage(pageId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    private boolean createhtml() {
        System.out.println("执行静态页面程序，保存静态化完成");
        return true;
    }

    //发送页面发布消息
    private void sendPostPage(String pageId) {
        CmsPage cmsPage = this.getById(pageId);
        if (cmsPage == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        Map<String, String> msgMap = new HashMap<>();
        msgMap.put("pageId", pageId);
//消息内容
        String msg = JSON.toJSONString(msgMap);
//获取站点id作为routingKey
        String siteId = cmsPage.getSiteId();
//发布消息
        this.rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE, siteId, msg);
    }
}
