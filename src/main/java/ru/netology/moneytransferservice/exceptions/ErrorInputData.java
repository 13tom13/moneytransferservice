package ru.netology.moneytransferservice.exceptions;

import ru.netology.moneytransferservice.logger.TransferLog;

public class ErrorInputData extends Error {
    public ErrorInputData(String msg, TransferLog log) {
        super(msg, log);
    }
}
