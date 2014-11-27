package db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/*

CREATE TABLE "customer_tbl" ("ID" INTEGER PRIMARY KEY  AUTOINCREMENT , "FIRSTNAME" VARCHAR NOT NULL  DEFAULT "",
"LASTNAME" VARCHAR NOT NULL  DEFAULT "", "USERNAME" VARCHAR NOT NULL  DEFAULT "", "PASSWORD" VARCHAR NOT NULL  DEFAULT "",
"EMAIL" VARCHAR NOT NULL  DEFAULT "")
*/

public final class DbManager
{
   private static Vector<String> fieldNames = null;
   private static Vector<String> typeNames = null;
   private static int numberOfFields = 0;

   private DbManager()
   {
   }

   public static void init()
   {
      if (fieldNames == null) {
         getTableMetadata();
      }
   }

   public static Vector<String> getFieldNames()
   {
      return fieldNames;
   }

   public static Vector<String> getTypeNames()
   {
      return typeNames;
   }


   private static void getTableMetadata()
   {
      fieldNames = new Vector<>();
      typeNames = new Vector<>();

      Connection connection = null;
      ResultSet resultSet = null;
      DatabaseMetaData metadata = null;

      try {
         connection = DbConnect.getConnection();
         metadata = connection.getMetaData();
         resultSet = metadata.getColumns(null, null, "customer_tbl", null);

         while (resultSet.next()) {
            String field = resultSet.getString("COLUMN_NAME");
            String type = resultSet.getString("TYPE_NAME").toUpperCase();
            fieldNames.add(field);
            typeNames.add(type);
         }

         numberOfFields = fieldNames.size();

      } catch (SQLException e) {
         DbUtils.printAllSqlExceptions(e);
      } finally {
         DbUtils.closeDbResource(resultSet);
      }
   }

   public static Vector<Vector<String>> findAllAddresses()
   {
      //TODO: make it field independent
      return filterAddresses("NACHNAME", "");
   }

   public static int newDataSet()
   {
      return execUpdateQuery(DbQuery.INSERT);
   }

   public static int deleteDataSet(final String id)
   {
      return execUpdateQuery(DbQuery.DELETE, id);
   }

   public static int updateDataSet(final String id, final String field, final String value)
   {
      String query = String.format(DbQuery.UPDATE, field);
      return execUpdateQuery(query, value, id);
   }

   public static Vector<Vector<String>> filterAddresses(final String field, final String filter)
   {
      Vector<Vector<String>> result = new Vector<>();
      Vector<String> row;

      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;

      String query = String.format(DbQuery.SELECT_FILTER, field);

      try {
         preparedStatement = DbConnect.getConnection().prepareStatement(query);
         preparedStatement.setString(1, filter + "%");
         resultSet = preparedStatement.executeQuery();

         while (resultSet.next()) {
            row = new Vector<>();
            for (int i = 0; i < numberOfFields; i++) {
               //Only the primary key is of type integer!
               if (typeNames.get(i).equals("INTEGER")) {
                  row.add(Integer.toString(resultSet.getInt(fieldNames.get(i))));
               } else {
                  row.add(resultSet.getString(fieldNames.get(i)));
               }
            }
            result.add(row);
         }

      } catch (SQLException e) {
         DbUtils.printAllSqlExceptions(e);
      } finally {
         DbUtils.closeDbResource(resultSet);
         DbUtils.closeDbResource(preparedStatement);
      }

      return result;
   }


   private static int execUpdateQuery(final String query, final String... args)
   {
      int result = 0;
      PreparedStatement preparedStatement = null;

      try {
         preparedStatement = DbConnect.getConnection().prepareStatement(query);
         for (int i = 0; i < args.length; i++) {
            preparedStatement.setString(i + 1, args[i]);
         }
         result = preparedStatement.executeUpdate();
      } catch (SQLException e) {
         DbUtils.printAllSqlExceptions(e);
      } finally {
         DbUtils.closeDbResource(preparedStatement);
      }

      return result;
   }

}
