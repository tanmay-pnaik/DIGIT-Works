package org.egov.service;

import digit.models.coremodels.AuditDetails;
import digit.models.coremodels.RequestInfoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.apache.commons.lang.StringUtils;
import org.egov.web.models.*;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.util.ResponseInfoFactory;
import org.egov.validator.AttendanceServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AttendanceService {
    @Autowired
    private AttendanceServiceValidator attendanceServiceValidator;

    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    /**
     * Create Attendance register
     *
     * @param attendanceRegisterRequest
     * @return
     */
    public AttendanceRegisterResponse createAttendanceRegister(AttendanceRegisterRequest attendanceRegisterRequest) {
        //TODO Returning Dummy Response
        AttendanceRegister attendanceRegister = attendanceRegisterRequest.getAttendanceRegister();
        createDummyRegisterForProvidedValues(attendanceRegister);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(attendanceRegisterRequest.getRequestInfo(), true);
        AttendanceRegisterResponse attendanceRegisterResponse = AttendanceRegisterResponse.builder().responseInfo(responseInfo).attendanceRegister(Collections.singletonList(attendanceRegister)).build();
        return attendanceRegisterResponse;
    }

    /**
     * Search attendace register based on given search criteria
     *
     * @param requestInfo
     * @param searchCriteria
     * @return
     */
    public AttendanceRegisterResponse searchAttendanceRegister(RequestInfo requestInfo, AttendanceRegisterSearchCriteria searchCriteria) {
        //TODO Returning Dummy Response
        AttendanceRegister attendanceRegister = searchDummyRegisterForProvidedCriteria(searchCriteria);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(requestInfo, true);
        AttendanceRegisterResponse attendanceRegisterResponse = AttendanceRegisterResponse.builder().responseInfo(responseInfo).attendanceRegister(Collections.singletonList(attendanceRegister)).build();
        return attendanceRegisterResponse;
    }

    /**
     * Update the given attendance register details
     *
     * @param attendanceRegisterRequest
     * @return
     */
    public AttendanceRegisterResponse updateAttendanceRegister(AttendanceRegisterRequest attendanceRegisterRequest) {
        //TODO Returning Dummy Response
        AttendanceRegister attendanceRegister = attendanceRegisterRequest.getAttendanceRegister();
        updateDummyRegisterWithProvidedValues(attendanceRegister);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(attendanceRegisterRequest.getRequestInfo(), true);
        AttendanceRegisterResponse attendanceRegisterResponse = AttendanceRegisterResponse.builder().responseInfo(responseInfo).attendanceRegister(Collections.singletonList(attendanceRegister)).build();
        return attendanceRegisterResponse;
    }

    private void createDummyRegisterForProvidedValues(AttendanceRegister attendanceRegister) {
        attendanceRegister.setRegisterNumber("REG/2022-23/001");
        attendanceRegister.setAuditDetails(addAuditDetails());
    }

    private void updateDummyRegisterWithProvidedValues(AttendanceRegister attendanceRegister) {
        attendanceRegister.setId(UUID.randomUUID());
        attendanceRegister.setRegisterNumber("REG/2022-23/001");
        attendanceRegister.setTenantId("pb.amritsar");
        StaffPermission staffPermission = getDummyStaffPermissionObj();
        IndividualEntry individualEntry = getIndividualEntryObj();
        attendanceRegister.setStaff(Collections.singletonList(staffPermission));
        attendanceRegister.setAttendees(Collections.singletonList(individualEntry));
        attendanceRegister.setAuditDetails(addAuditDetails());
    }

    private AttendanceRegister searchDummyRegisterForProvidedCriteria(AttendanceRegisterSearchCriteria searchCriteria) {

        AttendanceRegister attendanceRegister = new AttendanceRegister();
        StaffPermission staffPermission = getDummyStaffPermissionObj();
        IndividualEntry individualEntry = getIndividualEntryObj();

        if(!StringUtils.isEmpty(searchCriteria.getId())){
            attendanceRegister.setId(UUID.fromString(searchCriteria.getId()));
            staffPermission.setRegisterId(searchCriteria.getId());
            individualEntry.setRegisterId(UUID.fromString(searchCriteria.getId()));
        }else{
            UUID uuid = UUID.randomUUID();
            attendanceRegister.setId(uuid);
            staffPermission.setRegisterId(uuid.toString());
            individualEntry.setRegisterId(uuid);
        }

        if(!StringUtils.isEmpty(searchCriteria.getRegisterNumber())){
            attendanceRegister.setRegisterNumber(searchCriteria.getRegisterNumber());
        }else{
            attendanceRegister.setRegisterNumber("REG/2022-23/001");
        }

        if(!StringUtils.isEmpty(searchCriteria.getTenantId())){
            attendanceRegister.setTenantId(searchCriteria.getTenantId());
        }else{
            attendanceRegister.setTenantId("DUMMY-TId");
        }

        if(!StringUtils.isEmpty(searchCriteria.getName())){
            attendanceRegister.setName(searchCriteria.getName());
        }else{
            attendanceRegister.setName("DUMMY-Name");
        }

        if(searchCriteria.getFromDate() != null){
            attendanceRegister.setStartDate(searchCriteria.getFromDate().doubleValue());
        }else{
            attendanceRegister.setStartDate(1.665497225E12);
        }

        if(searchCriteria.getToDate() != null){
            attendanceRegister.setEndDate(searchCriteria.getToDate());
        }else{
            attendanceRegister.setEndDate(1.665497271E12);
        }

        if(searchCriteria.getStatus() != null){
            attendanceRegister.setStatus(searchCriteria.getStatus());
        }else{
            attendanceRegister.setStatus(Status.ACTIVE);
        }

        attendanceRegister.setStaff(Collections.singletonList(staffPermission));
        attendanceRegister.setAttendees(Collections.singletonList(individualEntry));
        attendanceRegister.setAuditDetails(addAuditDetails());

        return attendanceRegister;
    }

    private IndividualEntry getIndividualEntryObj() {
        IndividualEntry individualEntry = IndividualEntry.builder().build();
        individualEntry.setId(UUID.randomUUID());
        individualEntry.setEnrollmentDate(Double.MAX_VALUE);
        individualEntry.setAuditDetails(addAuditDetails());
        return individualEntry;
    }

    private StaffPermission getDummyStaffPermissionObj() {
        StaffPermission staffPermission = StaffPermission.builder().build();
        staffPermission.setId(UUID.randomUUID());
        staffPermission.setUserId("23323");
        PermissionLevel level = PermissionLevel.fromValue("VIEW");
        List<PermissionLevel> lst = new ArrayList<>();
        lst.add(level);
        staffPermission.setPermissionLevels(lst);
        staffPermission.setDenrollmentDate(Double.MIN_VALUE);
        staffPermission.setAuditDetails(addAuditDetails());
        return staffPermission;
    }

    private AuditDetails addAuditDetails() {
        return AuditDetails.builder().createdBy("Tester").lastModifiedBy("Tester").createdTime(System.currentTimeMillis()).lastModifiedTime(System.currentTimeMillis()).build();

    }
}
