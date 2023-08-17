package ru.netology.moneytransferservice.exceptions;

import ru.netology.moneytransferservice.logger.TransferLog;

public class ErrorTransfer extends ApplicationTransferException {
    public ErrorTransfer(String msg, TransferLog log) {
        super(msg, log);
    }

}
