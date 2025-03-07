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
//For Logback------------
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoanService {
    //Variables
    private final LoanDao lnDao;
    private final UserDao usrDao;
    public static final Logger logger = LoggerFactory.getLogger(LoanService.class);

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
        logger.info("Loan created in {}", LoanService.class.getName());
        return lnDao.createLoan(nwLoan);
    }

    //Gets the list of loans - WORKS :)
    public List<Loan> getListOfLoans(User usrLogged){
        logger.info("all loans in {}", LoanService.class.getName());
        return lnDao.getAllLoans();
    }

    //Gets a loan by id - WORKS :)
    public Loan getLoanByID(int id){
        logger.info("loan by id in {}", LoanService.class.getName());
        return lnDao.getLoanByID(id);
    }

    //Gets a list of loans based on the userID - WORKS :)
    public List<Loan> getAllLoansByUsrID(int usrid){
        logger.info("all loans by user in {}", LoanService.class.getName());
        return lnDao.getAllLoansByUserID(usrid);
    }

    //Updates the loan info - WORKS :)
    public void updateLoanInfo(Loan lnChange, String nwLnName, float nwLnAmount, String nwLnDate, int nwLnTypeId){
            //Changes userName
            if(!nwLnName.isEmpty()){
                logger.info("loan name change in {}", LoanService.class.getName());
                lnDao.changeLoanName(lnChange, nwLnName);
            }
            else
                logger.warn("You must enter a loanName");

            //Changes an amount
            if(nwLnAmount > 0){
                logger.info("loan amount change in {}", LoanService.class.getName());
                lnDao.changeAmount(lnChange, nwLnAmount);
            }
            else
                logger.warn("You must enter a loan amount");

            //Changes a loan date
            if(!nwLnDate.isEmpty()){
                logger.info("loan date change in {}", LoanService.class.getName());
                lnDao.changeLoanDate(lnChange, nwLnDate);
            }
            else
                logger.warn("You must enter a loan date");

            //Changes a loan type
            if(nwLnTypeId > 0 && nwLnTypeId < 4) {
                logger.info("loan type change in {}", LoanService.class.getName());
                lnDao.changeLoanTypeID(lnChange, nwLnTypeId);
            }
            else
                logger.warn("You must enter a loan type");

    }

    //Approves a loan - WORKS :)
    public void approvedLoan(Loan lnChange){
        //Checks if it's not approved already
        if(lnChange.getLoanStatus_id() != 2) {
            logger.info("loan approved in {}", LoanService.class.getName());
            lnDao.changeLoanStatusID(lnChange, 2);
        }
        else
            logger.warn("The loan is already approved");
    }

    //Rejects a loan - WORKS :)
    public void rejectedLoan(Loan lnChange){
        //Checks if it's not rejected already
        if(lnChange.getLoanStatus_id() != 3){
            logger.info("loan rejected in {}", LoanService.class.getName());
            lnDao.changeLoanStatusID(lnChange, 3);
        }
        else
            logger.warn("The loan is already rejected");
    }

}
