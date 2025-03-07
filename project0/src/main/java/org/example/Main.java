/* Author: Galilea Yanely Vilches Segundo
    Title: Main
    Date: February 25th, 2025
    Revature Training
*/
package org.example;

//Import classes-------------
import controller.LoanController;
import dao.UserDao;
import dao.LoanDao;
import service.LoanService;
import service.UserService;
import controller.UserController;
//For Database connection-------------
import java.sql.*;
//For HTTP Requests-----------------
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;


public class Main {
    private static final String drop_tables = """
               DROP TABLE IF EXISTS usersrole CASCADE;
               DROP TABLE IF EXISTS users CASCADE;
               DROP TABLE IF EXISTS loanstatus CASCADE;
               DROP TABLE IF EXISTS loantype CASCADE;
               DROP TABLE IF EXISTS loans CASCADE;
    """;
    private static final String create_tables = """
                create table usersrole(
                	id SERIAL PRIMARY KEY,
                	userRoleName varchar(100) NOT NULL
                );
        
                create table loanstatus(
                	id SERIAL PRIMARY KEY,
                	loanStatus varchar(100) NOT NULL
                );
                
                create table loantype(
                	id SERIAL PRIMARY KEY,
                	loanType varchar(100) NOT NULL,
                	timeRequested float NOT NULL
                );
                
                create table users(
                	id SERIAL PRIMARY KEY,
                	userName varchar(100) NOT NULL UNIQUE,
                	userPassword varchar(100) NOT NULL,
                	uName varchar(100) NOT NULL,
                	uMiddleName varchar(100),
                	uLastName varchar(100) NOT NULL,
                	userEmail varchar(100) NOT NULL,
                	userRole_id int NOT NULL,
                	isLogged boolean NOT NULL,
                	FOREIGN KEY (userRole_id) REFERENCES usersrole(id)
                );
                
                create table loans(
                	id SERIAL PRIMARY KEY,
                	user_id int NOT NULL,
                	loanName varchar(100) NOT NULL UNIQUE,
                	amountRequested float NOT NULL,
                	dateLoan varchar(100) NOT NULL,
                	loanStatus_id int NOT NULL,
                	loanType_id int NOT NULL,
                	FOREIGN KEY (user_id) REFERENCES users(id),
                	FOREIGN KEY (loanStatus_id) REFERENCES loanstatus(id),
                	FOREIGN KEY (loanType_id) REFERENCES loantype(id)
                );
                
    """;
    private static final String insert_data = """
                INSERT INTO usersrole (userRoleName)
                VALUES
                	('Manager'),
                	('Normal');
                
                INSERT INTO loanstatus(loanStatus)
                VALUES
                	('created'),
                	('approved'),
                	('rejected');
                
                INSERT INTO loantype(loanType, timeRequested)
                VALUES
                	('Car loan', 0.6),
                	('House loan', 1.0),
                	('Student loan', 0.3);
                
    """;

    public static void main(String[] args) {
        //For accessing to the DB
        String jdbcURL = "jdbc:postgresql://localhost:5432/project_db";
        String dbusr = "postgres";
        String dbpp = "pass";

        //Start with a clean DB
        sqlOperationIntoDB(jdbcURL,dbusr,dbpp,drop_tables);
        //Create the tables
        sqlOperationIntoDB(jdbcURL,dbusr,dbpp, create_tables);
        //Insert 3 registers
        sqlOperationIntoDB(jdbcURL, dbusr, dbpp, insert_data);

        //DAO's
        UserDao usrDao = new UserDao(jdbcURL, dbusr, dbpp);
        LoanDao lnDao = new LoanDao(jdbcURL, dbusr, dbpp);
        //Services
        UserService usrService = new UserService(usrDao);
        LoanService lnService = new LoanService(lnDao, usrDao);
        //Controllers
        UserController usrController = new UserController(usrService);
        LoanController lnController = new LoanController(lnService, usrService);

        Javalin app = Javalin.create().start(7000);

        //User's endpoints--------------------------------------------------------
        app.post("/auth/register", usrController::registerU);   //Register a new user
        app.post("/auth/login", usrController::login);          //Login
        app.post("/auth/logout", usrController::logout);        //Logout
        app.put("/users/{id}", usrController::updateProfile);   //Update profile (needs login)
        app.get("/users/", usrController::getAllUsers);         //View all users
        app.get("/users/{id}", usrController::getUserByID);     //Get user info (needs login)

        //Loan's endpoints--------------------------------------------------------
        app.post("/loans", lnController::createLoan);                       //Create a new loan (needs login)
        app.get("/loans/user/{id}", lnController::getLoansUsrId);           //View all loans of a user (needs login)
        app.get("/loans/manager/{id}", lnController::getLoansOnlyManager);  //View all loans (needs login and manager role)
        app.get("/loans/{id}", lnController::getLoanByID);                  //View only one loan of a user (needs login)
        app.put("/loans/{id}", lnController::uploadLoan);                   //Upload loan info (needs login)
        app.put("/loans/{id}/approved", lnController::approvedLoan);        //Approve a loan (needs login and manager role)
        app.put("/loans/{id}/rejected", lnController::rejectedLoan);        //Reject a loan (needs login and manager role)


    }

    //InsertDataIntoBD - For initializing it
    private static void sqlOperationIntoDB(String jdbcURL, String dbusr, String dbpp, String data){
        try{
            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
            PreparedStatement connectStatement = connect.prepareStatement(data);

            connectStatement.executeUpdate();

            System.out.println("Connected. The SQL operation works just fine :)");

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}