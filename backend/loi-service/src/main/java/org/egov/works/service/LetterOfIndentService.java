package org.egov.works.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.IdResponse;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.LOIConfiguration;
import org.egov.works.producer.Producer;
import org.egov.works.repository.IdGenRepository;
import org.egov.works.util.LetterOfIndentServiceUtil;
import org.egov.works.util.LetterOfIndentServiceValidator;
import org.egov.works.util.MDMSUtils;
import org.egov.works.web.models.LetterOfIndent;
import org.egov.works.web.models.LetterOfIndentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LetterOfIndentService {
    @Autowired
    private MDMSUtils mdmsUtils;

    @Autowired
    private LetterOfIndentServiceValidator validator;

    @Autowired
    private IdGenRepository idGenRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private LetterOfIndentServiceUtil loiUtil;

    @Autowired
    private LOIConfiguration config;

    @Autowired
    private Producer producer;

    public LetterOfIndentRequest createLOI(LetterOfIndentRequest request) throws IOException {

        Object mdmsData = mdmsUtils.mDMSCall(request.getLetterOfIndent());
        validator.ValidateCreateLoi(request, mdmsData);
        iddGenForLoi(request);

        producer.push(config.getSaveEstimateTopic(), request);
        return request;
    }

    /**
     * Returns a list of numbers generated from idgen
     *
     * @param requestInfo RequestInfo from the request
     * @param tenantId    tenantId of the city
     * @param idKey       code of the field defined in application properties for which ids are generated for
     * @param idformat    format in which ids are to be generated
     * @param count       Number of ids to be generated
     * @return List of ids generated using idGen service
     */
    private List<String> getIdList(RequestInfo requestInfo, String tenantId, String idKey,
                                   String idformat, int count) {
        List<IdResponse> idResponses = idGenRepository.getId(requestInfo, tenantId, idKey, idformat, count).getIdResponses();

        if (CollectionUtils.isEmpty(idResponses))
            throw new CustomException("IDGEN ERROR", "No ids returned from idgen Service");

        return idResponses.stream()
                .map(IdResponse::getId).collect(Collectors.toList());
    }

    private void iddGenForLoi(LetterOfIndentRequest request){
        RequestInfo requestInfo = request.getRequestInfo();
        LetterOfIndent loi = request.getLetterOfIndent();

        AuditDetails auditDetails = loiUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), loi, true);
        loi.setAuditDetails(auditDetails);
        loi.setId(UUID.randomUUID());

        List<String> loiNumbers = getIdList(requestInfo, loi.getTenantId()
                , config.getIdgenLoiNumberName(), config.getIdgenLoieNumberFormat(), 1);

        if (loiNumbers != null && !loiNumbers.isEmpty()) {
            loi.setLetterOfIndentNumber(loiNumbers.get(0));
        }

    }
}
