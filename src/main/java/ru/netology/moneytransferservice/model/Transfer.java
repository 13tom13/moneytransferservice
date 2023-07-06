package ru.netology.moneytransferservice.model;

public record Transfer (
        String cardToNumber,
        String cardFromValidTill,
        String cardFromNumber,
        String cardFromCVV,
        Amount amount
       ) {

}
