package sample;

import crypt_methods.Playfair;
import crypt_methods.Stolb;
import crypt_methods.Vigener;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField stolbKeyTextField;

    @FXML
    private Button stolbEncryptButton;

    @FXML
    private Button stolbDecryptButton;

    @FXML
    private TextField vigenerKeyTextField;

    @FXML
    private Button vigenerEncryptButton;

    @FXML
    private Button vigenerDecryptButton;

    @FXML
    private Button playfairEncryptButton;

    @FXML
    private Button playfairDecryptButton;



    public Stolb stolb;
    public Vigener vigener;
    public Playfair playfair;


    @FXML
    public void initialize(){
        stolb = new Stolb();
        vigener = new Vigener();
        playfair = new Playfair();



        stolbEncryptButton.setOnAction(actionEvent -> {
            String key = stolbKeyTextField.getText();
           if(isCorrectRusKey(key)) {
               stolb.setKey(key);
               stolb.encrypt();
           }
        });

        stolbDecryptButton.setOnAction(actionEvent -> {
            String key = stolbKeyTextField.getText();
            if(isCorrectRusKey(key)) {
                stolb.setKey(key);
                stolb.decrypt();
            }
        });

        vigenerEncryptButton.setOnAction(actionEvent -> {
            String key = vigenerKeyTextField.getText();
            if(isCorrectRusKey(key)){
                vigener.setKey(key);
                vigener.encrypt();
            }
        });

        vigenerDecryptButton.setOnAction(actionEvent -> {
            String key = vigenerKeyTextField.getText();
            if(isCorrectRusKey(key)){
                vigener.setKey(key);
                vigener.decrypt();
            }
        });

        playfairEncryptButton.setOnAction(actionEvent -> {
            playfair.encrypt();
        });

        playfairDecryptButton.setOnAction(actionEvent -> {
            playfair.decrypt();
        });

    }

    private boolean isCorrectRusKey(String key){
        String currentKey = "";
        if(key.length() == 0 ){
            showAlert("Пустой ключ");
            return false;
        }
        for (int i = 0; i < key.length(); i++) {
            if ((key.charAt(i) >= 1040 && key.charAt(i) <= 1103) || key.charAt(i) == 1025 || key.charAt(i) == 1105)
                currentKey += key.charAt(i);
        }
        if (currentKey.length() == 0){
            showAlert("Некорректый ключ");
            return false;
        }
        return true;
    }
    public void showAlert(String textOfError){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка ввода");
        alert.setContentText(textOfError);
        alert.showAndWait();
    }



}
