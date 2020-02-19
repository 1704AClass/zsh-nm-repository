package com.ningmeng.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.ningmeng.manage_cms.service.PageService;
import com.ningmeng.manage_cms_client.service.CmsPageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by 赵晟晖 on 2020/2/19.
 */
@Component
public class ConsumerPostPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerPostPage.class);
    @Autowired
    PageService pageService;
    @Autowired
    CmsPageService cmsPageService;




    @RabbitListener(queues={"${ningmeng.mq.queue}"})
    public void postPage(String msg){
//解析消息
        Map map = JSON.parseObject(msg, Map.class);
        LOGGER.info("receive cms post page:{}",msg.toString());
//取出页面id
        String pageId = (String) map.get("pageId");
//查询页面信息
//将页面保存到服务器物理路径
        cmsPageService.TestRabbit(pageId);
    }



}
