package ru.netology.moneytransferservice.repository;

import org.springframework.stereotype.Component;
import ru.netology.moneytransferservice.model.ConfirmOperation;
import ru.netology.moneytransferservice.model.OperationId;
import ru.netology.moneytransferservice.model.Transfer;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TransferRepository {

    private final ConcurrentHashMap<String, String> repository = new ConcurrentHashMap<>();

    public TransferRepository() {

    }

    public OperationId getOperationId(Transfer transfer) {
        String operationId = String.valueOf(UUID.randomUUID());
        OperationId iDFromRep = new OperationId(operationId);
        String code = "0000"; //code stub
        repository.put(operationId, code);
        return iDFromRep;
    }

    public String confirmOperation(ConfirmOperation confirmOperation) {
        String iDForConfirm = confirmOperation.operationId();
        String codeFromRep = repository.remove(iDForConfirm);
        if (confirmOperation.code().equals(codeFromRep)) {
            return iDForConfirm;
        } else {
            return null;
        }
    }


}
