package Main;

import management.CoffeeManagement;
import utilities.Utils;

public class Menu {

    public static void main(String[] args) {
        boolean flag = true;
        CoffeeManagement com = new CoffeeManagement();

        do {
            System.out.println("MENU");
            System.out.println("1) Manage ingredients ");
            System.out.println("2) Manage beverage recipes ");
            System.out.println("3) Dispensing beverages");
            System.out.println("4) Report");
            System.out.println("5) Store data to files");
            System.out.println("6) Others Quit");
            int choice = Utils.getInt("Enter a number from 1 to 6: ", 1, 6);
            switch (choice) {
                case 1:
                    com.MenuIngredients();
                    break;
                case 2:
                    com.beverageRecipesMenu();
                    break;
                case 3:
                    com.DispensingBeverages();
                    break;
                case 4:
                    com.Report();
                    break;
                case 5:
                    com.StoreData();
                    break;
                case 6:
                    if (com.checkOut()) {
                        System.out.println("Thanks for using");
                        flag = false;
                        break;
                    }
            }
        } while (flag);

    }

}
