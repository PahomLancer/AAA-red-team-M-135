package main.java.com.company.domains;
//Enum rights
public enum role {
    read("read"), write("write"), execute("execute");
    final String value;
    //Set rights
    role(String value) {
        this.value = value;
    }
    //Converting from string
    public static role fromString(String x) {
        switch (x) {
            case "read":
                return read;
            case "write":
                return write;
            case "execute":
                return execute;
        }
        return null;
    }
    //Return value
    public String returnValue() {
        return value;
    }
}