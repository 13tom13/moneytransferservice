package ru.netology.moneytransferservice.exceptions;

import ru.netology.moneytransferservice.logger.TransferLog;

public class ErrorTransfer extends ErrorLog {
    public ErrorTransfer(String msg, TransferLog log) {
        super(msg, log);
    }
}
