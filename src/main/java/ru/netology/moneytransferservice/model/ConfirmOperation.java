package ru.netology.moneytransferservice.model;

public record ConfirmOperation(
        String operationId,
        String code
) {
}
