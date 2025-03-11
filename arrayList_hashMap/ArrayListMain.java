/*  Author: Galilea Yanely Vilches Segundo 
    Title: ArrayList with Objects
    Date: February 13th, 2025
    Revature Training 
*/
import java.util.List;                      //For List
import java.util.ArrayList;                 //For ArrayList

public class ArrayListMain{
    public static void main(String[] args) {
        //Initialize an ArrayList of Strings   
        List<Directory> arrayListForObject = new ArrayList<>();  

        //Initialize 3 objects
        Directory usr1 = new Directory("Juanita Perez", "5567898092");
        Directory usr2 = new Directory("Mario Molina", "5567898093");
        Directory usr3 = new Directory("Sandy Lopez", "5567898094");

        //Add 3 objects into the list 
        arrayListForObject.add(usr1); 
        arrayListForObject.add(usr2);
        arrayListForObject.add(usr3);      

        //Print each object
        System.out.println("This is the ArrayList of a Directory:\n");
        //System.out.println(arrayListForObject);

        //For each loop
        System.out.println("----------------------------------------");
        System.out.println("| Name          | Phone Number          |");
        System.out.println("----------------------------------------");
        for(Directory var: arrayListForObject){
            System.out.println(var);
        }
        
    }
    
}
