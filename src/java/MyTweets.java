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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author sriram
 */
@ManagedBean
@RequestScoped
public class MyTweets {
    
     private ArrayList<Tweets> userTweets = new ArrayList<Tweets>();

    public ArrayList<Tweets> getUserTweets() {
        return userTweets;
    }

    public void setUserTweets(ArrayList<Tweets> userTweets) {
        this.userTweets = userTweets;
    }

    /**
     * Creates a new instance of MyTweets
     */
    public MyTweets() {
        
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/manchirajus5432";
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            
          
        }

        try {

            conn = DriverManager.getConnection(DB_URL, "manchirajus5432", "1447239");
            stat = conn.createStatement();
            
            rs = stat.executeQuery("select u.UserName,t.tweetText,t.TweetTime from tweet t natural join userdetails u   where UserId='" + OnlineAccount.getUserId() + "' order by TweetTime desc");
            while (rs.next()) {
                userTweets.add(new Tweets(rs.getString("UserName"), rs.getString("tweetText"), rs.getDate("TweetTime")));
            }
            //redirects to Home
            

        } catch (SQLException e) {
            e.printStackTrace();
          

        } finally {
            try {
                conn.close();
                stat.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
               
            }
        }

    }
    
}
