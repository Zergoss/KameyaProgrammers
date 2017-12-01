package model;

public class Recompenses {
    private String name;
    private String description;

    public Recompenses(){
        name = "";
        description = "";
    }

    public Recompenses(String name, String description){
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



