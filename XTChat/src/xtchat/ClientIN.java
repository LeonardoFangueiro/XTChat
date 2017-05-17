package xtchat;

import java.io.*;
import java.net.*;

public class ClientIN extends Thread {
  
    private MainScreenController parent;
    private Socket socket;
    private BufferedReader in;
    
    public ClientIN(MainScreenController parent,Socket s) throws IOException{
        this.parent = parent;
        this.socket = s;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
                // Recebe as mensagens do server
               while (true) {
                   String line = in.readLine();
                   if(line != null) this.parent.getAreaChat().appendText(line + "\n");
                   else break;
               }
            } 
            catch (IOException e) { System.out.println(e); }
    }
}