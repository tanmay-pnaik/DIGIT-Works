package org.egov.works.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.works.util.LetterOfIndentServiceValidator;
import org.egov.works.util.MDMSUtils;
import org.egov.works.web.models.LetterOfIndentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class LetterOfIndentService {
    @Autowired
    private MDMSUtils mdmsUtils;

    @Autowired
    private LetterOfIndentServiceValidator validator;

    @Autowired
    private ObjectMapper mapper;

    public LetterOfIndentRequest createLOI(LetterOfIndentRequest request) throws IOException {
        //Object mdmsData = mdmsUtils.mDMSCall(request.getLetterOfIndent());
        //validator.ValidateCreateLoi(request, mdmsData);


        return request;
    }
}
