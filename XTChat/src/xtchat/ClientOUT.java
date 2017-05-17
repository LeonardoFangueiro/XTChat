package xtchat;

import java.io.*;
import java.net.*;

public class ClientOUT extends Thread {
  
    private MainScreenController parent;
    private Socket socket;
    private PrintWriter out;
    
    public ClientOUT(MainScreenController parent,Socket s) throws IOException{
        this.parent = parent;
        this.socket = s;
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        String msg = this.parent.getAreaChatSend().getText();
        System.out.println(msg);
        out.println(msg);
        this.parent.getAreaChatSend().setText("");
    }
}