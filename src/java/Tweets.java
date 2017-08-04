
import java.sql.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sriram
 */
public class Tweets {
    
    private String userTweet;
    private Date tweetTime;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    

    public String getUserTweet() {
        return userTweet;
    }

    public void setUserTweet(String userTweet) {
        this.userTweet = userTweet;
    }

    public Date getTweetTime() {
        return tweetTime;
    }

    public void setTweetTime(Date tweetTime) {
        this.tweetTime = tweetTime;
    }

    
    
    
    
    public Tweets(String UserName,String UserTweet,Date TweetTime)
    {
        userName=UserName;
        userTweet=UserTweet;
        tweetTime=TweetTime;
    }
    
}
