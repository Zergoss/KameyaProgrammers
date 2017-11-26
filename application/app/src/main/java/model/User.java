package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrateur on 2017-11-23.
 */

public class User {
    private int id;
    private int points;
    private String userName;
    private String password;
    private List<Task> listTask; //Picture???

    //Default constructor
    public User() {
    }
    public User(String userName, String password) {
        this.points = 0;
        this.userName = userName;
        this.password = password;
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

    public String getuserName() {
        return userName;
    }
    public void setuserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public List<Task> getListPeople() {
        List<Task> listReturn = new ArrayList<Task>();
        for (Task aTask : this.listTask) {
            listReturn.add(aTask);
        }

        return listReturn;
    }
    public void setListPeople(List<Task> list) {
        for (Task aTask : list) {
            this.listTask.add(aTask);
        }
    }

}