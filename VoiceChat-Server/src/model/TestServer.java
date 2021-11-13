package model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.SerializationUtils;

public class TestServer {
    ServerSocket server;
    ListUser list;
    List<Socket> sockets;
    public TestServer(){
        try {
            server= new ServerSocket(1711);
            list= new ListUser();
            sockets= new ArrayList<>();
        } catch (IOException ex) {
            Logger.getLogger(TestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void RemoveSocket(Socket socket){
        for(Socket i:sockets){
            if(i == socket){
                sockets.remove(i);
                break;
            }
        }
    }
    
    public void RemoveItem(Socket socket){
        for(ClientInfo i: list.getList()){
            if(i.getSocket().equals(socket.toString())){
                list.getList().remove(i);
                break;
            }
        }
    }
    
    public void ListenConnect() throws IOException{
        while(true){
            Socket socket = this.server.accept();
            sockets.add(socket);
            ListenClientStatus statusListener = new ListenClientStatus(this,socket);
            statusListener.start();
            InputStream input = socket.getInputStream();
            //OutputStream output = socket.getOutputStream();
            byte[] buffer = new byte[1024];
            input.read(buffer, 0, buffer.length);
            String name = new String(buffer).trim();
            ClientInfo client = new ClientInfo(name,socket.toString());
            list.addUser(client);
            for(Socket s:sockets){
                OutputStream output = s.getOutputStream();
                ListUser tmp = new ListUser();
                for(ClientInfo i:list.getList()){
                   if(!i.getSocket().equals(s.toString())){
                       tmp.addUser(i);
                   }
                }
                byte[] data = SerializationUtils.serialize(tmp);
                output.write(data, 0, data.length);
            }
        }
    }
    public ServerSocket getServer() {
        return server;
    }

    public void setServer(ServerSocket server) {
        this.server = server;
    }

    public ListUser getList() {
        return list;
    }

    public void setList(ListUser list) {
        this.list = list;
    }

    public List<Socket> getSockets() {
        return sockets;
    }

    public void setSockets(List<Socket> sockets) {
        this.sockets = sockets;
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        TestServer test = new TestServer();
        test.ListenConnect();
    }
}

class ListenClientStatus extends Thread{
    TestServer test;
    Socket socket;
    public ListenClientStatus(TestServer test, Socket socket) {
        this.test = test;
        this.socket = socket;
    }

    public TestServer getTest() {
        return test;
    }

    public void setTest(TestServer test) {
        this.test = test;
    }
    
    
    @Override
    public void run(){
        try {
            InputStream ins = socket.getInputStream();
            while(true){
                int stt = ins.read();
                System.out.println(stt);
                switch(stt){
                    case 0:{
                        test.RemoveSocket(socket);
                        test.RemoveItem(socket);
                        for(Socket s:test.getSockets()){
                            OutputStream output = s.getOutputStream();
                            ListUser tmp = new ListUser();
                            for(ClientInfo i:test.getList().getList()){
                               if(!i.getSocket().equals(s.toString())){
                                   tmp.addUser(i);
                               }
                            }
                            byte[] data = SerializationUtils.serialize(tmp);
                            output.write(data, 0, data.length);
                        }
                    }
                }
            }
        } catch (IOException ex) {
        }
    }
}
