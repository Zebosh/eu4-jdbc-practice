package jdbctests;

import org.testng.annotations.Test;
import utilities.DBUtils;

import java.util.List;
import java.util.Map;

public class dbutils_practice {
    @Test
    public void test1(){
        DBUtils.createConnection();

       List<Map<String, Object>> queryResultMap = DBUtils.getQueryResultMap("select * from employees");

        //print the result
        for (Map<String,Object>map:queryResultMap ) {
            System.out.println(map.toString());

        }

//        Map<String, Object> rowMap = DBUtils.getRowMap("select * from employees\n" +
//                "where employee_id=100;");
//
//        System.out.println(rowMap);
        DBUtils.destroy();
    }
}
