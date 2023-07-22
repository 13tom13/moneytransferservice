package ru.netology.moneytransferservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.netology.moneytransferservice.interfaces.TransferRepository;
import ru.netology.moneytransferservice.model.ConfirmData;
import ru.netology.moneytransferservice.model.OperationId;
import ru.netology.moneytransferservice.model.TransferData;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class MoneyTransferRepository implements TransferRepository {

    private final ConcurrentHashMap<String, String> repository = new ConcurrentHashMap<>();

    @Override
    public OperationId getOperationId(TransferData transferData) {
        String operationId = String.valueOf(UUID.nameUUIDFromBytes(transferData.toString().getBytes()));
        OperationId iDFromRep = new OperationId(operationId);
        String code = "0000"; //code stub
        repository.put(operationId, code);
        return iDFromRep;
    }

    @Override
    public OperationId confirmOperation(ConfirmData confirmData) {
        String iDForConfirm = confirmData.operationId();
        String codeFromRep = repository.remove(iDForConfirm);
        if (confirmData.code().equals(codeFromRep)) {
            return new OperationId(iDForConfirm);
        } else {
            return new OperationId("denied");
        }
    }


}
