package ru.netology.moneytransferservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.exceptions.ErrorConfirmation;
import ru.netology.moneytransferservice.exceptions.ErrorInputData;
import ru.netology.moneytransferservice.exceptions.ErrorTransfer;
import ru.netology.moneytransferservice.repository.TransferRepository;
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
    public ResponseEntity<OperationId> transfer(TransferData transferData) {
        transferLog.transferLog(transferData);
        if (transferData.cardFromNumber() == null) {
            throw new ErrorInputData(
                    "debit card number not found", transferLog);
        }
        if (transferData.amount().value() > 100000) {
            throw new ErrorTransfer("value is so big (biggest then 1000)", transferLog);
        }
        OperationId iDFromRep = transferRepository.getOperationId(transferData);
        return new ResponseEntity<>(iDFromRep, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OperationId> confirmOperation(ConfirmData confirmData) {
        if (confirmData.code() == null) {
            throw new ErrorInputData("code not received", transferLog);
        }
        OperationId confirmId = transferRepository.confirmOperation(confirmData);
        if (confirmId.operationId().equals("denied"))
            throw new ErrorConfirmation("The transaction has not been approved", transferLog);
            transferLog.transferResultLog(confirmData);
            return new ResponseEntity<>(confirmId, HttpStatus.OK);
    }
}
