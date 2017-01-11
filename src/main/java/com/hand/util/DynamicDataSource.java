package com.hand.util;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 建立动态数据源
 * Created by jaywoods on 2017/1/10.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSourceType();
    }
}
