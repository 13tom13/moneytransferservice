package ru.netology.moneytransferservice.model;

import javax.validation.constraints.NotNull;

public record ConfirmData(

        @NotNull
        String operationId,

        @NotNull
        String code
) {
}
