package ru.netology.moneytransferservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.exceptions.ErrorConfirmation;
import ru.netology.moneytransferservice.exceptions.ErrorInputData;
import ru.netology.moneytransferservice.exceptions.ErrorTransfer;
import ru.netology.moneytransferservice.interfaces.TransferRepository;
import ru.netology.moneytransferservice.interfaces.TransferService;
import ru.netology.moneytransferservice.logger.TransferLog;
import ru.netology.moneytransferservice.model.ConfirmData;
import ru.netology.moneytransferservice.model.OperationId;
import ru.netology.moneytransferservice.model.TransferData;

@Service
@RequiredArgsConstructor
public class MoneyTransferService implements TransferService {

    private final TransferRepository transferRepository;

    private final TransferLog transferLog;

    @Override
    public ResponseEntity<?> transfer(TransferData transferData) {
        transferLog.transferLog(transferData);
        if (transferData.cardFromNumber().startsWith("4")) {
            throw new ErrorInputData(
                    "i'm don't like this card number: " + transferData.cardFromNumber() + " starts with \"4\"", transferLog);
        }
        if (transferData.amount().value() > 100000) {
            throw new ErrorTransfer("value is so big (biggest then 1000)", transferLog);
        }
        OperationId iDFromRep = transferRepository.getOperationId(transferData);
        return new ResponseEntity<>(iDFromRep, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> confirmOperation(ConfirmData confirmData) {
        if (confirmData.code() == null || confirmData.code().length() > 4) {
            throw new ErrorInputData("code not received", transferLog);
        }
        OperationId confirmId = transferRepository.confirmOperation(confirmData);
        if (!confirmId.operationId().equals("denied")) {
            transferLog.transferResultLog(confirmData);
            return new ResponseEntity<>(confirmId, HttpStatus.OK);
        } else {
            throw new ErrorConfirmation("The transaction has not been approved", transferLog);
        }
    }
}
