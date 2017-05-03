package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    public TextField weekValue;
    public TextField dayValue;
    public TextField hourValue;
    public TextField minutesValue;
    public TextField secondsValue;
    public DatePicker dateFrom;
    public DatePicker dateTo;
    public TimeSpinner timeFrom;
    public TimeSpinner timeTo;
    public Button buttonStart;
    public TextArea logPlace;
    public Button chooseFile;
    public Label filePath;
    public CheckBox isLimited;

    private String log="";

    public void initialize(URL location, ResourceBundle resources) {
        initializeValues();
        addListeners();
        setFileLoaded();
        changeFieldEdits(false);
        isLimited.setSelected(Configuration.getInstance().isTimeLimited());
    }

    @FXML
    public synchronized void  startScript(Event event) {

        if(!validate()) return;

        TimeScheduler.getInstance().setValues(
                Integer.parseInt(weekValue.getText()),
                Integer.parseInt(dayValue.getText()),
                Integer.parseInt(hourValue.getText()),
                Integer.parseInt(minutesValue.getText()),
                Integer.parseInt(secondsValue.getText()),
                dateFrom.getValue(),
                timeFrom.getValue(),
                dateTo.getValue(),
                timeTo.getValue());

        if(!TimeScheduler.getInstance().isRunning){
            TimeScheduler.getInstance().setController(this);
            TimeScheduler.getInstance().start();
        }

        if( TimeScheduler.getInstance().isWorking) {
            addToLog("Stop working.");
            buttonStart.setText("Start");
            TimeScheduler.getInstance().isWorking = false;
        }else{
            addToLog("Start working.");
            buttonStart.setText("Stop");
            TimeScheduler.getInstance().isWorking = true;
        }
        changeFieldEdits( TimeScheduler.getInstance().isWorking);

    }

    private boolean validate(){
        boolean succcess = true;
        if(dateFrom.getValue() == null){
            addToLog("No filled date from.");
            succcess = false;
        }

        if(dateTo.getValue() == null && isLimited.isSelected()){
            addToLog("No filled date to.");
            succcess = false;
        }

        if(weekValue.getText().equals("")|| dayValue.getText().equals("") || hourValue.getText().equals("") || minutesValue.getText().equals("") || secondsValue.getText().equals("")){
            addToLog("No filled time value.");
            succcess = false;
        }

        if(dateTo.getValue() != null && dateTo.getValue().isBefore(LocalDate.now())){
            addToLog("Date until cannot be from past.");
        }
        if(dateFrom.getValue().isAfter(LocalDate.now())){
            addToLog("Date from cannot be from future.");
        }

        return succcess;
    }

    public void addToLog(String text){
        log+= text+"\n";
        logPlace.setText(log);
    }

    public void chooseFile(Event event) {
        Stage stage = (Stage) chooseFile.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose .bat file script");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Script file","*.bat"));
        File script = fileChooser.showOpenDialog(stage);

        Configuration.getInstance().setScriptFile(script);

        setFileLoaded();

    }

    private void setFileLoaded() {
        if(Configuration.getInstance().getScriptFile()!=null){
            String scriptPath = Configuration.getInstance().getScriptFile().getAbsolutePath();
            filePath.setText(scriptPath.substring(scriptPath.length()-40<0 ? 0 : scriptPath.length()-40));
            filePath.setStyle("-fx-background-color: lightgreen");
            buttonStart.setDisable(false);
        }
    }

    public void changeVisibleFields(Event event) {
        Configuration.getInstance().setTimeLimited(isLimited.isSelected());
        changeFieldEdits(false);

    }

    public void initializeValues(){
        weekValue.setText(""+Configuration.getInstance().getWeek());
        dayValue.setText(""+Configuration.getInstance().getDay());
        hourValue.setText(""+Configuration.getInstance().getHour());
        minutesValue.setText(""+Configuration.getInstance().getMinute());
        secondsValue.setText(""+Configuration.getInstance().getSecond());
        if(Configuration.getInstance().getScriptFile()!=null){
            buttonStart.setDisable(false);
        }else{
            buttonStart.setDisable(true);
        }
        dateFrom.setValue(LocalDate.now());
    }

    public void addListeners(){
        weekValue.textProperty().addListener((observable, oldValue, newValue) -> {
            Configuration.getInstance().setWeek(Integer.valueOf(weekValue.getText()));
        });
        dayValue.textProperty().addListener((observable, oldValue, newValue) -> {
            Configuration.getInstance().setDay(Integer.valueOf(dayValue.getText()));
        });
        hourValue.textProperty().addListener((observable, oldValue, newValue) -> {
            Configuration.getInstance().setHour(Integer.valueOf(hourValue.getText()));
        });
        minutesValue.textProperty().addListener((observable, oldValue, newValue) -> {
            Configuration.getInstance().setMinute(Integer.valueOf(minutesValue.getText()));
        });
        secondsValue.textProperty().addListener((observable, oldValue, newValue) -> {
            Configuration.getInstance().setSecond(Integer.valueOf(secondsValue.getText()));
        });
    }

    private void changeFieldEdits(boolean switchVal){
        weekValue.setDisable(switchVal);
        dayValue.setDisable(switchVal);
        hourValue.setDisable(switchVal);
        minutesValue.setDisable(switchVal);
        secondsValue.setDisable(switchVal);
        dateFrom.setDisable(switchVal);
        timeFrom.setDisable(switchVal);
        chooseFile.setDisable(switchVal);
        isLimited.setDisable(switchVal);
        if(Configuration.getInstance().isTimeLimited() && !switchVal) {
            dateTo.setDisable(false);
            timeTo.setDisable(false);
        }
        else{
            dateTo.setDisable(true);
            timeTo.setDisable(true);
        }
    }

}
