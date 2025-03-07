/* Author: Galilea Yanely Vilches Segundo
    Title: Loan Service
    Date: March 4th, 2025
    Revature Training
*/
package service;
//Import classes-------------
import dao.LoanDao;
import model.Loan;
import model.User;
import dao.UserDao;
//For the List of Loans-------------
import java.util.List;


public class LoanService {
    //Variables
    private final LoanDao lnDao;
    private final UserDao usrDao;

    //Constructor for initializing variables
    public LoanService(LoanDao lnDao, UserDao usrDao){
        this.lnDao = lnDao;
        this.usrDao = usrDao;
    }

    //Registers a new loan into the DB - WORKS :)
    public Loan registerNewLoan(int usrID, String loanName, float amountRequested, String loanDate,
                                int loanTypeid, int loanStatusid){

        Loan nwLoan = new Loan();
        nwLoan.setUser_id(usrID);
        nwLoan.setLoanName(loanName);
        nwLoan.setAmountRequested(amountRequested);
        nwLoan.setLoanDate(loanDate);
        nwLoan.setLoanType_id(loanTypeid);
        nwLoan.setLoanStatus_id(loanStatusid);
        return lnDao.createLoan(nwLoan);
    }

    //Gets the list of loans - WORKS :)
    public List<Loan> getListOfLoans(User usrLogged){
        return lnDao.getAllLoans();
    }

    //Gets a loan by id - WORKS :)
    public Loan getLoanByID(int id){
        return lnDao.getLoanByID(id);
    }

    //Gets a list of loans based on the userID - WORKS :)
    public List<Loan> getAllLoansByUsrID(int usrid){
        return lnDao.getAllLoansByUserID(usrid);
    }

    //Updates the loan info - WORKS :)
    public void updateLoanInfo(Loan lnChange, String nwLnName, float nwLnAmount, String nwLnDate, int nwLnTypeId){
            //Changes userName
            if(!nwLnName.isEmpty())
                lnDao.changeLoanName(lnChange, nwLnName);
            else
                System.out.println("\nYou must enter a loan name :)");

            //Changes an amount
            if(nwLnAmount > 0)
                lnDao.changeAmount(lnChange, nwLnAmount);
            else
                System.out.println("\nYou must enter a loan amount :)");

            //Changes a loan date
            if(!nwLnDate.isEmpty())
                lnDao.changeLoanDate(lnChange, nwLnDate);
            else
                System.out.println("\nYou must enter a loan date :)");

            //Changes a loan type
            if(nwLnTypeId > 0 && nwLnTypeId < 4)
                lnDao.changeLoanTypeID(lnChange, nwLnTypeId);
            else
                System.out.println("\nYou must enter a loan type id :)");

    }

    //Approves a loan - WORKS :)
    public void approvedLoan(Loan lnChange){
        //Checks if it's not approved already
        if(lnChange.getLoanStatus_id() != 2)
            lnDao.changeLoanStatusID(lnChange, 2);
        else
            System.out.println("\nThe loan is already approved :)");
    }

    //Rejects a loan - WORKS :)
    public void rejectedLoan(Loan lnChange){
        //Checks if it's not rejected already
        if(lnChange.getLoanStatus_id() != 3)
            lnDao.changeLoanStatusID(lnChange, 3);
        else
            System.out.println("\nThe loan is already rejected :)");
    }

}
