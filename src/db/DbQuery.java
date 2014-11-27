package db;

public final class DbQuery
{
   //public static final String SELECT_ALL = "SELECT * FROM " + DbProp.get("table");
   public static final String SELECT_FILTER = "SELECT * FROM " + "customer_tbl" + " WHERE %s LIKE ?";
   public static final String UPDATE = "UPDATE " + "customer_tbl" + " SET %s = ? WHERE ID = ?";
   public static final String DELETE = "DELETE FROM " + "customer_tbl" + " WHERE ID = ?";
   public static final String INSERT = "INSERT INTO " + "customer_tbl" + " DEFAULT VALUES";

}
