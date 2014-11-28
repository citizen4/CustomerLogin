package bean;


import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class TestBean implements Serializable
{
    private String foo = "BAR";


    public TestBean()
    {
    }

    public String getFoo()
    {
        return foo;
    }

    public void setFoo(String foo)
    {
        this.foo = foo;
    }
}
