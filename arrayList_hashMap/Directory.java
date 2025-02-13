/*  Author: Galilea Yanely Vilches Segundo 
    Title: ArrayList Class: Directory
    Date: February 13th, 2025
    Revature Training 
*/

public class Directory{
    private String name;
    private String number;

    public Directory(String newName, String newNumber){
        name = newName;
        number = newNumber;
    }

    @Override
    public String toString() {
        return "\n| " + name + " | " + number + "\t\t|";
    }
}