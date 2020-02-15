package com.ningmeng.framework.domain.respose;

import com.ningmeng.framework.domain.cms.CmsPage;
import com.ningmeng.framework.model.response.ResponseResult;
import com.ningmeng.framework.model.response.ResultCode;
import lombok.Data;

/**
 * Created by 赵晟晖 on 2020/2/13.
 */
@Data
public class CmsPageResult extends ResponseResult {
    CmsPage cmsPage; public CmsPageResult(ResultCode resultCode, CmsPage cmsPage) {

    super(resultCode);
    this.cmsPage = cmsPage;
}
}