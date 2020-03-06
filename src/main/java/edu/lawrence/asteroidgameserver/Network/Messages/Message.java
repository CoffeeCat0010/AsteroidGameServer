/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lawrence.asteroidgameserver.Network.Messages;

import edu.lawrence.asteroidgameserver.Network.NetworkConsts;

/**
 *
 * @author Justin
 */
public class Message implements NetworkConsts{
    private final int messageType;
    
    protected Message(int messageType){
        this.messageType = messageType;
    }

    public int getMessageType() {
        return messageType;
    }
}
