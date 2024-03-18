package Information;

import java.io.Serializable;
import java.util.HashMap;

public class MenuCoffee implements Serializable, Comparable<MenuCoffee> {

    private String menuCode;
    private String menuName;
    private HashMap<Ingredients, Integer> recipeIngredients;

    public MenuCoffee(String code, String name) {
        this.menuCode = code;
        this.menuName = name;
    }

    public MenuCoffee() {
        recipeIngredients = new HashMap<>();
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public HashMap<Ingredients, Integer> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(HashMap<Ingredients, Integer> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }


    @Override
    public int compareTo(MenuCoffee o) {
        if (this.getMenuName().compareTo(o.getMenuName()) > 0) {
            return 1;
        } else if (this.getMenuName().compareTo(o.getMenuName()) < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "menu Code=" + menuCode + ", menu Name=" + menuName + ", List of Ingredients=" + recipeIngredients.toString();
    }
    

}
