package ru.netology.moneytransferservice.exceptions;

import ru.netology.moneytransferservice.logger.TransferLog;

public class ErrorConfirmation extends ApplicationTransferException {

    public ErrorConfirmation(String msg, TransferLog log) {
        super(msg, log);
    }
}
