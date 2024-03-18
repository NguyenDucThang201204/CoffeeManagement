package management;

import Information.Ingredients;
import Information.MenuCoffee;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import utilities.Utils;

public class DrinksManagement implements Method {

    CoffeeManagement cofm = new CoffeeManagement();
    ArrayList<Ingredients> khoNguyenLieu = new ArrayList<>();
    ArrayList<MenuCoffee> parentMenuList = new ArrayList<>();
    HashMap<Ingredients,Integer> requestMap = new HashMap<>();
    CheckUpdateMethod cm;

    public DrinksManagement(ArrayList<MenuCoffee> parentMenuList, ArrayList<Ingredients> khoNguyenLieu) {
        cm = new CheckUpdateMethod();
        this.parentMenuList = parentMenuList;
        this.khoNguyenLieu = khoNguyenLieu;
    }

    @Override
    public void addTo() {
        String code;
        String menuCode;
        String menuName;
        int quantity;
        int available = 0;

        menuCode = Utils.getStringreg("Enter menu's code(Dxx): ", "D\\d{2}", "DO NOT LET THIS BOX EMPTY", "INPUT MUST BE THE SAME AS PATTERN");
        for (MenuCoffee in : parentMenuList) {
            if (in.getMenuCode().equals(menuCode)) {
                System.out.println("Code menu exist");
                return;
            }
        }

        menuName = Utils.getStringreg("Enter menu's name: ", "^[a-zA-Z\\s]+", "DO NOT LET THIS BOX EMPTY", "INPUT MUST BE THE SAME AS PATTERN");
        MenuCoffee mc = new MenuCoffee(menuCode, menuName);

        int choice = 1;
        boolean flag = false;
        while (choice == 1) {
            System.out.println("Available ingredients: ");
            for (Ingredients in : khoNguyenLieu) {
                System.out.println("___________________________________________________________________________________________________________________________________________________");
                System.out.println(in.toString());

            }
            code = Utils.getStringreg("Enter ingredient's code(Ixx): ", "I\\d{2}", "DO NOT LET THIS BOX EMPTY", "INPUT MUST BE THE SAME AS PATTERN");
            for (Ingredients in : khoNguyenLieu) {
                if (in.getCode().equals(code)) {
                    available++;
                    quantity = Utils.getIntNoMax("Enter ingredient's quantity to make menu: ", 1);
                    requestMap.put(in,quantity);
                    flag = true;
                    break;
                } else {
                    flag = false;
                }
            }

            if (flag == false) {
                System.out.println("the code " + code + " is not available so please add Ingredients first!!");
            }

            choice = Utils.checkYN("Do you want to continue add Ingredient(Y/N): ");
        }

        if (available >=1) {
            System.out.println("Success");
            mc.setRecipeIngredients(requestMap);
            parentMenuList.add(mc);
            cofm.setMenuList(parentMenuList);
            CoffeeManagement.dataChange = true;
        } else {
            System.err.println("Fail");
        }

    }

    @Override
    public void update() {
        HashMap<Ingredients, Integer> showIn = new HashMap<>();
        
        String menuCode = Utils.getStringreg("Enter menu's code(Dxx): ", "D\\d{2}", "DO NOT LET THIS BOX EMPTY", "INPUT MUST BE THE SAME AS PATTERN");
        MenuCoffee menuUpdate = null;
        for (MenuCoffee menuFind : parentMenuList) {
            if (menuFind.getMenuCode().equals(menuCode.trim())) {
                menuUpdate = menuFind;
                break;
            }
        }

        if (menuUpdate != null) {
             
            System.out.println("Exist menu. Here is the menu you search for: ");
            System.out.println("Menu Code: " + menuUpdate.getMenuCode());
            System.out.println("Menu Name: " + menuUpdate.getMenuName());
            System.out.println("Recipe Ingredients:");
            showIn = menuUpdate.getRecipeIngredients();
            for(Ingredients in : showIn.keySet()){
                    System.out.printf("|%-16s|%-17s|%8d|%-8s|\n", in.getCode(), in.getName(),showIn.get(in),in.getMeasure());
                }
            System.out.println("If you input nothing but only Enter, the old information won't change");
            
            
            cm.updateInforMenu(menuUpdate, menuUpdate.getRecipeIngredients());
            
            
            System.out.println("Here is menu's information after update: ");
            System.out.println("Menu Code: " + menuUpdate.getMenuCode());
            System.out.println("Menu Name: " + menuUpdate.getMenuName());
            System.out.println("Recipe Ingredients:");
            for(Ingredients in : showIn.keySet()){
                    System.out.printf("|%-16s|%-17s|%8d|%-8s|\n", in.getCode(), in.getName(),showIn.get(in),in.getMeasure());
                }
            
            
            cofm.setMenuList(parentMenuList);
            CoffeeManagement.dataChange = true;
        } else {
            System.err.println("The drink does not exist");
        }
    }

    @Override
    public void delete() {
        HashMap<Ingredients, Integer> showIn = new HashMap<>();
        String menuCode = Utils.getStringreg("Enter menu's code(Dxx): ", "D\\d{2}", "DO NOT LET THIS BOX EMPTY", "INPUT MUST BE THE SAME AS PATTERN");
        int choice;
        for (MenuCoffee menuFind : parentMenuList) {
            if (menuFind.getMenuCode().equals(menuCode.trim())) {
                System.out.println("Here is menu's information you search: ");
                
            showIn = menuFind.getRecipeIngredients();
            System.out.println("Menu Code: " + menuFind.getMenuCode());
            System.out.println("Menu Name: " + menuFind.getMenuName());
            System.out.println("Recipe Ingredients:");
            for(Ingredients in : showIn.keySet()){
                    System.out.printf("|%-16s|%-17s|%8d|%-8s|\n", in.getCode(), in.getName(),showIn.get(in),in.getMeasure());
                }
            
                choice = Utils.checkYN("Do you ready want to delete this menu(Y/N): ");
                if (choice == 1) {
                    parentMenuList.remove(menuFind);
                    cofm.setMenuList(parentMenuList);
                    System.out.println("The menu has been deleted from the list successfully!");
                    CoffeeManagement.dataChange = true;
                }
                break;
            }
        }

    }

    @Override
    public void show() {
        HashMap<Ingredients, Integer> showIn = new HashMap<>();
        if (parentMenuList.isEmpty()) {
            System.err.println("Empty list");
        } else {
            Collections.sort(parentMenuList);
            for (MenuCoffee menu : parentMenuList) {
                System.out.println("__________________________________________________________________________________________");
            System.out.println("Menu Code: " + menu.getMenuCode());
            System.out.println("Menu Name: " + menu.getMenuName());
            System.out.println("Recipe Ingredients:");
            showIn = menu.getRecipeIngredients();
            System.out.printf("|%-16s|%-17s|%-8s|%-8s|\n", "Ingredient_code", "Ingredient_Name","Quantity","Unit");           
                for(Ingredients in : showIn.keySet()){
                    System.out.printf("|%-16s|%-17s|%8d|%-8s|\n", in.getCode(), in.getName(),showIn.get(in),in.getMeasure());
                }
        }
        }
    }

}
