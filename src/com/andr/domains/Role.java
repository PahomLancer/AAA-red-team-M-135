package com.andr.domains;

public enum Role {

    read("read"), write("write"), execute("execute");

    final String value;

    Role(String value) {
        this.value = value;
    }

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

    public String getValue() {
        return value;
    }
}
