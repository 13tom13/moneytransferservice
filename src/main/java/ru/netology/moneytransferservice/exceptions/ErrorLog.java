package ru.netology.moneytransferservice.exceptions;

import ru.netology.moneytransferservice.logger.TransferLog;

public abstract class ErrorLog extends RuntimeException {

    public ErrorLog(String msg, TransferLog log) {
        super(msg);
        log.errorLog(msg);
    }

}
