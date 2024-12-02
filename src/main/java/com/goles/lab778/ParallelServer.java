package com.goles.lab778;
import java.io.IOException;
import java.net.ServerSocket;

public class ParallelServer {
    
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(7777);
        Thread serverThread = new ServerThread(server);
        serverThread.start();
    }
}
