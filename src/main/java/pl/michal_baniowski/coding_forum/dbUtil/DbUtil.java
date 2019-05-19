package pl.michal_baniowski.coding_forum.dbUtil;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbUtil {
    private static DbUtil dbUtil;
    private  DataSource dataSource;

    private DbUtil(){
        try{
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/coding_forum");
        }catch (NamingException e){
            e.printStackTrace();
        }
    }
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }


     public static DbUtil getInstance(){
        if (dbUtil == null){
            dbUtil = new DbUtil();
        }
        return dbUtil;
    }
}
