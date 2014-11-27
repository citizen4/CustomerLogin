package db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DbConnect
{
   //static reference to itself
   private static final DbConnect INSTANCE = new DbConnect();
   //private static final String URL = "jdbc:sqlite::resource:";//DbProp.get("url");
   private static final String URL = "jdbc:sqlite:";//DbProp.get("url");
   private static final String DB = "C:/java3/sqlite.db";//DbProp.get("db");
   //private static final String USER = DbProp.get("username");
   //private static final String PASSWORD = DbProp.get("password");


   //private constructor
   private DbConnect()
   {
   }

   public static synchronized Connection getConnection() throws SQLException
   {
      //return INSTANCE.createConnection();
      try {
         Class.forName("org.sqlite.JDBC");
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      }

      return DriverManager.getConnection(URL + DB);
   }
}