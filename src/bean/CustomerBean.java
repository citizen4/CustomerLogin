package bean;

import db.CustomerDAO;
import model.Customer;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

//import javax.enterprise.context.SessionScoped;


@Named
@javax.enterprise.context.SessionScoped
public class CustomerBean implements Serializable
{
    @Inject
    private TestBean testBean;

    private CustomerDAO customerDAO;
    private Customer customer;
    private boolean loggedIn;
    private boolean newCustomer;
    private int customerState;
    private String customerValid;


    public CustomerBean()
    {
        //Session session = HibernateUtil.getSessionFactory().openSession();
        //session.close();

        this.customerDAO = new CustomerDAO();
        this.customer = new Customer();
        this.newCustomer = false;
        this.customerState = 0;
    }

    @PostConstruct
    private void init()
    {
        this.customerValid = testBean.getFoo();
        System.out.println("Post construct:" + this.customerValid);
    }

    public boolean isNewCustomer()
    {
        return newCustomer;
    }

    public void setNewCustomer(boolean newCustomer)
    {
        this.newCustomer = newCustomer;
    }

    public int getCustomerState()
    {
        return customerState;
    }

    public void setCustomerState(int customerState)
    {
        this.customerState = customerState;
    }

    public String doValidateCustomer()
    {
        customerState = 0;
        Customer foundCustomer = null;

        try {
            foundCustomer = customerDAO.findCustomer(customer.getUsername());
            //System.out.println(foundCustomer);
        } catch (RuntimeException e) {
            addFacesMessage(e.getMessage());
        }

        //customerValid = "valid";
        if (foundCustomer.getId() != null && isPasswordCorrect(customer.getPassword(), foundCustomer.getPasswordHash())) {
            this.customer = foundCustomer;
            customerState = 2;
            return "showCustomer";
        }

        addFacesMessage("Login incorrect!");

        return "customerForm";
    }


    public String doShowRegisterCustomer()
    {
        //customerValid = "invalid";
        customerState = 1;
        return "showCustomer";
    }

    public String doSaveCustomer()
    {
        try {
            String encryptedPassword = encryptPassword(customer.getPassword());
            customer.setPasswordHash(encryptedPassword);

            if (customerDAO.storeNewCustomer(this.customer)) {
                customerState = 2;
                //customerValid = "valid";
                return "showCustomer";
            }
        } catch (RuntimeException e) {
            addFacesMessage(e.getMessage());
        }

        return "customerForm";
    }

    public String doLogout()
    {
        customerState = 0;
        //customerValid = "invalid";
        newCustomer = false;
        this.customer = new Customer();
        return "customerForm";
    }

    /*
    public String getCustomerValid()
    {
        return this.customerValid;
    }

    public void setCustomerValid(String customerValid)
    {
        this.customerValid = customerValid;
    }*

    public boolean isLoggedIn()
    {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn)
    {
        this.loggedIn = loggedIn;
    }*/

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    private boolean isPasswordCorrect(final String password, final String hash)
    {
        String salt = hash.split(":")[1];
        return hashString(password + salt).equals(hash.split(":")[0]);
    }


    private String encryptPassword(final String password)
    {
        String result;
        Random rnd = new Random();
        StringBuilder saltStringBuilder = new StringBuilder();
        //generate 4 byte random salt:
        for (int i = 0; i < 4; i++) {
            saltStringBuilder.append(Integer.toString((rnd.nextInt(255) & 0xff), 16));
        }

        result = hashString(password + saltStringBuilder.toString()) + ":" + saltStringBuilder.toString();

        if(result == null){
            throw new RuntimeException("500: Internal Server Error!");
        }

        return result;
    }


    private String hashString(final String str)
    {
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            addFacesMessage(e.getMessage());
            return null;
        }

        md.update(str.getBytes());
        byte[] dataBytes = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < dataBytes.length; i++) {
            sb.append(Integer.toString((dataBytes[i] & 0xff), 16));
        }

        return sb.toString();
    }

    private void addFacesMessage(final String msg)
    {
        FacesMessage facesMsg = new FacesMessage(msg);
        facesMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(null,facesMsg);
    }

}
