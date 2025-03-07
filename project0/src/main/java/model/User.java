/* Author: Galilea Yanely Vilches Segundo
    Title: User class
    Date: February 27th, 2025
    Revature Training
*/
package model;

public class User {
    //Variables for the User Class
    private int id;
    private String userName;
    private String userPassword;
    private String uName;
    private String uMiddleName;
    private String uLastName;
    private String userEmail;
    private boolean isLogged;
    private int userRole_id;

    //Basic Constructor
    public User(){}

    //Constructor for initializing variables
    public User(int id, String userName, String userPassword, String uName, String uMiddleName, String uLastName,
               String userEmail, boolean isLogged, int userRole_id){

        this.id = id;
        this.userName = userName;
        this.userPassword = userPassword;
        this.uName = uName;
        this.uMiddleName = uMiddleName;
        this.uLastName = uLastName;
        this.userEmail = userEmail;
        this.isLogged = isLogged;
        this.userRole_id = userRole_id;

    }

    //----------------------------Getters----------------------------
    public int getId(){
        return id;
    }

    public String getUserName(){
        return userName;
    }

    public String getUserPassword(){
        return  userPassword;
    }

    public String getuName(){
        return  uName;
    }

    public String getuMiddleName(){
        return uMiddleName;
    }

    public String getuLastName(){
        return uLastName;
    }

    public String getUserEmail(){
        return userEmail;
    }
    public boolean getIsLogged(){
        return isLogged;
    }

    public int getUserRole_ID(){
        return userRole_id;
    }
    //----------------------------Setters----------------------------
    public void setId(int id){
        this.id = id;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setUserPassword(String userPassword){
        this.userPassword = userPassword;
    }

    public void setUName(String uName){
        this.uName = uName;
    }

    public void setUMiddleName(String uMiddleName){
        this.uMiddleName = uMiddleName;
    }

    public void setULastName(String uLastName){
        this.uLastName = uLastName;
    }

    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }

    public void setIsLogged(boolean isLogged){
        this.isLogged = isLogged;
    }

    public void setUserRole_id(int userRole_id){
        this.userRole_id = userRole_id;
    }

    //----------------------------toString----------------------------
    @Override
    public String toString(){
        return "\nUser{\n" +
                "\tID = " + id + ", \n" +
                "\tUserName = " + userName + ", \n" +
                "\tPassword = " + userPassword + ", \n" +
                "\tName = " + uName + ", \n" +
                "\tMiddle Name = " + uMiddleName + ", \n" +
                "\tLast Name = " + uLastName + ", \n" +
                "\tEmail = " + userEmail + ", \n" +
                "\tIs Logged? = " + isLogged + ", \n" +
                "\tRoleID = " + userRole_id + '\n' +"}";
    }

}
