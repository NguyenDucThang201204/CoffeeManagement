package Information;

import java.io.Serializable;

public class Ingredients implements Serializable, Comparable<Ingredients> {

    private String code;
    private String name;
    private int quantity;
    private String status;
    private String measure;

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Ingredients(String code, String name, int quantity, String measure) {
        this.code = code;
        this.name = name;
        this.quantity = quantity;
        this.measure=measure;
    }

    public Ingredients(String code) {
        this.code = code;
    }

    public Ingredients() {
    }

    @Override
    public String toString() {
        return String.format("|%-16s|%-17s|%8d|%-8s|%-11s|", code, name, quantity,measure,status);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Ingredients o) {
        if (this.getName().compareTo(o.getName()) > 0) {
            return 1;
        } else if (this.getName().compareTo(o.getName()) < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
