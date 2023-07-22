package ru.netology.moneytransferservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.netology.moneytransferservice.model.ConfirmOperation;
import ru.netology.moneytransferservice.model.OperationId;
import ru.netology.moneytransferservice.model.Transfer;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class TransferRepository {

    private final ConcurrentHashMap<String, String> repository = new ConcurrentHashMap<>();

    public OperationId getOperationId(Transfer transfer) {
        String operationId = String.valueOf(UUID.randomUUID());
        OperationId iDFromRep = new OperationId(operationId);
        String code = "0000"; //code stub
        repository.put(operationId, code);
        return iDFromRep;
    }

    public OperationId confirmOperation(ConfirmOperation confirmOperation) {
        String iDForConfirm = confirmOperation.operationId();
        String codeFromRep = repository.remove(iDForConfirm);
        if (confirmOperation.code().equals(codeFromRep)) {
            return new OperationId(iDForConfirm);
        } else {
            return new OperationId("denied");
        }
    }


}
