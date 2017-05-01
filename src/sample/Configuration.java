package sample;

import java.io.File;
import java.io.Serializable;

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

    public static void setInstanceOnLoad(Configuration c){
        instance = c;
    }

    private File scriptFile;

    private boolean isTimeLimited;

    private int month;

    private int day;

    private int hour;

    private int minute;

    private int second;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
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
}
