package ru.netology.moneytransferservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.moneytransferservice.interfaces.TransferController;
import ru.netology.moneytransferservice.interfaces.TransferService;
import ru.netology.moneytransferservice.model.ConfirmData;
import ru.netology.moneytransferservice.model.OperationId;
import ru.netology.moneytransferservice.model.TransferData;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class MoneyTransferController implements TransferController {


    private final TransferService transferServer;

    @Override
    @PostMapping("/transfer")
    public ResponseEntity<OperationId> transfer(@RequestBody @Valid TransferData transferData) {
        return transferServer.transfer(transferData);
    }

    @Override
    @PostMapping("/confirmOperation")
    public ResponseEntity<OperationId> confirmOperation(@RequestBody @Valid ConfirmData confirmData) {
        return transferServer.confirmOperation(confirmData);
    }
}
