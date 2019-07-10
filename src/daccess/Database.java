package daccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
 

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
       execute("CREATE TABLE IF NOT EXISTS accounts (id integer PRIMARY KEY, Badge integer NOT NULL, FirstName VARCHAR(25), LastName VARCHAR(25), isAdmin Boolean, Approved Boolean, Password VARCHAR(25), Pending Boolean, NFC VARCHAR(30));");
       execute("CREATE TABLE IF NOT EXISTS accessLog (id integer PRIMARY KEY, accountId integer NOT NULL, time text);");
   }
   
   public int createAccount(int badge, String fname, String lname, String password)
   {
	   if (accountExist(badge))
		   return 1;
	   execute("INSERT INTO accounts (Badge, FirstName, LastName, isAdmin, Approved, Password, Pending, NFC) " +
			   				" VALUES (%s ,\"%s\" ,\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\");" ,
			   				new String[] {
			   				Integer.toString(badge), fname, lname, "0" , "0", password, "0", ""
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
   
   /*public void changeApproval(int badge, boolean approval)
   {
   		execute("Update accounts Set approved=" + (approval ? 1 : 0) + " where badge=" + badge);
   }

   public void changeAdmin(int badge, boolean isAdmin)
   {
   		execute("Update accounts Set isAdmin=" + (isAdmin ? 1 : 0) + " where badge=" + badge);
   }*/
   
   //	public Account(int id, int badge, String firstName, String lastName, boolean isApproved, boolean isAdmin, String password)
   //       execute("CREATE TABLE IF NOT EXISTS accounts (id integer PRIMARY KEY, Badge integer NOT NULL, FirstName VARCHAR(25), LastName VARCHAR(25), isAdmin Boolean, Approved Boolean, Password VARCHAR(25));");

   public Account getAccount(int badge)
   {
	   ResultSet r = executeQ("Select * From Accounts where badge = " + badge + " Limit 1;");
	   try {
		if (r.next())
		{
			return new Account(r.getInt("id") , r.getInt("Badge") , r.getString("FirstName") , r.getString("LastName") , r.getBoolean("Approved") , r.getBoolean("isAdmin"), r.getString("Password"), r.getBoolean("Pending"), r.getString("NFC"));
		} else
		{
			return null;
		}
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
   }

   public Account getAccount(String NFC)
   {
	   ResultSet r = executeQ("Select * From Accounts where NFC = \"" + NFC + "\" Limit 1;");
	   try {
		if (r.next())
		{
			return new Account(r.getInt("id") , r.getInt("Badge") , r.getString("FirstName") , r.getString("LastName") , r.getBoolean("Approved") , r.getBoolean("isAdmin"), r.getString("Password"), r.getBoolean("Pending"), r.getString("NFC"));
		} else
		{
			return null;
		}
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
   }
   
   public ArrayList<Account> getAllAccounts()
   {
	   ArrayList<Account> accounts = new ArrayList<Account>();
	   ResultSet r = executeQ("Select * From Accounts Where Pending=1;");
	   try {
		while (r.next())
		{
			accounts.add( new Account(r.getInt("id") , r.getInt("Badge") , r.getString("FirstName") , r.getString("LastName") , r.getBoolean("Approved") , r.getBoolean("isAdmin"), r.getString("Password"), r.getBoolean("Pending"),r.getString("NFC")) );
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	 return accounts;  
   }
   
   public void approveAccount(int badge, boolean isApproved)
   {
	   Account ac = this.getAccount(badge);
	   ac.setApproved(isApproved);
	   ac.setPending(true);
	   this.updateAccount(ac);
   }
   
   public void setAdmin(int badge, boolean isAdmin)
   {
	   Account ac = this.getAccount(badge);
	   ac.setAdmin(isAdmin);
	   this.updateAccount(ac);
   }
   
   public void setApproved(int badge, boolean isApproved)
   {
	   Account ac = this.getAccount(badge);
	   ac.setApproved(isApproved);
	   this.updateAccount(ac);
   }
   
   public ArrayList<Account> getPendingAccounts()
   {
	   ArrayList<Account> accounts = new ArrayList<Account>();
	   ResultSet r = executeQ("Select * From Accounts Where Pending=\"0\";");
	   try {
		while (r.next())
		{
			accounts.add( new Account(r.getInt("id") , r.getInt("Badge") , r.getString("FirstName") , r.getString("LastName") , r.getBoolean("Approved") , r.getBoolean("isAdmin"), r.getString("Password"), r.getBoolean("Pending"),r.getString("NFC")) );
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	 return accounts;  
   }
   
   public void updateAccount(Account acc)
   {
	   execute("Update Accounts Set FirstName=\"%s\", LastName=\"%s\", isAdmin=\"%s\", Approved=\"%s\", Password=\"%s\", Pending=\"%s\", NFC=\"%s\" where badge=%s" ,
  				new String[] {
  				acc.getFirstName(), acc.getLastName(), acc.isAdmin() ? "1" : "0", acc.isApproved()  ? "1" : "0", acc.getPassword(), acc.isPending() ? "1" : "0", acc.getNFC(), Integer.toString(acc.getBadge())
  				});
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
