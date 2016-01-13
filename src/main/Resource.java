package main;

public class Resource {
    private int id;
    private String name;
    private Resource id_parent;

    public Resource(int id, String name, resource id_parent) {
        this.id = id;
        this.name = name;
        this.id_parent = id_parent;
    }

    public int returnId() {
        return id;
    }
    public String returnName() {
        return name;
    }
    public resource returnId_parent() {
        return id_parent;
    }
}
