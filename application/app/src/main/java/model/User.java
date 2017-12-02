package model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private int points;
    private int numberTask;
    private String userName;
    private String password;
    private List<Recompenses> listReward;
    private List<Task> listTask; //Picture???

    //Default constructor
    public User() {
        this.points = 0;
        this.numberTask = 0;
        this.userName = "";
        this.password = "";
        this.listReward = new ArrayList<>();
        this.listTask = new ArrayList<>();
    }
    public User(String userName, String password) {
        this.points = 0;
        this.numberTask = 0;
        this.userName = userName;
        this.password = password;
        this.listReward = new ArrayList<>();
        this.listTask = new ArrayList<>();
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

    public int getNumberTask() {
        return numberTask;
    }
    public void setNumberTask(int numberTask) {
        this.numberTask = numberTask;
    }

    public String getUsername() {
        return userName;
    }
    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public List<Recompenses> getListReward() {
        List<Recompenses> listReturn = new ArrayList<Recompenses>();
        for (Recompenses aReward : this.listReward) {
            listReturn.add(aReward);
        }

        return listReturn;
    }
    public void setListReward(List<Recompenses> list) {
        for (Recompenses aReward : list) {
            this.listReward.add(aReward);
        }
    }

    public List<Task> getListTask() {
        List<Task> listReturn = new ArrayList<Task>();
        for (Task aTask : this.listTask) {
            listReturn.add(aTask);
        }

        return listReturn;
    }
    public void setListTask(List<Task> list) {
        for (Task aTask : list) {
            this.listTask.add(aTask);
        }
    }

    public void addPoints(int pts) {
        points = points + pts;
    }

}