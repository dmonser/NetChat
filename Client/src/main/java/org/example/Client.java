package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.log.Log;

import java.io.*;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Client implements TCPConnectionListener {
    public static Scanner scanner = new Scanner(System.in);
    public static JSONParser parser = new JSONParser();
    private static TCPConnection connection;
    public static String serverIP;
    public static long serverPort;
    private static final String PATH_TO_SETTINGS = Paths.get("").toAbsolutePath().toString() +
            "/Client/src/main/resources/settings.json";
    public static String nikName;
    private static final Log log = new Log("/Client/src/main/resources/");

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void start() {
        try {
            Object obj = parser.parse(new FileReader(PATH_TO_SETTINGS));
            JSONObject jsonObject = (JSONObject) obj;
            serverIP = (String) jsonObject.get("serverIP");
            serverPort = (Long) jsonObject.get("serverPort");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        System.out.print("Введите отображаемое в чате имя: ");
        nikName = scanner.nextLine();

//        new Client();
        try {
            connection = new TCPConnection(this, serverIP, (int) serverPort);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            String msg = scanner.nextLine();
            if (msg.equals("")) return;
            if (msg.equals("/exit")) {
                connection.disconnect();
                break;
            }
            connection.sendString(nikName + ": " + msg);
            log.writeLog(nikName + ": " + msg);
        }
    }

    private Client() {
//        try {
//            connection = new TCPConnection(this, serverIP, (int) serverPort);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        System.out.println("Connection is ready...");
    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String value) {
        System.out.println(currentDateAndTime() + value);
        log.writeLog(value);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        System.out.println("Connection close");
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("Connection exception: " + e);
    }

    private static String currentDateAndTime() {
        return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) + " ";
    }
}