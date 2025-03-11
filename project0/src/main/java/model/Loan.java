/* Author: Galilea Yanely Vilches Segundo
    Title: Loan class
    Date: February 27th, 2025
    Revature Training
*/
package model;


public class Loan {
    //Parameters for the Loan Class
    private int id;
    private int user_id;
    private String loanName;
    private float amountRequested;
    private String loanDate;
    private int loanType_id;
    private int loanStatus_id;

    //Basic Constructor
    public Loan(){}

    //Constructor for initializing parameters
    public Loan(int id, int user_id, String loanName, float amountRequested, String loanDate,
                int loanType_id, int loanStatus_id){
        this.id = id;
        this.user_id = user_id;
        this.loanName = loanName;
        this.amountRequested = amountRequested;
        this.loanDate = loanDate;
        this.loanType_id = loanType_id;
        this.loanStatus_id = loanStatus_id;

    }

    //----------------------------Getters----------------------------
    public int getId(){
        return  id;
    }

    public int getUser_id(){
        return user_id;
    }

    public String getLoanName(){
        return  loanName;
    }

    public float getAmountRequested(){
        return amountRequested;
    }

    public String getLoanDate(){
        return loanDate;
    }

    public int getLoanType_id(){
        return loanType_id;
    }

    public int getLoanStatus_id(){
        return loanStatus_id;
    }

    //----------------------------Setters----------------------------
    public void setId(int id){
        this.id = id;
    }

    public void setLoanName(String loanName){
        this.loanName = loanName;
    }

    public void setUser_id(int user_id){
        this.user_id = user_id;
    }

    public void setAmountRequested(float amountRequested){
        this.amountRequested = amountRequested;
    }

    public void setLoanDate(String loanDate){
        this.loanDate = loanDate;
    }

    public void setLoanType_id(int loanType_id){
        this.loanType_id = loanType_id;
    }

    public void setLoanStatus_id(int loanStatus_id){
        this.loanStatus_id = loanStatus_id;
    }

    //----------------------------toString----------------------------
    @Override
    public String toString(){
        return "\nLoan{\n" +
                "\tID = " + id + ", \n" +
                "\tUserID = " + user_id + ", \n" +
                "\tLoanName = " + loanName + ", \n" +
                "\tAmount = " + amountRequested + ", \n" +
                "\tDate beginning = " + loanDate + ", \n" +
                "\tTypeID = " + loanType_id + ", \n" +
                "\tStatusID = " + loanStatus_id + ", \n" + "}";
    }
}
