package ru.netology.moneytransferservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record Transfer (

        @Pattern(regexp = "^{16}$")
        @NotBlank
        String cardToNumber,
        @Pattern(regexp = "^{3}$")
        @NotBlank
        String cardFromValidTill,
        @Pattern(regexp = "^{16}$")
        @NotBlank
        String cardFromNumber,
        @Pattern(regexp = "^{3}$")
        @NotBlank
        String cardFromCVV,
        Amount amount
       ) {

}
