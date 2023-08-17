package ru.netology.moneytransferservice.controller;

import org.springframework.http.ResponseEntity;
import ru.netology.moneytransferservice.model.ConfirmData;
import ru.netology.moneytransferservice.model.OperationId;
import ru.netology.moneytransferservice.model.TransferData;

public interface TransferController {

    ResponseEntity<OperationId> transfer(TransferData transferInformation);

    ResponseEntity<OperationId> confirmOperation(ConfirmData confirmData);

}
