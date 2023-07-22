package ru.netology.moneytransferservice.interfaces;

import ru.netology.moneytransferservice.model.ConfirmData;
import ru.netology.moneytransferservice.model.OperationId;
import ru.netology.moneytransferservice.model.TransferData;

public interface TransferRepository {

    OperationId getOperationId(TransferData transfer);

    OperationId confirmOperation(ConfirmData confirmOperation);

}
