package com.hand;


import com.hand.service.CommonService;
import com.hand.util.ApplicationUtil;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context=ApplicationUtil.startContext();
        CommonService commonService= (CommonService) context.getBean("commonService");
        List<Map<String, Object>> users=commonService.selectOracleUsers();
        try {
            File gitlabFile = new File("src/main/resources/oracleToGitlab.txt");
            commonService.insertUsersToGitlab(users,gitlabFile);
            File rdcFile = new File("src/main/resources/oralceToRdc.txt");
            commonService.insertUsersToRdc(users,rdcFile);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
