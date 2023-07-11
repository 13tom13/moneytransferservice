package ru.netology.moneytransferservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.netology.moneytransferservice.controller.TransferController;
import ru.netology.moneytransferservice.exceptions.ErrorConfirmation;
import ru.netology.moneytransferservice.model.Amount;
import ru.netology.moneytransferservice.model.ConfirmOperation;
import ru.netology.moneytransferservice.model.OperationId;
import ru.netology.moneytransferservice.model.Transfer;

@SpringBootTest
class MoneytransferserviceApplicationTests {

    @Autowired
    private TransferController transferController;


    @Test
    void transferControllerTest() {
        // given:
        Transfer transferTest = Mockito.mock(Transfer.class);
        Amount amountTest = Mockito.mock(Amount.class);
        Mockito.when(amountTest.value()).thenReturn(1000);
        Mockito.when(transferTest.amount()).thenReturn(amountTest);
        Mockito.when(transferTest.cardFromNumber()).thenReturn("0000000000000000");

        // when:
        OperationId testID = transferController.transfer(transferTest);
        // then:
        Assertions.assertNotNull(testID);
    }

    @Test
    void confirmOperationTest() {
        // given:
        String testIDRequest = "1234";
        ConfirmOperation confirmOperation = Mockito.mock(ConfirmOperation.class);
        Mockito.when(confirmOperation.code()).thenReturn("0000");
        Mockito.when(confirmOperation.operationId()).thenReturn(testIDRequest);

        // when:
        ErrorConfirmation testConfirmationException =
                Assertions.assertThrows(ErrorConfirmation.class, () -> transferController.confirmOperation(confirmOperation));
        // then:
        Assertions.assertEquals("The transaction has not been approved", testConfirmationException.getMessage());
    }

}
