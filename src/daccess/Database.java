package daccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 

public class Database {

	Connection conn;
	
	public Database()
	{
		connect();
		prepareDatabase();
	}
	
   public void connect() {
       conn = null;
       try {
           // db parameters
           String url = "jdbc:sqlite:daccess.db";
           // create a connection to the database
           conn = DriverManager.getConnection(url);
           System.out.println("Connection to SQLite has been established.");
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }    
   }
   
   public void prepareDatabase()
   {
       execute("CREATE TABLE IF NOT EXISTS accounts (id integer PRIMARY KEY, Badge integer NOT NULL, FirstName VARCHAR(25), LastName VARCHAR(25), isAdmin Boolean, Approved Boolean, Password VARCHAR(25));");
       execute("CREATE TABLE IF NOT EXISTS accessLog (id integer PRIMARY KEY, accountId integer NOT NULL, time text);");
   }
   
   public int createAccount(int badge, String fname, String lname, String password)
   {
	   if (accountExist(badge))
		   return 1;
	   execute("INSERT INTO accounts (Badge, FirstName, LastName, isAdmin, Approved, Password) " +
			   				" VALUES (%s ,\"%s\" ,\"%s\", \"%s\", \"%s\", \"%s\");" ,
			   				new String[] {
			   				Integer.toString(badge), fname, lname, "0" , "0", password
			   				});
	return 0;
   }
  
   public boolean accountExist(int badge)
   {
	   ResultSet r = executeQ("Select badge From Accounts where badge = " + badge + " Limit 1;");
	   try {
		return r.next();
	} catch (SQLException e) {
		e.printStackTrace();
		return false;
	}
   }

   public int verifyPassword(int badge, String pass)
   {
	   ResultSet r = executeQ("Select Password From Accounts where badge = " + badge + " Limit 1;");
	   try {
		if (r.next())
		{
			String oPass = r.getString(1);
			return (pass.equals(oPass) ? 0 : 1);
		} else
		{
			return 2;
		}
	} catch (SQLException e) {
		e.printStackTrace();
		return 2;
	}
   }
   
   
   
   public void execute(String qry)
   {
       try
       {       
          Statement stmt = conn.createStatement();
          System.out.println("DB Qr: " + qry);
          stmt.execute(qry);
       } catch (SQLException e) {
           System.out.println(e.getMessage());
           e.printStackTrace();
       }   
   }
   
   public ResultSet executeQ(String qry)
   {
       try
       {       
          Statement stmt = conn.createStatement();
          System.out.println("DB Qr: " + qry);
          return stmt.executeQuery(qry);
       } catch (SQLException e) {
           System.out.println(e.getMessage());
           e.printStackTrace();
       }
	return null;   
   }  
   
   public void execute(String qry, Object data[])
   {
	   String command = String.format(qry, data);
	   execute(command);
   }
   
   public ResultSet executeQ(String qry, Object data[])
   {
	   String command = String.format(qry, data);
	   return executeQ(command);
   }
   	
}
