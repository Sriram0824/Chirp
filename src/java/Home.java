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
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author sriram
 */
@ManagedBean
@RequestScoped
public class Home {
    
private ArrayList<FriendDetails> friendDetails=new ArrayList<>();
private int followers;
private int following;

private int id;
    private String email;
    private String userName;
    private String firstName;
    private String lastName;
    private String profilePicURL;
    private String gender;
    
    private ArrayList<Tweets> userTweets=new ArrayList<Tweets>();

    public ArrayList<Tweets> getUserTweets() {
        return userTweets;
    }

    public void setUserTweets(ArrayList<Tweets> userTweets) {
        this.userTweets = userTweets;
    }
    
    

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getProfilePicURL() {
        return profilePicURL;
    }

    public void setProfilePicURL(String profilePicURL) {
        this.profilePicURL = profilePicURL;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    



    public ArrayList<FriendDetails> getFriendDetails() {
        return friendDetails;
    }

   
    public Home()
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
         
         rs=stat.executeQuery("select * from userdetails where UserId='"+OnlineAccount.getUserId()+"' ");
         if(rs.next())
         {
             firstName=rs.getString("FirstName");
             lastName=rs.getString("LastName");
             userName=rs.getString("userName");
             profilePicURL=rs.getString("ProfilePicUrl");
             
         }
         
         //friend suggestions
         rs=stat.executeQuery("select * from userdetails where UserId <> '"+OnlineAccount.getUserId()+"' and UserId not in (select FollowingId from follow where UserId='"+OnlineAccount.getUserId()+"' ) limit 10");
         while(rs.next())
         {
             friendDetails.add(new FriendDetails(rs.getInt("UserId"),rs.getString("Email"),rs.getString("UserName"),rs.getString("FirstName"),rs.getString("LastName"),rs.getString("ProfilePicURL"),rs.getString("Gender") ));
         }
         
         rs=stat.executeQuery("select count(*) from follow where UserId='"+OnlineAccount.getUserId()+"' ");
         if(rs.next())
         {
         
             following=rs.getInt(1);
         }
         
         rs=stat.executeQuery("select count(*) from follow where FollowingId='"+OnlineAccount.getUserId()+"'");
         if(rs.next())
         {
               followers=rs.getInt(1);
         }
         
         rs=stat.executeQuery("select u.UserName,t.tweetText,t.TweetTime from tweet t natural join userdetails u   where UserId in (select FollowingId from follow where UserId='"+OnlineAccount.getUserId()+"') order by TweetTime desc");
         while(rs.next())
         {
             userTweets.add(new Tweets(rs.getString("UserName"),rs.getString("tweetText"),rs.getDate("TweetTime")));
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
