package sample;

import com.google.common.util.concurrent.AbstractScheduledService;

import java.io.IOException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;


class TimeScheduler extends Thread {

    private static TimeScheduler instance = null;
    protected TimeScheduler() {
    }
    public static TimeScheduler getInstance() {
        if(instance == null) {
            instance = new TimeScheduler();
        }
        return instance;
    }

    public LocalDateTime fromDateTime;
    public LocalDateTime toDateTime;
    public Duration period;
    public LocalDateTime nextRun;
    public boolean isRunning = false;
    public Boolean isWorking = false;

    public void setValues(int week, int day, int hour, int min, int sec, LocalDate dateFrom, LocalTime timeFrom, LocalDate dateTo, LocalTime timeTo){
        fromDateTime = LocalDateTime.of(dateFrom,timeFrom);
        if(dateTo != null && timeTo != null){
            toDateTime = LocalDateTime.of(dateTo,timeTo).withNano(0);
        }else{
            toDateTime = LocalDateTime.of(3000,1,1,1,1,1,1).withNano(0);
        }
        long seconds = week*7*24*60*60+day*24*60*60+hour*60*60+min*60+sec;
        period = Duration.ofSeconds(seconds);
        nextRun = fromDateTime.withNano(0);
    }

    private Controller c;

    void setController(Controller controller){
        this.c = controller;
    }

    @Override
    public void run() {
        isRunning = true;
        while(isRunning){
            try
            {
                if (isWorking) {
                    if(LocalDateTime.now().withNano(0).isEqual(nextRun) && toDateTime.isAfter(LocalDateTime.now().withNano(0))){
                        nextRun = nextRun.plus(period);
                        Process p = Runtime.getRuntime().exec(String.valueOf(Configuration.getInstance().getScriptFile()));
                        p.waitFor();
                        if(p.exitValue() == 0){
                            c.addToLog(LocalDateTime.now().withNano(0) + " Task executed success ");
                        }else{
                            c.addToLog(LocalDateTime.now().withNano(0) + " Task executed code " + p.exitValue());
                        }
                    }
                }
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {

            }catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
