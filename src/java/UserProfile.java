/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author sriram
 */
@ManagedBean
@RequestScoped
public class UserProfile {
    
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private String newPassword;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Creates a new instance of UserProfile
     */
    public UserProfile() {
        
        final String DB_URL="jdbc:mysql://mis-sql.uhcl.edu/manchirajus5432";
       Connection conn=null;
       Statement stat=null;
       ResultSet rs=null;
       
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
       
        try
       {
           
         conn=DriverManager.getConnection(DB_URL,"manchirajus5432","1447239");
         stat=conn.createStatement();
         
         //checking if another user exists with same email id
         rs=stat.executeQuery("select * from userdetails where UserId='"+OnlineAccount.getUserId()+"'");
         if(rs.next())
         {
             //fetching the user details
             firstName=rs.getString("FirstName");
             lastName=rs.getString("lastName");
             email=rs.getString("Email");
             userName=rs.getString("UserName");
            
         }
         
       }
       catch(SQLException e)
       {
           e.printStackTrace();
           
           
       }
       
       finally
       {
           try
           {
              conn.close();
              stat.close();
              rs.close();
           }
           
           catch(Exception e)
           {
               e.printStackTrace();
              
               
           }
       }
    }
    
    //gets user details from db
    public String ChangePassword()
    {
        
        final String DB_URL="jdbc:mysql://mis-sql.uhcl.edu/manchirajus5432";
       Connection conn=null;
       Statement stat=null;
       ResultSet rs=null;
       
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
       
        try
       {
           
         conn=DriverManager.getConnection(DB_URL,"manchirajus5432","1447239");
         stat=conn.createStatement();
         
         //checking if another user exists with same email id
         rs=stat.executeQuery("select * from userdetails where UserId='"+OnlineAccount.getUserId()+"' and Password='"+password+"'");
         if(rs.next())
         {
             
           String sql="update userdetails set Password=? where Email='"+email+"'";
           
           PreparedStatement p=conn.prepareStatement(sql);
           p.setString(1,newPassword);
           p.executeUpdate();
           
           return "PasswordUpdated";
            
         }
         
         else
         {
             return "WrongPassword";
         }
         
       }
       catch(SQLException e)
       {
           e.printStackTrace();
           return e.getMessage();
       }
       
       finally
       {
           try
           {
              conn.close();
              stat.close();
              rs.close();
           }
           
           catch(Exception e)
           {
               e.printStackTrace();
               return(e.getMessage());
              
               
           }
       }
       
        
    }
}
