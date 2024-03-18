package utilities;

import java.util.Scanner;

public class Utils {

    public static String getString(String welcome,String msg) {
        boolean check = true;
        String result = "";
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print(welcome);
            result = sc.nextLine();
            if (result.isEmpty()) {
                System.out.println(msg);
            } else {
                check = false;
            }
        } while (check);
        return result;
    }
    
    public static String getStringreg(String welcome,String pattern,String msg,String msgreg) {
        boolean check = true;
        String result = "";
        Scanner sc = new Scanner(System.in);
        do {
            
            System.out.print(welcome);
            result = sc.nextLine();
            if (result.isEmpty()) {
                System.err.println(msg);
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
    
    public static int getInt(String welcome, int min, int max) {
        boolean check = true;
        int number = 0;
        Scanner sc = new Scanner(System.in);
        do {
            try {
               
                System.out.print(welcome);
                number = Integer.parseInt(sc.nextLine());
                if(number< min){
                    System.err.println("Number must be larger or equal to " + min);
                }
                else if(number>max){
                    System.err.println("Number must be smaller or equal to " + max);
                }
                else{
                    check = false;
                }
                
            } catch (Exception e) {
                System.err.println("Input number!!!");
            }
        } while (check || number < min || number>max);
        return number;
    }
    
     public static int getIntNoMax(String welcome, int min) {
        boolean check = true;
        int number = 0;
        Scanner sc = new Scanner(System.in);
        do {
            try {
               
                System.out.print(welcome);
                number = Integer.parseInt(sc.nextLine());
                if(number< min){
                    System.out.println("Number must be large than " + min);
                }
                else{
                    check = false;
                }
                
            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number < min);
        return number;
    }
    
     public static float getFloat(String welcome, int min) {
        boolean check = true;
        float number = 0;
        Scanner sc = new Scanner(System.in);
        do {
            try {
                
                System.out.print(welcome);
                number = Float.parseFloat(sc.nextLine());
                 if(number< min){
                    System.out.println("Number must be large than " + min);
                }
                 else{
                      check = false;
                 }
               
            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number < min);
        return number;
    }
     
     public static String inputStringNotEmpty(String msg) {
        String input = "";
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println(msg);
            input = sc.nextLine();
        } while (input.trim().isEmpty());
        return input;
    }

     
     public static int checkYN(String msg) {
        String choice;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println(msg);
            choice = sc.nextLine();
            if (choice.equalsIgnoreCase("Y")) {
                return 1;
            } else if (choice.equalsIgnoreCase("N")) {
                return 0;
            } else {
                System.err.println("Must be Y or N");
                continue;
            }
        }
    }
  
}
