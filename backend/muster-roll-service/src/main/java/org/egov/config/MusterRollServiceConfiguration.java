package org.egov.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.tracer.config.TracerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Component
@Data
@Import({TracerConfiguration.class})
@NoArgsConstructor
@AllArgsConstructor
public class MusterRollServiceConfiguration {

    @Value("${app.timezone}")
    private String timeZone;

    //MDMS
    @Value("${egov.mdms.host}")
    private String mdmsHost;
    @Value("${egov.mdms.search.endpoint}")
    private String mdmsEndPoint;

    //Idgen Config
    @Value("${egov.idgen.host}")
    private String idGenHost;
    @Value("${egov.idgen.path}")
    private String idGenPath;

    //Idgen format
    @Value("${egov.idgen.musterroll.number.name}")
    private String idgenMusterRollNumberName;
    @Value("${egov.idgen.musterroll.number.format}")
    private String idgenMusterRollNumberFormat;

    //Workflow config
    @Value("${musterroll.workflow.module.name}")
    private String musterRollWFModuleName;
    @Value("${musterroll.workflow.business.service}")
    private String musterRollWFBusinessService;
    @Value("${egov.workflow.host}")
    private String wfHost;
    @Value("${egov.workflow.transition.path}")
    private String wfTransitionPath;
    @Value("${egov.workflow.businessservice.search.path}")
    private String wfBusinessServiceSearchPath;
    @Value("${egov.workflow.processinstance.search.path}")
    private String wfProcessInstanceSearchPath;

    //Topic
    @Value("${musterroll.kafka.create.topic}")
    private String saveMusterRollTopic;
    @Value("${musterroll.kafka.update.topic}")
    private String updateMusterRollTopic;

    //Attendance log service
    @Value("${works.attendance.log.host}")
    private String attendanceLogHost;
    @Value("${works.attendance.log.search.endpoint}")
    private String attendanceLogEndpoint;

    @PostConstruct
    public void initialize() {
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
    }

}
