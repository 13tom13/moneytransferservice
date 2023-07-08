package ru.netology.moneytransferservice.model;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TransferLog {

     public void transferLog (Transfer transfer){
         try(FileWriter writer = new FileWriter("src/main/resources/log/transfer.log", true))
         {
             double value = (double) transfer.amount().value()/100;
             double commission = value/100;
             String transferTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss"));
             // запись всей строки
             String transferForWrite = String.format("[TRANSFER] %s | from card: %s | to card: %s | value: %.2f | commission: %.2f\n",
                     transferTime, transfer.cardFromNumber(), transfer.cardToNumber(), value, commission);
             writer.write(transferForWrite);
             writer.flush();
             System.out.print(transferForWrite);
         }
         catch(IOException ex){
             System.out.println(ex.getMessage());
         }
     }

     public void transferResultLog (ConfirmOperation confirmOperation){
             try(FileWriter writer = new FileWriter("src/main/resources/log/transfer.log", true))
             {
                 String result = (confirmOperation.code() != null) ? "[RESULT] success\n" : "[RESULT] denied\n";
                 writer.write(result);
                 System.out.print(result);
                 writer.flush();
             }
             catch(IOException ex){
                 System.out.println(ex.getMessage());
             }
     }
}
