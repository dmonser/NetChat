package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Client implements TCPConnectionListener {
    public static Scanner scanner = new Scanner(System.in);
    public static JSONParser parser = new JSONParser();
//    private static TCPConnection connection;
    public static String serverIP;
    public static long serverPort;
    private static final String PATH_TO_SETTINGS = Paths.get("").toAbsolutePath().toString() +
            "/Client/src/main/java/org/example/settings.json";
    public static String nikName;

    public static void main(String[] args) {
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


    }


    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {

    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String value) {

    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {

    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {

    }
}