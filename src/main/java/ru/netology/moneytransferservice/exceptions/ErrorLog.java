package ru.netology.moneytransferservice.exceptions;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorLog extends RuntimeException {


    public ErrorLog(String msg) {
        super(msg);
        errorLog(msg);
    }

    public void errorLog(String msg) {
        try (FileWriter writer = new FileWriter("src/main/resources/log/transfer.log", true)) {
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss"));
            String error = String.format("[ERROR] %s | message: %s\n", time, msg);
            writer.write(error);
            System.out.print(error);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
