package management;

import Information.Ingredients;
import java.util.ArrayList;
import java.util.Collections;
import utilities.Utils;

public class IngredientManagement implements Method {

    CoffeeManagement cofm = new CoffeeManagement();
    ArrayList<Ingredients> khoNguyenLieu = new ArrayList<>();
    CheckUpdateMethod cm;

    public IngredientManagement(ArrayList<Ingredients> khoNguyenLieu) {
        this.khoNguyenLieu = khoNguyenLieu;
        cm = new CheckUpdateMethod();
    }

    @Override
    public void addTo() {
        String code;
        String name;
        int choice2;
        String measure="";
        int quantity;

        int choice = 1;
        while (choice == 1) {

            code = Utils.getStringreg("Enter ingredient's code(Ixx): ", "I\\d{2}", "DO NOT LET THIS BOX EMPTY", "INPUT MUST BE THE SAME AS PATTERN");
            for (Ingredients in : khoNguyenLieu) {
                if (in.getCode().equals(code)) {
                    System.out.println("Code ingredients cannot duplicate");
                    return;
                }
            }
            name = Utils.getStringreg("Enter ingredient's name: ", "^[a-zA-Z\\s]+", "DO NOT LET THIS BOX EMPTY", "INPUT MUST BE LETTERS ONLY");
            quantity = Utils.getIntNoMax("Enter ingredient's quantity: ", 1);
            choice2 = Utils.getInt("What kind of ingredient it is?\n1.Flour and fruit(g)\n2.Water(ml)\n: ", 1,2);
            switch (choice2) {
                case 1:
                    measure = "g";
                    break;
                case 2:
                    measure="ml";
                    break;
                default:
                    break;
            }
            
            Ingredients in = new Ingredients(code, name, quantity,measure);
            in.setStatus("available");
            khoNguyenLieu.add(in);
            System.out.println("Successful");
            CoffeeManagement.dataChange = true;
            choice = Utils.checkYN("Do you want to continue add Ingredient(Y/N): ");
        }
        cofm.setInList(khoNguyenLieu);

    }

    @Override
    public void update() {
        String code = Utils.getStringreg("Enter ingredient's code (Ixx): ", "I\\d{2}", "DO NOT LET THIS BOX EMPTY", "INPUT MUST BE THE SAME AS PATTERN");
        Ingredients inUpdate = null;
        for (Ingredients inFind : khoNguyenLieu) {
            if (inFind.getCode().equals(code.trim())) {
                inUpdate = inFind;
                break;
            }
        }
        if (inUpdate != null) {
            System.out.println("Exist ingredient. Here is the ingredient you search for: ");
            System.out.println(inUpdate.toString());
            System.out.println("If you input nothing but only Enter, the old information won't change");
            cm.updateInforIngredient(inUpdate);
            if(inUpdate.getQuantity()<=0){
                inUpdate.setStatus("unavailable");
            }else{
                inUpdate.setStatus("available");
            }
            System.out.println("Here is ingredient's information after update: ");
            System.out.println(inUpdate.toString());
            cofm.setInList(khoNguyenLieu);
            CoffeeManagement.dataChange = true;
        } else {
            System.err.println("Ingredient does not exist");
        }
    }

    @Override
    public void delete() {
        String code = Utils.getStringreg("Enter ingredient's code(Ixx): ", "I\\d{2}", "DO NOT LET THIS BOX EMPTY", "INPUT MUST BE THE SAME AS PATTERN");
        Ingredients inDelete = null;
        for (Ingredients inFind : khoNguyenLieu) {
            if (inFind.getCode().equals(code.trim())) {
                inDelete = inFind;
                break;
            }
        }
        if (inDelete != null) {
            System.out.println("Exist ingredient. Here is the ingredient you search for: ");
            System.out.println(inDelete.toString());
            int choice = Utils.checkYN("Do you ready want to delete this ingredient(Y/N): ");
            if (choice == 1) {
                khoNguyenLieu.remove(inDelete);
                cofm.setInList(khoNguyenLieu);
                System.out.println("The ingredient has been deleted from the list successfully!");
                CoffeeManagement.dataChange = true;
            }
        } else {
            System.err.println("Not found!");
        }
    }

    @Override
    public void show() {
        if (khoNguyenLieu.isEmpty()) {
            System.err.println("Empty list");
        } else {
            System.out.printf("|%-16s|%-17s|%-8s|%-8s|%-11s|\n", "Ingredient_code", "Ingredient_Name", "Quantity","Unit","Status");
            Collections.sort(khoNguyenLieu);
            for (Ingredients in : khoNguyenLieu) {
                System.out.println("___________________________________________________________________________________________________________________________________________________");
                System.out.println(in.toString());

            }
        }
    }

}
