/* Author: Galilea Yanely Vilches Segundo
    Title: User Controller
    Date: February 28th, 2025
    Revature Training
*/
package controller;

//Import classes--------------------
import service.UserService;
import model.User;
//For the List of Users-------------
import java.util.List;
//For the HTTP Requests-------------
import io.javalin.http.Context;
//For the Sessions------------------
import jakarta.servlet.http.HttpSession;


public class UserController {
    //Variables
    private final UserService usrService;

    //Constructor for initializing variables
    public UserController(UserService usrService){
        this.usrService = usrService;
    }

    //For getting all the users in the db - WORKS :)
    public void getAllUsers(Context ctx) {
        List<User> users = usrService.getListOfUsers();
        ctx.json(users);
    }

    //For registering a new user - WORKS :)
    /*
     * {
     *    "userName": "diana30",
     *    "userPassword": "wordPass",
     *    "uName": "Diana",
     *    "uMiddleName": "Laura",
     *    "uLastName": "Perez",
     *    "userEmail": "diana30@gmail.com",
     *    "userRole_id": 3
     * }
     */
    public void registerU(Context ctx){
        User usrForRegister = ctx.bodyAsClass(User.class);

        //Validation for empty fields
        if(usrForRegister.getUserName() == null || usrForRegister.getUserPassword() == null) {
            ctx.status(400).json("{\"error\":\"Missing username or password :(\"}");
        }

        User nwUser = usrService.registerNewUser(usrForRegister.getUserName(), usrForRegister.getUserPassword(),
                usrForRegister.getuName(), usrForRegister.getuMiddleName(), usrForRegister.getuLastName(),
                usrForRegister.getUserEmail(), usrForRegister.getUserRole_ID());

        if(nwUser == null)
            ctx.status(409).json("{\"error\":\"This user already exists :(\"}");
        else
            ctx.status(200).json("{\"message\":\"Great! New user registered\"}");
    }

    //For login user - WORKS :)
    /*
     * {
     *    "username": "diana30",
     *    "password": "wordPass"
     * }
     */
    public void login(Context ctx){
        User usrForLogin = ctx.bodyAsClass(User.class);

        //Validation for empty fields
        if(usrForLogin.getUserName() == null || usrForLogin.getUserPassword() == null) {
            ctx.status(400).json("{\"error\":\"Missing username or password :(\"}");
        }

        User logged = usrService.loginUser(usrForLogin.getUserName(), usrForLogin.getUserPassword());

        if(logged == null){
            ctx.status(401).json("{\"error\":\"This user does not exists :(\"}");
            return;
        }

        boolean isLogged = logged.getIsLogged();

        if(isLogged) {
            HttpSession sessionLogIn = ctx.req().getSession(true);
            sessionLogIn.setAttribute("user", logged);
            ctx.status(200).json("{\"message\":\"Great! New user logged in\"}");
        }
        else {
            ctx.status(409).json("{\"error\":\"Incorrect password or username :(\"}");
        }
    }

    //For updating information - WORKS :)
    /*
     * {
     *    "username": "melissa50",
     *    "userpassword": "HashedPP",
     *    "uname": "Melissa",
     *    "uMiddleName": "",
     *    "uLastName": "Juarez",
     *    "userEmail": "melissa50@gmail.com",
     *    "userRole_id": 1
     * }
     */
    public void updateProfile(Context ctx){
        int userId = Integer.parseInt(ctx.pathParam("id"));
        User newUsr = ctx.bodyAsClass(User.class);
        User usrForUpdate = usrService.getUserByID(userId);

        HttpSession session = ctx.req().getSession(false);

        if(session != null && session.getAttribute("user") != null){
            usrService.updateUserProfile(usrForUpdate, newUsr.getUserName(),
                    newUsr.getUserPassword(),newUsr.getuName(),
                    newUsr.getuMiddleName(), newUsr.getuLastName(),
                    newUsr.getUserEmail(),newUsr.getUserRole_ID());
            ctx.status(200).json("{\"message\":\"Profile updated :)\"}");
        }
        else {
            ctx.status(409).json("{\"error\":\"Cannot update because you have not logged in :(\"}");
        }
    }

    //For getting only 1 user - WORKS :)
    public void getUserByID(Context ctx){
        int userId = Integer.parseInt(ctx.pathParam("id"));
        User usrByID = usrService.getUserByID(userId);

        HttpSession session = ctx.req().getSession(false);

        if(session != null && session.getAttribute("user") != null)
            ctx.json(usrByID);
        else
            ctx.status(409).json("{\"error\":\"Cannot show you information you have not logged in :(\"}");
    }

    //For login user - WORKS :)
    /*
     * {
     *    "username": "diana30",
     *    "password": "wordPass"
     * }
     */
    public void logout(Context ctx){
        User usrForLogout = ctx.bodyAsClass(User.class);

        //Validation for empty fields
        if(usrForLogout.getUserName() == null || usrForLogout.getUserPassword() == null) {
            ctx.status(400).json("{\"error\":\"Missing username or password :(\"}");
        }

        User logged = usrService.logoutUser(usrForLogout.getUserName(), usrForLogout.getUserPassword());
        //System.out.println(logged);
        if(logged == null){
            ctx.status(401).json("{\"error\":\"This user does not exists :(\"}");
            return;
        }

        boolean isLogged = logged.getIsLogged();

        if(!isLogged) {
            HttpSession sessionLogOut = ctx.req().getSession();
            sessionLogOut.invalidate();
            ctx.status(200).json("{\"message\":\"Great! New user logged out\"}");
        }
        else {
            ctx.status(409).json("{\"error\":\"Incorrect password or username :(\"}");
        }
    }
}
