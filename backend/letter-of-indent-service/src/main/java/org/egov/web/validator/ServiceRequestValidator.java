package org.egov.web.validator;

import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.LetterOfIndent;
import org.egov.web.models.LetterOfIndentRequest;

import java.util.HashMap;
import java.util.Map;

public class ServiceRequestValidator {

    public void validateCreate(LetterOfIndentRequest request, Object mdmsData){
        Map<String,String> errorMap = new HashMap<>();
        validateLoiData(request,errorMap);
        //validateMDMS(request, mdmsData);
        if(!errorMap.isEmpty())
            throw new CustomException(errorMap);
    }
    private void validateLoiData(LetterOfIndentRequest request,Map<String, String> errorMap){

        LetterOfIndent letterOfIndent = request.getLetterOfIndent();
        if(StringUtils.isEmpty(letterOfIndent.getFileNumber()) || letterOfIndent.getAgreementDate() !=null ||
                letterOfIndent.getEmdAmount() !=null || letterOfIndent.getDefectLiabilityPeriod() !=null ||
                StringUtils.isEmpty(letterOfIndent.getContractorId()) || letterOfIndent.getOicId() != null)
            errorMap.put("INVALID_REQUEST","FileNumber,AgreementData,EmdAmount,DefectLiabilityPeriod,ContractorId,OicId is mandatory in LetterOfIndent object");
        }


}
