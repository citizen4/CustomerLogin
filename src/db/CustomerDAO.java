package db;

import hibernate.HibernateUtil;
import model.Customer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class CustomerDAO
{
    public CustomerDAO()
    {
    }

    public Customer findCustomer(final String username)
    {
        List<Customer> customerList;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Customer.class);

        criteria.add(Restrictions.eq("username",username));

        customerList = criteria.list();
        session.close();
        return (customerList != null && customerList.size() > 0) ? customerList.get(0) : new Customer();
    }

    public boolean storeNewCustomer(final Customer customer)
    {
        return true;
    }

    /*
    public Customer findCustomer(final String username)
    {
        Customer result = null;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "SELECT ID,FIRSTNAME,LASTNAME,EMAIL,PASSWORD FROM customer_tbl WHERE USERNAME = ?";

        try {
            preparedStatement = DbConnect.getConnection().prepareStatement(query);
            preparedStatement.setString(1, username);
            //preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result = new Customer();
                result.setCustomerId(resultSet.getInt(1));
                result.setFirstName(resultSet.getString(2));
                result.setLastName(resultSet.getString(3));
                result.setEmail(resultSet.getString(4));
                result.setUsername(username);
                //result.setPassword(password);
                result.setPasswordHash(resultSet.getString(5));
                break;
            }

        } catch (SQLException e) {
            DbUtils.printAllSqlExceptions(e);
            throw new RuntimeException(e.getMessage());
        } finally {
            DbUtils.closeDbResource(resultSet);
            DbUtils.closeDbResource(preparedStatement);
        }

        return result;
    }

    public boolean storeNewCustomer(final Customer customer)
    {
        int result = 0;

        if(usernameInUse(customer.getUsername())){
            throw new RuntimeException("Username already taken");
        }

        PreparedStatement preparedStatement = null;

        String query = "INSERT INTO customer_tbl(FIRSTNAME,LASTNAME,EMAIL,USERNAME,PASSWORD) VALUES(?,?,?,?,?)";

        try {
            preparedStatement = DbConnect.getConnection().prepareStatement(query);

            preparedStatement.setString(1,customer.getFirstName());
            preparedStatement.setString(2,customer.getLastName());
            preparedStatement.setString(3,customer.getEmail());
            preparedStatement.setString(4,customer.getUsername());
            //preparedStatement.setString(5,customer.getPassword());
            preparedStatement.setString(5,customer.getPasswordHash());

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DbUtils.printAllSqlExceptions(e);
        } finally {
            DbUtils.closeDbResource(preparedStatement);
        }

        return (result == 1);
    }


    private boolean usernameInUse(final String username)
    {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT COUNT(*) FROM customer_tbl WHERE USERNAME = ?";

        try {
            preparedStatement = DbConnect.getConnection().prepareStatement(query);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) > 0;

        } catch (SQLException e) {
            DbUtils.printAllSqlExceptions(e);
            throw new RuntimeException(e.getMessage());
        } finally {
            DbUtils.closeDbResource(resultSet);
            DbUtils.closeDbResource(preparedStatement);
        }
    }*/
}
