/*  Author: Galilea Yanely Vilches Segundo 
    Title: HashTable with Objects
    Date: February 13th, 2025
    Revature Training 
*/

import java.util.HashMap;                       //For Hashmap
import java.util.Map;                           //For Map

public class HashMapMain{

    public static void main(String[] args) {
        //Initialize a HashMap of Integers as key and Strings as value
        Map<String,String> hashMapForObject = new HashMap<>();
        String getTheValue;

        //Add 3 values into the map
        hashMapForObject.put("Juanita Perez", "5567898092");
        hashMapForObject.put("Mario Molina", "5567898093");
        hashMapForObject.put("Sandy Lopez", "5567898094");

        //Print each object
        System.out.println("\nThis is the HashMap of a Directory:\n");

        //Create a set of the keys in the HashMap
        System.out.println("----------------------------");
        System.out.println("| Key          | Value      |");
        System.out.println("----------------------------");

        for(String getTheKey: hashMapForObject.keySet()){
            getTheValue = hashMapForObject.get(getTheKey);
            System.out.println("| " + getTheKey + " | " + getTheValue + " |");
        }
        
    }
    
}