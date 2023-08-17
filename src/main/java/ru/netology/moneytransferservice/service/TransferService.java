package ru.netology.moneytransferservice.service;

import org.springframework.http.ResponseEntity;
import ru.netology.moneytransferservice.model.ConfirmData;
import ru.netology.moneytransferservice.model.OperationId;
import ru.netology.moneytransferservice.model.TransferData;

public interface TransferService {

    ResponseEntity<OperationId> transfer(TransferData transferData);

    ResponseEntity<OperationId> confirmOperation(ConfirmData confirmData);
}
