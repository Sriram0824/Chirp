/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author sriram
 */
@ManagedBean
@RequestScoped
public class Register {

    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private String gender;
    private String country;
     private Date birthDay;
    List<String> countryOptions;

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getCountryOptions() {
        return countryOptions;
    }

   
    //constructor
    public Register() 
    {
        //loading countries from db to arraylist- countryOptions
        countryOptions=new ArrayList<>();
       final String DB_URL="jdbc:mysql://mis-sql.uhcl.edu/manchirajus5432";
       Connection conn=null;
       Statement stat=null;
       ResultSet rs=null;
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            
        }
        
        try
       {
           
         conn=DriverManager.getConnection(DB_URL,"manchirajus5432","1447239");
         stat=conn.createStatement();
         
         rs=stat.executeQuery("select * from Country");
         
         while(rs.next())
         {
             countryOptions.add(rs.getString("Name"));
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
    
    //method to insert user details into registration table
    public String RegisterUser()
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
            return ("InternalError");
        }
       
        try
       {
           
         conn=DriverManager.getConnection(DB_URL,"manchirajus5432","1447239");
         stat=conn.createStatement();
         
         //checking if another user exists with same email id
         rs=stat.executeQuery("select * from users where email='"+email+"'");
         if(rs.next())
         {
             //redirects to user exists page
             return "UserExists";
         }
         
         else
         {
         
         rs=stat.executeQuery("select * from users where UserName='"+userName+"'");
         
         if(rs.next())
         {
             return "ChirpHandleExists";
         }
         
         else
         {
         
        String sql1 = "INSERT INTO users (firstname,lastname,username,password,email,country,gender,isactive)" +"VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql1);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, userName);
        preparedStatement.setString(4, password);
        preparedStatement.setString(5,email);
        preparedStatement.setString(6,country );
        preparedStatement.setString(7,gender);
        preparedStatement.setBoolean(8, true);
        
        preparedStatement.executeUpdate();
        
        //redirects to succesfully registered page
        return("Success");
         }
         
        }
      }
       
       catch(SQLException e)
       {
           e.printStackTrace();
           return(e.getMessage());
           
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
