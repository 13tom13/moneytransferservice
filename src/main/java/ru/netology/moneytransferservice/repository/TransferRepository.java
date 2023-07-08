package ru.netology.moneytransferservice.repository;

import org.springframework.stereotype.Component;
import ru.netology.moneytransferservice.model.Transfer;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class TransferRepository {

    private final ConcurrentHashMap<String, Transfer> repository = new ConcurrentHashMap<>();

    private static int count = 1;

    public TransferRepository() {

    }

    public String getOperationId(Transfer transfer) {
        String operationID = String.valueOf(count++);
        repository.put(operationID,transfer);
        return operationID;
    }
}
