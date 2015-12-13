import java.util.Scanner;

import auth.auth;
import role.role;
import account.account;
import work.work;
import role.roles;

import commonscli131.*;
import java.sql.Date;
import java.text.ParseException;
import java.util.Scanner;

public class main1 {
	 public static void main(String[] args) throws ParseException {
	        String login = "";
	        String password = "";
	        String res = "";
	        String rol = "READ";
	        String datestart = "";
	        String dateend = "";
	        String value = "";
	        int arg = 0;

	        auth user1 = new auth();
	        role role1 = new role();
	        account acc1 = new account();
	        //Задаю пользователей
	        auth user[] = new auth[5];
	        for (int i = 0; i < 5; i++)
	            user[i] = new auth();
	        user[0].setUser(1, "Andrew Didenko", "qa12ws34");
	        user[1].setUser(2, "Gleb Gluhov", "0192837465");
	        user[2].setUser(3, "Oleg Shlehtemayer", "zpxocivubyntmrr");
	        user[3].setUser(4, "Vasiliy Kupchinskiy", "uynbjgld");
	        user[4].setUser(5, "Mihail Shamin", "pocxopxc");
	        //Задаю права пользователям
	        role role[] = new role[8];
	        for (int i = 0; i < role.length; i++)
	            role[i] = new role();
	        role[0].setRole(user[0], roles.EXEC, "a.b.c");
	        role[1].setRole(user[0], roles.WRITE, "a.b.c");
	        role[2].setRole(user[0], roles.READ, "a.b.c");
	        role[3].setRole(user[1], roles.READ, "a");
	        role[4].setRole(user[2], roles.READ, "b");
	        role[5].setRole(user[3], roles.READ, "c");
	        role[6].setRole(user[4], roles.READ, "a");
	        role[7].setRole(user[4], roles.WRITE, "a");
	        String auto = "";
	        //Задаю опции
	        Options options = new Options()
	                .addOption("h", false, "print this help message")
	                .addOption("login", true, "login")
	                .addOption("pass", true, "password")
	                .addOption("res", true, "resource")
	                .addOption("role", true, "permission")
	                .addOption("ds", true, "date start")
	                .addOption("de", true, "date end")
	                .addOption("vol", true, "value");

	        CommandLineParser parser = new DefaultParser();
	        try {
	            CommandLine cmd = parser.parse(options, args);

	            if (cmd.hasOption("h")) {
	                Work.printHelp();
	            }
	            if (cmd.hasOption("login")) {
	                login = cmd.getOptionValue("login");
	                arg++;

	            }
	            if (cmd.hasOption("pass")) {
	                pass = cmd.getOptionValue("pass");
	                arg++;
	            }
	            if (cmd.hasOption("res")) {
	                res = cmd.getOptionValue("res");
	                arg++;

	            }
	            if (cmd.hasOption("role")) {
	                if (cmd.getOptionValue("role").equals("EXEC") || cmd.getOptionValue("role").equals("WRITE") || cmd.getOptionValue("role").equals("READ")) {
	                    rol = cmd.getOptionValue("role");
	                    arg++;
	                } else {
	                    System.out.print("Wrong role(3)");
	                    System.exit(3);
	                }

	            }
	            if (cmd.hasOption("ds")) {
	                ds = cmd.getOptionValue("ds");
	                Work.checkDate(ds);
	                arg++;

	            }
	            if (cmd.hasOption("de")) {
	                de = cmd.getOptionValue("de");
	                Work.checkDate(de);
	                arg++;

	            }
	            if (cmd.hasOption("vol")) {
	                vol = cmd.getOptionValue("vol");
	                arg++;
	            }
	        } catch (org.apache.commons.cli.ParseException e) {
	            Work.printHelp();

	        }

	        user1.setUser(login, pass);
	        role1.setRights(user1, Roles.valueOf(rol), res);
	        if (arg == 2) {
	            Work.checkUser(user, user1);

	            System.exit(0);
	        } else if (arg == 4) {
	            Work.checkUser(user, user1);
	            Work.checkRights(role, user1, role1);

	            System.exit(0);
	        } else if (arg == 7) {
	            Work.checkUser(user, user1);
	            Work.checkRights(role, user1, role1);
	            Work.checkVolume(vol);
	            acc1.setAcc(user1, role1, Date.valueOf(ds), Date.valueOf(de), Integer.valueOf(vol));

	            System.exit(0);
	        } else {
	            Work.printHelp();
	        }

	    }
}

//Старая версия
/*
public class main1 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
        int n = 0;
        String login, password, role;
        System.out.println("Enter number of accounts, that you want to add: ");
        if(sc.hasNextInt())
            n = sc.nextInt(); 
        else 
        	System.out.println("Error #1 you didn't insert a number\n");
        String buffer = sc.nextLine();
        auth user[] = new auth[n];
        role roles[] = new role[n];
        for (int i = 0; i < n; i++){
            user[i] = new auth();
            roles[i] = new role();
        }
        for (int i = 0; i < n; i++) {
        	System.out.println("Insert " + i + " user login: ");
        	login = sc.nextLine();
        	System.out.println("Insert " + i + " user password: ");
        	password = sc.nextLine();
        	user[i].setUser(i, login, password);
        	System.out.println("Insert " + i + " user role (r, w, e, rw, re, we, rwe): ");
        	role = sc.nextLine();
        	roles[i].setRole(i, user[i], role, role);
        	}
        for (int i = 0; i < n; i++) {
        	user[i].printUser();
        	System.out.println(i + " user role " + roles[i].returnvalue());
        	}
		}
	}
*/