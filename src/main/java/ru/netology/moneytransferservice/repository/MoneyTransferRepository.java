package ru.netology.moneytransferservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.netology.moneytransferservice.model.ConfirmData;
import ru.netology.moneytransferservice.model.OperationId;
import ru.netology.moneytransferservice.model.TransferData;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class MoneyTransferRepository implements TransferRepository {

    private static final String CODE_STUB = "0000";

    private final ConcurrentHashMap<String, String> repository = new ConcurrentHashMap<>();

    @Override
    public OperationId getOperationId(TransferData transferData) {
        String operationId = String.valueOf(UUID.nameUUIDFromBytes(transferData.toString().getBytes()));
        OperationId iDFromRep = new OperationId(operationId);
        repository.putIfAbsent(operationId, CODE_STUB);
        return iDFromRep;
    }

    @Override
    public OperationId confirmOperation(ConfirmData confirmData) {
        String iDForConfirm = confirmData.operationId();
        String codeFromRep = repository.remove(iDForConfirm);
        return confirmData.code().equals(codeFromRep) ?
                new OperationId(iDForConfirm) : new OperationId("denied");
    }

}
