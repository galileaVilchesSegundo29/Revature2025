/*  Author: Galilea Yanely Vilches Segundo 
    Title: Calculator
    Date: February 11th, 2025
    Revature Training 
*/

import java.util.Scanner;  //For input

public class Calculator{                        //Class Calculator
    public static void main(String[] args) {
        int num1, num2, option, sum, subs, mult, again;
        float divi;
        boolean getOut = true;

        System.out.println("---------------Calculator---------------\n\n");
        
        Scanner scNum = new Scanner(System.in);

        do{
            System.out.println("\nOption 1: +");                                        //Menu displayed
            System.out.println("Option 2: -");
            System.out.println("Option 3: x");
            System.out.println("Option 4: /");
            System.out.println("Option 5: Exit\n\n");


            System.out.println("Please select which operation you want to choose:");    //Reads the option
            option = scNum.nextInt();
            System.out.println("\n");

    

            switch (option) {
                //-------------------------------------Sum-------------------------------------
                case 1:   
                    System.out.println("\nPlease type in the first number");            //Reads the first input
                    num1 = scNum.nextInt();
                    //System.out.println("The first number is: " + num1);
                    System.out.println("\n");
                
                    System.out.println("Please type in the second number");             //Reads the second input
                    num2 = scNum.nextInt();
                    //System.out.println("The second number is: " + num2);
                    System.out.println("\n");      
                    sum = num1 + num2;
                    System.out.println("The result of " + num1 +" plus " + num2 +" is: " + sum);

                    System.out.println("\nDo you want to choose another operation?");    //Asks again
                    System.out.println("Yes: 1");                                  
                    System.out.println("No: 2");
                    again = scNum.nextInt();
                    if(again == 1){
                        getOut = false;
                    }
                    else{
                        getOut = true;
                    }
                break;
                //-------------------------------------Substract-------------------------------------
                case 2:    
                    System.out.println("\nPlease type in the first number");            //Reads the first input
                    num1 = scNum.nextInt();
                    //System.out.println("The first number is: " + num1);
                    System.out.println("\n");
                
                    System.out.println("Please type in the second number");             //Reads the second input
                    num2 = scNum.nextInt();
                    //System.out.println("The second number is: " + num2);
                    System.out.println("\n");     
                    if(num1 > num2){            //Checks is num1 is bigger than num2. For avoiding negative numbers
                        subs = num1 - num2;
                        System.out.println("The result of "+ num1 +" minus " + num2 +" is: " + subs);
                    }
                    else{
                        subs = num2 - num1;
                        System.out.println("The result of "+ num2 +" minus " + num1 +" is: " + subs);
                    }
                    
                    System.out.println("\nDo you want to choose another operation?");    //Asks again
                    System.out.println("Yes: 1");                                  
                    System.out.println("No: 2");
                    again = scNum.nextInt();
                    if(again == 1){
                        getOut = false;
                    }
                    else{
                        getOut = true;
                    }
                break;
                //-------------------------------------Multiply-------------------------------------
                case 3:    
                    System.out.println("\nPlease type in the first number");            //Reads the first input
                    num1 = scNum.nextInt();
                    //System.out.println("The first number is: " + num1);
                    System.out.println("\n");
                
                    System.out.println("Please type in the second number");             //Reads the second input
                    num2 = scNum.nextInt();
                    //System.out.println("The second number is: " + num2);
                    System.out.println("\n");    
                    mult = num1 * num2;
                    System.out.println("The result of " + num1 +" times " + num2 +" is: " + mult);

                    System.out.println("\nDo you want to choose another operation?");    //Asks again
                    System.out.println("Yes: 1");                                  
                    System.out.println("No: 2");
                    again = scNum.nextInt();
                    if(again == 1){
                        getOut = false;
                    }
                    else{
                        getOut = true;
                    }
                break;
                //-------------------------------------Division-------------------------------------
                case 4:
                    System.out.println("\nPlease type in the first number");            //Reads the first input
                    num1 = scNum.nextInt();
                    //System.out.println("The first number is: " + num1);
                    System.out.println("\n");
                
                    System.out.println("Please type in the second number");             //Reads the second input
                    num2 = scNum.nextInt();
                    //System.out.println("The second number is: " + num2);
                    System.out.println("\n");
                    if (num1 != 0){                                                     //Verifies that the first number is not 0
                        divi = num1 / num2;
                        System.out.println("The result of " + num1 +" divided by " + num2 +" is: " + divi);
                    }
                    else{
                        System.out.println("The first number is equal to 0\n");
                    }

                    System.out.println("\nDo you want to choose another operation?");    //Asks again
                    System.out.println("Yes: 1");                                  
                    System.out.println("No: 2");
                    again = scNum.nextInt();
                    if(again == 1){
                        getOut = false;
                    }
                    else{
                        getOut = true;
                    }
                break;
                //-------------------------------------Exit-------------------------------------
                case 5:
                    System.out.println("You selected Exit!\n");
                    getOut = true;
                break;
                //-------------------------------------Incorrect option-------------------------------------
                default:
                    System.out.println("You selected an incorrect option");
                    System.out.println("Please try again :)\n");
                    getOut = false;
                break;
            }
        }while(getOut == false);
    }
}

