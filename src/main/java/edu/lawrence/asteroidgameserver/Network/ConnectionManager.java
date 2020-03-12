/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lawrence.asteroidgameserver.Network;

import edu.lawrence.asteroidgameserver.App;
import edu.lawrence.asteroidgameserver.Game;
import edu.lawrence.networklib.Message;
import edu.lawrence.networklib.NetworkConsts;
import edu.lawrence.networklib.ProgressMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javafx.application.Platform;

/**
 *
 * @author Justin
 */
public class ConnectionManager implements Runnable, NetworkConsts {
    private Socket socket;
    private Game game;
    private int playerNumber;
    private String address;
    private ArrayList<Message> toSend;

    public ConnectionManager(Socket socket, Game game, int playerNumber){
        this.socket = socket;
        this.playerNumber = playerNumber;
        this.game = game; 
        toSend = new ArrayList<>();
    }
    
    @Override
    public void run() {
        ObjectInputStream OIS = null;
        ObjectOutputStream OOS= null;
        try{
            address = socket.getRemoteSocketAddress().toString();
            OOS = new ObjectOutputStream(socket.getOutputStream());
            OIS = new ObjectInputStream(socket.getInputStream());
            while(!game.isStarted());
            toSend.add(new Message(NetworkConsts.START));
            System.out.println("Game Started");
            while(true){
                Message[] messages;
               
                messages = (Message[])OIS.readObject();
                for(Message m : messages){
                switch(m.getMessageType()) {
                    case NetworkConsts.GET_PROGRESS:
                        int result = game.getProgress(playerNumber == 1 ? 0 : 1);
                        //Platform.runLater(new ServerUserMessage("Progress update requested", App.getListPane()));
                        toSend.add(new ProgressMessage(result));
                        break;
                    case NetworkConsts.UPDATED_PROGRESS:
                        ProgressMessage progress = (ProgressMessage) m;
                        game.setProgress(progress.getUpdatedProgress(), playerNumber);
                        //Platform.runLater(new ServerUserMessage("Progress updated to: " + game.getProgress(playerNumber) , App.getListPane()));
                    }
                }
                if (game.getWinner() != 2) toSend.add(new Message(game.getWinner() == playerNumber ? NetworkConsts.WINNER : NetworkConsts.LOSER)); 
                Message[] result = toSend.toArray(new Message[0]);
                OOS.writeObject(result);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            Platform.runLater(new ServerUserMessage("Connection Closed Unexpectedly: " + address, App.getListPane()));
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }finally{
            try {
                OIS.close();
                OOS.close();
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    
}
