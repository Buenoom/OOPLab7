package com.goles.lab778;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {
    private ServerSocket server;
    
    public ServerThread(ServerSocket server) {
        this.server = server;
    }
    
    public void run() {
        try {
            System.out.println("Server started");
            Socket client = server.accept();
            System.out.println("Connection established");
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            Matrix matrix = null;
            while (matrix == null) {
                matrix = (Matrix)in.readObject();
            }
            System.out.println("Matrix retrieved");
            Integer result = Matrix.getOddElementSum(matrix);
            out.writeObject(result);
            System.out.println("Result sent");
            in.close();
            out.close();
            client.close();
            server.close();
            System.out.println("Server shutdown");
        }
        catch (IOException | ClassNotFoundException e) {
            System.err.println(e);
        }
    }
}
