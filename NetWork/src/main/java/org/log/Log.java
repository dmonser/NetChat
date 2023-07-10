package org.log;

import java.io.*;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Log {
    private static final String PATH_TO_PROJECT = Paths.get("").toAbsolutePath().toString();
    private static final String LOG_FILE_NAME = "file.log";
    public final String pathToModule;
    private final File file;

    public File getFile() {
        return file;
    }

    public Log(String pathToModule) {
        this.pathToModule = pathToModule;
        file = new File(PATH_TO_PROJECT + pathToModule, LOG_FILE_NAME);

        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public synchronized void writeLog(String msg) {
        msg = currentDateAndTime() + msg + "\n";
        OutputStream os = null;

        byte[] buffer = msg.getBytes();
        try {
            os = new FileOutputStream(file, true);
            os.write(buffer, 0, msg.length());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String currentDateAndTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + ": ";
    }
}
