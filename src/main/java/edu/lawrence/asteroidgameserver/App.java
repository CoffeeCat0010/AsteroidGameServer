package edu.lawrence.asteroidgameserver;

import edu.lawrence.asteroidgameserver.Network.ConnectionManager;
import edu.lawrence.asteroidgameserver.Network.ServerUserMessage;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
    private Pane pane;
    private static VBox list;
    private int clientNum = 0;
    @Override
    public void start(Stage stage) {
        Game game = new Game();
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();
        list = new VBox();
        list.setLayoutX(0);
        list.setLayoutY(0);
        var scene = new Scene(list, 640, 480);
        stage.setScene(scene);
        stage.show();
        
        new Thread(()-> {
            try {
                ServerSocket serverSocket = new ServerSocket(8000);
                while(clientNum < 2){
                    Socket socket = serverSocket.accept();
                    clientNum++;
                    System.out.println("Connection added from: " + socket.getRemoteSocketAddress().toString());
                    Platform.runLater(new ServerUserMessage("Connection added from: " + socket.getRemoteSocketAddress().toString(), list));
                    new Thread(new ConnectionManager(socket, game, clientNum - 1)).start();
                    if(clientNum == 2) game.start();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        }).start();
    }
    public static VBox getListPane(){
        return list;
    }

    public static void main(String[] args) {
        launch();
    }

}