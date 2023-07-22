package ru.netology.moneytransferservice.logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.netology.moneytransferservice.model.ConfirmData;
import ru.netology.moneytransferservice.model.TransferData;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TransferLog {

    @Value("${log.file.name}")
    private String filename;

    private final String transferTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss"));

    private synchronized void logEntry(String log) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(log);
            writer.flush();
            System.out.print(log);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void transferLog(TransferData transfer) {
        double value = (double) transfer.amount().value() / 100;
        double commission = value / 100;
        String transferForWrite =
                String.format("[TRANSFER] %s | from card: %s | to card: %s | value: %.2f | commission: %.2f\n",
                        transferTime, transfer.cardFromNumber(), transfer.cardToNumber(), value, commission);
        logEntry(transferForWrite);
    }

    public void errorLog(String msg) {
        String error = String.format("[ERROR] %s | message: %s\n", transferTime, msg);
        logEntry(error);
    }

    public synchronized void transferResultLog(ConfirmData confirmOperation) {
        String result = String.format("[RESULT] %s | success | (OperationID: %s)\n", transferTime, confirmOperation.operationId());
        logEntry(result);
    }
}
