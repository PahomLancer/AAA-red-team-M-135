package main;
import java.util.Date;

import main.auth;

public class account {
	//Старая версия
	/*
		int accountid[] = {0, 1, 2, 3, 4};
		private String accountname[] = {"Andrew", "Vasiliy", "Michail", "Sergey", "Kate" };
		private String accountlogin[] = {"Qwerty", "14822", "664423", "53535", "32323"};
		int accountsalt[];
		int accounthash[];
		int maxid = 4;
		*/
	private auth user;
	private role role;
	private Date start_date;
	private Date end_date;
	private int value;
	//Старая версия
	/*
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
	*/
	//Установка аккаунта
	public void setAccount(auth user, role role, Date start_date, Date end_date, int value) {
        this.user = user;
        this.role = role;
        this.start_date = start_date;
        this.end_date = end_date;
        this.value = value;
    }
	//Вывод аккаунта
    public void showAccount() {
        System.out.println(start_date + " " + end_date + " " + value);
    }
}
