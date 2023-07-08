package ru.netology.moneytransferservice.server;


import org.springframework.stereotype.Component;
import ru.netology.moneytransferservice.exceptions.ErrorConfirmation;
import ru.netology.moneytransferservice.exceptions.ErrorInputData;
import ru.netology.moneytransferservice.exceptions.ErrorTransfer;
import ru.netology.moneytransferservice.model.ConfirmOperation;
import ru.netology.moneytransferservice.model.Transfer;
import ru.netology.moneytransferservice.model.TransferLog;
import ru.netology.moneytransferservice.repository.TransferRepository;

@Component
public class TransferServer {

    private final TransferRepository transferRepository;

    private final TransferLog transferLog;


    public TransferServer(TransferRepository transferRepository, TransferLog transferLog) {
        this.transferRepository = transferRepository;
        this.transferLog = transferLog;
    }

    public String transfer(Transfer transfer) {
        transferLog.transferLog(transfer);
        if (transfer.cardFromNumber().startsWith("4")) {
            throw new ErrorInputData("i'm don't like this card number: " + transfer.cardFromNumber() + " starts with \"4\"");
        }
        if (transfer.amount().value() > 100000) {
            throw new ErrorTransfer("value is so big (biggest then 1000)");
        }
        return transferRepository.getOperationId(transfer);
    }

    public String confirmOperation(ConfirmOperation confirmOperation) {
        if (confirmOperation.operationId() == null) {
            transferLog.transferResultLog(confirmOperation);
            return transferRepository.confirmOperation(confirmOperation);
        } else {
            throw new ErrorConfirmation("transfer is not confirmed from repository");
        }

    }
}
