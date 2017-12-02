package model;

public class Task {
    private int id;
    private int points;
    private String name;
    private String description;
    private String dueDate;
    private User creator;
    private User assignedUser;

    //Default
    public Task() {
        this.points = 0;
        this.name = "No name";
        this.description = "No description";
        this.dueDate = "No due date";
    }

    //getters & setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getPoints(){
        return this.points;
    }
    public void setPoints(int points){
        this.points = points;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public String getDueDate(){
            return this.dueDate;
    }
    public void setDueDate(String dueDate){
            this.dueDate = dueDate;
    }

    public User getCreator(){
        return this.creator;
    }
    public void setCreator(User creator){
        this.creator = creator;
    }

    public User getAssignedUser(){
        return this.assignedUser;
    }
    public void setAssignedUser(User assignedUser){
        this.assignedUser = assignedUser;
    }

    public Boolean isAvailable(){
        Boolean dispo = true;
        if (this.assignedUser!=null) {
            dispo = false;
        }
        return dispo;
    }

}