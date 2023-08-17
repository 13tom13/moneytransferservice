package ru.netology.moneytransferservice.exceptions;

import ru.netology.moneytransferservice.logger.TransferLog;

public class ErrorTransfer extends Error {
    public ErrorTransfer(String msg, TransferLog log) {
        super(msg, log);
    }

}
