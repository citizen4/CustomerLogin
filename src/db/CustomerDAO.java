package db;

import hibernate.HibernateUtil;
import model.Customer;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

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
        if(usernameInUse(customer.getUsername())){
            throw new RuntimeException("Username already taken");
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(customer);
            tx.commit();
            System.out.println("TX result:" + tx.wasCommitted());
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
            return false;
        } finally {
            session.close();
        }

    }

    private boolean usernameInUse(final String username)
    {
        return findCustomer(username).getId() != null;
    }

}
