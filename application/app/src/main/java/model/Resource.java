package model;

public class Resource {
    private int id;
    private String name;
    private String description;
    private int group;

    public Resource(){
        name = "";
        description = "";
        group = 0;
    }
    public Resource(String name, String description){
       this.name = name;
       this.description = description;
    }

    public String getDescription(){
        return  description;
    }
    public void setDescription(String value){
        description = value;
    }

    public String getName(){
        return name;
    }
    public void setName (String nom){
        name = nom;
    }

    public int getGroup() {
        return group;
    }
    public void setGroup(int group) {
        this.group = group;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
