package com.company.domains;

public class Resource {
    private int id;
    private String name;
    private Resource id_parent;
    //Set recoursess
    public Resource(int id, String name, Resource id_parent) {
        this.id = id;
        this.name = name;
        this.id_parent = id_parent;
    }
    //Get perem
    public int returnId() {
        return id;
    }
    public String returnName() {
        return name;
    }
    public Resource returnId_parent() {
        return id_parent;
    }
}
