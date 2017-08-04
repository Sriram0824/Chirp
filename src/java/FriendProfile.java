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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author sriram
 */
@ManagedBean
@RequestScoped
public class FriendProfile {

    private int UserId;
    private int id;
    private String email;
    private String userName;
    private String firstName;
    private String lastName;
    private String profilePicURL;
    private String gender;
    private String buttonValue;
    private int followers;
    private int following;
    private ArrayList<FriendDetails> friendDetails=new ArrayList<>();
    private String message;
    private ArrayList<Conversation>conversation=new ArrayList<>();
    private int friendId;
     private ArrayList<Tweets> userTweets=new ArrayList<Tweets>();

    public ArrayList<Tweets> getUserTweets() {
        return userTweets;
    }

    public void setUserTweets(ArrayList<Tweets> userTweets) {
        this.userTweets = userTweets;
    }

    private String error_message;

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
           
    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }
    
    public int getFriendId() {
        return friendId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
   
    public ArrayList<Conversation> getConversation() {
        return conversation;
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

    public ArrayList<FriendDetails> getFriendDetails() {
        return friendDetails;
    }

    public void setFriendDetails(ArrayList<FriendDetails> friendDetails) {
        this.friendDetails = friendDetails;
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
     
    public String getButtonValue() {
        return buttonValue;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
     
    /**
     * Creates a new instance of FriendProfile
     */  
    
    public FriendProfile() 
    {
        
    
    }
        
    public String ViewFriendProfile()
    {
        
        HttpServletRequest request = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest());
        friendId=Integer.parseInt(request.getParameter("friendId"));
        
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
         
         
         rs=stat.executeQuery("select u.UserName,t.tweetText,t.TweetTime from tweet t natural join userdetails u   where UserId='"+friendId+"' order by TweetTime desc");
         while(rs.next())
         {
             userTweets.add(new Tweets(rs.getString("UserName"),rs.getString("tweetText"),rs.getDate("TweetTime")));
         }
         
         rs=stat.executeQuery("select * from userdetails where UserId='"+friendId+"'");
         if(rs.next())
         {
             //fetching the user details
            
             userName=rs.getString("UserName");
            
         }
         
         rs=stat.executeQuery("select count(*) from follow where UserId='"+friendId+"' ");
         if(rs.next())
         {
         
             following=rs.getInt(1);
         }
         
         rs=stat.executeQuery("select count(*) from follow where FollowingId='"+friendId+"'");
         if(rs.next())
         {
               followers=rs.getInt(1);
         }
         
         
         
         //finding if he already follows the friend or not
         rs=stat.executeQuery("select * from follow where UserId='"+OnlineAccount.getUserId()+"' and FollowingId='"+friendId+"'");
         if(rs.next())
         {
              buttonValue="UnFollow";
             
         }
         
         else
         {
              buttonValue="Follow";
         }
         
         return "FriendProfile";
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
               return e.getMessage();
           }
       }
         
    }
    
    public String Follow()
    {
        
        HttpServletRequest request = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest());
        friendId=Integer.parseInt(request.getParameter("friendId"));
       
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
            return e.getMessage();
        }
       
        try
       {
           
         conn=DriverManager.getConnection(DB_URL,"manchirajus5432","1447239");
         stat=conn.createStatement();
         
        rs=stat.executeQuery("select * from follow where UserId='"+OnlineAccount.getUserId()+"' and FollowingId='"+friendId+"'");
         if(rs.next())
         {
             
             //unfollowing the existing friend
             int r=stat.executeUpdate("delete from follow where UserId='"+OnlineAccount.getUserId()+"' and FollowingId='"+friendId+"'");
              
              
                  //Making buttonvalue follow
                    buttonValue="Follow"; 
              
             
         }
         
         else
         {
         
         
             //we insert a new row in to following table if  userId doesnot exist in the table 
        String sql1 = "INSERT INTO follow (UserId,FollowingId)" +"VALUES (?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql1);
        preparedStatement.setInt(1,OnlineAccount.getUserId());
        preparedStatement.setInt(2, friendId);
        preparedStatement.executeUpdate();
        
        //Making buttonvalue unfollow
                    buttonValue="UnFollow"; 
        
        
          }
         
         rs=stat.executeQuery("select u.UserName,t.tweetText,t.TweetTime from tweet t natural join userdetails u   where UserId='"+friendId+"' order by TweetTime desc");
         while(rs.next())
         {
             userTweets.add(new Tweets(rs.getString("UserName"),rs.getString("tweetText"),rs.getDate("TweetTime")));
         }
         
         rs=stat.executeQuery("select * from userdetails where UserId='"+friendId+"'");
         if(rs.next())
         {
             //fetching the user details
            
             userName=rs.getString("UserName");
            
         }
         
         rs=stat.executeQuery("select count(*) from follow where UserId='"+friendId+"' ");
         if(rs.next())
         {
         
             following=rs.getInt(1);
         }
         
         rs=stat.executeQuery("select count(*) from follow where FollowingId='"+friendId+"'");
         if(rs.next())
         {
               followers=rs.getInt(1);
         }
         
        return "FriendProfile";
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
              return e.getMessage();
           }
       }
       
        
    }
    
    //view Message Page
    public String ViewMessage()
    {
      HttpServletRequest request = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest());
      friendId=Integer.parseInt(request.getParameter("friendId"));
      
     
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
            return e.getMessage();
        }
       
        try
       {
           
             conn = DriverManager.getConnection(DB_URL,"manchirajus5432","1447239");
             stat = conn.createStatement();
             rs=stat.executeQuery("select * from userdetails where UserId='"+friendId+"'");
            if(rs.next())
                {
                //fetching the user details
            
                 userName=rs.getString("UserName");
            
                }
             rs=stat.executeQuery("SELECT u2.NumberOfMessages,u1.username,u2.message FROM userdetails u1 natural join message u2 WHERE (u2.userid = '"+OnlineAccount.getUserId()+"' and u2.friendid = '"+friendId+"') or (u2.userid = '"+friendId+"' and u2.friendid = '"+OnlineAccount.getUserId()+"') order by messagetime ASC");
             while(rs.next())
             {
                 conversation.add(new Conversation(rs.getInt("NumberOfMessages"),rs.getString("UserName"),rs.getString("Message")));
             }
             
             return "Message.xhtml";
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
               
               return e.getMessage();
                       //"Message.xhtml";
                       //e.getMessage();
           }
       }
    }
    //Messaging
  public void Message()
  {
      
      HttpServletRequest request = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest());
      friendId=Integer.parseInt(request.getParameter("friendId"));
      
      
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
         
            rs=stat.executeQuery("select * from userdetails where UserId='"+friendId+"'");
            if(rs.next())
                {
                //fetching the user details
            
                 userName=rs.getString("UserName");
                }
         
             String sql1 = "Insert into message (UserId,FriendID,Message)"+"Values(?,?,?)";
             PreparedStatement preparedStatement = conn.prepareStatement(sql1);
             preparedStatement.setInt(1,OnlineAccount.getUserId());
             preparedStatement.setInt(2,friendId);
             preparedStatement.setString(3,message);
             preparedStatement.executeUpdate();
             
             message="";
           rs=stat.executeQuery("SELECT u2.NumberOfMessages,u1.username,u2.message FROM userdetails u1 natural join message u2 WHERE (u2.userid = '"+OnlineAccount.getUserId()+"' and u2.friendid = '"+friendId+"') or (u2.userid = '"+friendId+"' and u2.friendid = '"+OnlineAccount.getUserId()+"') order by messagetime ASC");
             while(rs.next())
             {
                 conversation.add(new Conversation(rs.getInt("NumberOfMessages"),rs.getString("UserName"),rs.getString("Message")));
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
