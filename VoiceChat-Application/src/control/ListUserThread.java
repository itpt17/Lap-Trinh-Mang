package control;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import model.ListUser;
import org.apache.commons.lang3.SerializationUtils;

public class ListUserThread extends Thread {
    private Socket socket;
    private ListUser list;
    public ListUserThread(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run(){
        try {
            InputStream input = socket.getInputStream();
            while(true){
                byte[] data = new byte[1024];
                input.read(data, 0, data.length);
                list = (ListUser) SerializationUtils.deserialize(data);
            }
        } catch (IOException ex) {}
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ListUser getList() {
        return list;
    }

    public void setList(ListUser list) {
        this.list = list;
    }
    
}
