package ru.netology.moneytransferservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.exceptions.ErrorConfirmation;
import ru.netology.moneytransferservice.exceptions.ErrorInputData;
import ru.netology.moneytransferservice.exceptions.ErrorTransfer;
import ru.netology.moneytransferservice.model.ConfirmOperation;
import ru.netology.moneytransferservice.model.OperationId;
import ru.netology.moneytransferservice.model.Transfer;
import ru.netology.moneytransferservice.logger.TransferLog;
import ru.netology.moneytransferservice.repository.TransferRepository;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;

    private final TransferLog transferLog;

    public ResponseEntity<?> transfer(Transfer transfer) {
        transferLog.transferLog(transfer);
        if (transfer.cardFromNumber().startsWith("4")) {
            throw new ErrorInputData(
                    "i'm don't like this card number: " + transfer.cardFromNumber() + " starts with \"4\"", transferLog);
        }
        if (transfer.amount().value() > 100000) {
            throw new ErrorTransfer("value is so big (biggest then 1000)", transferLog);
        }
        OperationId iDFromRep = transferRepository.getOperationId(transfer);
        return new ResponseEntity<>(iDFromRep, HttpStatus.OK);
    }

    public ResponseEntity<?> confirmOperation(ConfirmOperation confirmOperation) {
        if (confirmOperation.code() == null || confirmOperation.code().length() > 4) {
            throw new ErrorInputData("code not received", transferLog);
        }
        OperationId confirmId = transferRepository.confirmOperation(confirmOperation);
        if (!confirmId.operationId().equals("denied")) {
            transferLog.transferResultLog(confirmOperation);
            return new ResponseEntity<>(confirmId, HttpStatus.OK);
        } else {
            throw new ErrorConfirmation("The transaction has not been approved", transferLog);
        }
    }
}
