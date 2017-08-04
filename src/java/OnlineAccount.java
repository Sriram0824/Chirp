
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sriram
 */
public class OnlineAccount {
    
     private static String firstName;
    private static String lastName;
    private static int userId;
    private static String ProfilePicUrl;
    private static String userName;
    private static String email;
    private static boolean isLoggedIn = false;

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String firstName) {
        OnlineAccount.firstName = firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        OnlineAccount.lastName = lastName;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        OnlineAccount.userId = userId;
    }

    public static String getProfilePicUrl() {
        return ProfilePicUrl;
    }

    public static void setProfilePicUrl(String ProfilePicUrl) {
        OnlineAccount.ProfilePicUrl = ProfilePicUrl;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        OnlineAccount.userName = userName;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        OnlineAccount.email = email;
    }

    public static boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    public static void setIsLoggedIn(boolean isLoggedIn) {
        OnlineAccount.isLoggedIn = isLoggedIn;
    }

   
    
}
