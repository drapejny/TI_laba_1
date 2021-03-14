package crypt_methods;

import javafx.scene.control.Alert;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Vigener {

    private String key;
    private String text;
    private char[] alphabet = new char[33];

    public void encrypt() {
        readText("text.txt");
        if(text.length() == 0) {
            showAlert("Текст пуст");
            return;
        }
        if (key.length() > text.length()) {
            key = key.substring(0,text.length());
        }
        saveEncryption();
    }

    public void decrypt() {
        readText("enc.txt");
        if(text.length() == 0) {
            showAlert("Текст пуст");
            return;
        }
        if (key.length() > text.length()) {
            key = key.substring(0,text.length());
        }
        System.out.println("KEY - " + key);
        System.out.println("TEXT - " + text);
        saveDecryption();
    }

    private String getDecryptedText() {
        String keyLine = key;
        String currentText = "";
        for (int i = 0; i < text.length(); i++) {
            char letter = alphabet[((getLetterNum(text.charAt(i)) + 33 - getLetterNum(keyLine.charAt(i))) % 33)];
            if (keyLine.length() < text.length()) {
                currentText += letter;
                keyLine += letter;
            } else {
                currentText += letter;
            }
            System.out.println("keyLine-l" + keyLine.length());
            System.out.println(keyLine);
        }
        System.out.println(currentText);
        return currentText;
    }

    public void saveDecryption() {
        String decryptedText = getDecryptedText();
        try (FileWriter fileWriter = new FileWriter("C:\\Users\\User\\Desktop\\Cryptography\\2\\dec.txt", false)) {
            for (int i = 0; i < decryptedText.length(); i++) {
                fileWriter.write(decryptedText.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveEncryption() {
        String ciphergram = getCiphergram();
        try (FileWriter fileWriter = new FileWriter("C:\\Users\\User\\Desktop\\Cryptography\\2\\enc.txt", false)) {
            for (int i = 0; i < ciphergram.length(); i++) {
                fileWriter.write(ciphergram.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCiphergram() {
        String text = this.text.toUpperCase();
        String keyLine = getKeyLine();
        System.out.println(keyLine);
        System.out.println(text);
        String ciphergram = "";

        for (int i = 0; i < text.length(); i++) {
            char cipherLetter = alphabet[(getLetterNum(text.charAt(i)) + getLetterNum(keyLine.charAt(i))) % 33];
            ciphergram += cipherLetter;
        }
        return ciphergram;

    }

    private int getLetterNum(char letter) {
        letter = Character.toUpperCase(letter);
        for (int i = 0; i < alphabet.length; i++) {
            if (letter == alphabet[i])
                return i;
        }
        return 0;
    }

    private String getKeyLine() {
        String keyLine = key;
        for (int i = 0; i < text.length(); i++) {
            if (keyLine.length() == text.length()) {
                return keyLine;
            }
            keyLine += text.charAt(i);
        }
        return keyLine;
    }


    public Vigener() {
        key = "";
        text = "";
        setAlphabet();

    }

    public void setKey(String key) {
        String currentKey = "";
        for (int i = 0; i < key.length(); i++) {
            if ((key.charAt(i) >= 1040 && key.charAt(i) <= 1103) || key.charAt(i) == 1025 || key.charAt(i) == 1105)
                currentKey += Character.toUpperCase(key.charAt(i));
        }
        this.key = currentKey;
    }

    private void readText(String filename) {
        text = "";
        try (FileReader fileReader = new FileReader("C:\\Users\\User\\Desktop\\Cryptography\\2\\" + filename)) {
            int c;
            while ((c = fileReader.read()) != -1) {
                if ((c >= 1040 && c <= 1103) || c == 1025 || c == 1105) {
                    text += Character.toUpperCase((char) c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String textOfError) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка ввода");
        alert.setContentText(textOfError);
        alert.showAndWait();
    }

    public String getKey() {
        return key;
    }

    public String getText() {
        return text;
    }

    private void setAlphabet() {
        int letter = 1040;
        for (int i = 0; i < 33; i++) {
            if (i == 6) {
                alphabet[i] = 'Ё';
                continue;
            }
            alphabet[i] = (char) letter;
            letter++;
        }
    }

}


