package org.egov.web.utils;

import org.egov.common.contract.request.RequestInfo;
import org.egov.web.models.LetterOfIndent;
import org.springframework.stereotype.Component;

@Component
public class MDMSUtils {

    public Object mDMSCall(LetterOfIndent request){
        String tenantId = request.getTenantId();
        //MdmsCriteriaReq mdmsCriteriaReq = getMDMSRequest(request,tenantId);
        //Object result = serviceRequestRepository.fetchResult(getMdmsSearchUrl(), mdmsCriteriaReq);
        return null;
    }
}
