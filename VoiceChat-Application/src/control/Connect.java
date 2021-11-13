package control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connect {
    private Socket socket;
    private InputStream input;
    private OutputStream output;
    public Connect(){}
   
    public Connect(String addr, int port){
        try {
            socket  = new Socket(addr,port);
            input   = socket.getInputStream();
            output  = socket.getOutputStream();
        } catch (IOException ex) {
        }
    }

    public void sendName(String name){
        try {
            output.write(name.getBytes(), 0, name.length());
        } catch (IOException ex) {
        }
    }
    
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
