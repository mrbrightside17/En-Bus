package com.enbus.www.en_bus;

public class Favorites {

    //Attributes:
    private int id;
    private String RouteName;

    //Constructors:
    public Favorites(){ super(); }

    public Favorites(String RouteName){
        this.RouteName = RouteName;
    }

    public Favorites(int id, String RouteName){
        this.id = id;
        this.RouteName = RouteName;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getRouteName() { return RouteName; }

    public void setRouteName(String routeName) { RouteName = routeName; }
}
