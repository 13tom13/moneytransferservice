package ru.netology.moneytransferservice.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.moneytransferservice.model.ConfirmOperation;
import ru.netology.moneytransferservice.model.OperationId;
import ru.netology.moneytransferservice.model.Transfer;
import ru.netology.moneytransferservice.server.TransferServer;

@CrossOrigin
@RestController
public class TransferController {

    private final TransferServer transferServer;

    public TransferController(TransferServer transferServer) {
        this.transferServer = transferServer;
    }

    @PostMapping("/transfer")
    public OperationId transfer(@RequestBody Transfer transfer) {
        return transferServer.transfer(transfer);
    }

    @PostMapping("/confirmOperation")
    public OperationId confirmOperation(@RequestBody ConfirmOperation confirmOperation) {
        return transferServer.confirmOperation(confirmOperation);
    }
}
