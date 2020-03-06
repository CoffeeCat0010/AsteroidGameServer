/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lawrence.asteroidgameserver.Network;

import edu.lawrence.asteroidgameserver.Game;
import edu.lawrence.asteroidgameserver.Network.Messages.Message;
import edu.lawrence.asteroidgameserver.Network.Messages.ProgressMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Justin
 */
public class ConnectionManager implements Runnable, NetworkConsts {
    private Socket socket;
    private Game game;
    private int playerNumber;

    public ConnectionManager(Socket socket, Game game, int playerNumber){
        this.socket = socket;
        this.playerNumber = playerNumber;
        game.setProgress(0);
    }
    
    @Override
    public void run() {
        try{
            ObjectOutputStream OOS = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream OIS = new ObjectInputStream(socket.getInputStream());
            while(true){
                Message message = (Message)OIS.readObject();
                switch(message.getMessageType()){
                    case NetworkConsts.GET_PROGRESS:
                        int result = game.getProgress();
                        OOS.writeObject(new ProgressMessage(result));
                        break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    
}
