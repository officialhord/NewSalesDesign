package Sales;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnector {

    public static Connection ConnectDb() {

        try {
            Class.forName("org.sqlite.JDBC");

            Connection conn = DriverManager.getConnection("jdbc:sqlite:SalesDb.db");

            return conn;

        } catch (Exception e) {


            return null;
        }
    }


}
