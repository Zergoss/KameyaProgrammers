package model;

public class Resource {
    private String name;
    private String description;

    public Resource(){
        name = "";
        description = "";
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

}
