/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lawrence.asteroidgameserver.Network;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 *
 * @author Justin
 */
public class ServerUserMessage implements Runnable{
    private Label label;
    private Pane pane;
    private LocalTime time;
    public ServerUserMessage(String message, Pane parent){
        pane = parent;
        time = LocalTime.now();
        label = new Label(time.format(DateTimeFormatter.ISO_TIME)+ ": "  + message);
    }
    @Override
    public void run() {
        pane.getChildren().add(label);
    }
    
    
}
