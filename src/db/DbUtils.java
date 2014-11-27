package db;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DbUtils
{
   private static Logger logger = Logger.getLogger("DbUtils");

   private DbUtils()
   {
   }


   public static void printAllSqlExceptions(SQLException e)
   {
      for (; e != null; e = e.getNextException()) {
         /*
         System.err.println("---------- SqlException ----------");
         System.err.println("Message:    " + e.getMessage());
         System.err.println("SQL State:  " + e.getSQLState());
         System.err.println("Error Code: " + e.getErrorCode());*/
         logger.log(Level.ALL,"---------- SqlException ----------");
         logger.log(Level.ALL,"Message:    " + e.getMessage());
         logger.log(Level.ALL,"SQL State:  " + e.getSQLState());
         logger.log(Level.ALL,"Error Code: " + e.getErrorCode());
      }
   }


   public static void closeDbResource(final AutoCloseable resource)
   {
      if (resource != null) {
         try {
            resource.close();
         } catch (final SQLException e) {
            DbUtils.printAllSqlExceptions(e);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }

}
