package cn.lenovo.eleccal.model;

import java.io.Serializable;

/**
 * Created by Aaron on 2017/1/9.
 */

public class User implements Serializable{

    private String name;
    private float last_data;
    private float current_data;
    private float air_fee;
    private float should_pay;

    public User(){
        this.name="";
        this.last_data=0;
        this.current_data=0;
        this.air_fee=0;
        this.should_pay=0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLast_data() {
        return last_data;
    }

    public void setLast_data(float last_data) {
        this.last_data = last_data;
    }

    public float getCurrent_data() {
        return current_data;
    }

    public void setCurrent_data(float current_data) {
        this.current_data = current_data;
    }

    public float getAir_fee() {
        return air_fee;
    }

    public void setAir_fee(float air_fee) {
        this.air_fee = air_fee;
    }

    public float getShould_pay() {
        return should_pay;
    }

    public void setShould_pay(float should_pay) {
        this.should_pay = should_pay;
    }
}
