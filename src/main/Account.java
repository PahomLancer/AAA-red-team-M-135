package main;
import java.util.Date;

//import main.auth;

import java.util.Calendar;
//import java.util.Date;

public class Account {
    private int id;
    private Role role;
    private User user;
    private Date loginDate;
    private Date logoutDate;
    private int value;
    private Resource resources;

    public Account(int id, role role, user user, resource resources) {
        this.id = id;
        this.role = role;
        this.user = user;
        this.loginDate = Calendar.getInstance().getTime();
        this.logoutDate = null;
        this.value = 0;
        this.resources = resources;
    }

    public void setLogoutDate(Date logoutDate) {
        this.logoutDate = logoutDate;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int returnId() {
        return id;
    }
    public role returnRole() {
        return role;
    }
    public user returnUser() {
        return user;
    }
    public Date returnLoginDate() {
        return loginDate;
    }
    public Date returnLogoutDate() {
        return logoutDate;
    }
    public int returnValue() {
        return value;
    }
    public resource returnResources() {
        return resources;
    }
}
//R1-R2 version
//public class account {

	/*
		int accountid[] = {0, 1, 2, 3, 4};
		private String accountname[] = {"Andrew", "Vasiliy", "Michail", "Sergey", "Kate" };
		private String accountlogin[] = {"Qwerty", "14822", "664423", "53535", "32323"};
		int accountsalt[];
		int accounthash[];
		int maxid = 4;
		*/
	/*
	private auth user;
	private role role;
	private Date start_date;
	private Date end_date;
	private int value;
	*/

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

	/*
	public void setAccount(auth user, role role, Date start_date, Date end_date, int value) {
        this.user = user;
        this.role = role;
        this.start_date = start_date;
        this.end_date = end_date;
        this.value = value;
    }

    public void showAccount() {
        System.out.println(start_date + " " + end_date + " " + value);
    }
}
*/
