package auth;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException; 

public class auth{
	int id = 0;
	public String login;
	private String password;
	private String salt = "qa12ws34ed56rf78tg90";
	//������ ������������
	public void setUser(String login, String password){
		this.login = login;
		//this.password = password;
		this.password = hash.makeHash(password, salt);
		this.id = 0;
	}
	//�������� ������������
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
	//�������� ������������
	public auth getUser(auth user) {
        user.id = this.id;
        user.password = this.password;
        return user;
    }
	//������� ������������
	public void printUser()
	{
		System.out.println(this.id + " " + this.login + " " + this.password);
	}
}
