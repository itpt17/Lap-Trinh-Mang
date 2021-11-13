package model;

import java.io.Serializable;

public class ClientInfo implements Serializable{
    private String name;
    private String socket;
    public ClientInfo() {
    }

    public ClientInfo(String name, String socket) {
        this.name = name;
        this.socket = socket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }
    
}
