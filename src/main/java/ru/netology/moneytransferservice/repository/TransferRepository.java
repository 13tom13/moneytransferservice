package ru.netology.moneytransferservice.repository;

import org.springframework.stereotype.Component;
import ru.netology.moneytransferservice.model.ConfirmOperation;
import ru.netology.moneytransferservice.model.Transfer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TransferRepository {

    private final ConcurrentHashMap<String, Transfer> repository = new ConcurrentHashMap<>();

    private static final AtomicInteger count = new AtomicInteger(1);

    public TransferRepository() {

    }

    public String getOperationId(Transfer transfer) {
        String operationID = String.valueOf(count.getAndIncrement());
        repository.put(operationID, transfer);
        return operationID;
    }

    public String confirmOperation(ConfirmOperation confirmOperation) {
        // здесь можно добавить обработку поддтверждения операции
        // по operationID, но запрос с сервера всегда operationID = null
        if (confirmOperation.operationId() == null){
            return String.valueOf(count.get());
        } else {
            return null;
        }

    }


}
