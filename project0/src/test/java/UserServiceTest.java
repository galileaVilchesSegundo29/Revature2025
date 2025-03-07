/* Author: Galilea Yanely Vilches Segundo
    Title: User Service Test
    Date: March 6th, 2025
    Revature Training
*/

//Import classes-------------
import dao.UserDao;
import model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserDao usrDaoMock;
    private UserService usrService;

    //Create a mock of UserDao and pass it to Service before each test
    @BeforeEach
    void setUpBeforeService(){
        //Create a mockito
        usrDaoMock = Mockito.mock(UserDao.class);
        //Pass the mock into the service
        usrService = new UserService(usrDaoMock);
    }

    @Test
    void registerUser_userAlreadyExists_ReturnNull(){
        //Arrange
        String usrName = "somebody";
        String usrPP = "somePassword";

        //Create a user with the data provided.
        User somebodyExisting = new User();
        somebodyExisting.setUserName(usrName);
        //Looks for a user with these exact userName
        when(usrDaoMock.getUserByUserName(usrName)).thenReturn(somebodyExisting);

        //Act
        User usrResult = usrService.registerNewUser(usrName, usrPP,
                "Jane", "Marie", "Collins",
                "jane44@gmail.com", 1);

        //Asserts
        assertNull(usrResult, "Expected: Null if it exists");
        verify(usrDaoMock).getUserByUserName(usrName);
    }

}
