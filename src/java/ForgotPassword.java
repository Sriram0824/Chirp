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
import javax.faces.bean.RequestScoped;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author sriram
 */
@ManagedBean
@RequestScoped
public class ForgotPassword {
    
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    

    /**
     * Creates a new instance of ForgotPassword
     */
    public ForgotPassword()
    {
        
    }
    
    public String ProcessForgotPassword()
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
         rs=stat.executeQuery("select * from userdetails where Email='Manchiraju.sriram@gmail.com'");
         if(rs.next())
         {
             //if email exists
             password=rs.getString("Password");
             String fromAddress, fromAddressPassword, subject, body;
            fromAddress= "msriram35@gmail.com";
            String[] toAddress={email};
            fromAddressPassword= "suharshith";
            subject= "Chirp Password";
            body= "Your Password is " +password;
            sendFromGMail(fromAddress, fromAddressPassword, toAddress, subject, body);
             
         }
         
         else
         {
             //if no such user exists
             
             return "WrongPassword";
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
        
        return "";
    }
    
    public String sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return "Register";
            
        }
        catch (AddressException ae) {
            ae.printStackTrace();
            return ae.getMessage();
        }
        catch (MessagingException me) {
            me.printStackTrace();
            return me.getMessage();
        }
    }
    
    
    
}
