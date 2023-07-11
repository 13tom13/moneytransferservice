package ru.netology.moneytransferservice;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.moneytransferservice.controller.TransferController;
import ru.netology.moneytransferservice.exceptions.ErrorConfirmation;
import ru.netology.moneytransferservice.model.Amount;
import ru.netology.moneytransferservice.model.ConfirmOperation;
import ru.netology.moneytransferservice.model.OperationId;
import ru.netology.moneytransferservice.model.Transfer;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneytransferserviceApplicationTests {

    @Autowired
    private TransferController transferController;

    @Autowired
    TestRestTemplate restTemplate;

    static Transfer transferTest;

    @Container
    private static final GenericContainer<?> moneytransferservice = new GenericContainer<>("moneytransferservice:latest")
            .withExposedPorts(5500);

    @BeforeEach
    public void setUp() {
        transferTest = Mockito.mock(Transfer.class);
        Amount amountTest = Mockito.mock(Amount.class);
        Mockito.when(amountTest.value()).thenReturn(100);
        Mockito.when(amountTest.currency()).thenReturn("RUR");
        Mockito.when(transferTest.amount()).thenReturn(amountTest);
        Mockito.when(transferTest.cardFromNumber()).thenReturn("0000000000000000");
        Mockito.when(transferTest.cardToNumber()).thenReturn("1000000000000000");
        Mockito.when(transferTest.cardFromCVV()).thenReturn("123");
        Mockito.when(transferTest.cardFromValidTill()).thenReturn("1234");
    }

    @AfterEach
    public void setDown() {
        transferTest = null;
    }

    @Test
    void moneytransferserviceTest() {
        // given:
        moneytransferservice.start();
        Integer port = moneytransferservice.getMappedPort(5500);
        // when:
        OperationId operationId = restTemplate.postForObject
                ("http://localhost:" + port + "/transfer", transferTest, OperationId.class);
        // then:
        Assertions.assertNotNull(operationId.operationId());
    }


    @Test
    void transferControllerTest() {
        OperationId testID = transferController.transfer(transferTest);
        // then:
        Assertions.assertNotNull(testID);
    }

    @Test
    void confirmOperationTest() {
        // given:
        String testIDRequest = "1234";
        String testCode = "0000";
        ConfirmOperation confirmOperation = Mockito.mock(ConfirmOperation.class);
        Mockito.when(confirmOperation.code()).thenReturn(testCode);
        Mockito.when(confirmOperation.operationId()).thenReturn(testIDRequest);
        // when:
        ErrorConfirmation testConfirmationException = Assertions.assertThrows(ErrorConfirmation.class,
                () -> transferController.confirmOperation(confirmOperation));
        // then:
        Assertions.assertEquals("The transaction has not been approved",
                testConfirmationException.getMessage());
    }


}
