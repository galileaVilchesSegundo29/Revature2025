/* Author: Galilea Yanely Vilches Segundo
    Title: Loan Controller
    Date: March 4th, 2025
    Revature Training
*/
package controller;
//Import classes-------------
import service.LoanService;
import model.Loan;
import service.UserService;
import model.User;
//For the List of Loans-------------
import java.util.List;
//For the HTTP Requests-------------
import io.javalin.http.Context;
//For the Sessions------------------
import jakarta.servlet.http.HttpSession;

public class LoanController {
    //Variables
    private final LoanService lnService;
    private final UserService usrService;

    //Constructor for initializing variables
    public LoanController(LoanService lnService, UserService usrService){
        this.lnService = lnService;
        this.usrService = usrService;
    }

    //For creating a new loan (If you are logged in) - WORKS :)
    /*
     * {
     *    "user_id": 4,
     *    "loanName": "loanCreated",
     *    "amountRequested": 15.8,
     *    "loanDate": "2025-08-13",
     *    "loanStatus_id": 1,
     *    "loanType_id": 2
     * }
     */
    public void createLoan(Context ctx){
        Loan lnCreate = ctx.bodyAsClass(Loan.class);
        int usrIdInLoan = lnCreate.getUser_id();
        User userCheck = usrService.getUserByID(usrIdInLoan);

        HttpSession session = ctx.req().getSession(false);

        //Validate if it is logged in
        if(session != null && session.getAttribute("user") != null){
            User userLogged = (User) session.getAttribute("user");
            //Compares if the user logged in is the one that wants to create the loan
            if(userLogged.getIsLogged() == userCheck.getIsLogged()) {
                Loan nwLoan = lnService.registerNewLoan(usrIdInLoan,
                        lnCreate.getLoanName(),
                        lnCreate.getAmountRequested(),
                        lnCreate.getLoanDate(),
                        lnCreate.getLoanType_id(),
                        lnCreate.getLoanStatus_id());
                ctx.status(200).json("{\"message\":\"Great! New loan created\"}");
                //ctx.status(200).json(nwLoan);
            }
            else
                ctx.status(409).json("{\"error\":\"Cannot create loan because you are not the user :(\"}");
        }
        else {
            ctx.status(401).json("{\"error\":\"Cannot create loan because you have not logged in :(\"}");
        }
    }

    //For getting all your own loans (if you are logged in) - WORKS :)
    public void getLoansUsrId(Context ctx){
        int userId = Integer.parseInt(ctx.pathParam("id"));
        User usrByID = usrService.getUserByID(userId);

        HttpSession session = ctx.req().getSession(false);

        //Validate if it is logged in
        if(session != null && session.getAttribute("user") != null){
            User userLogged = (User) session.getAttribute("user");
            //Compares if the user logged in is the one that wants to create the loan
            if(userLogged.getIsLogged() == usrByID.getIsLogged()) {
                List<Loan> loansByUserID = lnService.getAllLoansByUsrID(userId);
                ctx.json(loansByUserID);
            }
            else
                ctx.status(409).json("{\"error\":\"Cannot get loans because you are not the user :(\"}");
        }
        else {
            ctx.status(401).json("{\"error\":\"Cannot change information because you have not logged in :(\"}");
        }
    }

    //For getting all loans for manager (if you are logged in) - WORKS :)
    public void getLoansOnlyManager(Context ctx){
        int userId = Integer.parseInt(ctx.pathParam("id"));
        User usrByID = usrService.getUserByID(userId);

        HttpSession session = ctx.req().getSession(false);

        if(session != null && session.getAttribute("user") != null){
            User userLogged = (User) session.getAttribute("user");
            //Compares if the user logged in is the one that wants to create the loan
            if((userLogged.getUserRole_ID() == 1) && (userLogged.getIsLogged() == usrByID.getIsLogged())) {
                List<Loan> loansByUserID = lnService.getListOfLoans(usrByID);
                ctx.json(loansByUserID);
            }
            else
                ctx.status(409).json("{\"error\":\"Cannot show you information you are not a manager :(\"}");
        }
        else {
            ctx.status(401).json("{\"error\":\"Cannot change information because you have not logged in :(\"}");
        }

    }

    //For getting a specific loan by its ID (if you are logged in) - WORKS :)
    public void getLoanByID(Context ctx){
        int lnId = Integer.parseInt(ctx.pathParam("id"));
        Loan loanByID = lnService.getLoanByID(lnId);

        int usrIdInLoan = loanByID.getUser_id();
        User userCheck = usrService.getUserByID(usrIdInLoan);

        HttpSession session = ctx.req().getSession(false);

        //Validate if it is logged in
        if(session != null && session.getAttribute("user") != null){
            User userLogged = (User) session.getAttribute("user");
            if((userLogged.getUserRole_ID() == 1) || (userLogged.getIsLogged() == userCheck.getIsLogged()))
                ctx.json(loanByID);
            else
                ctx.status(409).json("{\"error\":\"Cannot get loan because you are not the right user or are not manager :(\"}");
        }
        else {
            ctx.status(401).json("{\"error\":\"Cannot get information because you have not logged in :(\"}");
        }
    }

    //For updating a loan's information (if you are logged in) - WORKS :)
    /*{
    *       "loanName": "loanModifiedForDiana",
    *       "amountRequested": 190.90,
    *       "loanDate": "2010-08-16",
    *       "loanType_id": 3
    }
    * */
    public void uploadLoan(Context ctx){
        int userId = Integer.parseInt(ctx.pathParam("id"));
        Loan newLoan = ctx.bodyAsClass(Loan.class);
        Loan loanForUpdate = lnService.getLoanByID(userId);
        User usrByID = usrService.getUserByID(userId);

        HttpSession session = ctx.req().getSession(false);

        if(session != null && session.getAttribute("user") != null){
            User userLogged = (User) session.getAttribute("user");
            if((userLogged.getUserRole_ID() == 1) || (userLogged.getIsLogged() == usrByID.getIsLogged())) {
                lnService.updateLoanInfo(loanForUpdate, newLoan.getLoanName(), newLoan.getAmountRequested(),
                        newLoan.getLoanDate(), newLoan.getLoanType_id());
                ctx.status(200).json("{\"message\":\"Loan updated :)\"}");
            }
            else
                ctx.status(409).json("{\"error\":\"Cannot update because you are not the user or are not manager:(\"}");
        }
        else {
            ctx.status(401).json("{\"error\":\"Cannot update because you have not logged in :(\"}");
        }

    }

    //For approving a loan (If you are logged and manager) - WORKS :)
    public void approvedLoan(Context ctx){
        int lnId = Integer.parseInt(ctx.pathParam("id"));
        Loan loanByID = lnService.getLoanByID(lnId);

        int usrIdInLoan = loanByID.getUser_id();
        User userCheck = usrService.getUserByID(usrIdInLoan);

        HttpSession session = ctx.req().getSession(false);

        if(session != null && session.getAttribute("user") != null){
            User usrByID = (User) session.getAttribute("user");
            if(usrByID.getUserRole_ID() == 1) {
                lnService.approvedLoan(loanByID);
                ctx.status(200).json("{\"message\":\"Loan approved :)\"}");
            }
            else
                ctx.status(409).json("{\"error\":\"Cannot change information because you are not a manager\"}");
        }
        else {
            ctx.status(401).json("{\"error\":\"Cannot change information because you have not logged in :(\"}");
        }
    }

    //For rejecting a loan (If you are logged and manager) - WORKS :)
    public void rejectedLoan(Context ctx){
        int lnId = Integer.parseInt(ctx.pathParam("id"));
        Loan loanByID = lnService.getLoanByID(lnId);

        HttpSession session = ctx.req().getSession(false);

        if(session != null && session.getAttribute("user") != null){
            User usrByID = (User) session.getAttribute("user");
            if(usrByID.getUserRole_ID() == 1) {
                lnService.rejectedLoan(loanByID);
                ctx.status(200).json("{\"message\":\"Loan rejected :)\"}");
            }
            else
                ctx.status(409).json("{\"error\":\"Cannot change information because you are not a manager :(\"}");
        }
        else {
            ctx.status(401).json("{\"error\":\"Cannot change information because you have not logged in :(\"}");
        }
    }

}
