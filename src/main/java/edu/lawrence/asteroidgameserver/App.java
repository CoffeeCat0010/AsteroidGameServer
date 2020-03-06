package edu.lawrence.asteroidgameserver;

import edu.lawrence.asteroidgameserver.Network.ConnectionManager;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
    private int clientNum = 0;
    @Override
    public void start(Stage stage) {
        Game game = new Game();
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
        
        new Thread(()-> {
            try {
                ServerSocket serverSocket = new ServerSocket(8000);
                while(clientNum < 2){
                    Socket socket = serverSocket.accept();
                    clientNum++;
                    new Thread(new ConnectionManager(socket, game, clientNum)).start();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        });
    }

    public static void main(String[] args) {
        launch();
    }

}