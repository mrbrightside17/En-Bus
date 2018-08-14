package com.enbus.www.en_bus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MySqliHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "en-bus";
    private static final String TABLE_ROUTES = "routes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ROUTE_NAME ="routeName";
    private static final String COLUMN_STATUS = "status";
    //comments table constants.
    private static final String TABLE_COMMENTS = "comments";
    private static final String COLUMN_ROUTE = "route";
    private static final String COLUMN_TEXT = "text";
    private static final String COLUMN_USER = "user";
    private static final String COLUMN_GRADE = "grade";


    String CREATE_ROUTES_TABLE = "CREATE TABLE " + TABLE_ROUTES + " ( " +
            COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_ROUTE_NAME + " TEXT" +", " + COLUMN_STATUS + " TEXT"+ " )"; //String para tabla de las rutas

    String CREATE_COMMENTS_TABLE ="CREATE TABLE " + TABLE_COMMENTS + " ( " +
            COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_ROUTE + " TEXT" + ", " + COLUMN_TEXT + " TEXT" + ", "+COLUMN_USER +
            " TEXT ," + COLUMN_GRADE+ " TEXT"+" )";



    public MySqliHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override                                   //Sobreescritura de onCreate con creacion de ambas tablas
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ROUTES_TABLE);
        db.execSQL(CREATE_COMMENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        onCreate(db);
    }

    //Add Route.
    public void addRoute(Routes routes){
        SQLiteDatabase database = MySqliHandler.this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROUTE_NAME, routes.getName());
        values.put(COLUMN_STATUS, routes.getStatus());

        database.insert(TABLE_ROUTES,null, values);
        database.close();
    }

    //Add Comment.
    public void addComment(Comments comments){
        SQLiteDatabase database = MySqliHandler.this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("route", comments.getRoute());
        values.put("text", comments.getComment());
        values.put("user", comments.getUser());
        values.put("grade", comments.getGrade());

        database.insert(TABLE_COMMENTS, null, values);
        database.close();
    }

    //Getting all routes.
    public List<Routes> getAllRoutes(){
        List<Routes> routesList = new ArrayList<>();

        String selectAllQuery = "SELECT * FROM " + TABLE_ROUTES;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectAllQuery, null);
        if (cursor.moveToFirst()){
            do{
                Routes routes = new Routes();
                routes.setId(Integer.parseInt(cursor.getString(0)));
                routes.setName(cursor.getString(1));
                routes.setStatus(cursor.getString(2));

                routesList.add(routes);
            } while(cursor.moveToNext());
        }
        return routesList;
    }

    //Getting all comments.
    public List<Comments> getAllComments(){
        List<Comments> commentsList = new ArrayList<>();

        String selectAllQuery = "SELECT * FROM "+ TABLE_COMMENTS;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectAllQuery,null);
        if (cursor.moveToFirst()){
            do{
                Comments comments = new Comments();
                comments.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                comments.setRoute(cursor.getString(cursor.getColumnIndex("route")));
                comments.setComment(cursor.getString(cursor.getColumnIndex("text")));
                comments.setUser(cursor.getString(cursor.getColumnIndex("user")));

                commentsList.add(comments);
            } while (cursor.moveToNext());
        }
        return commentsList;

    }

    public List<Comments> getSumComments(String criteria){
        List<Comments> commentsList = new ArrayList<>();

        String selectSumQuery ="SELECT * FROM " + TABLE_COMMENTS + " WHERE " +COLUMN_ROUTE + " = '"+criteria+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectSumQuery, null);
        if (cursor.moveToFirst()){
            do {
                Comments comments = new Comments();
                comments.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                comments.setRoute(cursor.getString(cursor.getColumnIndex("route")));
                comments.setComment(cursor.getString(cursor.getColumnIndex("text")));
                comments.setUser(cursor.getString(cursor.getColumnIndex("user")));
                comments.setGrade(cursor.getString(cursor.getColumnIndex("grade")));

                commentsList.add(comments);
            }while(cursor.moveToNext());
        }
        return commentsList;
    }

    public void deleteAllRoutes(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM " + TABLE_ROUTES);
    }

    public void deleteAllComments(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM " + TABLE_COMMENTS);
    }

    public void deleteComment(String criteria){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM "+ TABLE_COMMENTS+" WHERE "+COLUMN_TEXT+" = "+"'"+criteria+"'");
    }

    //Set favorite.
    public void setFavorite(String routeName){
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, "yes");
        SQLiteDatabase database = this.getWritableDatabase();
        database.update(TABLE_ROUTES,values, "routeName='"+routeName+"'",null);
    }

    //Remove favorite.
    public void removeFavorite(String routeName){
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, "no");
        SQLiteDatabase database = this.getWritableDatabase();
        database.update(TABLE_ROUTES,values, "routeName='"+routeName+"'",null);
    }

    //set grade on a comment.
    public void updateGrade(String comment, String grade){
        ContentValues values = new ContentValues();
        values.put(COLUMN_GRADE, grade);
        SQLiteDatabase database = this.getWritableDatabase();
        database.update(TABLE_COMMENTS,values, "text='"+comment+"'", null);

    }
}
