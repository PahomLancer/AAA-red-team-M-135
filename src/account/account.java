package account;
public class account {
		int accountid[] = {0, 1, 2, 3, 4};
		private String accountname[] = {"Andrew", "Vasiliy", "Michail", "Sergey", "Kate" };
		private String accountlogin[] = {"Qwerty", "14822", "664423", "53535", "32323"};
		int accountsalt[];
		int accounthash[];
		int maxid = 4;
		
	public String seelogpass(int id){ 
		if (id <= maxid)
		{
			String phrase = id + " " + accountname[id] + " " + accountlogin[id] + " logged in \n";
			return phrase;
		}
		else
		{
			String phrase = "This account id not found! Enter another id!\n";
			return phrase;
		}
	}
	public String logging(String acc, String log)
	{
		String phrase = acc + " " + log;
		return phrase;
	}
	
}
