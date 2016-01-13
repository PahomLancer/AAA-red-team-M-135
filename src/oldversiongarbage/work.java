package oldversiongarbage;
//import javax.annotation.processing.SupportedSourceVersion;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
//import java.util.Date;
import java.util.GregorianCalendar;

import main.auth;
import main.role;
/*
public class work {

	static public void check(String in, auth[] user, role[] role) throws ParseException {
    }
	//Проверка пользователя
	static public void checkUser(auth[] user, auth user1) {
        int l = 0;
        int f = 0;
        for (int i = 0; i < user.length; i++) {
            if (user1.checkUser(user[i]) == 1) {
                f = 1;
            }
            if (user1.checkUser(user[i]) == 2) {
                l = 2;
            }
        }
        if (f != 1) {
            if (l == 2) {
            	//Неверный пароль
                System.exit(2);
            }
            if (l == 0) {
            	//Неизвестный логин
                System.exit(1);
            }
        }
    }
	//Проверка прав
	static public void checkRights(role[] role, auth user, role role1) {
        int l = 0;
        for (int i = 0; i < role.length; i++) {
            if (user.checkUser(role[i].getUser()) == 1) {
                if (role[i].checkRights(role1) == 1) {
                    l = 1;
                    break;
                }
            }
        }
        if (l == 0) {
        	//Нет доступа
            System.exit(4);
        }
    }
	//Проверка даты
	static public void checkDate(String str1) {
        try {
            Calendar calendar = new GregorianCalendar();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
            formatter.setLenient(false);
            calendar.setTime(formatter.parse(str1));
        } catch (Exception e) {
        	//Некорректная активность (невалидная дата или объем)
            System.exit(5);
        }
    }
	//Проверка объема
	static public void checkValue(String value) {
        try {
            Integer.parseInt(value);
            if (Integer.valueOf(value) < 0) {
            	//Некорректная активность (невалидная дата или объем)
                System.exit(5);
            }
        } catch (NumberFormatException e) {
        	//Некорректная активность (невалидная дата или объем)
            System.exit(5);
        }
    }
	//Помощь
	static public void printHelp() {
        System.out.print("This is help adviser");
        System.out.println("[-h] - Show help\n" +
                "[-login login] - Enter login\n" +
                "[-pass password] - Enter password\n" +
                "Login may be entered just with the password" +
                "[-res resource] - Enter resource\n" +
                "[-role role] - Enter role\n" +
                "Resource may be entered just with the role" +
                "[-ds date] - Enter date1\n" +
                "[-df date] - Enter date2\n" +
                "[-vol volume] - Enter volume\n" +
                "Dates and volume may be entered just together");
        //Успех
        System.exit(0);
    }
//Старая версия
/*	 static public void check(String in, auth[] user, role[] role) throws ParseException {
		 int i = 0;
		 String[] str1 = in.split(" ");
	        auth user1 = new auth();
	        role role1 = new role();
	        //Accaunt acc1 = new Accaunt();
	        //System.out.println(str1.length);
	        if(str1.length == 2) {
	            user1.setUser(i, str1[0], str1[1]);
	            //checkUser(user, user1);
	            //System.out.println("Succses");
	            System.exit(0);
	        }
	        if(str1.length == 4) {
	            user1.setUser(i, str1[0], str1[1]);
	            //checkUser(user, user1);
	            role rights;
	           // rights = checkRoless(str1[2]);
	 }
	        role1.setRights(user1, rights, str1[3]);
            checkRights(role, user1, role1);
            System.out.println("Succses");
            System.exit(0);
        }
        if(str1.length == 7){
            user1.setUser(str1[0], str1[1]);
            checkUser(user, user1);
            Roles rights;
            rights = checkRoless(str1[2]);
            role1.setRights(user1, rights, str1[3]);
            checkRights(role, user1, role1);
            checkDate(str1[4]);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = format.parse(str1[4]);
            checkDate(str1[5]);
            //SimpleDateFormat format2 = new SimpleDateFormat("yyyy.MM.dd");
            Date date2 = format.parse(str1[5]);
            int vol = Integer.valueOf(str1[6]);
            checkVolume(vol);

            acc1.setAcc(user1,role1,date1,date2,vol);
            acc1.showAcc();
            System.out.println("Succses");
            System.exit(0);
           // acc1.showAcc();
        }
        else
        {
            System.out.println();
        }

    }

    static public void checkUser(User[] user, User user1){

        int l = 0;
        int f = 0;
        for(int i = 0; i < user.length; i++) {
            if (user1.checkUser(user[i]) == 1) {
                System.out.println("Succes");
                f = 1;
            }
            if(user1.checkUser(user[i]) == 2){
                l = 2;
            }
        }
        if(f != 1) {
            if (l == 2) {
                System.out.println("Wrong password");
                System.exit(2);
            }
            if (l == 0) {
                System.out.println("Wrong login");
                System.exit(1);
            }
        }
        }

    static public void checkRights(Role[] role, User user, Role role1){
        int l = 0;
        for(int i = 0; i < role.length; i++)
        {
            if(user.checkUser(role[i].getUser()) == 1) {
                if (role[i].checkRights(role1) == 1) {
                    System.out.println("Succes11");
                    l = 1;
                    break;
                }
            }
        }
        if(l == 0)
        {
            System.out.println("No dostyp");
            System.exit(4);
        }
    }

    static public void checkDate(String str1)
    {
        try {
            Calendar calendar = new GregorianCalendar();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
            formatter.setLenient(false);
            calendar.setTime(formatter.parse(str1));



        }
        catch (Exception e) {
            System.exit(5);
        }



    }

    static public void checkVolume(int vol){
        if(vol <= 0)
        {
            System.out.println("invalid vol");
            System.exit(5);
        }
    }

    static public Roles checkRoless(String s){
        if (s.equals("READ"))
        {
            return Roles.READ;
        }
        if (s.equals("EXEC"))
        {
            return Roles.EXEC;
        }
        if(s.equals("WRITE"))
        {
            return Roles.WRITE;
        }
        else {
            System.exit(3);
            return Roles.READ;

        }


    }
    */
//}
