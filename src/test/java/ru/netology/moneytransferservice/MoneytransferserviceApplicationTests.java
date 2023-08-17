package ru.netology.moneytransferservice;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.moneytransferservice.exceptions.ErrorConfirmation;
import ru.netology.moneytransferservice.controller.TransferController;
import ru.netology.moneytransferservice.repository.TransferRepository;
import ru.netology.moneytransferservice.service.TransferService;
import ru.netology.moneytransferservice.logger.TransferLog;
import ru.netology.moneytransferservice.model.Amount;
import ru.netology.moneytransferservice.model.ConfirmData;
import ru.netology.moneytransferservice.model.OperationId;
import ru.netology.moneytransferservice.model.TransferData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneytransferserviceApplicationTests {

    @Autowired
    private TransferController transferController;

    @Autowired
    private TransferService transferService;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TransferLog transferLog;

    private static TransferData transferDataTest;

    private static ConfirmData confirmDataTest;

    @Container
    private static final GenericContainer<?> moneytransferservice = new GenericContainer<>("moneytransferservice:latest")
            .withExposedPorts(5500);


    @BeforeEach
    public void setUp() {
        //Mock for TransferData
        transferDataTest = Mockito.mock(TransferData.class);
        Amount amountTest = Mockito.mock(Amount.class);
        Mockito.when(amountTest.value()).thenReturn(100);
        Mockito.when(amountTest.currency()).thenReturn("RUR");
        Mockito.when(transferDataTest.amount()).thenReturn(amountTest);
        Mockito.when(transferDataTest.cardFromNumber()).thenReturn("test000000000000");
        Mockito.when(transferDataTest.cardToNumber()).thenReturn("test000000000000");
        Mockito.when(transferDataTest.cardFromCVV()).thenReturn("123");
        Mockito.when(transferDataTest.cardFromValidTill()).thenReturn("1234");

        //Mock for ConfirmData
        confirmDataTest = Mockito.mock(ConfirmData.class);
        Mockito.when(confirmDataTest.code()).thenReturn("0000");
        Mockito.when(confirmDataTest.operationId()).thenReturn("test");
    }

    @AfterEach
    public void setDown() {
        transferDataTest = null;
        confirmDataTest = null;
    }

    @Test
    void transferIntegrationTest() {
        // when:
        ResponseEntity<OperationId> testEntity = transferController.transfer(transferDataTest);
        String operationId = String.valueOf(UUID.nameUUIDFromBytes(transferDataTest.toString().getBytes()));
        OperationId testId = new OperationId(operationId);
        // then:
        Assertions.assertEquals(testEntity.getBody(), testId);
    }

    @Test
    void confirmOperationIntegrationTest() {
        // when:
        ErrorConfirmation testConfirmationException = Assertions.assertThrows(ErrorConfirmation.class,
                () -> transferController.confirmOperation(confirmDataTest));
        // then:
        Assertions.assertEquals("The transaction has not been approved",
                testConfirmationException.getMessage());
    }

    @Test
    void transferServiceTest() {
        // given:
        OperationId testId = new OperationId(String.valueOf(UUID.nameUUIDFromBytes(transferDataTest.toString().getBytes())));
        Mockito.when(confirmDataTest.operationId()).thenReturn(testId.operationId());
        // when:
        OperationId idFromTransferService = transferService.transfer(transferDataTest).getBody();
        OperationId idFromConfirmService = transferService.confirmOperation(confirmDataTest).getBody();
        // then:
        Assertions.assertEquals(testId.operationId(), Objects.requireNonNull(idFromTransferService).operationId());
        Assertions.assertEquals(idFromTransferService.operationId(), Objects.requireNonNull(idFromConfirmService).operationId());
    }

    @Test
    void transferRepositoryTest() {
        // given:
        OperationId testId = new OperationId(String.valueOf(UUID.nameUUIDFromBytes(transferDataTest.toString().getBytes())));
        Mockito.when(confirmDataTest.operationId()).thenReturn(testId.operationId());
        // when:
        OperationId idFromRep = transferRepository.getOperationId(transferDataTest);
        OperationId confirmId = transferRepository.confirmOperation(confirmDataTest);
        // then:
        Assertions.assertEquals(testId, idFromRep);
        Assertions.assertEquals(idFromRep, confirmId);
    }

    @Test
    void loggerTest(@Value("${log.file.name}") String logFile) throws IOException {
        // given:
        String testErrorMesseg = "testError";
        Path pathLog = Paths.get(logFile);
        //when:
        String testError = transferLog.errorLog(testErrorMesseg);
        String testTranserLog = transferLog.transferLog(transferDataTest);
        String testResultLog = transferLog.transferResultLog(confirmDataTest);
        //then:
        boolean checkError = Files.lines(pathLog).anyMatch(testError::contains);
        boolean checkTranserLog = Files.lines(pathLog).anyMatch(testTranserLog::contains);
        boolean checkResultLog = Files.lines(pathLog).anyMatch(testResultLog::contains);

        Assertions.assertTrue(checkError);
        Assertions.assertTrue(checkTranserLog);
        Assertions.assertTrue(checkResultLog);
    }

    @Test
    void moneytransferserviceTest() {
        // given:
        moneytransferservice.start();
        Integer port = moneytransferservice.getMappedPort(5500);
        // when:
        OperationId operationIdFromTransfer = restTemplate.postForObject
                ("http://localhost:" + port + "/transfer", transferDataTest, OperationId.class);
        Mockito.when(confirmDataTest.operationId()).thenReturn(operationIdFromTransfer.operationId());

        OperationId operationIdFromConfirmOperation = restTemplate.postForObject
                ("http://localhost:" + port + "/confirmOperation", confirmDataTest, OperationId.class);
        // then:
        Assertions.assertEquals(operationIdFromTransfer, operationIdFromConfirmOperation);
    }

}
