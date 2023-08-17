package ru.netology.moneytransferservice.repository;

import ru.netology.moneytransferservice.model.ConfirmData;
import ru.netology.moneytransferservice.model.OperationId;
import ru.netology.moneytransferservice.model.TransferData;

public interface TransferRepository {

    OperationId getOperationId(TransferData transfer);

    OperationId confirmOperation(ConfirmData confirmOperation);

}
