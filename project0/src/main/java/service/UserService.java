/* Author: Galilea Yanely Vilches Segundo
    Title: User Service
    Date: February 28th, 2025
    Revature Training
*/
package service;
//Import classes-------------
import dao.UserDao;
import model.User;
//For the List of Users-------------
import java.util.List;
//For Hashing the password------------
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    //Variables
    private final UserDao usrDao;

    //Constructor for initializing variables
    public UserService(UserDao usrDao){
        this.usrDao = usrDao;
    }

    //Register a new user into the DB - WORKS :)
    public User registerNewUser(String userName, String userPassword, String uName, String uMiddleName,
                                String uLastName, String userEmail, int userRole_id){

        if(usrDao.userAlreadyExists(userName)){
            return null;
        }

        String hashedUserPassword = BCrypt.hashpw(userPassword, BCrypt.gensalt(10));

        User nwUsr = new User();
        nwUsr.setUserName(userName);
        nwUsr.setUserPassword(hashedUserPassword);
        nwUsr.setUName(uName);
        nwUsr.setUMiddleName(uMiddleName);
        nwUsr.setULastName(uLastName);
        nwUsr.setUserEmail(userEmail);
        nwUsr.setIsLogged(false);
        nwUsr.setUserRole_id(userRole_id);

        return usrDao.createUser(nwUsr);
    }

    //Get the list of users - WORKS :)
    public List<User> getListOfUsers(){
        return usrDao.getAllUsers();
    }

    //Get a user by id - WORKS :)
    public User getUserByID(int id){
        return usrDao.getUserByID(id);
    }

    //Logs in a user - WORKS :)
    public User loginUser (String userName, String userPassword){
        User checkUsr = usrDao.getUserByUserName(userName);

        if(checkUsr == null){
            System.out.println("\nThis user does not exists :(");
            return null;
        }

        boolean checkdPassword = BCrypt.checkpw(userPassword, checkUsr.getUserPassword());

        if(checkdPassword && userName.equals(checkUsr.getUserName())){
            System.out.println("\nYou logged in");
            usrDao.changeIsLogged(checkUsr,true);
        }
        else{
            System.out.println("\nYou haven't logged in. Incorrect password or username :(");
            usrDao.changeIsLogged(checkUsr,false);
        }
        return checkUsr;
    }

    //Updates the userInfo - WORKS :)
    public void updateUserProfile(User usrChange, String nwUserName, String nwUserPassword, String nwUName, String nwUMiddleName,
                                  String nwULastName, String nwUserEmail, int nwRole){

        String hashedNwUserPassword = BCrypt.hashpw(nwUserPassword, BCrypt.gensalt(10));

        //Changes userName
        if(!nwUserName.isEmpty())
            usrDao.changeUserName(usrChange, nwUserName);
        else
            System.out.println("\nYou must enter a userName :)");

        //Changes a userRole
        if(nwRole > 0 && nwRole < 3)
            usrDao.changeUserRole(usrChange, nwRole);
        else
            System.out.println("\nThe role is incorrect :)");

        //Changes the email
        if(!nwUserPassword.isEmpty())
            usrDao.changeUserPassword(usrChange, hashedNwUserPassword);
        else
            System.out.println("\nYou must enter a Password :)");

        //Changes the name
        if(!nwUName.isEmpty())
            usrDao.changeUName(usrChange, nwUName);
        else
            System.out.println("\nYou must enter a Name :)");

        //Changes the middle name
        usrDao.changeUMiddleName(usrChange, nwUMiddleName);

        //Changes the last name
        if(!nwULastName.isEmpty())
            usrDao.changeULastName(usrChange, nwULastName);
        else
            System.out.println("\nYou must enter a Last Name :)");

        //Changes the email
        if(!nwUserEmail.isEmpty())
            usrDao.changeUserEmail(usrChange, nwUserEmail);
        else
            System.out.println("\nYou must enter a Email :)");
    }

    //Logs out a user - WORKS :)
    public User logoutUser (String userName, String userPassword){
        User checkUsr = usrDao.getUserByUserName(userName);

        if(checkUsr == null){
            System.out.println("\nThis user does not exists :(");
            return null;
        }

        boolean checkdPassword = BCrypt.checkpw(userPassword, checkUsr.getUserPassword());

        if(checkdPassword && userName.equals(checkUsr.getUserName())){
            System.out.println("\nYou logged out");
            usrDao.changeIsLogged(checkUsr,false);
        }
        else{
            System.out.println("\nYou haven't logged out. Incorrect password or username :(");
            usrDao.changeIsLogged(checkUsr,true);
        }

        return checkUsr;
    }
}
