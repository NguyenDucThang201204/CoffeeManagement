package management;

import Information.Drinks;
import Information.Ingredients;
import Information.MenuCoffee;
import Information.Order;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import utilities.Utils;

public class OrderList {

    CoffeeManagement cofm = new CoffeeManagement();
    ArrayList<Order> orderList = new ArrayList<>();
    ArrayList<MenuCoffee> congThucDoUong = new ArrayList<>();
    ArrayList<Ingredients> khoNguyenLieu = new ArrayList<>();
    HashMap<Ingredients, Integer> recipe = new HashMap<>();
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    String formatDate = dateFormat.format(date);

    public OrderList(ArrayList<Order> orderList, ArrayList<MenuCoffee> parentMenuList, ArrayList<Ingredients> khoNguyenLieu) {
        this.congThucDoUong = parentMenuList;
        this.khoNguyenLieu = khoNguyenLieu;
        this.orderList = orderList;
    }

    public void DispenseDrink() {
        String orderCode, menuCode;
        HashMap<Drinks, Integer> showDrinks = new HashMap();
        int drinkQuantity = 0;
        boolean flag = false;
        int choiceDrink = 1;
        int count = 0;
        ArrayList<Integer> minimumDrink = new ArrayList<>();
        HashMap<Ingredients, Integer> showIn = new HashMap<>();
        HashMap<Drinks, Integer> orderMap = new HashMap<>();

        System.out.println("Available order: ");
        if (orderList.isEmpty()) {
            System.err.println("Empty");
        } else {
            for (Order order : orderList) {

                System.out.println("___________________________________________________________________________________________________________________________________________________");
                System.out.println("Menu Code: " + order.getOrderCode());
                System.out.println("Menu Date: " + order.getDate());
                System.out.println("List of Drinks:");
                showDrinks = order.getOrderMap();
                System.out.printf("|%-16s|%-30s|%-8s|\n", "Drink_code", "Drink_Name", "Quantity");
                for (Drinks dr : showDrinks.keySet()) {
                    System.out.printf("|%-16s|%-30s|%8d|\n", dr.getDrinkCode(), dr.getName(), showDrinks.get(dr));
                }
            }
        }

        orderCode = Utils.getStringreg("Enter order's code(Oxx): ", "O\\d{2}", "DO NOT LET THIS BOX EMPTY", "INPUT MUST BE THE SAME AS PATTERN");
        for (Order or : orderList) {
            if (orderCode.equalsIgnoreCase(or.getOrderCode())) {
                System.out.println("Fail because Order's code already exist");
                return;
            }
        }
        Order or = new Order(orderCode);
        or.setDate(formatDate);

        while (choiceDrink == 1) {
            System.out.println("Available menu: ");
            for (MenuCoffee menu : congThucDoUong) {
                System.out.println("__________________________________________________________________________________________");
                System.out.println("Menu Code: " + menu.getMenuCode());
                System.out.println("Menu Name: " + menu.getMenuName());
            }

            menuCode = Utils.getStringreg("Enter menu's code(Dxx): ", "D\\d{2}", "DO NOT LET THIS BOX EMPTY", "INPUT MUST BE THE SAME AS PATTERN");
            for (Drinks drinkDuplicate : or.getOrderMap().keySet()) {
                if (drinkDuplicate.getDrinkCode().equalsIgnoreCase(menuCode)) {
                    if (count >= 1) {
                        System.err.println("Error because of duplicate drink! Please add Order again!!");
                        return;
                    }
                }
            }

            for (MenuCoffee drinkRecipe : congThucDoUong) {
                if (drinkRecipe.getMenuCode().equals(menuCode.trim())) {
                    flag = true;
                    recipe = drinkRecipe.getRecipeIngredients();

                    for (Ingredients in : recipe.keySet()) {
                        String code = in.getCode();
                        Ingredients inStorage = DrinkQuantity(code);

                        if (inStorage != null) {
                            if (recipe.get(in) <= inStorage.getQuantity()) {
                                int canCreate = inStorage.getQuantity() / recipe.get(in);
                                minimumDrink.add(canCreate);
                            } else {
                                System.err.println(in.getName() + " doesn't have enough quantity");
                                return;
                            }
                        } else {
                            System.err.println("Fail");
                        }
                    }

                    int maxCanCreate = Collections.min(minimumDrink);
                    if (maxCanCreate != 1) {
                        System.out.println("Maximum Drink can create is: " + maxCanCreate);
                        drinkQuantity = Utils.getInt("Enter drink's quantity: ", 1, maxCanCreate);
                    } else if (maxCanCreate == 1) {
                        System.out.println(maxCanCreate + " is the maximum can create");
                        drinkQuantity = 1;
                    }

                    for (Ingredients in : recipe.keySet()) {
                        String code = in.getCode();
                        Ingredients inStorage = DrinkQuantity(code);
                        int index = khoNguyenLieu.indexOf(inStorage);
                        if (inStorage != null) {
                            if (recipe.get(in) <= inStorage.getQuantity()) {
                                int remain = inStorage.getQuantity() - recipe.get(in) * drinkQuantity;
                                inStorage.setQuantity(remain);
                                if (remain == 0) {
                                    inStorage.setStatus("unavailable");
                                }
                                khoNguyenLieu.set(index, inStorage);
                                cofm.setInList(khoNguyenLieu);
                            } else {
                                System.err.println(in.getName() + " doesn't have enough quantity");
                                return;
                            }
                        } else {
                            System.err.println("Fail because there is such not ingredient in the Ingredients' storage");
                            return;
                        }
                    }

                    Drinks drink = new Drinks(drinkRecipe.getMenuCode(), drinkRecipe.getMenuName());
                    orderMap.put(drink, drinkQuantity);
                    count++;
                    or.setOrderMap(orderMap);

                }
            }
            if (flag == false) {
                System.err.println("The drink does not exist");
            }
            choiceDrink = Utils.checkYN("Do you want to continue dispense Drink(Y/N): ");

        }
        orderList.add(or);
        CoffeeManagement.dataChange = true;
        System.out.println("successful");
    }

    
    public void UpdateDispenseDrink() {
        String orderCode, drinkCode;
        int remain;
        boolean flag = false;
        String choice;
        HashMap<String, Integer> UpdateMap = new HashMap<>();
        HashMap<Drinks, Integer> showDrinks = new HashMap();
        

        if (orderList.isEmpty()) {
            System.err.println("Empty");
        } else {
            for (Order order : orderList) {

                System.out.println("___________________________________________________________________________________________________________________________________________________");
                System.out.println("Menu Code: " + order.getOrderCode());
                System.out.println("Menu Date: " + order.getDate());
                System.out.println("List of Drinks:");
                showDrinks = order.getOrderMap();
                System.out.printf("|%-16s|%-30s|%-8s|\n", "Drink_code", "Drink_Name", "Quantity");
                for (Drinks dr : showDrinks.keySet()) {
                    System.out.printf("|%-16s|%-30s|%8d|\n", dr.getDrinkCode(), dr.getName(), showDrinks.get(dr));
                }
            }
        }

        orderCode = Utils.getStringreg("Enter drink's code(Oxx): ", "O\\d{2}", "DO NOT LET THIS BOX EMPTY", "INPUT MUST BE THE SAME AS PATTERN");
        for (Order or : orderList) {
            HashMap<Drinks, Integer> orderMap = new HashMap<>();
            if (orderCode.equals(or.getOrderCode())) {
                orderMap = or.getOrderMap();
                choice = Utils.getStringreg("Enter menu's code(Dxx): ", "D\\d{2}", "DO NOT LET THIS BOX EMPTY", "INPUT MUST BE THE SAME AS PATTERN");

                for (Drinks dr : orderMap.keySet()) {
                    if (choice.equalsIgnoreCase(dr.getDrinkCode())) {
                        flag = true;
                        drinkCode = dr.getDrinkCode();
                        MenuCoffee updateDrinkRecipe = MenuRecipe(drinkCode);
                        recipe = updateDrinkRecipe.getRecipeIngredients();
                        int quantityToUpdate = Utils.getIntNoMax("Enter the updated drink's quantity: ", 0);
                        if (quantityToUpdate == 0) {
                            orderMap.put(dr, quantityToUpdate);
                            CoffeeManagement.dataChange = true;
                            System.out.println("Success");
                            return;
                        }
                        int remainDrink = quantityToUpdate - orderMap.get(dr);
                        
                        
                        for (Ingredients in : recipe.keySet()) {
                            String code = in.getCode();
                            Ingredients inStorage = DrinkQuantity(code);

                            if (inStorage != null) {
                                remain = inStorage.getQuantity() - recipe.get(in) * remainDrink;
                                if (remain == 0) {
                                    inStorage.setStatus("unavailable");
                                }
                                if (remain < 0) {
                                    System.err.println(in.getName() + " doesn't have enough quantity");
                                    return;
                                }
                                UpdateMap.put(inStorage.getCode(), remain);

                            } else {
                                System.err.println("Ingredient is not found in the storage");
                                return;
                            }
                        }
                        orderMap.put(dr, quantityToUpdate);
                        

                        for (Ingredients ingredient : khoNguyenLieu) {
                            if (UpdateMap.containsKey(ingredient.getCode())) {
                                ingredient.setQuantity(UpdateMap.get(ingredient.getCode()));
                            }
                        }

                        cofm.setInList(khoNguyenLieu);
                        CoffeeManagement.dataChange = true;
                        System.out.println("Successful");
                        return;
                    }
                }
                if (flag == false) {
                    System.out.println("The drink does not exist");
                }
            }
        }
        System.err.println("The order does not exist");
    }

    public Ingredients DrinkQuantity(String code) {
        for (Ingredients inFind : khoNguyenLieu) {
            if (inFind.getCode().equals(code.trim())) {
                return inFind;
            }
        }
        return null;
    }

    public MenuCoffee MenuRecipe(String code) {
        for (MenuCoffee menuFind : congThucDoUong) {
            if (menuFind.getMenuCode().equals(code.trim())) {
                return menuFind;
            }
        }
        return null;
    }

}
