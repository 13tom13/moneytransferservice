package ru.netology.moneytransferservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.moneytransferservice.interfaces.TransferController;
import ru.netology.moneytransferservice.model.ConfirmData;
import ru.netology.moneytransferservice.model.TransferData;
import ru.netology.moneytransferservice.service.MoneyTransferService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class MoneyTransferController implements TransferController {


    private final MoneyTransferService transferServer;

    @Override
    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody @Valid TransferData transferData) {
        return transferServer.transfer(transferData);
    }

    @Override
    @PostMapping("/confirmOperation")
    public ResponseEntity<?> confirmOperation(@RequestBody  @Valid ConfirmData confirmData) {
        return transferServer.confirmOperation(confirmData);
    }
}
