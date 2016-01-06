package main.java.com.company;


import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import main.java.com.company.domains.role;

import java.io.PrintWriter;
import java.sql.SQLException;


public class main1 {
    private static final Logger logger = LogManager.getLogger(main1.class);
    private static Option makeOptionWithArgument(String shortName, String description, boolean isRequired) {
        Option result = new Option(shortName, true, description);
        result.setArgs(1);
        result.setRequired(isRequired);
        return result;
    }
    static void printHelp(Options options) {
        final PrintWriter writer = new PrintWriter(System.out);
        final HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp(
                writer,
                80,
                "[program]",
                "Options:",
                options,
                3,
                5,
                "-- HELP --",
                true);
        writer.flush();
    }
    public static int work(String[] args) throws SQLException {
        Options options = new Options()
                .addOption(makeOptionWithArgument("login", "Login name", true))
                .addOption(makeOptionWithArgument("pass", "Password", true))
                .addOption(makeOptionWithArgument("role", "Role", false))
                .addOption(makeOptionWithArgument("res", "Resource name", false))
                .addOption(makeOptionWithArgument("ds", "Start date", false))
                .addOption(makeOptionWithArgument("de", "End date", false))
                .addOption(makeOptionWithArgument("vol", "Volume", false));
        CommandLine commandLine = null;
        try {
            commandLine = new DefaultParser().parse(options, args);
        } catch (ParseException e) {
            printHelp(options);
            return 255;
        }
        logger.warn("***********START***********");
        auth auth = new auth();
        //Аутентификация
        auth.authentication(commandLine.getOptionValue("login"), commandLine.getOptionValue("pass"));
        if (!auth.isCorrectLogin()) {
            logger.warn("Incorrect login");
            return 1;
        }
        if (!auth.isCorrectPass()) {
            logger.warn("Incorrect password");
            return 2;
        }
        if (commandLine.hasOption("role") && commandLine.hasOption("res")) {
            //Авторизация
            role role = role.fromString(commandLine.getOptionValue("role"));
            if (role == null) {
                logger.warn("Incorrect role");
                return 3;
            }
            //Проверка доступа к ресурсу
            auth.res(commandLine.getOptionValue("res"), role);
            if (!auth.isResAccess()) {
                logger.warn("Access denied");
                return 4;
            }
            if (commandLine.hasOption("ds") && commandLine.hasOption("de") && commandLine.hasOption("vol")) {
            	//Аккаунтинг
                auth.checkVolume(commandLine.getOptionValue("vol"));
                auth.checkDate(commandLine.getOptionValue("ds"), commandLine.getOptionValue("de"));
                if (!auth.isCorrectVolume() || !auth.isCorrectDate()) {
                    logger.warn("Incorrect volume or date");
                    return 5;
                }
                auth.insertToAccau(commandLine.getOptionValue("role"));
            }
        }
        return 0;
    }
    public static void main(String[] args) throws SQLException {
        System.exit(work(args));
    }
}

//R1-R2 version
/*
import java.util.Scanner;

import main.auth;

//import commonscli131.*;
//import lib.commons-cli-1.3.1;
import org.apache.commons.cli.*;
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
	        //Тесты
	        auth user[] = new auth[2];
	        for (int i = 0; i < 2; i++)
	            user[i] = new auth();
	        user[0].setUser("jdoe", "sup3rpaZZ");
	        user[1].setUser("jrow", "Qweqrty12");
	        //auth user[] = new auth[5];
	        //for (int i = 0; i < 5; i++)
	        //    user[i] = new auth();
	        //user[0].setUser("Andrew Didenko", "qa12ws34");
	        //user[1].setUser("Gleb Gluhov", "0192837465");
	        //user[2].setUser("Oleg Shlehtemayer", "zpxocivubyntmrr");
	        //user[3].setUser("Vasiliy Kupchinskiy", "uynbjgld");
	        //user[4].setUser("Mihail Shamin", "pocxopxc");
	        //Задаю права пользователям
	        /*role role[] = new role[8];
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
	        */
	        //Тесты
/*
	        role role[] = new role[4];
	        for (int i = 0; i < role.length; i++)
	            role[i] = new role();
	        role[0].setRole(user[0], roles.READ, "a");
	        role[1].setRole(user[0], roles.WRITE, "a.b");
	        role[2].setRole(user[1], roles.EXEC, "a.b.c");
	        role[3].setRole(user[0], roles.EXEC, "a.bc");
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
	                work.printHelp();
	            }
	            if (cmd.hasOption("login")) {
	                login = cmd.getOptionValue("login");
	                arg++;

	            }
	            if (cmd.hasOption("pass")) {
	                password = cmd.getOptionValue("pass");
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
	                datestart = cmd.getOptionValue("ds");
	                work.checkDate(datestart);
	                arg++;

	            }
	            if (cmd.hasOption("de")) {
	                dateend = cmd.getOptionValue("de");
	                work.checkDate(dateend);
	                arg++;

	            }
	            if (cmd.hasOption("vol")) {
	                value = cmd.getOptionValue("vol");
	                arg++;
	            }
	        } catch (org.apache.commons.cli.ParseException e) {
	            work.printHelp();

	        }

	        user1.setUser(login, password);
	        role1.setRole(user1, roles.valueOf(rol), res);
	        if (arg == 2) {
	            work.checkUser(user, user1);

	            System.exit(0);
	        } else if (arg == 4) {
	            work.checkUser(user, user1);
	            work.checkRights(role, user1, role1);

	            System.exit(0);
	        } else if (arg == 7) {
	            work.checkUser(user, user1);
	            work.checkRights(role, user1, role1);
	            work.checkValue(value);
	            acc1.setAccount(user1, role1, Date.valueOf(datestart), Date.valueOf(dateend), Integer.valueOf(value));

	            System.exit(0);
	        } else {
	            work.printHelp();
	        }

	    }
}
*/
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