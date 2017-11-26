package ca.uottawa.cohab;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrateur on 2017-11-23.
 */

public class User {
    private int id;
    private int age;
    private int points;
    private String name;
    private String userName;
    private String password;
    private List<Task> listTask; //Picture???


    public User(int id, int age, String name, String userName, String password) {
        this.id = id;
        this.age = age;
        this.points = 0;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.listTask = new ArrayList<>();
    }

    public User(User user) {
        this.id = user.id;
        this.age = user.age;
        this.points = user.points;
        this.name = user.name;
        this.userName = user.userName;
        this.password = user.password;
        for (Task aTask : user.listTask) {
            this.listTask.add(aTask);
        }
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public int getPoints(){
        return this.points;
    }
    public void setPoints(int points){
        this.points = points;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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