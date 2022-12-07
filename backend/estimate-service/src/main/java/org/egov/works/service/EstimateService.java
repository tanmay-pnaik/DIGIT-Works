package org.egov.works.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.ProcessInstance;
import digit.models.coremodels.ProcessInstanceResponse;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.User;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.EstimateServiceConfiguration;
import org.egov.works.producer.Producer;
import org.egov.works.repository.EstimateRepository;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.validator.EstimateServiceValidator;
import org.egov.works.web.models.Estimate;
import org.egov.works.web.models.EstimateRequest;
import org.egov.works.web.models.EstimateRequestWorkflow;
import org.egov.works.web.models.EstimateSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EstimateService {

    @Autowired
    private EstimateServiceConfiguration serviceConfiguration;

    @Autowired
    private Producer producer;

    @Autowired
    private EstimateServiceValidator serviceValidator;

    @Autowired
    private EnrichmentService enrichmentService;

    @Autowired
    private EstimateRepository estimateRepository;

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private ServiceRequestRepository repository;

    @Autowired
    private ObjectMapper mapper;


    /**
     * Create Estimate by validating the details, enriched , update the workflow
     * and finally pushed to kafka to persist in postgres DB.
     *
     * @param request
     * @return
     */
    public EstimateRequest createEstimate(EstimateRequest request) {
        serviceValidator.validateCreateEstimate(request);
        enrichmentService.enrichCreateEstimate(request);
        workflowService.updateWorkflowStatus(request);
        producer.push(serviceConfiguration.getSaveEstimateTopic(), request);
        return request;
    }

    /**
     * Search Estimate based on given search criteria
     *
     * @param requestInfoWrapper
     * @param searchCriteria
     * @return
     */
    public List<Estimate> searchEstimate(RequestInfoWrapper requestInfoWrapper, EstimateSearchCriteria searchCriteria) {
        serviceValidator.validateSearchEstimate(requestInfoWrapper, searchCriteria);
        enrichmentService.enrichSearchEstimate(requestInfoWrapper.getRequestInfo(), searchCriteria);

        List<Estimate> estimateList = estimateRepository.getEstimate(searchCriteria);

        List<EstimateRequest> estimateRequestList = new LinkedList<>();
        for (Estimate estimate : estimateList) {
            EstimateRequest estimateRequest = EstimateRequest.builder().estimate(estimate).build();
            estimateRequestList.add(estimateRequest);
        }

        return estimateList;
    }

    /**
     * Except Date of Proposal, everything will be editable.
     *
     * @param request
     * @return
     */
    public EstimateRequest updateEstimate(EstimateRequest request) {
        serviceValidator.validateUpdateEstimate(request);
        enrichmentService.enrichUpdateEstimate(request);
        workflowService.updateWorkflowStatus(request);
        producer.push(serviceConfiguration.getUpdateEstimateTopic(), request);
        return request;
    }

    public void elasticPush(EstimateRequest request) {
        ProcessInstanceResponse processInstanceResponse = null;
        StringBuilder searchUrl = workflowService.getprocessInstanceSearchURL(request.getEstimate().getTenantId(), request.getEstimate().getEstimateNumber());
        Object result = repository.fetchResult(searchUrl, request.getRequestInfo());
        try {
            processInstanceResponse = mapper.convertValue(result, ProcessInstanceResponse.class);
        } catch (IllegalArgumentException e) {
            throw new CustomException("PARSING ERROR", "Failed to parse response of workflow processInstance search");
        }

        List<ProcessInstance> processInstances = processInstanceResponse.getProcessInstances();
        Map<String, EstimateRequestWorkflow> businessIdToWorkflow = workflowService.getWorkflow(processInstances);
        request.setWorkflow(businessIdToWorkflow.get(request.getEstimate().getEstimateNumber()));
        request.getEstimate().setAdditionalDetails(processInstances.get(0));

        producer.push(serviceConfiguration.getEstimateInboxTopic(), request);
    }
}
