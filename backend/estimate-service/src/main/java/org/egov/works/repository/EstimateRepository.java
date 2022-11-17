package org.egov.works.repository;


import org.egov.works.repository.rowmapper.EstimateQueryBuilder;
import org.egov.works.repository.rowmapper.EstimateRowMapper;
import org.egov.works.web.models.Estimate;
import org.egov.works.web.models.EstimateSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EstimateRepository {

    @Autowired
    private EstimateRowMapper rowMapper;

    @Autowired
    private EstimateQueryBuilder queryBuilder;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * @param searchCriteria
     * @return
     */
    public List<Estimate> getEstimate(EstimateSearchCriteria searchCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getEstimateQuery(searchCriteria, preparedStmtList);
        List<Estimate> estimateList = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        return estimateList;
    }


    /**
     * @param searchCriteria
     * @return count of estimates based on given search criteria
     */
    public Integer getEstimatesCount(EstimateSearchCriteria searchCriteria) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getEstimatesCountQuery(searchCriteria, preparedStmtList);
        List<Estimate> estimateList = jdbcTemplate.query(query, rowMapper, preparedStmtList.toArray());
        if (estimateList != null && !estimateList.isEmpty()) {
            return estimateList.size();
        }
        return 0;
    }
}
