package org.egov.works.config;

import lombok.*;
import org.egov.tracer.config.TracerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Import({TracerConfiguration.class})
@Component
public class LOIConfiguration {

    //Idgen Config
    @Value("${egov.idgen.host}")
    private String idGenHost;

    @Value("${egov.idgen.path}")
    private String idGenPath;

    //MDMS
    @Value("${egov.mdms.host}")
    private String mdmsHost;

    @Value("${egov.mdms.search.endpoint}")
    private String mdmsEndPoint;

    //id format
    @Value("${egov.idgen.loi.number.name}")
    private String idgenLoiNumberName;

    @Value("${egov.idgen.loi.number.format}")
    private String idgenLoieNumberFormat;

    //Topic
    @Value("${loi.kafka.create.topic}")
    private String saveEstimateTopic;

    @Value("${loi.kafka.update.topic}")
    private String updateEstimateTopic;

    //HRMS
    @Value("${egov.hrms.host}")
    private String hrmsHost;

    @Value("${egov.hrms.search.endpoint}")
    private String hrmsEndPoint;

}