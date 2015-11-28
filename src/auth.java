public class auth{
	int id = 0;
	int maxid = 0;
	public String login;
	private String password;
	String salt = "qa12ws34ed56rf78tg90";
	public void setUser(String login, String password){
		id = maxid;
		this.login = login;
		this.password = password;
		id++;
		maxid++;
	}
	public int checkUser(auth user){
		if (user.login.equals(this.login))
		{
			if (user.password.equals(this.password))
			{
				return 1;
			}
			return 2;
		}
		return 3;
	}
	public void printUser(int id)
	{
		System.out.println(this.id + " " + this.login + " " + this.password);
	}
}
