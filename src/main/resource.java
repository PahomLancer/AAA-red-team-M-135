package main;

public class resource {
    private int id;
    private String name;
    private resource id_parent;
    //������ �������
    public resource(int id, String name, resource id_parent) {
        this.id = id;
        this.name = name;
        this.id_parent = id_parent;
    }
    //���������� ����������
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