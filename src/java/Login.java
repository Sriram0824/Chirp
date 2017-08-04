/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author sriram
 */
@ManagedBean
@SessionScoped
public class Login {

     private String userName;
    private String password;

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
    
    
    /**
     * Creates a new instance of Login
     */
    public Login() {
    }
    
     public String ValidateUser()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
        }
        catch (Exception e)
        {
            return (e.getMessage());
        }
       
        final String DB_URL="jdbc:mysql://mis-sql.uhcl.edu/manchirajus5432";
        Connection conn=null;
        Statement stat=null;
        ResultSet rs=null;
        
        try
        {
           conn=DriverManager.getConnection(DB_URL,"manchirajus5432","1447239");
           stat=conn.createStatement();
           
              
           rs=stat.executeQuery("select * from userdetails where Email='"+userName+"' and Password='"+password+"' ");
           if(rs.next())
           {
               OnlineAccount.setUserId(rs.getInt("UserId"));
               OnlineAccount.setFirstName(rs.getString("FirstName"));
               OnlineAccount.setLastName(rs.getString("LastName"));
               OnlineAccount.setUserName(rs.getString("UserName"));
               OnlineAccount.setProfilePicUrl(rs.getString("ProfilePicUrl"));
             //redirects the user to online home page if account is unlocked and username password matches
             return "Home";
           }
           
           else
           {
               //redirects the user back to login page if password username doesnt match
               return "index";
           }
           
          
           
        }
        
        catch(SQLException e)
        {
            
            e.printStackTrace();
            return (e.getMessage());
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
                return (e.getMessage());
            }
            
           
        }
        
       
        
      }
}
