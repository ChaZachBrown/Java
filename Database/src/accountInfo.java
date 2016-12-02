
public class accountInfo {
	/*holds the information for each account. 
	 * each account will then be stored in listOfAccounts arrayList
	 */
	private int accountID;
	private String accountName;
	private int accountBalance;
	
	
	//constructor1************************
	accountInfo(int aID, String aN, int aB){
		accountID = aID;
		accountName = aN;
		accountBalance = aB;
	}
	
	//constructor2************************
	accountInfo(){
		
	}
	
	
	//setters******************************
	public void setaccountID(int i){
		accountID = i;
	}
	public void setaccountName(String s){
		accountName = s;
	}
	public void setaccountBalance(int i){
		accountBalance = i;
	}
	
	
	//getters ******************************
	public int getaccountID(){
		return accountID;
	}
	public String getaccountName(){
		return accountName;
	}
	public int getaccountBalance(){
		return accountBalance;
	}
	
}
