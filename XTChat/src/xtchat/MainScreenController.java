
package xtchat;

import java.io.IOException;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.BrokenBarrierException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class MainScreenController implements Initializable {
    
    @FXML
    private Button connectButton;
    @FXML
    private Button disconnectButton;
    @FXML
    private Button enviarButton;
    @FXML
    private TextField fieldIP;
    @FXML
    private TextField fieldPORT;
    @FXML
    private TextArea areaChat;
    @FXML
    private TextField areaChatSend;
    @FXML
    private TextArea areaUsers;
    private ClientIN clientIN;
    private ClientOUT clientOUT;
    private boolean isConnected = false;
    private Socket socket;
    
    public String getFieldIP() { return this.fieldIP.getText(); }
    public String getFieldPORT() { return this.fieldPORT.getText(); }
    public TextArea getAreaChat() { return this.areaChat; }
    public TextField getAreaChatSend() { return this.areaChatSend; }
    public TextArea getAreaUsers() { return this.areaUsers; }
    public boolean isConnected() { return this.isConnected; }
    public void setConnected(boolean c) { this.isConnected = c; }
    
    @FXML
    public void connectButtonAction(ActionEvent evt) throws IOException, InterruptedException, BrokenBarrierException {
        this.socket =  new Socket(this.getFieldIP(), Integer.parseInt(this.getFieldPORT()));
        this.clientIN = new ClientIN(this,socket);
        this.setConnected(true);
        this.clientIN.start();
    }
    
    @FXML
    public void disconnectButtonAction(ActionEvent evt) {
        try {
            this.socket.close();
            this.setConnected(false);
        } catch (IOException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @FXML
    public void enviarButtonAction(ActionEvent evt) throws IOException {
            this.clientOUT = new ClientOUT(this,this.socket);
            this.clientOUT.start();
    } 
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
