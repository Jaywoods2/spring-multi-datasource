package com.hand.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by jaywoods on 2017/1/10.
 */
public interface UsersMapper {
    List<Map<String,Object>> getUser();

    void insertUsers(@Param("table") String table, @Param("column") String column,@Param("val") String val);

}
