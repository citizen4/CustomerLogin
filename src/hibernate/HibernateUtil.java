package hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class HibernateUtil
{

    private static SessionFactory sessionFactory;
    //private static final EntityManagerFactory entityManagerFactory;


    static {

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Foo");
            e.printStackTrace();
        }

        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }


    /*
    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("myapp");
            System.out.println("Entity Manager Test.............." + entityManagerFactory);
        } catch (Throwable ex) {

            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);

        }
    }*/

    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    /*
    public static EntityManagerFactory getEntityManagerFactory()
    {
        return entityManagerFactory;
    }*/
}