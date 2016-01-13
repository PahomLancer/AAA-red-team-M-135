package com.company.domains;
//Enum rights
public enum Role {
    read("read"), write("write"), execute("execute");
    final String value;
    //Set rights
    Role(String value) {
        this.value = value;
    }
    //Converting from string
    public static Role fromString(String x) {
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