package model;

import java.util.ArrayList;
import java.util.List;

public class Task {
    private int id;
    private int points;
    private int status;
    private int group;
    private String name;
    private String description;
    private String dueDate;
    private User creator;
    private User assignedUser;
    private List<Resource> listResource;


    //Default
    public Task() {
        points = 0;
        status = 0;
        group = 0;
        name = "No name";
        description = "No description";
        dueDate = "No due date";
        assignedUser = new User();
        creator = new User();
        listResource = new ArrayList<>();
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

    public List<Resource> getListResource() {
        List<Resource> listReturn = new ArrayList<Resource>();
        for (Resource aResource : this.listResource) {
            listReturn.add(aResource);
        }

        return listReturn;
    }
    public void setListResource(List<Resource> list) {
        for (Resource aResource : list) {
            this.listResource.add(aResource);
        }
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public int getGroup() {
        return group;
    }
    public void setGroup(int group) {
        this.group = group;
    }


    public Boolean isAvailable(){
        Boolean dispo = true;
        if (this.assignedUser!=null) {
            dispo = false;
        }
        return dispo;
    }

}