package crypt_methods;

import javafx.scene.control.Alert;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Stolb {
    private String key;
    private String text;

    private char[][] matrix;

    private int[] collumnsNums;

    private char[] alphabet = new char[33];

    public void encrypt() {
        readText("text.txt");
        if (text.length() == 0) {
            showAlert("Текст пуст");
            return;
        }

        System.out.println(key);
        System.out.println(text);
        createEncryptionMatrix();
        createCollumsNums();
        saveEncryption();
    }

    public void decrypt() {
        readText("enc.txt");
        if (text.length() == 0) {
            showAlert("Текст пуст");
            return;
        }
        createCollumsNums();
        createDecryptionMatrix();
        saveDecryption();

    }

    public void saveDecryption() {
        try (FileWriter fileWriter = new FileWriter("C:\\Users\\User\\Desktop\\Cryptography\\1\\dec.txt")) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    if (matrix[i][j] == '\0') {
                        return;
                    } else {
                        fileWriter.write((char) matrix[i][j]);

                    }
                }
                fileWriter.write('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createDecryptionMatrix() {
        int m;
        int n;
        int ostatok;

        if ((ostatok = text.length() % key.length()) == 0) {
            m = text.length() / key.length();
            n = key.length();
        } else {
            m = text.length() / key.length() + 1;
            n = key.length();
        }

        matrix = new char[m][n];
        int pointer = 0;

        for (int num = 1; num <= collumnsNums.length; num++) {
            for (int j = 0; j < collumnsNums.length; j++) {
                if (collumnsNums[j] == num) {
                    if (ostatok == 0) {
                        for (int i = 0; i < m; i++) {
                            matrix[i][j] = text.charAt(pointer);
                            pointer++;
                        }
                    } else {
                        if (j + 1 <= ostatok) {
                            for (int i = 0; i < m; i++) {
                                matrix[i][j] = text.charAt(pointer);
                                pointer++;
                            }
                        } else {
                            if (j < matrix[0].length) {
                                for (int i = 0; i < m - 1; i++) {
                                    matrix[i][j] = text.charAt(pointer);
                                    pointer++;
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    private void createEncryptionMatrix() {
        int m;
        int n;
        if (text.length() % key.length() == 0) {
            m = text.length() / key.length();
            n = key.length();
        } else {
            m = text.length() / key.length() + 1;
            n = key.length();
        }

        matrix = new char[m][n];

        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n && count < text.length(); j++) {
                matrix[i][j] = text.charAt(count);
                count++;
            }
        }
    }

    private void createCollumsNums() {
        String key = this.key.toUpperCase();
        collumnsNums = new int[key.length()];
        int collumnsNum = 1;
        for (int lettreNum = 0; lettreNum < 33; lettreNum++) {
            for (int i = 0; i < key.length(); i++) {
                if (key.charAt(i) == alphabet[lettreNum] && collumnsNums[i] == 0) {
                    collumnsNums[i] = collumnsNum;
                    collumnsNum++;
                }
            }
        }
    }

    private String getCiphergram() {

        String ciphergram = "";
        for (int num = 1; num <= collumnsNums.length; num++) {
            for (int i = 0; i < collumnsNums.length; i++) {
                if (collumnsNums[i] == num && i < matrix[0].length) {
                    for (int j = 0; j < matrix.length && matrix[j][i] != '\0'; j++) {
                        ciphergram += matrix[j][i];
                    }
                    ciphergram += ' ';
                    break;
                }
            }
        }

        return ciphergram;
    }


    public void saveEncryption() {
        String ciphergram = getCiphergram();
        try (FileWriter fileWriter = new FileWriter("C:\\Users\\User\\Desktop\\Cryptography\\1\\enc.txt", false)) {
            for (int i = 0; i < ciphergram.length(); i++) {
                fileWriter.write(ciphergram.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Stolb() {
        key = "";
        text = "";
        setAlphabet();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        String currentKey = "";
        for (int i = 0; i < key.length(); i++) {
            if ((key.charAt(i) >= 1040 && key.charAt(i) <= 1103) || key.charAt(i) == 1025 || key.charAt(i) == 1105)
                currentKey += key.charAt(i);
        }
        this.key = currentKey;
    }

    private void readText(String filename) {
        text = "";
        try (FileReader fileReader = new FileReader("C:\\Users\\User\\Desktop\\Cryptography\\1\\" + filename)) {
            int c;
            while ((c = fileReader.read()) != -1) {
                if ((c >= 1040 && c <= 1103) || c == 1025 || c == 1105) {
                    text += (char) c;
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
