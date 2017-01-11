package com.hand.service.impl;

import com.hand.annotation.DataSource;
import com.hand.mapper.UsersMapper;
import com.hand.service.CommonService;
import com.hand.util.ApplicationUtil;
import com.hand.util.DataSourceConst;
import com.hand.util.DataSourceContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pakchoi on 2017/1/10.
 */

@Service("commonService")

public class CommonServiceImpl implements CommonService {

    /**
     * 查询oralce数据库的用户信息
     * @return
     */
    @DataSource(DataSourceConst.ORACLE)
    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public List<Map<String, Object>> selectOracleUsers() {
        UsersMapper usersMapper= (UsersMapper) ApplicationUtil.getBean(UsersMapper.class);
        return usersMapper.getUser();
    }

    /**
     * 将用户信息插入mysql的gitlab数据库中
     * @return
     */
    @DataSource(DataSourceConst.GITLAB)
    @Transactional
    public void insertUsersToGitlab(List<Map<String, Object>> users,File file)throws Exception{
        UsersMapper usersMapper= (UsersMapper) ApplicationUtil.getBean(UsersMapper.class);
        Map<String,String> resultMap=resolveMappingConfigFile(users,file);

        String table="users";
        String column=resultMap.get("columns");
        String vals=resultMap.get("vals");

        usersMapper.insertUsers(table,column,vals);
    }

    /**
     * 将用户信息插入mysql的zentao数据库中
     * @return
     */
    @DataSource(DataSourceConst.ZENTAO)
    @Transactional
    public void insertUsersToRdc(List<Map<String, Object>> users,File file)throws Exception{
        UsersMapper usersMapper= (UsersMapper) ApplicationUtil.getBean(UsersMapper.class);
        Map<String,String> resultMap=resolveMappingConfigFile(users,file);
        
        String table="zt_user";
        String column=resultMap.get("columns");
        String vals=resultMap.get("vals");
    
        usersMapper.insertUsers(table,column,vals);
        
    }

    
    public Map<String,String> resolveMappingConfigFile(List<Map<String, Object>> users,File file) throws Exception{
        Map<String,String> resultMap=new HashMap<String,String>();
        if(file.isFile() && file.exists()){
            System.out.println("开始解析文件...");
            InputStreamReader read = new InputStreamReader(new FileInputStream(file),"UTF-8");
            BufferedReader bufferedReader = new BufferedReader(read);
            String line = null;
            //将映射关系存入内存
            List<Map<String,String>> maps=new ArrayList<Map<String, String>>();
            while((line = bufferedReader.readLine()) != null){
                System.out.println(line);
                String [] key=line.split("\\s+");
                Map<String,String> m=new HashMap<String, String>();
                m.put("oracle",key[0]);
                m.put("mysql",key[1]);
                maps.add(m);
            }
            read.close();
            //拼接sql
            StringBuilder columns=new StringBuilder();
            StringBuilder vals=new StringBuilder();
            //拼接列
            for(Map key:maps){
                columns.append(key.get("mysql"));
                columns.append(",");
            }
           
            System.out.println(columns.lastIndexOf(","));
            columns.deleteCharAt(columns.lastIndexOf(","));
            System.out.println(columns);
            for (Map user:users){
                vals.append("(");
                for(Map key:maps){
                    Object obj=user.get(key.get("oracle"));
                    if(obj!=null){
                        vals.append("'"+obj+"'");
                    }else{
                       vals.append("''"); 
                    }
                    vals.append(",");
                }
                vals.deleteCharAt(vals.lastIndexOf(","));
                vals.append("),");
            }
            vals.deleteCharAt(vals.lastIndexOf(","));
            resultMap.put("columns",columns.toString());
            resultMap.put("vals",vals.toString());
        }else{
            System.out.println("文件不存在!");
        }
        return resultMap;
    }
}
