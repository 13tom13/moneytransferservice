package ru.netology.moneytransferservice.exceptions;

import ru.netology.moneytransferservice.model.TransferLog;

public class ErrorInputData extends ErrorLog {
    public ErrorInputData(String msg, TransferLog log) {
        super(msg, log);
    }
}
