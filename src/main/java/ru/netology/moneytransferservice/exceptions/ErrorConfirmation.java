package ru.netology.moneytransferservice.exceptions;

import ru.netology.moneytransferservice.model.TransferLog;

public class ErrorConfirmation extends ErrorLog {

    public ErrorConfirmation(String msg, TransferLog log) {
        super(msg, log);
    }
}
