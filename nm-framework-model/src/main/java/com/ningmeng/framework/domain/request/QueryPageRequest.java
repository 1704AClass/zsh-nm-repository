package com.ningmeng.framework.domain.request;

import com.ningmeng.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by 赵晟晖 on 2020/2/12.
 */
@Data
public class QueryPageRequest extends RequestData {
    //站点id
    @ApiModelProperty("站点id")
    private String siteId;
    //页面ID
    @ApiModelProperty("页面id")
    private String pageId;
    //页面名称
    @ApiModelProperty("页面名称")
    private String pageName;
    //别名
    @ApiModelProperty("页面别名")
    private String pageAliase;
    //模版id
    @ApiModelProperty("模版id")
    private String templateId;
}
