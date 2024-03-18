package management;

import Information.Drinks;
import Information.Ingredients;
import Information.MenuCoffee;
import Information.Order;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import utilities.Utils;

public class CoffeeManagement {

    private ArrayList<Ingredients> inList;
    private ArrayList<MenuCoffee> menuList;
    private ArrayList<Order> orderList;
    static boolean dataChange;

    public ArrayList<Ingredients> getInList() {
        return inList;
    }

    public void setInList(ArrayList<Ingredients> inList) {
        this.inList = inList;
    }

    public ArrayList<MenuCoffee> getMenuList() {
        return menuList;
    }

    public void setMenuList(ArrayList<MenuCoffee> menuList) {
        this.menuList = menuList;
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }
    

    public CoffeeManagement() {
        inList = new ArrayList<>();
        menuList = new ArrayList<>();
        orderList = new ArrayList<>();

        this.loadIngredientsFromFile(inList, "Ingredients.dat");
        this.loadMenuFromFile(menuList, "Menu.dat");
        this.loadOrderFromFile(orderList, "Order.dat");

    }

    public void MenuIngredients() {
        int choice;
        boolean flag = true;
        IngredientManagement im = new IngredientManagement(inList);
        do {
            System.out.println("Ingredients MENU");
            System.out.println("1) Add an ingredient ");
            System.out.println("2) Update ingredient information ");
            System.out.println("3) Delete ingredient");
            System.out.println("4) Show all the ingredients");
            System.out.println("5) Out to Main menu");
            choice = Utils.getInt("Enter a number from 1 to 5: ", 1, 5);
            switch (choice) {
                case 1:
                    im.addTo();
                    break;
                case 2:
                    im.update();
                    break;
                case 3:
                    im.delete();
                    break;
                case 4:
                    im.show();
                    break;
                case 5:
                    System.out.println("Out to main menu");
                    flag = false;
            }
        } while (flag);

    }

    public void beverageRecipesMenu() {
        int choice;
        boolean flag = true;
        DrinksManagement dm = new DrinksManagement(menuList, inList);
        do {
            System.out.println("Beverage Recipes MENU");
            System.out.println("1) Add the drink to the menu. ");
            System.out.println("2) Update the drink information ");
            System.out.println("3) Delete the drink from the menu");
            System.out.println("4) Show all the menu");
            System.out.println("5) Out to Main menu");
            choice = Utils.getInt("Enter a number from 1 to 5: ", 1, 5);
            switch (choice) {
                case 1:
                    dm.addTo();
                    break;
                case 2:
                    dm.update();
                    break;
                case 3:
                    dm.delete();
                    break;
                case 4:
                    dm.show();
                    break;
                case 5:
                    System.out.println("Out to main menu");
                    flag = false;
            }
        } while (flag);

    }

    public void DispensingBeverages() {
        int choice;
        boolean flag=true;
        OrderList om = new OrderList(orderList, menuList, inList);
        do {
            System.out.println("Dispense Drink MENU");
            System.out.println("1) Dispensing the drink ");
            System.out.println("2) Update the dispensing drink ");
            System.out.println("3) Out to Main menu");
            choice = Utils.getInt("Enter a number from 1 to 3: ", 1, 3);
            switch (choice) {
                case 1:
                    om.DispenseDrink();
                    break;
                case 2:
                    om.UpdateDispenseDrink();
                    break;
                case 3:
                    System.out.println("Out to main menu");
                    flag = false;
            }
        } while (flag);
    }

    public void Report() {
        int choice;
        HashMap<Ingredients, Integer> showIn = new HashMap<>();
        HashMap<Drinks, Integer> showDrinks = new HashMap<>();
        ArrayList<Ingredients> availableIngredients = new ArrayList<>();
        ArrayList<MenuCoffee> drinksOutOfIngredients = new ArrayList<>();
        do {
            System.out.println("Report MENU");
            System.out.println("1) The ingredients are available ");
            System.out.println("2) The drinks for which the store is out of ingredients ");
            System.out.println("3) Show all the dispensing drink ");
            choice = Utils.getInt("Enter a number from 1 to 3: ", 1, 3);
            switch (choice) {
                case 1:
                    for (Ingredients in : inList) {
                        if (in.getStatus().equalsIgnoreCase("available")) {
                            availableIngredients.add(in);
                        }
                    }
                    for (Ingredients in : availableIngredients) {
                        System.out.println(in.toString());
                    }
                    break;
                case 2:
                    for (MenuCoffee menu : menuList) {
                        boolean ingredientsAvailable = true;
                        for (Ingredients ingredient : menu.getRecipeIngredients().keySet()) {
                            Ingredients storageIngredient = findIngredientInStorage(ingredient.getCode());
                            if (storageIngredient == null || storageIngredient.getStatus().equalsIgnoreCase("unavailable")) {
                                ingredientsAvailable = false;
                                break;
                            }
                        }
                        if (!ingredientsAvailable) {
                            drinksOutOfIngredients.add(menu);
                        }
                    }
                    if (drinksOutOfIngredients.isEmpty()) {
                        System.out.println("All drinks are available.");
                    } else {
                        System.out.println("The following drinks are out of ingredients:");
                        for (MenuCoffee menu : drinksOutOfIngredients) {
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
                    break;
                    
                case 3:
                    if (orderList.isEmpty()) {
                        System.err.println("Empty list of drinks");
                    } else {
                        for (Order order : orderList) {
                            
                            System.out.println("___________________________________________________________________________________________________________________________________________________");
                            System.out.println("Menu Code: " + order.getOrderCode());
                            System.out.println("Menu Date: " + order.getDate());
                            System.out.println("List of Drinks:");
                            showDrinks = order.getOrderMap();
                            System.out.printf("|%-16s|%-30s|%-8s|\n", "Drink_code", "Drink_Name","Quantity");           
                for(Drinks dr : showDrinks.keySet()){
                    System.out.printf("|%-16s|%-30s|%8d|\n", dr.getDrinkCode(), dr.getName(),showDrinks.get(dr));
                }    
                        }
                    }
                    break;
            }
        } while (choice < 1 || choice > 3);
    }

    private Ingredients findIngredientInStorage(String code) {
        for (Ingredients ingredient : inList) {
            if (ingredient.getCode().equals(code)) {
                return ingredient;
            }
        }
        return null;
    }

    public void StoreData() {
        this.saveIngredientsToFile(inList, "Ingredients.dat", "Successful save ingredients", "Fail");
        this.saveMenuToFile(menuList, "Menu.dat", "Successful save menu", "Fail");
        this.saveOrderToFile(orderList, "Order.dat", "Successful save order", "Fail");
        dataChange = false;
    }

    public boolean checkOut() {
        if (dataChange == true) {
            int choice1;
            choice1 = Utils.checkYN("Do you want to save data to file(Y/N): ");
            if (choice1 == 1) {
                this.saveIngredientsToFile(inList, "Ingredients.dat", "Successful save ingredients", "Fail");
                this.saveMenuToFile(menuList, "Menu.dat", "Successful save menu", "Fail");
                this.saveOrderToFile(orderList, "Order.dat", "Successful save order", "Fail");
                return true;
            }
        }
        return true;
    }

    public boolean saveIngredientsToFile(ArrayList<Ingredients> inList, String FName, String success, String error) {
        try {
            File f = new File(FName);
            FileOutputStream os = new FileOutputStream(f);
            ObjectOutputStream output = new ObjectOutputStream(os);
            for (Ingredients in : inList) {
                output.writeObject(in);
            }
            os.close();
            output.close();
            System.out.println(success);
            return true;
        } catch (Exception e) {
            System.err.println(error + ": " + e);
            return false;
        }
    }

    public boolean saveMenuToFile(ArrayList<MenuCoffee> menuList, String FName, String success, String error) {
        try {
            File f = new File(FName);
            FileOutputStream os = new FileOutputStream(f);
            ObjectOutputStream output = new ObjectOutputStream(os);
            for (MenuCoffee mn : menuList) {
                output.writeObject(mn);
            }
            os.close();
            output.close();
            System.out.println(success);
            return true;
        } catch (Exception e) {
            System.err.println(error + ": " + e);
            return false;
        }
    }

   
    public boolean saveOrderToFile(ArrayList<Order> orderList, String FName, String success, String error) {
        try {
            File f = new File(FName);
            FileOutputStream os = new FileOutputStream(f);
            ObjectOutputStream output = new ObjectOutputStream(os);
            for (Order d : orderList) {
                output.writeObject(d);
            }
            os.close();
            output.close();
            System.out.println(success);
            return true;
        } catch (Exception e) {
            System.err.println(error + ": " + e);
            return false;
        }
    }

    public boolean loadIngredientsFromFile(ArrayList<Ingredients> inList, String FName) {
        if (inList.size() > 0) {
            inList.clear();
        }
        try {
            File f = new File(FName);
            if (!f.exists()) {
                return false;
            }
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream is = new ObjectInputStream(fis);
            if (f.length() == 0) {
                System.err.println("File is empty");
            }

            boolean flag = true;
            while (flag) {
                try {
                    Ingredients in = (Ingredients) is.readObject();
                    inList.add(in);
                } catch (EOFException e) {
                    flag = false;
                }
            }

            fis.close();
            is.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + FName);
            return false;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading from file: " + FName + e);
            return false;
        } catch (NumberFormatException e) {
            System.err.println("Error parsing double value from input: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean loadMenuFromFile(ArrayList<MenuCoffee> list, String FName) {
        if (list.size() > 0) {
            list.clear();
        }
        try {
            File f = new File(FName);
            if (!f.exists()) {
                return false;
            }
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream is = new ObjectInputStream(fis);
            if (f.length() == 0) {
                System.err.println("File is empty");
            }

            boolean flag = true;
            while (flag) {
                try {
                    MenuCoffee h = (MenuCoffee) is.readObject();
                    list.add(h);
                } catch (EOFException e) {
                    flag = false;
                }
            }

            fis.close();
            is.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + FName);
            return false;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading from file: " + FName + e);
            return false;
        } catch (NumberFormatException e) {
            System.err.println("Error parsing double value from input: " + e.getMessage());
            return false;
        }
        return true;
    }

    
    public boolean loadOrderFromFile(ArrayList<Order> orderList, String FName) {
        if (orderList.size() > 0) {
            orderList.clear();
        }
        try {
            File f = new File(FName);
            if (!f.exists()) {
                return false;
            }
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream is = new ObjectInputStream(fis);
            if (f.length() == 0) {
                System.err.println("File is empty");
            }

            boolean flag = true;
            while (flag) {
                try {
                    Order h = (Order) is.readObject();
                    orderList.add(h);
                } catch (EOFException e) {
                    flag = false;
                }
            }

            fis.close();
            is.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + FName);
            return false;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading from file: " + FName + e);
            return false;
        } catch (NumberFormatException e) {
            System.err.println("Error parsing double value from input: " + e.getMessage());
            return false;
        }
        return true;
    }
}
