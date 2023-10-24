package stub2.Models;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class SaveUserInfo {

    String dataFromFile;
    String newData;
    int randomLine;

    String fileName = "output.txt";
    ObjectMapper mapper = new ObjectMapper();
    User user;
    private FileWriter writer;
    private FileReader reader;


    public void saveToFile(User user) {
        try {
            newData = readFileAsString() + "\n" + mapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try {
            writer = new FileWriter(fileName);
            writer.write(newData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String readFileAsString() {
        try {
            dataFromFile = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dataFromFile;
    }

    public void readRandomUserFromFile() {

        // определяется количество строк в файле
        LineNumberReader count = null;
        int noOfLines;
        try {
            reader = new FileReader(fileName);
            count = new LineNumberReader(reader);
            count.skip(Integer.MAX_VALUE);
            noOfLines = count.getLineNumber();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                reader.close();
                count.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        randomLine = new Random().nextInt(noOfLines);
        user = new User();

        // Читается файл построчно, доходит до рандомной строки, строка мапится в user, ресурсы закрываются автоматич
        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            String line;
            line = lines.skip(randomLine).findFirst().get();
            System.out.println(line);
            user = mapper.readValue(line, User.class);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(user);
    }
}

