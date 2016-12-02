import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class logic {
	//global variables
    private static Connection con;
    private static Statement stmt;
    static ArrayList<accountInfo> listOfAccounts = new ArrayList<accountInfo>(); //creating list to hold accountInfo objects,
    //holding information I got from the database used in queryTable method

    //method to connect to the database
    public static void connect() throws SQLException {//connecting to the database
        // The ODBC Data Source Name (DSN) is example
        //String url = "jdbc:odbc:example";
        String url = "jdbc:mysql://kc-sce-appdb01.kc.umkc.edu/zbtf7";
        String userID = "zbtf7";
        String password = "vSf3EdJfbk";
  
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch(java.lang.ClassNotFoundException e) {
            System.out.println(e);
            System.exit(0);
        }
       
        con = DriverManager.getConnection(url,userID,password);
        stmt = con.createStatement();     
    }
    
    
    public static void cleanup() throws SQLException {
        // Close connection and statement
        // Connections, statements, and result sets are
        // closed automatically when garbage collected
        // but it is a good idea to free them as soon
       // as possible.
        // Closing a statement closes its current result set.
        // Operations that cause a new result set to be
        // created for a statement automatically close
        // the old result set.
        stmt.close();
        con.close();
    }
 
    
    public void createTables() throws SQLException { }
    
	public static void updateFullTable() throws SQLException {
		/*
		 * updating the tables with information from the database
		 * creates a vector to hold accountinfo in 
		 * goes though all of listIfAccounts and takes the accountinfo and adds its to vector
		 * to then add into the tempListOfAcounts vecotor to pass into defaultTableModel
		 * then updates the table with all new information
		 */

		Vector<Object> tempListOfAccounts = new Vector<Object>();
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Account ID");
		columnNames.add("Account Name");
		columnNames.add("Balacnce");

		for (int i = 0; i < listOfAccounts.size(); i++) {
			Vector<Object> aI = new Vector<Object>();
			aI.add(listOfAccounts.get(i).getaccountID());
			aI.add(listOfAccounts.get(i).getaccountName());
			aI.add(listOfAccounts.get(i).getaccountBalance());
			tempListOfAccounts.add(aI);
		}
		DefaultTableModel ndtm = new DefaultTableModel(tempListOfAccounts, columnNames);
		gui.table1.setModel(ndtm);
	}
	
	public static void updateAccoutsOnTable(int account1, int account2){//updating the accounts rows on the table that have changed 
		gui.table1.getModel().setValueAt(listOfAccounts.get(account1 -1).getaccountBalance(), account1 -1, 2);
		gui.table1.getModel().setValueAt(listOfAccounts.get(account2 -1).getaccountBalance(), account2 -1, 2);
	}
		
    
    public static void queryTable() throws SQLException {//getting info from database
    	/*getting account information from database and storing the information in accountinfo objects
    	 * and then storing these objects in listOfAccounts ArrayList.
    	 * */ 
    
    	String sqlSelectCMD = "Select * from account"; 
    	ResultSet rs = stmt.executeQuery(sqlSelectCMD); //sending command to SQL and saving the results into ResultSet rs
    	
    	System.out.println();
    	System.out.println();
    	System.out.println();
    	System.out.println("Result Set:");
    	System.out.println("------------------------");
    	System.out.println();
      
        try {
            ResultSetMetaData rsmd = rs.getMetaData ();    
            int numCols = rsmd.getColumnCount (); 
                        
            for (int i=1; i<=numCols; i++) {
            	System.out.print(rsmd.getColumnLabel(i));
            	System.out.print(" ");
            }
            System.out.println();
        
			while (rs.next()) {//while there is still information in the ResultSetMetaData
				
				//creating a accountInfo object with information we got from the database stored in rs and then putting it into the listOfAccounts
				listOfAccounts.add(new accountInfo(rs.getInt("account_id"), rs.getString("account_name"), rs.getInt("account_balance")));
				
				//debugging
				System.out.print(rs.getInt("account_id"));
				System.out.print("   ");
				System.out.print(rs.getString("account_name"));
				System.out.print("   ");
				System.out.print(rs.getInt("account_balance"));
				System.out.print(" ");
				System.out.println();
			}
		
            System.out.println();
        }
        catch (SQLException ex) {//error handling 
        	System.out.println ("*** SQLException");
            
            while (ex != null) {
            	System.out.println ("SQLState: " + ex.getSQLState ());
            	System.out.println ("Message: " + ex.getMessage ());
            	System.out.println ("Vendor: " + ex.getErrorCode ());
                ex = ex.getNextException ();
                System.out.println ("");
            }
        }
    }
    	

	public static void clearMethod(){//clear the textfields
		gui.fromField.setText("");
		gui.toField.setText("");
		gui.amountField.setText("");
	}
 
	
	public static void transferMoney(int account1, int account2, int amount) throws SQLException{
		/*takes in the accounts to take money and transfer money to along with the amount
		 * creates rollback account balances if the transfer fails
		 * takes money from one account and add it to the other
		 * this information if currently stored in the listOfAccounts objects until the transfer goes through
		 * if the transfer fails then the account balance if rolledback to what it was before it tried to transfer money
		 * 
		 */
		
		
		if (listOfAccounts.get(account1 -1).getaccountBalance() - amount >= 0){//checking to see if there is the proper amount of money to transfer
			int rollbackAccount1 = listOfAccounts.get(account1 -1).getaccountBalance();//holding current account balance to rollback to if the transfer fails
			int rollbackAccount2 = listOfAccounts.get(account2 -1).getaccountBalance();//holding current account balance to rollback to if the transfer fails
			listOfAccounts.get(account1 -1).setaccountBalance(listOfAccounts.get(account1 - 1).getaccountBalance() - amount);//taking money from account1
			listOfAccounts.get(account2 -1).setaccountBalance(listOfAccounts.get(account2 -1).getaccountBalance() + amount);//adding money into account2
			
			try {
				System.out.println("Fdsafdsa");

				con.setAutoCommit(false);//creating transfer i.e not allowing the statments to go through to the database if one of them fails
				System.out.println("UPDATE account " + "SET account_balance = "
						+ String.valueOf(listOfAccounts.get(account1 - 1).getaccountBalance()) + " WHERE account_id = " + String.valueOf(account1));
			
				String updateAccount1CMD = "UPDATE account " + "SET account_balance = "
						+ String.valueOf(listOfAccounts.get(account1 - 1).getaccountBalance()) + " WHERE account_id = " + String.valueOf(account1);
				String updateAccount2CMD = "UPDATE account " + "SET account_balance = "
						+ String.valueOf(listOfAccounts.get(account2 - 1).getaccountBalance()) + " WHERE account_id = " + String.valueOf(account2);
				stmt.executeUpdate(updateAccount1CMD);
				stmt.executeUpdate(updateAccount2CMD);
				
				con.commit();
				updateAccoutsOnTable(account1, account2);
				System.out.println("money sent");

			} catch (SQLException e) {
				System.out.println(e);
				listOfAccounts.get(account1 -1).setaccountBalance(rollbackAccount1);//rolling back account balance
				listOfAccounts.get(account2 -1).setaccountBalance(rollbackAccount2);//rolling back account balance
				updateAccoutsOnTable(account1, account2);
			}
			
			System.out.println(listOfAccounts.get(account1 - 1).getaccountBalance());
			System.out.println(listOfAccounts.get(account2 - 1).getaccountBalance());
					
		}
	}
}
