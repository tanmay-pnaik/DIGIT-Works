package org.egov.works.util;

import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.RequestInfoWrapper;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.LOIConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

public class HRMSUtils {
    @Autowired
    private LOIConfiguration config;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    private static final String HRMS_EMP_UUID_JSONPATH = "$.Employees.*.user.uuid";

    public List<String> getUUIDs(List<String> uuids, RequestInfo requestInfo)
    {

        StringBuilder url = getHRMSURI(uuids);

        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();

        Object res = serviceRequestRepository.fetchResult(url, requestInfoWrapper);

        List<String> uuidList = null;

        try {
            uuidList = JsonPath.read(res, HRMS_EMP_UUID_JSONPATH);
        }
        catch (Exception e){
            throw new CustomException("PARSING_ERROR","Failed to parse HRMS response");
        }

        if(CollectionUtils.isEmpty(uuidList))
            throw new CustomException("OICID_NOT_FOUND","The OICID of the user: "+uuids.toString()+" is not found");

        return uuidList;
    }
    public StringBuilder getHRMSURI(List<String> uuids){

        StringBuilder builder = new StringBuilder(config.getHrmsHost());
        builder.append(config.getHrmsEndPoint());
        builder.append("?uuids=");
        builder.append(StringUtils.join(uuids, ","));

        return builder;
    }
}
