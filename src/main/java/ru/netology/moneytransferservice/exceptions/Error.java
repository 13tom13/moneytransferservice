package ru.netology.moneytransferservice.exceptions;

import ru.netology.moneytransferservice.logger.TransferLog;

import java.util.Random;

public abstract class Error extends RuntimeException {

    public Error(String msg, TransferLog log) {
        super(msg);
        log.errorLog(msg);
    }
    private final int id = Math.abs(new Random().nextInt());
}
