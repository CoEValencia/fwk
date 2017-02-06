package jaxb;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by vbolinch on 06/02/2017.
 */
@XmlRootElement
public class Pojo {

    private String foo;
    private int boo;
    private double too;

    public Pojo(){
    }

    public String getFoo() {
        return foo;
    }

    public Pojo setFoo(String foo) {
        this.foo = foo;
        return this;
    }

    public int getBoo() {
        return boo;
    }

    public Pojo setBoo(int boo) {
        this.boo = boo;
        return this;
    }

    public double getToo() {
        return too;
    }

    public Pojo setToo(double too) {
        this.too = too;
        return this;
    }
}
