package ru.netology.moneytransferservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.moneytransferservice.model.ConfirmOperation;
import ru.netology.moneytransferservice.model.Transfer;
import ru.netology.moneytransferservice.service.TransferService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class TransferController {


    private final TransferService transferServer;


    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody @Valid Transfer transfer) {
        return transferServer.transfer(transfer);
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<?> confirmOperation(@RequestBody ConfirmOperation confirmOperation) {
        return transferServer.confirmOperation(confirmOperation);
    }
}
