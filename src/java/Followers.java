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
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author sriram
 */
@ManagedBean
@RequestScoped
public class Followers {

    private ArrayList<FriendDetails> friendDetails=new ArrayList<>();

    public ArrayList<FriendDetails> getFriendDetails() {
        return friendDetails;
    }

    public void setFriendDetails(ArrayList<FriendDetails> friendDetails) {
        this.friendDetails = friendDetails;
    }
    
    /**
     * Creates a new instance of Followers
     */
    public Followers() {
        
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
         
         
         
         //Followers
         rs=stat.executeQuery("select * from userdetails where  UserId  in (select UserId from follow where FollowingId='"+OnlineAccount.getUserId()+"' )");
         while(rs.next())
         {
             friendDetails.add(new FriendDetails(rs.getInt("UserId"),rs.getString("Email"),rs.getString("UserName"),rs.getString("FirstName"),rs.getString("LastName"),rs.getString("ProfilePicURL"),rs.getString("Gender") ));
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
    
}
