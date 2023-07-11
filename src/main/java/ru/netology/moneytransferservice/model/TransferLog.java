package ru.netology.moneytransferservice.model;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TransferLog {

    @Value("${log.file.name}")
    private String filename;

    public void transferLog(Transfer transfer) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            double value = (double) transfer.amount().value() / 100;
            double commission = value / 100;
            String transferTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss"));
            String transferForWrite =
                    String.format("[TRANSFER] %s | from card: %s | to card: %s | value: %.2f | commission: %.2f\n",
                            transferTime, transfer.cardFromNumber(), transfer.cardToNumber(), value, commission);
            writer.write(transferForWrite);
            writer.flush();
            System.out.print(transferForWrite);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void errorLog(String msg) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss"));
            String error = String.format("[ERROR] %s | message: %s\n", time, msg);
            writer.write(error);
            System.out.print(error);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void transferResultLog(ConfirmOperation confirmOperation) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            String result = String.format("[RESULT] success | (OperationID: %s)\n", confirmOperation.operationId());
            writer.write(result);
            System.out.print(result);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
