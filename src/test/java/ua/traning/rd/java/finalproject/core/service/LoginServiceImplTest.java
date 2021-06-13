package ua.traning.rd.java.finalproject.core.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.traning.rd.java.finalproject.core.dao.DbServiceImpl;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.servlet.exception.ServiceException;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {

    @Mock
    private Account accountMock;

    @Mock
    private List<Account> accountListMock;

    @Mock
    private DbServiceImpl<Account> dbServiceImplMock;

    @InjectMocks
    private LoginServiceImpl loginServiceImplMock;

    @Test
    void checkAccountTest() {
        when(dbServiceImplMock.getBeansBy(anyString(), anyString())).thenReturn(accountListMock);
        when(accountListMock.get(anyInt())).thenReturn(accountMock);
        String mdPassword = ServiceUtils.getMd5("password");
        when(accountMock.getMd5()).thenReturn(mdPassword);
        assertEquals(accountMock, loginServiceImplMock.checkAccount("email", "password"));
    }

    @Test
    void checkAccountExceptionTest() {
        when(dbServiceImplMock.getBeansBy(anyString(), anyString())).thenReturn(accountListMock);
        when(accountListMock.get(anyInt())).thenReturn(accountMock);
        when(accountMock.getMd5()).thenReturn("password");
        assertThrows(ServiceException.class, () -> loginServiceImplMock.checkAccount("email", "password"));
    }
}