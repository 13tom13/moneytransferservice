package ru.netology.moneytransferservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TransferData(

        @Size(min = 16)
        @NotBlank
        String cardToNumber,
        @Size(min = 4)
        @NotBlank
        String cardFromValidTill,
        @Size(min = 16)
        @NotBlank
        String cardFromNumber,
        @Size(min = 3)
        @NotBlank
        String cardFromCVV,
        Amount amount
       ) {

}
