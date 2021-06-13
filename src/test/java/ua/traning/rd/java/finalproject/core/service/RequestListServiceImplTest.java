package ua.traning.rd.java.finalproject.core.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.traning.rd.java.finalproject.core.model.AccountActivity;
import ua.traning.rd.java.finalproject.core.model.Request;
import ua.traning.rd.java.finalproject.jdbc.dao.DaoJdbc;
import ua.traning.rd.java.finalproject.jdbc.sessionmanager.SessionManagerJdbc;
import ua.traning.rd.java.finalproject.servlet.exception.DaoException;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestListServiceImplTest {

    @Mock
    private DataSource mockSource;
    @Mock
    private SessionManagerJdbc mockSessionManager;
    @Mock
    private DaoJdbc<AccountActivity> accountActivityDaoMock;
    @Mock
    private DaoJdbc<Request> requestDaoMock;
    @InjectMocks
    private RequestListServiceImpl requestListServiceImplMock;

    @DisplayName("processRequestTransaction(): have to complete transaction of DB operations depends on request type")
    @ParameterizedTest(name = "is request for addition new Activity {0}")
    @ValueSource(booleans = {false, true})
    void processRequestTransactionTest(boolean isNewActivityAddingRequest) {
        assertTrue(requestListServiceImplMock.processRequestTransaction(
                isNewActivityAddingRequest, 1, 1, 1)
        );
        InOrder inOrder = inOrder(mockSessionManager);
        inOrder.verify(mockSessionManager, times(1)).beginSession();
        inOrder.verify(mockSessionManager, times(1)).commitSession();
        inOrder.verify(mockSessionManager, never()).rollbackSession();
    }

    @DisplayName("processRequestTransaction(): have to rollback transaction depends on request type")
    @ParameterizedTest(name = "is request for addition new Activity {0}")
    @ValueSource(booleans = {false, true})
    void processRequestTransactionFailedTest(boolean isNewActivityAddingRequest) {
        doThrow(DaoException.class).when(mockSessionManager).commitSession();
        assertThrows(DaoException.class, () -> requestListServiceImplMock.processRequestTransaction(
                isNewActivityAddingRequest, 1, 1, 1)
        );
        InOrder inOrder = inOrder(mockSessionManager);
        inOrder.verify(mockSessionManager, times(1)).beginSession();
        inOrder.verify(mockSessionManager, times(1)).commitSession();
        inOrder.verify(mockSessionManager, times(1)).rollbackSession();
    }
}