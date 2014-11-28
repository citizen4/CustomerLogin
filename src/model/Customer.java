package model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "customer_tbl")
public class Customer
{
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRSTNAME")
    @NotEmpty
    private String firstName;

    @Column(name = "LASTNAME")
    @NotEmpty
    private String lastName;

    @Column(name = "EMAIL")
    @Pattern(regexp = "\\w+[.-]*\\w+@\\w+\\.\\w+", message = "Not a valid email address!")
    private String email;

    @Column(name = "USERNAME")
    @Size(min = 2, max = 32)
    private String username;

    @Column(name = "PASSWORD")
    private String passwordHash;

    @Transient
    private String password;

    @Transient
    private int customerId = -1;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getPasswordHash()
    {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash)
    {
        this.passwordHash = passwordHash;
    }

    public int getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(int customerId)
    {
        this.customerId = customerId;
    }


    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
}
