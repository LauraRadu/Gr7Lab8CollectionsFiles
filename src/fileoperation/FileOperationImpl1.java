package fileoperation;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileOperationImpl1 implements FileOperations {

    public List<String> readFromFileAsList(String fileName) {

        Path p = Paths.get(".", fileName);
        Charset c = Charset.forName("UTF-8");
        List<String> content= null;
        try {
            content = Files.readAllLines(p,c);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public String readFromFile(String fileName) {
        Path p = Paths.get(".", fileName);
        Charset c = Charset.forName("UTF-8");
        List<String> content= null;
        try {
            content = Files.readAllLines(p,c);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String rezultat="";

        for (String s: content
             ) {
            rezultat = rezultat+s + " " +System.getProperty("line.separator");
        }

        return rezultat;

    }

    public void writeFile(String fileName, String content) {

        try {
            Path p = Paths.get(".", fileName);
            if (Files.exists(p)) {
                Files.write(p, content.getBytes(), StandardOpenOption.APPEND);
            } else {

                Files.write(p, content.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeFile(String fileName, List<String> listOfStrings) {

        try {
            Path p = Paths.get(".", fileName);
            if (Files.exists(p)) {
                Files.write(p, listOfStrings, StandardOpenOption.APPEND);
            } else {

                Files.write(p, listOfStrings);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeFileMakeItEmpty(String fileName, String st) {
        try {
            Path p = Paths.get(".", fileName);
            if (Files.exists(p)) {
                Files.write(p, st.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
