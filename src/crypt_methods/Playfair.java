package crypt_methods;

import javafx.scene.control.Alert;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Playfair {

    public String text;

    private char[][] matrix =
            {{'C', 'R', 'Y', 'P', 'T'},
                    {'O', 'G', 'A', 'H', 'B'},
                    {'D', 'E', 'F', 'I', 'K'},
                    {'L', 'M', 'N', 'Q', 'S'},
                    {'U', 'V', 'W', 'X', 'Z'}};

    public void decrypt() {
        readText("enc.txt");
        if (text.length() % 2 != 0) {
            showAlert("Длина текста должна быть чётной");
            return;
        }
        if (text.length() == 0) {
            showAlert("Текст пуст");
            return;
        }
        saveDecryption();

    }



    public void encrypt() {
        readText("text.txt");
        if (text.length() == 0) {
            showAlert("Текст пуст");
            return;
        }
        modifyText();
        saveEncryption();
    }

    private String getCurrentText() {
        String currentText = "";
        char letterOne;
        char letterTwo;
        char currentLetterOne = '#';
        char currentLetterTwo = '#';
        for (int i = 0; i < text.length(); i += 2) {
            letterOne = text.charAt(i);
            letterTwo = text.charAt(i + 1);
            if (getI(letterOne) != getI(letterTwo) && getJ(letterOne) != getJ(letterTwo)) {
                currentLetterOne = matrix[getI(letterOne)][getJ(letterTwo)];
                currentLetterTwo = matrix[getI(letterTwo)][getJ(letterOne)];
            } else {
                if (getI(letterOne) == getI(letterTwo)) {
                    int j = getJ(letterOne) - 1;
                    if (j == -1)
                        j = 4;
                    currentLetterOne = matrix[getI(letterOne)][j];
                    j = getJ(letterTwo) - 1;
                    if (j == -1)
                        j = 4;
                    currentLetterTwo = matrix[getI(letterTwo)][j];
                } else {
                        int j = getI(letterOne) - 1;
                        if (j == -1)
                            j = 4;
                        currentLetterOne = matrix[j][getJ(letterOne)];
                        j = getI(letterTwo) - 1;
                        if (j == -1)
                            j = 4;
                        currentLetterTwo = matrix[j][getJ(letterTwo)];

                }
            }
                currentText += currentLetterOne;
                currentText += currentLetterTwo;

        }
        return currentText;
    }

    private String getCiphergram() {
        String ciphergram = "";
        for (int i = 0; i < text.length(); i += 2) {
            char letterOne = text.charAt(i);
            char letterTwo = text.charAt(i + 1);
            char newLetterOne;
            char newLetterTwo;

            if (getI(letterOne) != getI(letterTwo) && getJ(letterOne) != getJ(letterTwo)) {
                newLetterOne = matrix[getI(letterOne)][getJ(letterTwo)];
                newLetterTwo = matrix[getI(letterTwo)][getJ(letterOne)];
            } else {
                if (getI(letterOne) == getI(letterTwo)) {
                    newLetterOne = matrix[getI(letterOne)][(getJ(letterOne) + 1) % 5];
                    newLetterTwo = matrix[getI(letterTwo)][(getJ(letterTwo) + 1) % 5];
                } else {
                    newLetterOne = matrix[(getI(letterOne) + 1) % 5][getJ(letterOne)];
                    newLetterTwo = matrix[(getI(letterTwo) + 1) % 5][getJ(letterTwo)];
                }
            }

            ciphergram += newLetterOne;
            ciphergram += newLetterTwo;
        }
        return ciphergram;
    }

    private void modifyText() {
        String currentText = "";
        System.out.println(text);
        for (int i = 0; i < text.length(); i += 2) {
            currentText += text.charAt(i);
            if (i == text.length() - 1) {
                if (currentText.length() % 2 != 0) {
                    if (text.charAt(i) == 'X') {
                        currentText += 'Q';
                        break;
                    } else {
                        currentText += 'X';
                        break;
                    }
                }
            }

            if (getI(text.charAt(i)) == getI(text.charAt(i + 1)) && getJ(text.charAt(i)) == getJ(text.charAt(i + 1))) {
                if (text.charAt(i) == 'X') {
                    currentText += 'Q';
                    i--;
                } else {
                    currentText += 'X';
                    i--;
                }

            } else {
                currentText += text.charAt(i + 1);
            }
        }

        text = currentText;

        System.out.println(currentText);
    }

    private int getI(char letter) {
        if (letter == 'J')
            return 2;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (letter == matrix[i][j])
                    return i;
            }
        }
        return 0;
    }

    private int getJ(char letter) {
        if (letter == 'J')
            return 3;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (letter == matrix[i][j])
                    return j;
            }
        }
        return 0;
    }


    public Playfair() {
        text = "";
    }

    public void saveEncryption() {
        String ciphergram = getCiphergram();
        System.out.println(ciphergram);
        try (FileWriter fileWriter = new FileWriter("C:\\Users\\User\\Desktop\\Cryptography\\3\\enc.txt", false)) {
            for (int i = 0; i < ciphergram.length(); i++) {
                fileWriter.write(ciphergram.charAt(i));
                if (i % 2 == 1 && i != 0) {
                    fileWriter.write(' ');
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDecryption() {
        String decryptedText = getCurrentText();
        try (FileWriter fileWriter = new FileWriter("C:\\Users\\User\\Desktop\\Cryptography\\3\\dec.txt", false)) {
            for (int i = 0; i < decryptedText.length(); i++) {
                fileWriter.write(decryptedText.charAt(i));
                if (i % 2 == 1 && i != 0) {
                    fileWriter.write(' ');
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readText(String filename) {
        text = "";
        try (FileReader fileReader = new FileReader("C:\\Users\\User\\Desktop\\Cryptography\\3\\" + filename)) {
            int c;
            while ((c = fileReader.read()) != -1) {
                if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) {
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
}
