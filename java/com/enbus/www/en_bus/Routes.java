package com.enbus.www.en_bus;

public class Routes {

    //Attributes:
    private int id;
    private String name;
    private String status;


    //Constructors:
    public Routes(){
        super();
    }

    public Routes(int id, String name){
        setId(id);
        setName(name);
        setStatus("no");
    }

    public Routes(String name){
        setName(name);
        setStatus("no");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
