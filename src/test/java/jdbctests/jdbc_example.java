package jdbctests;

import org.testng.annotations.Test;

import java.sql.*;

public class jdbc_example {
    String dbUrl="jdbc:oracle:thin:@54.224.194.204:1521:xe";
    String dbUsername="hr";
    String dbPassword="hr";

    @Test
    public void test1() throws SQLException {
        //create a connection
        Connection connection= DriverManager.getConnection(dbUrl,dbUsername,dbPassword);


        //create statement object
        Statement statement= connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

        //run query and get the result in resultset object

//First query
        ResultSet resultSet= statement.executeQuery("Select * from departments");

        //how to find how many rows we have for the query
        //go to last row

        resultSet.last();

        //get the row count
        int rowCount= resultSet.getRow();

        System.out.println(rowCount);
      //go to before first row
        resultSet.beforeFirst();

        while(resultSet.next()){
            System.out.println(resultSet.getString(2));
        }
//Second query
        resultSet= statement.executeQuery("Select * from regions");
        while(resultSet.next()){
            System.out.println(resultSet.getString(2));
        }

        resultSet.close();
        statement.close();
        connection.close();
    }

    @Test
    public void MetaDataExample() throws SQLException {

        Connection connection= DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //create statement object
        Statement statement= connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

        //run query and get the result in resultset object
        ResultSet resultSet= statement.executeQuery("Select * from departments");

        //get the database related data inside dbMetaData object

        DatabaseMetaData dbMetaData= connection.getMetaData();

        System.out.println("dbMetaData.getUserName() = " + dbMetaData.getUserName());
        System.out.println("dbMetaData.getDatabaseProductName() = " + dbMetaData.getDatabaseProductName());
        System.out.println(dbMetaData.getDatabaseMajorVersion());
        System.out.println("dbMetaData.getDriverName() = " + dbMetaData.getDriverName());
        System.out.println("dbMetaData.getDriverVersion() = " + dbMetaData.getDriverVersion());

       // get the resultset OBJECT METHODS
        ResultSetMetaData resultSetMetaData= resultSet.getMetaData();

        int columnCount = resultSetMetaData.getColumnCount();
        System.out.println(columnCount);

        System.out.println(resultSetMetaData.getColumnName(1));
        System.out.println(resultSetMetaData.getColumnName(2));

        //print all the column names dynamically

        for (int i = 1; i <=resultSetMetaData.getColumnCount(); i++) {
            System.out.println(resultSetMetaData.getColumnName(i));
        }


        resultSet.close();
        statement.close();
        connection.close();
    }
}
