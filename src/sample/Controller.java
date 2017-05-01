package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    public TextField monthValue;
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

    Stage stage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValues();
        editableTimeAndDate();
        addListeners();
        setFileLoaded();
    }

    @FXML
    public void startScript(Event event) {

    }

    public void chooseFile(Event event) {
        stage = (Stage) chooseFile.getScene().getWindow();

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
        editableTimeAndDate();
    }

    public void initializeValues(){
        monthValue.setText(""+Configuration.getInstance().getMonth());
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

    private void editableTimeAndDate(){
        if(!Configuration.getInstance().isTimeLimited()){
            isLimited.setSelected(false);
            dateTo.setDisable(true);
            timeTo.setDisable(true);
        }else {
            isLimited.setSelected(true);
            dateTo.setDisable(false);
            timeTo.setDisable(false);
        }
    }

    public void addListeners(){
        monthValue.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                Configuration.getInstance().setMonth(Integer.valueOf(monthValue.getText()));
            }
        });
        dayValue.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                Configuration.getInstance().setDay(Integer.valueOf(dayValue.getText()));
            }
        });
        hourValue.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                Configuration.getInstance().setHour(Integer.valueOf(hourValue.getText()));
            }
        });
        minutesValue.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                Configuration.getInstance().setMinute(Integer.valueOf(minutesValue.getText()));
            }
        });
        secondsValue.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                Configuration.getInstance().setSecond(Integer.valueOf(secondsValue.getText()));
            }
        });
    }
}
