/* Author: Galilea Yanely Vilches Segundo
    Title: Loan DAO (Data Access Layer)
    Date: March 3rd, 2025
    Revature Training
*/
package dao;
//Import Loan class-------------
import model.Loan;
//For the Connection with the DB-------------
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//For the Statements-------------
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
//For the List of Loans-------------
import java.util.ArrayList;
import java.util.List;

public class LoanDao {
    //Variables for the LoanDao Class
    private final String jdbcURL;
    private final String dbusr;
    private final String dbpp;

    //Constructor for initializing variables
    public LoanDao(String jdbcURL, String dbusr, String dbpp){
        this.jdbcURL = jdbcURL;
        this.dbusr = dbusr;
        this.dbpp = dbpp;
    }

    //Create a new loan - WORKS :)
    public Loan createLoan(Loan nwLoan){
        //Create a new sql operation
        String insert_nwLoan = """
                INSERT INTO loans (user_id, loanname, amountrequested, dateloan, loanstatus_id, loantype_id)
                VALUES
                	(?, ?, ?, ?, ?, ?);
        """;

        try{
            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
            PreparedStatement connectStatement = connect.prepareStatement(insert_nwLoan, Statement.RETURN_GENERATED_KEYS);

            //Inserts the parameters into the sql operation
            connectStatement.setInt(1, nwLoan.getUser_id());
            connectStatement.setString(2,nwLoan.getLoanName());
            connectStatement.setFloat(3, nwLoan.getAmountRequested());
            connectStatement.setString(4, nwLoan.getLoanDate());
            connectStatement.setInt(5, nwLoan.getLoanStatus_id());
            connectStatement.setInt(6, nwLoan.getLoanType_id());

            //Executes the sqlOperation
            connectStatement.executeUpdate();

            //Gets the ID generated for the new user
            try{
                ResultSet generatedKeysForId = connectStatement.getGeneratedKeys();
                if(generatedKeysForId.next()){
                    // The columnIndex is 1 because that's the newly inserted ID
                    nwLoan.setId(generatedKeysForId.getInt(1));
                    //System.out.println("\nGreat! The new loan is created :)");
                }
//                else{
//                    System.out.println("\nSorry, it was not created :(");
//                }
            }catch (SQLException e){
                e.printStackTrace();
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return nwLoan;
    }

    //Get the list of all the existing loans - WORKS :)
    public List<Loan> getAllLoans(){
        //Create a new sql operation
        String showLoans = """
            SELECT * FROM loans;
        """;
        List<Loan> listOfLoans = new ArrayList<>();

        try{
            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
            PreparedStatement connectStatement = connect.prepareStatement(showLoans);

            ResultSet checkLoans = connectStatement.executeQuery();

            while(checkLoans.next()){
                Loan tempLoan = new Loan(checkLoans.getInt(1),
                        checkLoans.getInt(2),
                        checkLoans.getString(3),
                        checkLoans.getFloat(4),
                        checkLoans.getString(5),
                        checkLoans.getInt(7),
                        checkLoans.getInt(6));
                listOfLoans.add(tempLoan);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return listOfLoans;
    }

    //Looks for a loan by the id - WORKS :)
    public Loan getLoanByID(int lnid){
        //Create a new sql operation
        String lookFor_lnID = """
                SELECT * FROM loans WHERE id = ?
        """;
        Loan foundedLoan = new Loan();

        try{
            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
            PreparedStatement connectStatement = connect.prepareStatement(lookFor_lnID);

            //Inserts the parameters into the sql operation
            connectStatement.setInt(1, lnid);

            //Gets the ID generated for the loan we are looking for
            try{
                ResultSet generatedKeysForId = connectStatement.executeQuery();
                if(generatedKeysForId.next()){
                    foundedLoan.setId(generatedKeysForId.getInt(1));
                    foundedLoan.setUser_id(generatedKeysForId.getInt(2));
                    foundedLoan.setLoanName(generatedKeysForId.getString(3));
                    foundedLoan.setAmountRequested(generatedKeysForId.getFloat(4));
                    foundedLoan.setLoanDate(generatedKeysForId.getString(5));
                    foundedLoan.setLoanType_id(generatedKeysForId.getInt(7));
                    foundedLoan.setLoanStatus_id(generatedKeysForId.getInt(6));
                    //System.out.println("\nLooks like you found a loan :)");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return foundedLoan;
    }

    //Change the user_ID in the Loans table: For change of information
//    public void changeUserIDLoans(Loan lnForChange, int nwUserID){
//        //Create a new sql operation
//        String updateUserIDLoans = """
//                UPDATE loans SET user_id = ? WHERE loanName = ?;
//        """;
//
//        try {
//            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
//            PreparedStatement connectStatement = connect.prepareStatement(updateUserIDLoans, Statement.RETURN_GENERATED_KEYS);
//
//            //Inserts the parameters into the sql operation
//            connectStatement.setInt(1, nwUserID);
//            connectStatement.setString(2, lnForChange.getLoanName());
//
//            connectStatement.executeUpdate();
//
//            try{
//                ResultSet generatedKeysForId = connectStatement.getGeneratedKeys();
//                if(generatedKeysForId.next()) {
//                    lnForChange.setUser_id(nwUserID);
//                    System.out.println("\nThe userid for the loan has changed :)");
//                }
//            }catch (SQLException e){
//                e.printStackTrace();
//            }
//
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//    }

    //Change the loan name in the Loans table: For change of information - WORKS :)
    public void changeLoanName(Loan lnForChange, String nwLoanName){
        //Create a new sql operation
        String updateLoanName = """
                UPDATE loans SET loanname = ? WHERE loanname = ?;
        """;

        try {
            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
            PreparedStatement connectStatement = connect.prepareStatement(updateLoanName, Statement.RETURN_GENERATED_KEYS);

            //Inserts the parameters into the sql operation
            connectStatement.setString(1, nwLoanName);
            connectStatement.setString(2, lnForChange.getLoanName());

            connectStatement.executeUpdate();

            try{
                ResultSet generatedKeysForId = connectStatement.getGeneratedKeys();
                if(generatedKeysForId.next()) {
                    lnForChange.setLoanName(nwLoanName);
                    //System.out.println("\nThe loan name has changed :)");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Change the loan amount in the Loans table: For change of information - WORKS :)
    public void changeAmount(Loan lnForChange, float nwLoanAmount){
        //Create a new sql operation
        String updateLoanAmount = """
                UPDATE loans SET amountrequested = ? WHERE loanname = ?;
        """;

        try {
            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
            PreparedStatement connectStatement = connect.prepareStatement(updateLoanAmount, Statement.RETURN_GENERATED_KEYS);

            //Inserts the parameters into the sql operation
            connectStatement.setFloat(1, nwLoanAmount);
            connectStatement.setString(2, lnForChange.getLoanName());

            connectStatement.executeUpdate();

            try{
                ResultSet generatedKeysForId = connectStatement.getGeneratedKeys();
                if(generatedKeysForId.next()) {
                    lnForChange.setAmountRequested(nwLoanAmount);
                    //System.out.println("\nThe loan amount has changed :)");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Change the loan date in the Loans table: For change of information - WORKS :)
    public void changeLoanDate(Loan lnForChange, String nwLoanDate){
        //Create a new sql operation
        String updateLoanDate = """
                UPDATE loans SET dateloan = ? WHERE loanname = ?;
        """;

        try {
            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
            PreparedStatement connectStatement = connect.prepareStatement(updateLoanDate, Statement.RETURN_GENERATED_KEYS);

            //Inserts the parameters into the sql operation
            connectStatement.setString(1, nwLoanDate);
            connectStatement.setString(2, lnForChange.getLoanName());

            connectStatement.executeUpdate();

            try{
                ResultSet generatedKeysForId = connectStatement.getGeneratedKeys();
                if(generatedKeysForId.next()) {
                    lnForChange.setLoanDate(nwLoanDate);
                    //System.out.println("\nThe loan date has changed :)");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Change the LoanType ID in the Loans table: For change of information - WORKS :)
    public void changeLoanTypeID(Loan lnForChange, int nwLoanTypeID){
        //Create a new sql operation
        String updateLoanTypeID = """
                UPDATE loans SET loantype_id = ? WHERE loanname = ?;
        """;

        try {
            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
            PreparedStatement connectStatement = connect.prepareStatement(updateLoanTypeID, Statement.RETURN_GENERATED_KEYS);

            //Inserts the parameters into the sql operation
            connectStatement.setInt(1, nwLoanTypeID);
            connectStatement.setString(2, lnForChange.getLoanName());

            connectStatement.executeUpdate();

            try{
                ResultSet generatedKeysForId = connectStatement.getGeneratedKeys();
                if(generatedKeysForId.next()) {
                    lnForChange.setLoanType_id(nwLoanTypeID);
                    //System.out.println("\nThe loan type ID has changed :)");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Change the LoanStatus ID in the Loans table: For change of information - WORKS :)
    public void changeLoanStatusID(Loan lnForChange, int nwLoanStatusID){
        //Create a new sql operation
        String updateLoanStatusID = """
                UPDATE loans SET loanstatus_id = ? WHERE loanname = ?;
        """;

        try {
            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
            PreparedStatement connectStatement = connect.prepareStatement(updateLoanStatusID, Statement.RETURN_GENERATED_KEYS);

            //Inserts the parameters into the sql operation
            connectStatement.setInt(1, nwLoanStatusID);
            connectStatement.setString(2, lnForChange.getLoanName());

            connectStatement.executeUpdate();

            try{
                ResultSet generatedKeysForId = connectStatement.getGeneratedKeys();
                if(generatedKeysForId.next()) {
                    lnForChange.setLoanStatus_id(nwLoanStatusID);
                    //System.out.println("\nThe loan status ID has changed :)");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Looks for a loan by the userID - WORKS :)
    public List<Loan> getAllLoansByUserID(int usrid){
        //Create a new sql operation
        String lookFor_usrID = """
                SELECT * FROM loans WHERE user_id = ?
        """;
        List<Loan> listOfLoans = new ArrayList<>();

        try{
            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
            PreparedStatement connectStatement = connect.prepareStatement(lookFor_usrID);

            //Inserts the parameters into the sql operation
            connectStatement.setInt(1, usrid);

            try{
                ResultSet checkLoans = connectStatement.executeQuery();
                while(checkLoans.next()){
                    Loan tempLoan = new Loan(checkLoans.getInt(1),
                                checkLoans.getInt(2),
                                checkLoans.getString(3),
                                checkLoans.getFloat(4),
                                checkLoans.getString(5),
                                checkLoans.getInt(7),
                                checkLoans.getInt(6));
                    listOfLoans.add(tempLoan);
                    //System.out.println("\nLooks like you found a loan :)");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return listOfLoans;
    }
}
