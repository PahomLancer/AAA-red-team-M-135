package com.andr.domains;

public class Resource {

    private int id;

    private String name;

    private Resource id_parent;

    public Resource(int id, String name, Resource id_parent) {
        this.id = id;
        this.name = name;
        this.id_parent = id_parent;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Resource getId_parent() {
        return id_parent;
    }

}
