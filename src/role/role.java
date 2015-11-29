package role;
public class role {
	int id;
	//1 read 2 write 4 execute 3 read-write 5 read-execute 6 write-execute 7 read-write-execute
	private int value;
	public void setRole(int id, String role){
		this.id = id;
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
	public int returnvalue(){
		return this.value;
	}
}
