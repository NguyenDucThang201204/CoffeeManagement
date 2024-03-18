package Information;

import java.io.Serializable;
import java.util.HashMap;

public class Order implements Serializable{
        private String orderCode;
        private String date;
        HashMap<Drinks,Integer> orderMap = new HashMap<>();

    public Order(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public HashMap<Drinks, Integer> getOrderMap() {
        return orderMap;
    }

    public void setOrderMap(HashMap<Drinks, Integer> orderMap) {
        this.orderMap = orderMap;
    }

    @Override
    public String toString() {
        return "Order{" + "orderCode=" + orderCode + ", orderMap=" + orderMap + '}';
    }


    }
        
        
        

