package com.hand.service;

import java.util.List;
import java.util.Map;
import java.io.*;


/**
 * Created by jaywoods on 2017/1/10.
 */
public interface CommonService {

    public List<Map<String,Object>> selectOracleUsers();

    public void insertUsersToGitlab(List<Map<String, Object>> users,File file)throws Exception;

    public void insertUsersToRdc(List<Map<String, Object>> users,File file)throws Exception;

}
