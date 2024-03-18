package management;


import Information.Ingredients;
import Information.MenuCoffee;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import utilities.Utils;

public class CheckUpdateMethod {
    Scanner sc = new Scanner(System.in);

        public String getStringregIsEmpty(String welcome,String pattern,String msgreg) {
        boolean check = true;
        String result = "";
        Scanner sc = new Scanner(System.in);
        do {
            
            System.out.print(welcome);
            result = sc.nextLine();
            if(result.trim().isEmpty()){
                return result;
            }
           else if(!result.matches(pattern)){
                System.err.println(msgreg);
            } 
            else {
                check = false;
            }
        } while (check);
        return result;
    }
         
         public void updateInforIngredient(Ingredients in){
             String name;
             String quantity;
             int flag=0;
             String measure;
             
             
             name=this.getStringregIsEmpty("Enter name to update: ", "^[a-zA-Z\\s]+", "input must only letters");
            if (!name.trim().isEmpty()) {
        in.setName(name);
        flag=1;
    }
            quantity=this.getStringregIsEmpty("Enter quantity to update: ", "^[0-9]+", "input must larger than 0");
             if(!quantity.trim().isEmpty()){
                
                 in.setQuantity(Integer.parseInt(quantity));
             }
             if(in.getQuantity()==0){
                 in.setStatus("unavailable");
             }else{
                 in.setStatus("available");
             }
             
            if(flag==1){
                String choice = this.getStringregIsEmpty("What kind of ingredient it is?\n1.Flour and fruit(g)\n2.Water(ml)\n: ", "^([1-2]|)","input must be from 1 to 2 or enter nothing");
                
                if(choice.contains("1")){
                    measure = "g";
                    in.setMeasure(measure);
                } else if (choice.contains("2")){
                    measure="ml";
                    in.setMeasure(measure);
                }
            
            }
             
             
         }
         
         public void updateQuantityMenu(Ingredients in){
             String quantity;
             quantity=this.getStringregIsEmpty("Enter quantity to update: ", "^[0-9]+", "input must only numbers and larger than 0");
             if(quantity.trim().isEmpty()){
                 in.setQuantity(in.getQuantity());
             }else{
                 in.setQuantity(Integer.parseInt(quantity));
             }
         }
         
          public void updateInforMenu(MenuCoffee menu, HashMap<Ingredients,Integer> requestList){
             String name;
             int choice=1;
             
             name=this.getStringregIsEmpty("Enter name to update: ", "^[a-zA-Z\\s]+", "input must only letters");
             if(name.trim().isEmpty()){
                 menu.setMenuName(menu.getMenuName());
             }else{
                 menu.setMenuName(name);
             }
             
             while(choice==1){
              String code = Utils.getStringreg("Enter ingredient's code(Ixx): ","I\\d{2}","DO NOT LET THIS BOX EMPTY","INPUT MUST BE THE SAME AS PATTERN");
              Ingredients inUpdate = null ;
              Ingredients inIndex = null;
        
        for (Ingredients inFind : requestList.keySet()) {
                if(inFind.getCode().equals(code.trim())){
                    inIndex =inFind;
                    inUpdate = new Ingredients(inFind.getCode(),inFind.getName(),requestList.get(inFind),inFind.getMeasure());
                    break;
                }
            }
        if (inUpdate != null) {
            System.out.println("Exist ingredient. Here is the ingredient you search for: ");
            System.out.printf("|%-16s|%-17s|%8d|%-8s|\n", inUpdate.getCode(), inUpdate.getName(), inUpdate.getQuantity(),inUpdate.getMeasure());
            this.updateQuantityMenu(inUpdate);
            System.out.println("Here is ingredient's information after update: ");
            System.out.printf("|%-16s|%-17s|%8d|%-8s|\n", inUpdate.getCode(), inUpdate.getName(), inUpdate.getQuantity(),inUpdate.getMeasure());
            requestList.put(inIndex, inUpdate.getQuantity());
            menu.setRecipeIngredients(requestList);
            CoffeeManagement.dataChange=true;
        } else {
            System.err.println("Ingredient does not exist");
        }
             choice = Utils.checkYN("Do you want to continue update Ingredient(Y/N): ");
              }   
         } 
         
}
