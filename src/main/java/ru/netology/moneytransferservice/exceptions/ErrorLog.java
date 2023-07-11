package ru.netology.moneytransferservice.exceptions;

import ru.netology.moneytransferservice.model.TransferLog;

public class ErrorLog extends RuntimeException {

    public ErrorLog(String msg, TransferLog log) {
        super(msg);
        log.errorLog(msg);
    }

}
