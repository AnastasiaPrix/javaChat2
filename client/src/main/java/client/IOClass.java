package client;

import javafx.scene.control.TextArea;

import java.awt.*;
import java.io.*;

public class IOClass {


    public static void writeWithBufferedWriter(String value, String nikname) {
        File file = new File("client/src/main/resources/history_"+nikname+".txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))){
            bw.newLine();
            bw.write(value);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readLastHundred(String nikname, TextArea textArea) {
        File file = new File("client/src/main/resources/history_"+nikname+".txt");
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = br.readLine()) != null && sb.length()<=100) {
                sb.append(line);
                textArea.appendText(line.toString()+ "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
