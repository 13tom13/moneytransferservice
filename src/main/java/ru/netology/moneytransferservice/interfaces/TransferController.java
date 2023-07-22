package ru.netology.moneytransferservice.interfaces;

import org.springframework.http.ResponseEntity;
import ru.netology.moneytransferservice.model.ConfirmData;
import ru.netology.moneytransferservice.model.TransferData;

public interface TransferController {

    ResponseEntity<?> transfer(TransferData transferInformation);

    ResponseEntity<?> confirmOperation(ConfirmData confirmData);

}
