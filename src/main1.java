import java.util.Scanner;

import auth.auth;
import role.role;
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

