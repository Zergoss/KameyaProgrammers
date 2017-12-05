package sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Recompenses;
import model.Resource;
import model.Task;
import model.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 8;

    // Database Name
    private static final String DATABASE_NAME = "DataManager.db";

    // Table name
    private static final String TABLE_USER = "user";
    private static final String TABLE_TASK = "task";
    private static final String TABLE_REWARD = "reward";
    private static final String TABLE_RESOURCE = "resource";
    private static final String TABLE_TASK_RESOURCE = "task_resource";


    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_POINTS = "user_points";
    private static final String COLUMN_USER_USERNAME = "user_name";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    private static final String COLUMN_TASK_ID = "task_id";
    private static final String COLUMN_TASK_POINTS = "task_points";
    private static final String COLUMN_TASK_NAME = "task_name";
    private static final String COLUMN_TASK_DESCRIPTION = "task_description";
    private static final String COLUMN_TASK_DUEDATE = "task_dueDate";
    private static final String COLUMN_TASK_CREATOR = "task_creator";
    private static final String COLUMN_TASK_ASSIGNEDUSER = "task_assignedUser";
    private static final String COLUMN_TASK_STATUS = "task_status";
    private static final String COLUMN_TASK_GROUP = "task_group";


    private static final String COLUMN_REWARD_ID = "reward_id";
    private static final String COLUMN_REWARD_NAME = "reward_name";
    private static final String COLUMN_REWARD_DESCRIPTION = "reward_description";
    private static final String COLUMN_REWARD_ASSIGNEDUSER = "reward_assignedUser";

    private static final String COLUMN_RESOURCE_ID = "resource_id";
    private static final String COLUMN_RESOURCE_NAME = "resource_name";
    private static final String COLUMN_RESOURCE_DESCRIPTION = "resource_description";
    private static final String COLUMN_RESOURCE_GROUP = "resource_group";

    private static final String COLUMN_TASK_RESOURCE_ID = "task_resource_id";
    private static final String COLUMN_REF_TASK_ID = "ref_task_id";
    private static final String COLUMN_REF_RESOURCE_ID = "ref_resource_id";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_POINTS + " INTEGER,"
            + COLUMN_USER_USERNAME + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT" + ")";


    private String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASK + "("
            + COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_TASK_POINTS + " INTEGER,"
            + COLUMN_TASK_NAME + " TEXT,"
            + COLUMN_TASK_DESCRIPTION + " TEXT,"
            + COLUMN_TASK_DUEDATE + " TEXT,"
            + COLUMN_TASK_STATUS + " INTEGER,"
            + COLUMN_TASK_GROUP + " INTEGER,"
            + COLUMN_TASK_CREATOR + " INTEGER,"
            + COLUMN_TASK_ASSIGNEDUSER + " INTEGER,"
            + "CONSTRAINT fk_creator "
            + "FOREIGN KEY (" + COLUMN_TASK_CREATOR + ") REFERENCES " + TABLE_USER +"(" + COLUMN_USER_ID + ") "
            + "ON DELETE SET NULL,"
            + "CONSTRAINT fk_assign "
            + "FOREIGN KEY (" + COLUMN_TASK_ASSIGNEDUSER + ") REFERENCES " + TABLE_USER +"(" + COLUMN_USER_ID + ") "
            + "ON DELETE SET NULL)";

    private String CREATE_REWARD_TABLE = "CREATE TABLE " + TABLE_REWARD + "("
            + COLUMN_REWARD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_REWARD_NAME + " TEXT,"
            + COLUMN_REWARD_DESCRIPTION + " TEXT,"
            + COLUMN_REWARD_ASSIGNEDUSER + " INTEGER,"
            + "CONSTRAINT fk_user "
            + "FOREIGN KEY (" + COLUMN_REWARD_ASSIGNEDUSER + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + ") ON DELETE CASCADE)";

    private String CREATE_RESOURCE_TABLE = "CREATE TABLE " + TABLE_RESOURCE + "("
            + COLUMN_RESOURCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_RESOURCE_NAME + " TEXT,"
            + COLUMN_RESOURCE_DESCRIPTION + " TEXT,"
            + COLUMN_RESOURCE_GROUP + " INTEGER)";

    private String CREATE_TASK_RESOURCE_TABLE = "CREATE TABLE " + TABLE_TASK_RESOURCE + "("
            + COLUMN_TASK_RESOURCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_REF_TASK_ID + " INTEGER,"
            + COLUMN_REF_RESOURCE_ID + " INTEGER,"
            + "FOREIGN KEY (" + COLUMN_REF_TASK_ID + ") REFERENCES " + TABLE_TASK + "(" + COLUMN_TASK_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY (" + COLUMN_REF_RESOURCE_ID + ") REFERENCES " + TABLE_RESOURCE + "(" + COLUMN_RESOURCE_ID + ") ON DELETE CASCADE)";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private String DROP_TASK_TABLE = "DROP TABLE IF EXISTS " + TABLE_TASK;
    private String DROP_REWARD_TABLE = "DROP TABLE IF EXISTS " + TABLE_REWARD;
    private String DROP_RESOURCE_TABLE = "DROP TABLE IF EXISTS " + TABLE_RESOURCE;
    private String DROP_TASK_RESOURCE_TABLE = "DROP TABLE IF EXISTS " + TABLE_TASK_RESOURCE;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_TASK_TABLE);
        db.execSQL(CREATE_REWARD_TABLE);
        db.execSQL(CREATE_RESOURCE_TABLE);
        db.execSQL(CREATE_TASK_RESOURCE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_TASK_TABLE);
        db.execSQL(DROP_REWARD_TABLE);
        db.execSQL(DROP_RESOURCE_TABLE);
        db.execSQL(DROP_TASK_RESOURCE_TABLE);

        // Create tables again
        onCreate(db);

    }


    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_USERNAME, user.getUsername());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_POINTS, user.getPoints());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }
    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_POINTS, task.getPoints());
        values.put(COLUMN_TASK_NAME, task.getName());
        values.put(COLUMN_TASK_DESCRIPTION, task.getDescription());
        values.put(COLUMN_TASK_DUEDATE, task.getDueDate());
        values.put(COLUMN_TASK_CREATOR, task.getCreator().getId());
        values.put(COLUMN_TASK_ASSIGNEDUSER, task.getAssignedUser().getId());
        values.put(COLUMN_TASK_STATUS, task.getStatus());
        values.put(COLUMN_TASK_GROUP, task.getGroup());

        // Inserting Row
        db.insert(TABLE_TASK, null, values);
        db.close();
    }
    public void addReward(Recompenses reward){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_REWARD_NAME, reward.getName());
        values.put(COLUMN_REWARD_DESCRIPTION, reward.getDescription());
        values.put(COLUMN_REWARD_ASSIGNEDUSER, reward.getUser().getId());

        // Inserting Row
        db.insert(TABLE_REWARD, null, values);
        db.close();
    }
    public void addResource(Resource resource){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_RESOURCE_NAME, resource.getName());
        values.put(COLUMN_RESOURCE_DESCRIPTION, resource.getDescription());
        values.put(COLUMN_RESOURCE_GROUP, resource.getGroup());

        // Inserting Row
        db.insert(TABLE_RESOURCE, null, values);
        db.close();
    }
    public void addTaskResource(Task task, Resource res){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_REF_TASK_ID, task.getId());
        values.put(COLUMN_REF_RESOURCE_ID, res.getId());

        // Inserting Row
        db.insert(TABLE_TASK_RESOURCE, null, values);
        db.close();
    }


    //Return User, Task
    public User getUser(String username) {

        SQLiteDatabase db = this.getReadableDatabase();
        User user = new User();

        String query =("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_USERNAME + " = '" + username + "'");
        Cursor cursor = db.rawQuery(query, null);

        // Traversing through a column and all rows adding to user
        if (cursor.moveToFirst()) {
            user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
            user.setPoints(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_POINTS)));
            user.setNumberTask(checkNumberTask(user));
            user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USER_USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
            user.setListReward(getRewardOf(user));
        }

        cursor.close();
        db.close();

        return user;
    }
    public User getUser(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        User user = new User();

        String query =("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = '" + id + "'");
        Cursor cursor = db.rawQuery(query, null);

        // Traversing through a column and all rows adding to user
        if (cursor.moveToFirst()) {
            user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
            user.setPoints(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_POINTS)));
            user.setNumberTask(checkNumberTask(user));
            user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USER_USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
            user.setListReward(getRewardOf(user));
        }

        cursor.close();
        db.close();

        return user;
    }
    public Task getTask(String name) {

        SQLiteDatabase db = this.getReadableDatabase();
        Task task = new Task();

        String query =("SELECT * FROM " + TABLE_TASK + " WHERE " + COLUMN_TASK_NAME + " = '" + name + "'");
        Cursor cursor = db.rawQuery(query, null);

        // Traversing through a column and all rows adding to user
        if (cursor.moveToFirst()) {
            task.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ID)));
            task.setPoints(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_POINTS)));
            task.setName(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME)));
            task.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION)));
            task.setDueDate(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DUEDATE)));
            task.setAssignedUser(getUser(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ASSIGNEDUSER))));
            task.setCreator(getUser(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_CREATOR))));
            task.setStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_STATUS)));
            task.setGroup(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_GROUP)));
        }
        cursor.close();
        db.close();

        return task;
    }
    public Task getTask(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Task task = new Task();

        String query =("SELECT * FROM " + TABLE_TASK + " WHERE " + COLUMN_TASK_ID + " = '" + id + "'");
        Cursor cursor = db.rawQuery(query, null);

        // Traversing through a column and all rows adding to user
        if (cursor.moveToFirst()) {
            task.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ID)));
            task.setPoints(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_POINTS)));
            task.setName(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME)));
            task.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION)));
            task.setDueDate(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DUEDATE)));
            task.setAssignedUser(getUser(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ASSIGNEDUSER))));
            task.setCreator(getUser(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_CREATOR))));
            task.setStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_STATUS)));
            task.setGroup(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_GROUP)));
        }
        cursor.close();
        db.close();

        return task;
    }
    public Recompenses getReward(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Recompenses reward = new Recompenses();

        String query =("SELECT * FROM " + TABLE_REWARD + " WHERE " + COLUMN_REWARD_ID + " = '" + id + "'");
        Cursor cursor = db.rawQuery(query, null);

        // Traversing through a column and all rows adding to user
        if (cursor.moveToFirst()) {
            reward.setName(cursor.getString(cursor.getColumnIndex(COLUMN_REWARD_NAME)));
            reward.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_REWARD_DESCRIPTION)));
            reward.setUser(getUser(cursor.getInt(cursor.getColumnIndex(COLUMN_REWARD_ASSIGNEDUSER))));
        }
        cursor.close();
        db.close();

        return reward;
    }
    public Resource getResource(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Resource resource = new Resource();

        String query =("SELECT * FROM " + TABLE_RESOURCE + " WHERE " + COLUMN_RESOURCE_ID + " = '" + id + "'");
        Cursor cursor = db.rawQuery(query, null);

        // Traversing through a column and all rows adding to user
        if (cursor.moveToFirst()) {
            resource.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_RESOURCE_ID)));
            resource.setName(cursor.getString(cursor.getColumnIndex(COLUMN_RESOURCE_NAME)));
            resource.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_RESOURCE_DESCRIPTION)));
            resource.setGroup(cursor.getInt(cursor.getColumnIndex(COLUMN_RESOURCE_GROUP)));
        }
        cursor.close();
        db.close();

        return resource;
    }


    //Return list of all user
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_POINTS,
                COLUMN_USER_USERNAME,
                COLUMN_USER_PASSWORD
        };

        String sortOrder =
                COLUMN_USER_USERNAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, columns, null, null, null, null, sortOrder);

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setPoints(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_POINTS)));
                user.setNumberTask(checkNumberTask(user));
                user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USER_USERNAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return userList;
    }
    public List<User> getAllUser(User profile) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_POINTS,
                COLUMN_USER_USERNAME,
                COLUMN_USER_PASSWORD
        };

        String sortOrder =
                COLUMN_USER_USERNAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, columns, null, null, null, null, sortOrder);

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setPoints(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_POINTS)));
                user.setNumberTask(checkNumberTask(user));
                user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USER_USERNAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding user record to list
                if(user.getId()!=profile.getId()){
                    userList.add(user);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return userList;
    }
    public List<Task> getAllTask() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_TASK_ID,
                COLUMN_TASK_POINTS,
                COLUMN_TASK_NAME,
                COLUMN_TASK_DESCRIPTION,
                COLUMN_TASK_DUEDATE,
                COLUMN_TASK_CREATOR,
                COLUMN_TASK_ASSIGNEDUSER
        };
        // sorting orders
        String sortOrder =
                COLUMN_TASK_NAME + " ASC";
        List<Task> taskList = new ArrayList<Task>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        Cursor cursor = db.query(TABLE_TASK, columns, null, null, null, null, sortOrder);


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_ID))));
                task.setPoints(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_POINTS)));
                task.setName(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME)));
                task.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION)));
                task.setDueDate(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DUEDATE)));
                task.setCreator(getUser(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_CREATOR))));
                task.setAssignedUser(getUser(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ASSIGNEDUSER))));

                taskList.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return taskList;
    }
    public List<Task> getTaskOf(User user) {

        SQLiteDatabase db = this.getReadableDatabase();
        List<Task> taskList = new ArrayList<Task>();
        int id = user.getId();

        String query =("SELECT * FROM " + TABLE_TASK + " WHERE " + COLUMN_TASK_ASSIGNEDUSER + " = '" + id + "'");
        Cursor cursor = db.rawQuery(query, null);

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ID)));
                task.setPoints(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_POINTS)));
                task.setName(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME)));
                task.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DESCRIPTION)));
                task.setDueDate(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_DUEDATE)));
                task.setCreator(getUser(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_CREATOR))));
                task.setAssignedUser(getUser(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_CREATOR))));

                taskList.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return taskList;
    }
    public List<Recompenses> getRewardOf(User user) {

        SQLiteDatabase db = this.getReadableDatabase();
        List<Recompenses> rewardList = new ArrayList<Recompenses>();
        int id = user.getId();

        String query =("SELECT * FROM " + TABLE_REWARD + " WHERE " + COLUMN_REWARD_ASSIGNEDUSER + " = '" + id + "'");
        Cursor cursor = db.rawQuery(query, null);

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Recompenses reward = new Recompenses();
                reward.setName(cursor.getString(cursor.getColumnIndex(COLUMN_REWARD_NAME)));
                reward.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_REWARD_DESCRIPTION)));
                reward.setUser(user);

                rewardList.add(reward);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return rewardList;
    }
    public List<Recompenses> getAllReward() {

        List<Recompenses> rewardList = new ArrayList<Recompenses>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query =("SELECT * FROM " + TABLE_REWARD);
        Cursor cursor = db.rawQuery(query, null);

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Recompenses reward = new Recompenses();
                reward.setName(cursor.getString(cursor.getColumnIndex(COLUMN_REWARD_NAME)));
                reward.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_REWARD_DESCRIPTION)));
                reward.setUser(getUser(cursor.getInt(cursor.getColumnIndex(COLUMN_REWARD_ASSIGNEDUSER))));

                rewardList.add(reward);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return rewardList;
    }
    public List<Resource> getResourceOf(int group) {

        SQLiteDatabase db = this.getReadableDatabase();
        List<Resource> resourceList = new ArrayList<Resource>();

        String query =("SELECT * FROM " + TABLE_RESOURCE + " WHERE " + COLUMN_RESOURCE_GROUP + " = '" + group + "'");
        Cursor cursor = db.rawQuery(query, null);

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Resource resource = new Resource();
                resource.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_RESOURCE_ID)));
                resource.setName(cursor.getString(cursor.getColumnIndex(COLUMN_RESOURCE_NAME)));
                resource.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_RESOURCE_DESCRIPTION)));
                resource.setGroup(cursor.getInt(cursor.getColumnIndex(COLUMN_RESOURCE_GROUP)));

                resourceList.add(resource);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return resourceList;
    }
    public List<Resource> getResourceOf(Task task) {

        SQLiteDatabase db = this.getReadableDatabase();
        List<Resource> resourceList = new ArrayList<Resource>();

        String query =("SELECT * FROM " + TABLE_TASK_RESOURCE + " WHERE " + COLUMN_REF_TASK_ID + " = '" + task.getId() + "'");
        Cursor cursor = db.rawQuery(query, null);

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Resource resource = getResource(cursor.getInt(cursor.getColumnIndex(COLUMN_REF_RESOURCE_ID)));
                resourceList.add(resource);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return resourceList;
    }
    public List<Resource> getAllResource() {

        List<Resource> resourceList = new ArrayList<Resource>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query =("SELECT * FROM " + TABLE_RESOURCE);
        Cursor cursor = db.rawQuery(query, null);

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Resource resource = new Resource();
                resource.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_RESOURCE_ID)));
                resource.setName(cursor.getString(cursor.getColumnIndex(COLUMN_RESOURCE_NAME)));
                resource.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_RESOURCE_DESCRIPTION)));
                resource.setGroup(cursor.getInt(cursor.getColumnIndex(COLUMN_RESOURCE_GROUP)));

                resourceList.add(resource);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return resourceList;
    }

    //Update user records
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_USERNAME, user.getUsername());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_POINTS, user.getPoints());

        // Update row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }
    public void updateTask(Task task) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_POINTS, task.getPoints());
        values.put(COLUMN_TASK_NAME, task.getName());
        values.put(COLUMN_TASK_DESCRIPTION, task.getDescription());
        values.put(COLUMN_TASK_DUEDATE, task.getDueDate());
        values.put(COLUMN_TASK_CREATOR , task.getCreator().getId());
        values.put(COLUMN_TASK_ASSIGNEDUSER , task.getAssignedUser().getId());
        values.put(COLUMN_TASK_STATUS, task.getStatus());
        values.put(COLUMN_TASK_GROUP, task.getGroup());

        updateAllResourceOfTask(task, task.getGroup());

        SQLiteDatabase db = this.getWritableDatabase();
        // updating row
        db.update(TABLE_TASK, values, COLUMN_TASK_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
        db.close();
    }
    public void updateResource(Resource resource) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_RESOURCE_NAME, resource.getName());
        values.put(COLUMN_RESOURCE_DESCRIPTION, resource.getDescription());
        values.put(COLUMN_RESOURCE_GROUP, resource.getGroup());

        // updating row
        db.update(TABLE_RESOURCE, values, COLUMN_RESOURCE_ID + " = ?",
                new String[]{String.valueOf(resource.getId())});
        db.close();
    }
    public void updateAllResourceOfTask(Task task, int newGroup) {

        List<Resource> listResource = getResourceOf(task);

        for (int i = 0; i < listResource.size(); i++) {
            listResource.get(i).setGroup(newGroup);
            updateResource(listResource.get(i));
        }

    }

    //Delete user or task
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(user.getId())});
        db.close();
    }
    public void deleteTask(Task task) {
        updateAllResourceOfTask(task, 0);

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK, COLUMN_TASK_ID + " = ?", new String[]{String.valueOf(task.getId())});
        db.delete(TABLE_TASK_RESOURCE, COLUMN_REF_TASK_ID + " = ?", new String[]{String.valueOf(task.getId())});
        db.close();
    }
    public void deleteReward(Recompenses reward) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_REWARD, COLUMN_REWARD_NAME + " = ?", new String[]{String.valueOf(reward.getName())});
        db.close();
    }
    public void deleteResource(Resource resource) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_RESOURCE, COLUMN_RESOURCE_ID + " = ?", new String[]{String.valueOf(resource.getId())});
        db.delete(TABLE_TASK_RESOURCE, COLUMN_REF_RESOURCE_ID + " = ?", new String[]{String.valueOf(resource.getId())});
        db.close();
    }
    public void deleteTaskResource(Task task, Resource resource) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query =("SELECT * FROM " + TABLE_TASK_RESOURCE + " WHERE " + COLUMN_REF_TASK_ID + " = '" + task.getId() + "'");
        Cursor cursor = db.rawQuery(query, null);

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                if(resource.getId() == cursor.getInt(cursor.getColumnIndex(COLUMN_REF_RESOURCE_ID))) {
                    db.delete(TABLE_TASK_RESOURCE, COLUMN_TASK_RESOURCE_ID + " = ?",
                            new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_RESOURCE_ID)))});
                }
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }



    //User or task return 1 if exist & password if it's good
    public boolean checkUser(String username) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USER_USERNAME + " = ?";

        String[] selectionArgs = {username};

        ///SELECT user_id FROM name WHERE user_name = 'username';
        try {
            Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);                      //The sort order
            int cursorCount = cursor.getCount();
            cursor.close();
            db.close();

            if (cursorCount > 0) {
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    public boolean checkTask(String name) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_TASK_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_TASK_NAME + " = ?";

        // selection arguments
        String[] selectionArgs = {name};

        try {
            Cursor cursor = db.query(TABLE_TASK, columns, selection, selectionArgs, null, null, null);                      //The sort order
            int cursorCount = cursor.getCount();
            cursor.close();
            db.close();

            if (cursorCount > 0) {
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    public String checkPassword(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_USER_USERNAME + ", " + COLUMN_USER_PASSWORD + " FROM " + TABLE_USER;
        Cursor cursor = db.rawQuery(query, null);
        String a, b;
        b = "not found";
        if(cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);
                if(a.equals(username)) {
                    b = cursor.getString(1);
                    break;
                }
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return b;
    }

    public int checkNumberTask(User user)  {
        // array of columns to fetch
        String[] columns = {
                COLUMN_TASK_ASSIGNEDUSER
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_TASK_ASSIGNEDUSER + " = ?";

        String[] selectionArgs = {Integer.toString(user.getId())};

        try {
            Cursor cursor = db.query(TABLE_TASK, columns, selection, selectionArgs, null, null, null);                      //The sort order
            int cursorCount = cursor.getCount();
            cursor.close();
            db.close();

            if (cursorCount > 0) {
                return cursorCount;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public void doneTask(Task task) {
        User user = task.getAssignedUser();
        user.addPoints(task.getPoints());
        updateUser(user);
        task.setStatus(3);
        updateTask(task);
    }
    public String checkNameValue(int value, int statusGroup) {
        String str = "";
        if(statusGroup == 0){
            switch (value) {
                case 0:
                    str = "Waiting";
                    break;
                case 1:
                    str = "In process";
                    break;
                case 2:
                    str = "Reported";
                    break;
                case 3:
                    str = "Finish";
                    break;
                default:
                    str = "Nothing";
            }
        } else {
            switch (value) {
                case 0:
                    str = "No group";
                    break;
                case 1:
                    str = "Inside";
                    break;
                case 2:
                    str = "Outside";
                    break;
                default:
                    str = "No group";
            }
        }
        return str;
    }


}