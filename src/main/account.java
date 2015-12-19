package main;
import java.util.Date;

//import main.auth;

import java.util.Calendar;
//import java.util.Date;

public class account {
    private int id;
    private role role;
    private user user;
    private Date loginDate;
    private Date logoutDate;
    private int value;
    private resource resources;
    //Задаем аккаунт
    public account(int id, role role, user user, resource resources) {
        this.id = id;
        this.role = role;
        this.user = user;
        this.loginDate = Calendar.getInstance().getTime();
        this.logoutDate = null;
        this.value = 0;
        this.resources = resources;
    }
    //Запоминаем разлогирование
    public void setLogoutDate(Date logoutDate) {
        this.logoutDate = logoutDate;
    }
    //Задаем объем
    public void setValue(int value) {
        this.value = value;
    }
    //Возвращаем переменные
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
	//Старая версия
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
	/*
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
*/
