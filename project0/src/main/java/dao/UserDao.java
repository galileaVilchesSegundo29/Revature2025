/* Author: Galilea Yanely Vilches Segundo
    Title: User DAO (Data Access Layer)
    Date: February 27th, 2025
    Revature Training
*/
package dao;
//Import User class-------------
import model.User;
//For the Connection with the DB-------------
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//For the Statements-------------
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
//For the List of Users-------------
import java.util.List;
import java.util.ArrayList;


public class UserDao {
    //Variables for the UserDao Class
    private final String jdbcURL;
    private final String dbusr;
    private final String dbpp;

    //Constructor for initializing variables
    public UserDao(String jdbcURL, String dbusr, String dbpp){
        this.jdbcURL = jdbcURL;
        this.dbusr = dbusr;
        this.dbpp = dbpp;
    }

    //Create a new user in the DB: For register a new user - WORKS :)
    public User createUser(User nwUsr){
        //Create a new sql operation
        String insert_nwUsr = """
                INSERT INTO users (username, userpassword, uname, umiddlename, ulastName, useremail, islogged, userRole_id)
                VALUES
                	(?, ?, ?, ?, ?, ?, ?, ?);
        """;

        try{
            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
            PreparedStatement connectStatement = connect.prepareStatement(insert_nwUsr, Statement.RETURN_GENERATED_KEYS);

            //Inserts the parameters into the sql operation
            connectStatement.setString(1, nwUsr.getUserName());
            connectStatement.setString(2, nwUsr.getUserPassword());
            connectStatement.setString(3, nwUsr.getuName());
            connectStatement.setString(4, nwUsr.getuMiddleName());
            connectStatement.setString(5, nwUsr.getuLastName());
            connectStatement.setString(6, nwUsr.getUserEmail());
            connectStatement.setBoolean(7, nwUsr.getIsLogged());
            connectStatement.setInt(8, nwUsr.getUserRole_ID());

            //Executes the sqlOperation
            connectStatement.executeUpdate();

            //Gets the ID generated for the new user
            try{
                ResultSet generatedKeysForId = connectStatement.getGeneratedKeys();
                if(generatedKeysForId.next()){
                    // The columnIndex is 1 because that's the newly inserted ID
                    nwUsr.setId(generatedKeysForId.getInt(1));
                    //System.out.println("\nGreat! The new user is created :)");
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

        return nwUsr;
    }

    //Looks for a user by the userName: For login - WORKS :)
    public User getUserByUserName(String userName){
        //Create a new sql operation
        String lookFor_usrName = """
                SELECT * FROM users WHERE userName = ?
        """;
        User foundedUser = new User();

        try{
            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
            PreparedStatement connectStatement = connect.prepareStatement(lookFor_usrName);

            //Inserts the parameters into the sql operation
            connectStatement.setString(1, userName);

            //Gets the ID generated for the user we are looking for
            try{
                ResultSet generatedKeysForId = connectStatement.executeQuery();
                if(generatedKeysForId.next()){
                    foundedUser.setId(generatedKeysForId.getInt(1));
                    foundedUser.setUserName(generatedKeysForId.getString(2));
                    foundedUser.setUserPassword(generatedKeysForId.getString(3));
                    foundedUser.setUName(generatedKeysForId.getString(4));
                    foundedUser.setUMiddleName(generatedKeysForId.getString(5));
                    foundedUser.setULastName(generatedKeysForId.getString(6));
                    foundedUser.setUserEmail(generatedKeysForId.getString(7));
                    foundedUser.setUserRole_id(generatedKeysForId.getInt(8));
                    foundedUser.setIsLogged(generatedKeysForId.getBoolean(9));
                    //System.out.println("\nLooks like you found a user :)");
                }
                else
                    return null;
            }catch (SQLException e){
                e.printStackTrace();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return foundedUser;
    }

    //Checks if a user already exists in the DB: For register a new user - WORKS :)
    public boolean userAlreadyExists(String userName){
        //Create a new sql operation
        String lookFor_usrName = """
                SELECT * FROM users WHERE userName = ?
        """;
        boolean isInTheDB = false;

        try{
            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
            PreparedStatement connectStatement = connect.prepareStatement(lookFor_usrName);

            //Inserts the parameters into the sql operation
            connectStatement.setString(1, userName);

            try{
                ResultSet generatedKeysForId = connectStatement.executeQuery();
                if(generatedKeysForId.next()) {
                    isInTheDB = true;
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return isInTheDB;
    }

    //Change the userRole in the Users table: For change of information - WORKS :)
    public void changeUserRole(User usrForChange, int nwUserRole_Id){
        //Create a new sql operation
        String updateRole = """
                UPDATE users SET userrole_id = ? WHERE username = ?;
        """;

        try {
            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
            PreparedStatement connectStatement = connect.prepareStatement(updateRole, Statement.RETURN_GENERATED_KEYS);

            //Inserts the parameters into the sql operation
            connectStatement.setInt(1, nwUserRole_Id);
            connectStatement.setString(2, usrForChange.getUserName());

            connectStatement.executeUpdate();

            try{
                ResultSet generatedKeysForId = connectStatement.getGeneratedKeys();
                if(generatedKeysForId.next()) {
                    usrForChange.setUserRole_id(nwUserRole_Id);
                    //System.out.println("\nThe role has changed :)");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Get userRoleId from the userRole Table: For change of role - WORKS :)
//    public int getUserRoleIdFromUsersRoleTable(String role){
//        //Create a new sql operation
//        String lookFor_role = """
//                SELECT * FROM usersrole WHERE userrolename = ?
//        """;
//        int roleId = 0;
//
//        try{
//            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
//            PreparedStatement connectStatement = connect.prepareStatement(lookFor_role);
//
//            //Inserts the parameters into the sql operation
//            connectStatement.setString(1, role);
//
//            try{
//                ResultSet generatedKeysForId = connectStatement.executeQuery();
//                if(generatedKeysForId.next()) {
//                    roleId = generatedKeysForId.getInt(1);
//                    //System.out.println("\nYou found the ID for the role: " + role + " :)");
//                }
//            }catch (SQLException e){
//                e.printStackTrace();
//            }
//        }catch(SQLException e){
//            e.printStackTrace();
//        }
//        return roleId;
//    }

    //Change the userName in the Users table: For change of information  - WORKS :)
    public void changeUserName(User usrForChange, String nwUserName){
        //Create a new sql operation
        String updateUserName = """
                UPDATE users SET userName = ? WHERE username = ?;
        """;

        try {
            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
            PreparedStatement connectStatement = connect.prepareStatement(updateUserName, Statement.RETURN_GENERATED_KEYS);

            //Inserts the parameters into the sql operation
            connectStatement.setString(1, nwUserName);
            connectStatement.setString(2, usrForChange.getUserName());

            connectStatement.executeUpdate();

            try{
                ResultSet generatedKeysForId = connectStatement.getGeneratedKeys();
                if(generatedKeysForId.next()) {
                    usrForChange.setUserName(nwUserName);
                    //System.out.println("\nThe userName has changed :)");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Change the uName in the Users table: For change of information - WORKS :)
    public void changeUName(User usrForChange, String nwUName) {
        //Create a new sql operation
        String updateName = """
            UPDATE users SET uName = ? WHERE username = ?;
        """;

        try {
            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
            PreparedStatement connectStatement = connect.prepareStatement(updateName, Statement.RETURN_GENERATED_KEYS);

            //Inserts the parameters into the sql operation
            connectStatement.setString(1, nwUName);
            connectStatement.setString(2, usrForChange.getUserName());

            connectStatement.executeUpdate();

            try {
                ResultSet generatedKeysForId = connectStatement.getGeneratedKeys();
                if (generatedKeysForId.next()) {
                    usrForChange.setUName(nwUName);
                    //System.out.println("\nThe Name has changed :)");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Change the uName in the Users table: For change of information
    public void changeUserPassword(User usrForChange, String nwUserPassword) {
        //Create a new sql operation
        String updatePP = """
            UPDATE users SET userPassword = ? WHERE username = ?;
        """;

        try {
            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
            PreparedStatement connectStatement = connect.prepareStatement(updatePP, Statement.RETURN_GENERATED_KEYS);

            //Inserts the parameters into the sql operation
            connectStatement.setString(1, nwUserPassword);
            connectStatement.setString(2, usrForChange.getUserName());

            connectStatement.executeUpdate();

            try {
                ResultSet generatedKeysForId = connectStatement.getGeneratedKeys();
                if (generatedKeysForId.next()) {
                    usrForChange.setUserPassword(nwUserPassword);
                    //System.out.println("\nThe Password has changed :)");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Change the MiddleName in the Users table: For change of information - WORKS :)
    public void changeUMiddleName(User usrForChange, String nwUMiddleName) {
        //Create a new sql operation
        String updateMiddleName = """
            UPDATE users SET uMiddleName = ? WHERE username = ?;
        """;

        try {
            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
            PreparedStatement connectStatement = connect.prepareStatement(updateMiddleName, Statement.RETURN_GENERATED_KEYS);

            //Inserts the parameters into the sql operation
            connectStatement.setString(1, nwUMiddleName);
            connectStatement.setString(2, usrForChange.getUserName());

            connectStatement.executeUpdate();

            try {
                ResultSet generatedKeysForId = connectStatement.getGeneratedKeys();
                if (generatedKeysForId.next()) {
                    usrForChange.setUMiddleName(nwUMiddleName);
                    //System.out.println("\nThe Middle Name has changed :)");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Change the Last Name in the Users table: For change of information - WORKS :)
    public void changeULastName(User usrForChange, String nwULastName) {
        //Create a new sql operation
        String updateLastName = """
            UPDATE users SET uLastName = ? WHERE username = ?;
        """;

        try {
            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
            PreparedStatement connectStatement = connect.prepareStatement(updateLastName, Statement.RETURN_GENERATED_KEYS);

            //Inserts the parameters into the sql operation
            connectStatement.setString(1, nwULastName);
            connectStatement.setString(2, usrForChange.getUserName());

            connectStatement.executeUpdate();

            try {
                ResultSet generatedKeysForId = connectStatement.getGeneratedKeys();
                if (generatedKeysForId.next()) {
                    usrForChange.setULastName(nwULastName);
                    //System.out.println("\nThe Last Name has changed :)");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Change the Email in the Users table: For change of information - WORKS :)
    public void changeUserEmail(User usrForChange, String nwUserEmail) {
        //Create a new sql operation
        String updateEmail = """
            UPDATE users SET userEmail = ? WHERE username = ?;
        """;

        try {
            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
            PreparedStatement connectStatement = connect.prepareStatement(updateEmail, Statement.RETURN_GENERATED_KEYS);

            //Inserts the parameters into the sql operation
            connectStatement.setString(1, nwUserEmail);
            connectStatement.setString(2, usrForChange.getUserName());

            connectStatement.executeUpdate();

            try {
                ResultSet generatedKeysForId = connectStatement.getGeneratedKeys();
                if (generatedKeysForId.next()) {
                    usrForChange.setUserEmail(nwUserEmail);
                    //System.out.println("\nThe Email has changed :)");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Change the Logged value in the Users table: For change of information - WORKS :)
    public void changeIsLogged(User usrForChange, boolean nwIsLogged) {
        //Create a new sql operation
        String updateLogged = """
            UPDATE users SET islogged = ? WHERE username = ?;
        """;

        try {
            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
            PreparedStatement connectStatement = connect.prepareStatement(updateLogged, Statement.RETURN_GENERATED_KEYS);

            //Inserts the parameters into the sql operation
            connectStatement.setBoolean(1, nwIsLogged);
            connectStatement.setString(2, usrForChange.getUserName());

            connectStatement.executeUpdate();

            try {
                ResultSet generatedKeysForId = connectStatement.getGeneratedKeys();
                if (generatedKeysForId.next()) {
                    usrForChange.setIsLogged(nwIsLogged);
                    //System.out.println("\nThe Log value has changed to " +  usrForChange.getIsLogged() + ":)");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Get the list of all the existing users - WORKS :)
    public List<User> getAllUsers(){
        //Create a new sql operation
        String showUsers = """
            SELECT * FROM users;
        """;
        List<User> listOfUsers = new ArrayList<>();

        try{
            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
            PreparedStatement connectStatement = connect.prepareStatement(showUsers);

            ResultSet checkUsers = connectStatement.executeQuery();

            while(checkUsers.next()){
                User tempUsr = new User(checkUsers.getInt(1),
                                        checkUsers.getString(2),
                                        checkUsers.getString(3),
                                        checkUsers.getString(4),
                                        checkUsers.getString(5),
                                        checkUsers.getString(6),
                                        checkUsers.getString(7),
                                        checkUsers.getBoolean(9),
                                        checkUsers.getInt(8));
                listOfUsers.add(tempUsr);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return listOfUsers;
    }

    //Looks for a user by the id - WORKS :)
    public User getUserByID(int usrid){
        //Create a new sql operation
        String lookFor_usrID = """
                SELECT * FROM users WHERE id = ?
        """;
        User foundedUser = new User();

        try{
            Connection connect = DriverManager.getConnection(jdbcURL, dbusr, dbpp);
            PreparedStatement connectStatement = connect.prepareStatement(lookFor_usrID);

            //Inserts the parameters into the sql operation
            connectStatement.setInt(1, usrid);

            //Gets the ID generated for the user we are looking for
            try{
                ResultSet generatedKeysForId = connectStatement.executeQuery();
                if(generatedKeysForId.next()){
                    foundedUser.setId(generatedKeysForId.getInt(1));
                    foundedUser.setUserName(generatedKeysForId.getString(2));
                    foundedUser.setUserPassword(generatedKeysForId.getString(3));
                    foundedUser.setUName(generatedKeysForId.getString(4));
                    foundedUser.setUMiddleName(generatedKeysForId.getString(5));
                    foundedUser.setULastName(generatedKeysForId.getString(6));
                    foundedUser.setUserEmail(generatedKeysForId.getString(7));
                    foundedUser.setIsLogged((generatedKeysForId.getBoolean(9)));
                    foundedUser.setUserRole_id(generatedKeysForId.getInt(8));
                    //System.out.println("\nLooks like you found a user :)");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return foundedUser;
    }
}
