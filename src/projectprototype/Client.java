package projectprototype;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Client {

    private ObjectInputStream input;
    private ObjectOutputStream output;

    protected Player clientPlayer;
    
    public Client() {
        try {
            Socket socket = new Socket("localhost", 4444);
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());
        } catch(Exception e) {}
        startListening();
    }

    public Client(String address) {
        try {
            Socket socket = new Socket(address, 18);
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {}
        startListening();
    }

    public void send(Circle circle) throws IOException {
        output.writeObject(circle);
    }
    
    public void send(Player player) throws IOException {
        output.writeObject(player);
    }
    
    public void startListening() {
        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                String t = JOptionPane.showInputDialog("Please enter your name:");
                clientPlayer = new Player(t);
                try {
                    send(clientPlayer);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                while(true) {
                    Circle circle = null;
                    try {
                        circle = (Circle)input.readObject();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                    if(circle != null) {
                        circle = new Circle(circle);
                        circle.player = GamePanel.player1;
                        GamePanel.player1.objects.add(circle);
                    }
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }
}