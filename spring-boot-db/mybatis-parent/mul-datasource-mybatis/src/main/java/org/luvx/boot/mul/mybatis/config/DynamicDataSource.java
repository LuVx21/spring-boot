package org.luvx.boot.mul.mybatis.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DSTypeContainer.getDataSourceType();
    }
}