package org.example;

import org.log.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Server implements TCPConnectionListener {

    public static void main(String[] args) {
        System.out.println(Paths.get("").toAbsolutePath().toString());
        new Server();
    }

    private final Log log = new Log("/Server/src/main/java/");

    private final ArrayList<TCPConnection> connections = new ArrayList<>();

    private Server() {
        System.out.println("Server running...");
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            while (true) {
                try {
                    new TCPConnection(this, serverSocket.accept());
                } catch (IOException e) {
                    System.out.println("TCPConnection exception: " + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendToAllConnections(String value) {
        System.out.println(value);
        log.writeLog(value);
        for (TCPConnection connection : connections) {
            connection.sendString(value);
        }
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        sendToAllConnections("Client connected: " + tcpConnection);
        log.writeLog("Client connected: " + tcpConnection);
    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String value) {
        sendToAllConnections(value);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sendToAllConnections("Client disconnected: " + tcpConnection);
        log.writeLog("Client disconnected: " + tcpConnection);
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection exception: " + e);
    }


}