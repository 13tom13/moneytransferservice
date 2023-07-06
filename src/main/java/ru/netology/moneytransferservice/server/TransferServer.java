package ru.netology.moneytransferservice.server;


import org.springframework.stereotype.Component;
import ru.netology.moneytransferservice.model.Transfer;
import ru.netology.moneytransferservice.repository.TransferRepository;

@Component
public class TransferServer {

    private final TransferRepository transferRepository;


    public TransferServer(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public String transfer (Transfer transfer) {
        return transferRepository.getOperationId(transfer);
    }
}
