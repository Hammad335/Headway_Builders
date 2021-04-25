package com.codewithhamad.headwaybuilders.databasehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.codewithhamad.headwaybuilders.models.AnalystLoginModel;
import com.codewithhamad.headwaybuilders.models.BuildingModel;
import com.codewithhamad.headwaybuilders.models.WorkerModel;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    Context context;
    private static final String DB_NAME = "headway.db";
    private static final int DB_VERSION = 1;

    private String table_1_buildings = "Buildings";
    private static final String COLUMN_BUILDING_ID = "id";
    private static final String COLUMN_BUILDING_NAME = "name";
    private static final String COLUMN_BUILDING_TYPE = "buildingType";
    private static final String COLUMN_BUILDING_AREA = "buildingAreaInSqFt";
    private static final String COLUMN_BUILDING_FLATS = "numberOfFlats";
    private static final String COLUMN_BUILDING_FLOORS = "numberOfFloors";
    private static final String COLUMN_BUILDING_LIFTS = "numberOfLifts";
    private static final String COLUMN_BUILDING_P_AREA = "parkingAreaInSqFt";
    private static final String COLUMN_BUILDING_DETAILS = "details";
    private static final String COLUMN_BUILDING_LOCATION = "location";
    private static final String COLUMN_BUILDING_IMAGE = "buildingImage";
    private static final String COLUMN_BUILDING_DATETIME = "datetime";

    private String table_2_workers = "Workers";
    private static final String COLUMN_WORKER_ID = "worker_id";
    private static final String COLUMN_WORKER_NAME = "worker_name";
    private static final String COLUMN_WORKER_JOB = "job";
    private static final String COLUMN_WORKER_SAL = "sal";
    private static final String COLUMN_WORKER_BUILDING_ID = "id";

    private String table_3_analyst= "Analysts";
    private static final String COLUMN_ANALYST_USERNAME = "user_name";
    private static final String COLUMN_ANALYST_PASS = "pass";


    // for converting bitmap image into byte array
    private ByteArrayOutputStream imageByteArrayOutputStream;
    private byte[] imageInBytes;


    // create table queries
    String buildingTableCreateQuery = "CREATE TABLE " + table_1_buildings + " ( " + COLUMN_BUILDING_ID + " INTEGER PRIMARY KEY, " + COLUMN_BUILDING_NAME
            + " VARCHAR , " + COLUMN_BUILDING_TYPE + " VARCHAR, " + COLUMN_BUILDING_AREA + " INTEGER NOT NULL, " +
            COLUMN_BUILDING_FLATS + " INTEGER, " + COLUMN_BUILDING_FLOORS + " INTEGER, " + COLUMN_BUILDING_LIFTS + " INTEGER, "
            + COLUMN_BUILDING_P_AREA + " INTEGER, " + COLUMN_BUILDING_DETAILS + " VARCHAR, " + COLUMN_BUILDING_LOCATION
            + " VARCHAR NOT NULL, " + COLUMN_BUILDING_IMAGE + " BLOB, " + COLUMN_BUILDING_DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)";

    String workerTableCreateQuery = "CREATE TABLE " + table_2_workers + " ( " + COLUMN_WORKER_ID + " INTEGER PRIMARY KEY, " + COLUMN_WORKER_NAME
            + " VARCHAR NOT NULL, " + COLUMN_WORKER_JOB + " VARCHAR NOT NULL, " + COLUMN_WORKER_SAL + " INTEGER NOT NULL, "
            + COLUMN_WORKER_BUILDING_ID + " INTEGER , FOREIGN KEY  (" + COLUMN_WORKER_BUILDING_ID + ") REFERENCES " + table_1_buildings
            + "(" + COLUMN_BUILDING_ID + "))";

    String analystsTableCreateQuery = "CREATE TABLE " + table_3_analyst + " ( " + COLUMN_ANALYST_USERNAME + " VARCHAR PRIMARY KEY, "+
            COLUMN_ANALYST_PASS + " VARCHAR NOT NULL)";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(buildingTableCreateQuery);
            db.execSQL(workerTableCreateQuery);
            db.execSQL(analystsTableCreateQuery);
            Toast.makeText(context, "tables created successfully", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Toast.makeText(context, "Error creating tables", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // insert record into buildings table
    public void insertInToBuildingsTable(BuildingModel buildingModel) {
        SQLiteDatabase sqLiteDatabaseWritableObj = this.getWritableDatabase();

        try {
            Bitmap bitmapImage = buildingModel.getBuildingImage();

            // converting bitmap image into byte array
            imageByteArrayOutputStream = new ByteArrayOutputStream();
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, imageByteArrayOutputStream);
            imageInBytes = imageByteArrayOutputStream.toByteArray();

            // database record values for buildingsTable
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", buildingModel.getBuildingId());
            contentValues.put("name", buildingModel.getBuildingName());
            contentValues.put("buildingType", buildingModel.getBuildingType());
            contentValues.put("buildingAreaInSqFt", buildingModel.getBuildingAreaInSqFt());

            if (buildingModel.getNumbOfFlats() != -1)
                contentValues.put("numberOfFlats", buildingModel.getNumbOfFlats());

            if (buildingModel.getNumbOfFloors() != -1)
                contentValues.put("numberOfFloors", buildingModel.getNumbOfFloors());

            if (buildingModel.getNumbOfLifts() != -1)
                contentValues.put("numberOfLifts", buildingModel.getNumbOfLifts());

            contentValues.put("parkingAreaInSqFt", buildingModel.getParkingAreaInSqFt());
            contentValues.put("details", buildingModel.getShortDetails());
            contentValues.put("location", buildingModel.getBuildingLocation());
            contentValues.put("buildingImage", imageInBytes);

            // insert() method returns -1 if exception occurs
            long check = sqLiteDatabaseWritableObj.insert(table_1_buildings, null, contentValues);

            if (check != -1) {
                Toast.makeText(context, "Building added to the database " + table_1_buildings, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Failed to add the record to database.", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(context, "Failed to add record to database", Toast.LENGTH_SHORT).show();
        }
        sqLiteDatabaseWritableObj.close();
    }

    // get all from buildings table
    public ArrayList<BuildingModel> getAllFromBuildingsTable(String order) {
        SQLiteDatabase sqLiteDatabaseReadableObj = this.getReadableDatabase();

        try {
            ArrayList<BuildingModel> buildings = new ArrayList<>();

            String getAllDataQuery = "SELECT * FROM " + table_1_buildings + " ORDER BY " + order;
            Cursor cursor = sqLiteDatabaseReadableObj.rawQuery(getAllDataQuery, null);

            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {

                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    String type = cursor.getString(2);
                    int bArea = cursor.getInt(3);

                    // getType(index) return 0 if columnValue is null
                    int flats;
                    if (cursor.getType(4) == 0)
                        flats = -1;
                    else
                        flats = cursor.getInt(4);

                    int floors;
                    if (cursor.getType(5) == 0)
                        floors = -1;
                    else
                        floors = cursor.getInt(5);

                    int lifts;
                    if (cursor.getType(6) == 0)
                        lifts = -1;
                    else
                        lifts = cursor.getInt(6);

                    int pArea = cursor.getInt(7);
                    String details = cursor.getString(8);
                    String location = cursor.getString(9);
                    byte[] imageInBytes = cursor.getBlob(10);

//                    String dateTime= cursor.getString(7);

//                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                    Date date= new Date();
//                    date= dateFormat.parse(dateTime);


//                    Log.d("check", "getAllFromBuildingsTable: " + date.toString());

                    // converting byteArray image into bitmap
                    Bitmap bitmapImage = BitmapFactory.decodeByteArray(imageInBytes, 0, imageInBytes.length);

                    buildings.add(new BuildingModel(bitmapImage, type, id, name, bArea, flats, floors, lifts, pArea, details, location));
                }
                cursor.close();
                sqLiteDatabaseReadableObj.close();
                return buildings;
            }
            else {
                Toast.makeText(context, "No buildings exist in database", Toast.LENGTH_SHORT).show();
                cursor.close();
                sqLiteDatabaseReadableObj.close();
                return null;
            }

        } catch (Exception e) {
            Toast.makeText(context, "Error fetching data form buildings table", Toast.LENGTH_SHORT).show();
            sqLiteDatabaseReadableObj.close();
            return null;
        }
    }

    // update record in buildings table
    public void updateRecordInToBuildingsTable(BuildingModel buildingModel) {
        SQLiteDatabase sqLiteDatabaseWritableObj = this.getWritableDatabase();

        try {

            Bitmap bitmapImage = buildingModel.getBuildingImage();

            // converting bitmap image into byte array
            imageByteArrayOutputStream = new ByteArrayOutputStream();
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, imageByteArrayOutputStream);
            imageInBytes = imageByteArrayOutputStream.toByteArray();

            // database record values
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_BUILDING_ID, buildingModel.getBuildingId());
            contentValues.put(COLUMN_BUILDING_NAME, buildingModel.getBuildingName());
            contentValues.put(COLUMN_BUILDING_TYPE, buildingModel.getBuildingType());
            contentValues.put(COLUMN_BUILDING_AREA, buildingModel.getBuildingAreaInSqFt());

            if (buildingModel.getNumbOfFlats() != -1)
                contentValues.put(COLUMN_BUILDING_FLATS, buildingModel.getNumbOfFlats());

            if (buildingModel.getNumbOfFloors() != -1)
                contentValues.put(COLUMN_BUILDING_FLOORS, buildingModel.getNumbOfFloors());

            if (buildingModel.getNumbOfLifts() != -1)
                contentValues.put(COLUMN_BUILDING_LIFTS, buildingModel.getNumbOfLifts());

            contentValues.put(COLUMN_BUILDING_P_AREA, buildingModel.getParkingAreaInSqFt());
            contentValues.put(COLUMN_BUILDING_DETAILS, buildingModel.getShortDetails());
            contentValues.put(COLUMN_BUILDING_LOCATION, buildingModel.getBuildingLocation());
            contentValues.put("buildingImage", imageInBytes);

            // update() method returns -1 if exception occurs
            long check = sqLiteDatabaseWritableObj.update(table_1_buildings, contentValues,
                    COLUMN_BUILDING_ID + "=?", new String[]{String.valueOf(buildingModel.getBuildingId())});

            if (check != -1)
                Toast.makeText(context, "Building updated.", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Failed to update the record.", Toast.LENGTH_SHORT).show();

        }
        catch (Exception e) {
        Toast.makeText(context, "Error updating record.", Toast.LENGTH_SHORT).show();
        }
        sqLiteDatabaseWritableObj.close();
}

    // check whether record exists in building table or not
    public boolean doesExistInBuildingTable(int buildingId) {

        SQLiteDatabase sqLiteDatabaseReadableObj = this.getReadableDatabase();

        String getDataByIdQuery = "SELECT * FROM " + table_1_buildings + " WHERE " + COLUMN_BUILDING_ID + " = " + buildingId;

        Cursor cursor = sqLiteDatabaseReadableObj.rawQuery(getDataByIdQuery, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            sqLiteDatabaseReadableObj.close();
            return false;
        }
        else {
            sqLiteDatabaseReadableObj.close();
            return true;
        }
    }

    // get whole row/record by buildingId
    public BuildingModel getByIdFromBuildingTable(int buildingId){
        SQLiteDatabase sqLiteDatabaseReadableObj= this.getReadableDatabase();

        try{
            BuildingModel buildingModel = null;

            String getSingleRecordByIdQuery= "SELECT * FROM " + table_1_buildings + " WHERE "+COLUMN_BUILDING_ID+" = "  + buildingId;
            Cursor cursor= sqLiteDatabaseReadableObj.rawQuery(getSingleRecordByIdQuery, null);

            if(cursor.getCount() != 0){
                while (cursor.moveToNext()){

                    int id= cursor.getInt(0);
                    String name= cursor.getString(1);
                    String type= cursor.getString(2);
                    int bArea= cursor.getInt(3);

                    // getType(index) return 0 if columnValue is null
                    int flats;
                    if(cursor.getType(4) == 0)
                        flats= -1;
                    else
                        flats= cursor.getInt(4);

                    int floors;
                    if(cursor.getType(5) == 0)
                        floors= -1;
                    else
                        floors= cursor.getInt(5);

                    int lifts;
                    if(cursor.getType(6) == 0)
                        lifts= -1;
                    else
                        lifts= cursor.getInt(6);

                    int pArea= cursor.getInt(7);
                    String details= cursor.getString(8);
                    String location= cursor.getString(9);
                    byte[] imageInBytes= cursor.getBlob(10);
//                    String dateTime= cursor.getString(7);

                    // converting byteArray image into bitmap
                    Bitmap bitmapImage= BitmapFactory.decodeByteArray(imageInBytes, 0, imageInBytes.length);

                    buildingModel= new BuildingModel(bitmapImage, type, id, name, bArea, flats, floors, lifts, pArea, details, location);
                }
                cursor.close();
                sqLiteDatabaseReadableObj.close();
                return buildingModel;
            }
            else{
                Toast.makeText(context, "No buildings exist in database", Toast.LENGTH_SHORT).show();
                cursor.close();
                sqLiteDatabaseReadableObj.close();
                return null;
            }
        }
        catch (Exception e){
            Toast.makeText(context, "Error fetching data form buildings table", Toast.LENGTH_SHORT).show();
            sqLiteDatabaseReadableObj.close();
            return null;
        }
    }

    public void insertInToWorkersTable(WorkerModel workerModel) {
        SQLiteDatabase sqLiteDatabaseWritableObj = this.getWritableDatabase();

        try {

            // database record values For WorkersTable
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_WORKER_ID, workerModel.getWorkerId());
            contentValues.put(COLUMN_WORKER_NAME, workerModel.getWorkerName());
            contentValues.put(COLUMN_WORKER_JOB, workerModel.getJob());
            contentValues.put(COLUMN_WORKER_SAL, workerModel.getSalary());
            contentValues.put(COLUMN_WORKER_BUILDING_ID, workerModel.getBuildingId());

            // insert() method returns -1 if exception occurs
            long check = sqLiteDatabaseWritableObj.insert(table_2_workers, null, contentValues);

            if (check != -1) {
                Toast.makeText(context, "Worker added to the database " + table_2_workers, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, "Failed to add the record to database.", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e) {
            Toast.makeText(context, "Failed to add record to database", Toast.LENGTH_SHORT).show();
        }
        sqLiteDatabaseWritableObj.close();
    }

    public ArrayList<WorkerModel> getAllFromWorkersTable(String order){
        SQLiteDatabase sqLiteDatabaseReadableObj = this.getReadableDatabase();

        try {
            ArrayList<WorkerModel> workers = new ArrayList<>();

            String getAllDataQuery = "SELECT * FROM " + table_2_workers + " ORDER BY " + order;
            Cursor cursor = sqLiteDatabaseReadableObj.rawQuery(getAllDataQuery, null);

            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {

                    int wId = cursor.getInt(0);
                    String wName = cursor.getString(1);
                    String wJob = cursor.getString(2);
                    int wSal = cursor.getInt(3);
                    int bId = cursor.getInt(4);

//                    String dateTime= cursor.getString(8);

                    workers.add(new WorkerModel(wId, bId, wName, wJob, wSal));
                }
                cursor.close();
                sqLiteDatabaseReadableObj.close();
                return workers;
            } else {
                Toast.makeText(context, "No worker exists in database", Toast.LENGTH_SHORT).show();
                cursor.close();
                sqLiteDatabaseReadableObj.close();
                return null;
            }
        }
        catch (Exception e){
            Toast.makeText(context, "Error fetching data form workers table", Toast.LENGTH_SHORT).show();
            sqLiteDatabaseReadableObj.close();
            return null;
        }
    }

    // get all records from workers table by building id
    public ArrayList<WorkerModel> getAllFromWorkersTableByBuildingId(int buildingId){

        try{
            SQLiteDatabase sqLiteDatabaseReadableObj= this.getReadableDatabase();
            ArrayList<WorkerModel> workers = new ArrayList<>();

            String getById= "SELECT * FROM " + table_2_workers + " WHERE "+COLUMN_WORKER_BUILDING_ID+" = "  + buildingId;
            Cursor cursor= sqLiteDatabaseReadableObj.rawQuery(getById, null);

            if(cursor.getCount() != 0){
                while (cursor.moveToNext()){

                    int wId= cursor.getInt(0);
                    String name= cursor.getString(1);
                    String job= cursor.getString(2);
                    int sal= cursor.getInt(3);
                    int bId= cursor.getInt(4);

                    workers.add(new WorkerModel(wId, bId, name, job, sal));
                }
                cursor.close();
                sqLiteDatabaseReadableObj.close();
                return workers;
            }
            else{
                Toast.makeText(context, "Worker does not exist in database", Toast.LENGTH_SHORT).show();
                cursor.close();
                sqLiteDatabaseReadableObj.close();
                return null;
            }

        }
        catch (Exception e){
            Toast.makeText(context, "Error fetching data form worker table", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    // get all records from workers table by building id and order
    public ArrayList<WorkerModel> getAllFromWorkersTableByBuildingId(int buildingId, String order){

        try{
            SQLiteDatabase sqLiteDatabaseReadableObj= this.getReadableDatabase();
            ArrayList<WorkerModel> workers = new ArrayList<>();

            String getById= "SELECT * FROM " + table_2_workers + " WHERE "+COLUMN_WORKER_BUILDING_ID+" = "  + buildingId + " ORDER BY " + order;
            Cursor cursor= sqLiteDatabaseReadableObj.rawQuery(getById, null);

            if(cursor.getCount() != 0){
                while (cursor.moveToNext()){

                    int wId= cursor.getInt(0);
                    String name= cursor.getString(1);
                    String job= cursor.getString(2);
                    int sal= cursor.getInt(3);
                    int bId= cursor.getInt(4);

                    workers.add(new WorkerModel(wId, bId, name, job, sal));
                }
                cursor.close();
                sqLiteDatabaseReadableObj.close();
                return workers;
            }
            else{
                Toast.makeText(context, "Worker does not exist in database", Toast.LENGTH_SHORT).show();
                cursor.close();
                sqLiteDatabaseReadableObj.close();
                return null;
            }

        }
        catch (Exception e){
            Toast.makeText(context, "Error fetching data form worker table", Toast.LENGTH_SHORT).show();
            return null;
        }
    }


    // update record in worker table
    public void updateRecordInToWorkerTable(WorkerModel workerModel){
        SQLiteDatabase sqLiteDatabaseWritableObj = this.getWritableDatabase();

        try {

            // database record values
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_WORKER_ID, workerModel.getWorkerId());
            contentValues.put(COLUMN_WORKER_NAME, workerModel.getWorkerName());
            contentValues.put(COLUMN_WORKER_JOB, workerModel.getJob());
            contentValues.put(COLUMN_WORKER_SAL, workerModel.getSalary());
            contentValues.put(COLUMN_WORKER_BUILDING_ID, workerModel.getBuildingId());

            // update() method returns -1 if exception occurs
            long check = sqLiteDatabaseWritableObj.update(table_2_workers, contentValues,
                    COLUMN_WORKER_ID + "=?", new String[]{String.valueOf(workerModel.getWorkerId())});

            if (check != -1)
                Toast.makeText(context, "Worker updated.", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Failed to update the record.", Toast.LENGTH_SHORT).show();

        }
        catch (Exception e) {
            Toast.makeText(context, "Error updating record.", Toast.LENGTH_SHORT).show();
        }
        sqLiteDatabaseWritableObj.close();
    }

    public boolean doesExistInWorkerTable(int workerId){
        SQLiteDatabase sqLiteDatabaseReadableObj = this.getReadableDatabase();

        String getDataByIdQuery = "SELECT * FROM " + table_2_workers + " WHERE "+COLUMN_WORKER_ID+" = "  + workerId;

        Cursor cursor = sqLiteDatabaseReadableObj.rawQuery(getDataByIdQuery, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            sqLiteDatabaseReadableObj.close();
            return false;
        }
        else
            sqLiteDatabaseReadableObj.close();
            return true;
    }

    // get whole row/record by workerId
    public WorkerModel getByIdFromWorkerTable(int workerId){
        try{
            SQLiteDatabase sqLiteDatabaseReadableObj= this.getReadableDatabase();
            WorkerModel workerModel = null;

            String getSingleRecordByIdQuery= "SELECT * FROM " + table_2_workers + " WHERE "+COLUMN_WORKER_ID+" = "  + workerId;
            Cursor cursor= sqLiteDatabaseReadableObj.rawQuery(getSingleRecordByIdQuery, null);

            if(cursor.getCount() != 0){
                while (cursor.moveToNext()){

                    int wId= cursor.getInt(0);
                    String name= cursor.getString(1);
                    String job= cursor.getString(2);
                    int sal= cursor.getInt(3);
                    int bId= cursor.getInt(4);

//                   String dateTime= cursor.getString(7);

                     workerModel= new WorkerModel(wId, bId, name, job, sal);
                }
                cursor.close();
                sqLiteDatabaseReadableObj.close();
                return workerModel;
            }
            else{
                Toast.makeText(context, "Worker does not exist in database", Toast.LENGTH_SHORT).show();
                cursor.close();
                sqLiteDatabaseReadableObj.close();
                return null;
            }

        }
        catch (Exception e){
            Toast.makeText(context, "Error fetching data form worker table", Toast.LENGTH_SHORT).show();
            return null;
        }
    }




    // check whether record exists in analysts table or not by analystName
    public boolean doesExistInAnalystsTable(String analystUserName) {

        SQLiteDatabase sqLiteDatabaseReadableObj = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabaseReadableObj.query(table_3_analyst, null, COLUMN_ANALYST_USERNAME+"=?",
                new String[] { analystUserName }, null, null, null, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            sqLiteDatabaseReadableObj.close();
            return false;
        }
        else {
            sqLiteDatabaseReadableObj.close();
            return true;
        }
    }

    // check whether record exists in analysts table or not
    public boolean doesExistInAnalystsTable(AnalystLoginModel analystLoginModel) {

        SQLiteDatabase sqLiteDatabaseReadableObj = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabaseReadableObj.query(table_3_analyst, null, COLUMN_ANALYST_USERNAME+"=?",
                new String[] { analystLoginModel.getName() }, null, null, null, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            sqLiteDatabaseReadableObj.close();
            return false;
        }
        else {
            cursor.moveToFirst();
            if (cursor.getString(1).equals(analystLoginModel.getPassword())) {
                cursor.close();
                sqLiteDatabaseReadableObj.close();
                return true;
            }
            else{
                cursor.close();
                sqLiteDatabaseReadableObj.close();
                return false;
            }
        }

    }


    // insert record into analysts table
    public void insertInToAnalystsTable(AnalystLoginModel analystLoginModel) {
        SQLiteDatabase sqLiteDatabaseWritableObj = this.getWritableDatabase();

        try {

            // database record values for buildingsTable
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ANALYST_USERNAME, analystLoginModel.getName());
            contentValues.put(COLUMN_ANALYST_PASS, analystLoginModel.getPassword());

            // insert() method returns -1 if exception occurs
            long check = sqLiteDatabaseWritableObj.insert(table_3_analyst, null, contentValues);

            if (check != -1) {
                Toast.makeText(context, "Analyst added to database successfully.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Failed to add analyst to database.", Toast.LENGTH_SHORT).show();
            }

        }
        catch (Exception e) {
            Toast.makeText(context, "Failed to add record to database", Toast.LENGTH_SHORT).show();
        }
        sqLiteDatabaseWritableObj.close();
    }

    // get all from analysts table
    public ArrayList<AnalystLoginModel> getAllFromAnalystsTable() {
        SQLiteDatabase sqLiteDatabaseReadableObj = this.getReadableDatabase();

        try {
            ArrayList<AnalystLoginModel> allAnalysts = new ArrayList<>();

            String getAllDataQuery = "SELECT * FROM " + table_3_analyst;
            Cursor cursor = sqLiteDatabaseReadableObj.rawQuery(getAllDataQuery, null);

            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {

                    String name = cursor.getString(0);
                    String pass = cursor.getString(1);

                    allAnalysts.add(new AnalystLoginModel(name, pass));
                }
                cursor.close();
                sqLiteDatabaseReadableObj.close();
                return allAnalysts;
            } else {
                Toast.makeText(context, "No analysts exist in database", Toast.LENGTH_SHORT).show();
                cursor.close();
                sqLiteDatabaseReadableObj.close();
                return null;
            }

        } catch (Exception e) {
            Toast.makeText(context, "Error fetching data form analysts table", Toast.LENGTH_SHORT).show();
            sqLiteDatabaseReadableObj.close();
            return null;
        }
    }

    // delete record from analysts table by userName
    public boolean deleteRecByNameFromAnalystsTable(String analystName){
        SQLiteDatabase sqLiteDatabaseReadableObj = this.getWritableDatabase();
        try {
            // delete() method returns the number of affected rows
            int affectedRows = sqLiteDatabaseReadableObj.delete(table_3_analyst, COLUMN_ANALYST_USERNAME + "=?", new String[]{analystName});
            return affectedRows > 0;
        }
        catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            sqLiteDatabaseReadableObj.close();
        }
        sqLiteDatabaseReadableObj.close();
        return false;

    }

    // delete record from buildings table by id
    public boolean deleteRecByIdFromBuildingsTable(int id){
        SQLiteDatabase sqLiteDatabaseReadableObj = this.getWritableDatabase();
        try {
            // delete() method returns the number of affected rows
            int affectedRows = sqLiteDatabaseReadableObj.delete(table_1_buildings, COLUMN_BUILDING_ID + "=" + id, null);
            return affectedRows > 0;
        }
        catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            sqLiteDatabaseReadableObj.close();
        }
        sqLiteDatabaseReadableObj.close();
        return false;

    }

    // delete record from worker table by id
    public boolean deleteRecByIdFromWorkerTable(int id){
        SQLiteDatabase sqLiteDatabaseReadableObj = this.getWritableDatabase();
        try {
            // delete() method returns the number of affected rows
            int affectedRows = sqLiteDatabaseReadableObj.delete(table_2_workers, COLUMN_WORKER_ID + "=" + id, null);
            sqLiteDatabaseReadableObj.close();
            return affectedRows > 0;
        }
        catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            sqLiteDatabaseReadableObj.close();
        }
        sqLiteDatabaseReadableObj.close();
        return false;
    }

    // return total number of records in worker table
    public int getTotalWorkersFromWorkerTable() {

        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + table_2_workers;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = -1;
        if (cursor != null)
            if (cursor.getCount() > 0)
                count = cursor.getCount();

            cursor.close();
            db.close();
            return count;
    }

    // return record count by building id
    public int getTotalWorkersFromWorkerTableByBuildingId(int buildingId){
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + table_2_workers + " WHERE " + COLUMN_WORKER_BUILDING_ID + "=" + buildingId;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = -1;
        if (cursor != null)
            if (cursor.getCount() > 0)
                count = cursor.getCount();

        cursor.close();
        db.close();
        return count;
    }

    // return total salary given in worker table
    public double getTotalSalaryFromWorkerTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sumQuery= "SELECT SUM(" + COLUMN_WORKER_SAL + ") as Total FROM " + table_2_workers;
        Cursor cursor = db.rawQuery(sumQuery, null);

        double s= -1;
        if(cursor.moveToFirst())
            s = cursor.getDouble(0);

        cursor.close();
        db.close();
        return s;

    }

    // return total salary given in worker table by building id
    public double getTotalSalaryFromWorkerTableByBuildingId(int buildindId){
        SQLiteDatabase db = this.getReadableDatabase();
        String sumQuery= "SELECT SUM(" + COLUMN_WORKER_SAL + ") as Total FROM " + table_2_workers + " WHERE " + COLUMN_WORKER_BUILDING_ID + "=" + buildindId;
        Cursor cursor = db.rawQuery(sumQuery, null);

        double s= -1;
        if(cursor.moveToFirst())
            s = cursor.getDouble(0);

        cursor.close();
        db.close();
        return s;

    }


}
