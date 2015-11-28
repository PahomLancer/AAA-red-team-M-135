import java.util.Scanner;
public class main1 {
	public main1() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        //String s1, s2;
        //s1 = sc.nextLine();
        //s2 = sc.nextLine();
		Scanner sc = new Scanner(System.in);
        int n = 0;
        String login, password;
        System.out.println("Enter number of accounts, that you want to add: ");
        if(sc.hasNextInt())
            n = sc.nextInt(); 
        else 
        	System.out.println("Error #1 you didn't insert a number\n");
        String buffer = sc.nextLine();
        auth user[] = new auth[n];
        for (int i = 0; i < n; i++)
            user[i] = new auth();
        for (int i = 0; i < n; i++) {
        	System.out.println("Insert " + i + " user login: ");
        	login = sc.nextLine();
        	System.out.println("Insert " + i + " user password: ");
        	password = sc.nextLine();
        	user[i].setUser(i, login, password);
        	}
        for (int i = 0; i < n; i++) {
        	user[i].printUser();
        	}
		//auth ouraccount1 = new auth();
		//ouraccount1.setUser(1, "Andrew", "343421");;
		//ouraccount1.printUser();
		//ouraccount1.setUser(2, "Mihail", "642422");
		//ouraccount1.printUser();
		//ouraccount1.getUser(ouraccount1);
		//ouraccount1.printUser();
		}
	}

