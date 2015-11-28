public class main1 {

	public main1() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//account ouraccount = new account();
		//String say = ouraccount.seelogpass(1);
		//System.out.println(say);
		//String say2 = ouraccount.logging("Andrew", "GGGG");
		//System.out.println(say2);
		//hash hashaccount = new hash();
		//System.out.println(hashaccount.makeHash("gggg", "5555"));
		auth ouraccount1 = new auth();
		ouraccount1.setUser("Andrew", "343421");;
		ouraccount1.printUser(1);
		ouraccount1.setUser("Mihail", "642422");
		ouraccount1.printUser(1);
		//ouraccount1.getUser(ouraccount1);
		ouraccount1.printUser(1);
		String s1 = "H.E.X.L.E.T";
		String s2 = "Java";
		System.out.println(s1.toLowerCase().substring(6, 10));
		}
	}

