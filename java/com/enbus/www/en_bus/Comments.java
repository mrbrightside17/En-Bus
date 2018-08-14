package com.enbus.www.en_bus;

public class Comments {
    //Attributes:
    private int id;
    private String grade;
    private String route;
    private String comment;
    private String user;

    //Constructors:
    public Comments(){ super(); }

    public Comments(String route, String comment, String user){
        setRoute(route);
        setComment(comment);
        setUser(user);
        setGrade("0");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getGrade() { return grade; }

    public void setGrade(String grade) { this.grade = grade; }
}
