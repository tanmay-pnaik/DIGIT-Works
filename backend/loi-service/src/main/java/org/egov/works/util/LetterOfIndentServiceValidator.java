package org.egov.works.util;

import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.web.models.LetterOfIndent;
import org.egov.works.web.models.LetterOfIndentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static org.egov.works.util.LOIConstants.MDMS_MASTER;
import static org.egov.works.util.LOIConstants.MDMS_MODULE_NAME;

public class LetterOfIndentServiceValidator {

    @Autowired
    private HRMSUtils hrmsUtils;
    public void ValidateCreateLoi(LetterOfIndentRequest request, Object mdmsData) {
        Map<String, String> errorMap = new HashMap<>();
        validateRequestInfo(request.getRequestInfo(), errorMap);
        validateLoiData(request, errorMap);
        validateMdmsData(request.getLetterOfIndent(), errorMap, mdmsData);
        validateHRMSData(request,errorMap);
    }

    private void validateLoiData(LetterOfIndentRequest request, Map<String, String> errorMap) {

        LetterOfIndent letterOfIndent = request.getLetterOfIndent();
        if (StringUtils.isEmpty(letterOfIndent.getFileNumber())) {
            errorMap.put("FileNUmber", "FileNUmber is Mandatory");
        }
        if (letterOfIndent.getAgreementDate() != null) {
            errorMap.put("AgreementDate", "AgreementDate is Mandatory");
        }
        if (letterOfIndent.getEmdAmount() != null) {

            errorMap.put("EmdAmount", "EmdAmount is Mandatory");
        }
        if (letterOfIndent.getDefectLiabilityPeriod() != null) {
            errorMap.put("DefectLiabilityPeriod", "DefectLiabilityPeriod is Mandatory");
        }
        if (StringUtils.isEmpty(letterOfIndent.getContractorId())) {
            errorMap.put("ContractorId", "ContractorId is Mandatory");
        }
        if (letterOfIndent.getOicId() != null) {
            errorMap.put("OicId", "OicId is Mandatory");

        }
    }

    private void validateRequestInfo(RequestInfo requestInfo, Map<String, String> errorMap) {
        if (requestInfo == null) {
            throw new CustomException("REQUEST_INFO", "Request info is mandatory");
        }
        if (requestInfo.getTs() == null || requestInfo.getTs() == 0) {
            errorMap.put("TIMESTAMP", "Ts is mandatory");
        }
        if (StringUtils.isBlank(requestInfo.getMsgId())) {
            errorMap.put("MESSAGE_ID", "MsgIf is mandatory");
        }
        if (StringUtils.isBlank(requestInfo.getAction())) {
            errorMap.put("ACTION", "Action is mandatory");
        }
        if (requestInfo.getUserInfo() == null) {
            errorMap.put("USERINFO", "UserInfo is mandatory");
        }
        if (requestInfo.getUserInfo() != null && StringUtils.isBlank(requestInfo.getUserInfo().getUuid())) {
            errorMap.put("USERINFO_UUID", "UUID is mandatory");
        }
    }

    private void validateMdmsData(LetterOfIndent request, Map<String, String> errorMap, Object mdmsData) {
        try {
            final String jsonPathForTenants = "$.MdmsRes." + MDMS_MODULE_NAME + "." + MDMS_MASTER;
            List<List<String>> tenantRes = JsonPath.read(mdmsData, jsonPathForTenants);
            if (CollectionUtils.isEmpty(tenantRes))
                errorMap.put("INVALID_TENANT", "The tenant: " + request.getTenantId() + " is not present in MDMS");
            else {
                List<String> tenantlist =
                        tenantRes.stream()
                                .flatMap(List::stream)
                                .collect(Collectors.toList());
                if (tenantlist.contains(request.getTenantId())) {
                    errorMap.put("INVALID_TENANT", "The tenant: " + request.getTenantId() + " is not present in MDMS");
                }


            }
        }
        catch(Exception e){
            throw new CustomException("JSONPATH_ERROR", "Failed to parse mdms response");
        }


    }

    private void validateHRMSData(LetterOfIndentRequest request, Map<String, String> errorMap) {
    try
    {
        List<String> uuids = new ArrayList<>();
        String uuid = request.getLetterOfIndent().getOicId().toString();
        uuids.add(uuid);
        List<String> hmrsRes =  hrmsUtils.getUUIDs(uuids,request.getRequestInfo());
        if(!hmrsRes.contains(uuid))
        {
            errorMap.put("INVALID_OICID", "The OICID: " + uuid + " is not present in HRMS");
        }
    }catch(Exception e){
        throw new CustomException("JSONPATH_ERROR", "Failed to parse HRMS response");
    }

    }
}
