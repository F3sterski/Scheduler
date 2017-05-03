package sample;

import java.io.*;

/**
 * Created by szkol_000 on 01.05.2017.
 */
public class Configuration implements Serializable{
    private static Configuration instance = null;
    protected Configuration() {
    }
    public static Configuration getInstance() {
        if(instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    private File scriptFile;

    private boolean isTimeLimited;

    private int week;

    private int day;

    private int hour;

    private int minute;

    private int second;

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public boolean isTimeLimited() {
        return isTimeLimited;
    }

    public void setTimeLimited(boolean timeLimited) {
        isTimeLimited = timeLimited;
    }

    public File getScriptFile() {
        return scriptFile;
    }

    public void setScriptFile(File scriptFile) {
        this.scriptFile = scriptFile;
    }

    public static void serializeConfiguration(){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("configuration.azc");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(Configuration.getInstance());
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in configuration.azc");
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    public static void deserializeConfiguration(){
        try {
            FileInputStream fileIn = new FileInputStream("configuration.azc");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            instance = (Configuration) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i) {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c) {
            System.out.println("Configuration class not found");
            c.printStackTrace();
            return;
        }
    }
}
