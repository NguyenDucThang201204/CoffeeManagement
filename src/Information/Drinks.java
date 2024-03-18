package Information;

import java.io.Serializable;

public class Drinks implements Serializable, Comparable<Drinks> {

    private String drinkCode;
    private String name;

    public Drinks(String drinkCode, String name) {
        this.drinkCode = drinkCode;
        this.name = name;
    }

    public String getDrinkCode() {
        return drinkCode;
    }

    public void setDrinkCode(String drinkCode) {
        this.drinkCode = drinkCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "drinkCode=" + drinkCode + ", name=" + name ;
    }

    @Override
    public int compareTo(Drinks o) {
        if (this.getName().compareTo(o.getName()) > 0) {
            return 1;
        } else if (this.getName().compareTo(o.getName()) < 0) {
            return -1;
        } else {
            return 0;
        }
    }

}
