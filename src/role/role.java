package role;
import auth.auth;
public class role {
	int id;
	//1 read 2 write 4 execute 3 read-write 5 read-execute 6 write-execute 7 read-write-execute
	private int value;
	private auth user;
	private String source;
	public void setRole(int id, auth user, String role, String source){
		this.id = id;
		this.user = user;
		this.source = source;
		switch (role) {
            case "r": this.value = 1;
                    break;
            case "w": this.value = 2;
            		break;
            case "rw": this.value = 3;
    				break;
            case "e": this.value = 4;
    				break;
            case "re": this.value = 5;
    				break;
            case "we": this.value = 6;
    				break;  
            case "rwe": this.value = 7;
    				break;  
            default: this.value = 0;
                     break;
        }
	}
	public void setRole(int id, int role){
		this.id = id;
		this.value = role;
	}
/*	public int checkrole(role role){
		System.out.println(this.source);
        String parse[] = role.source.split("\\.");
        String[] atrStr = this.source.split("\\.");
        if (parse.length >= atrStr.length) {
            for (int i = 0; i < atrStr.length; i++){
            	 if (parse[i].equals(atrStr[i]))
                      continue;
            	 else
            		 System.exit(4);
            	 if (role.value == this.value)
            		 return 1;
            	 else
            		 return 2;
            }
        }
        else
        	return 3;      	 
	}
	*/
	public int returnvalue(){
		return this.value;
	}
	public auth returnuser(){
		return this.user;
	}
}
