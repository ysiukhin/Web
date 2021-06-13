package ua.traning.rd.java.finalproject.servlet.controller.command.action;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.model.AccountSignedActivities;
import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.servlet.controller.command.UserTimerCommand;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static ua.traning.rd.java.finalproject.Constants.*;

@ExtendWith(MockitoExtension.class)
class AccountActionCommandTest {
    @Mock
    private HttpServletRequest requestMock;
    @Mock
    private HttpSession sessionMock;
    @Mock
    EntityListService<Account> entityListServiceMock;
    @InjectMocks
    private AccountActionCommand commandMock;

    @DisplayName("When request parameters are not valid have to redirect")
    @Test
    void executeRequestIsNotValid() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(LANGUAGE)).thenReturn("en");
        when(requestMock.getParameter(FIRST_NAME)).thenReturn(EMPTY_STRING);
        when(requestMock.getParameter(LAST_NAME)).thenReturn(EMPTY_STRING);
        when(requestMock.getParameter(MIDDLE_NAME)).thenReturn(EMPTY_STRING);
        when(requestMock.getParameter(EMAIL)).thenReturn(EMPTY_STRING);
        when(requestMock.getParameter(PASSWORD_MD5)).thenReturn(EMPTY_STRING);
        assertEquals((REDIRECT + ":" + COMMAND_ADMIN_ACCOUNT_LIST + "?" + PAGE + "=" + COMMAND_ADMIN_ACCOUNT_LIST)
                , commandMock.execute(requestMock));
    }

    @DisplayName("When request parameters are valid and ")
    @ParameterizedTest(name = "operation create new account return {0} affected rows quantity")
    @ValueSource(ints = {0, 1})
    void executeRequestInsertAccount(int affected) {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(LANGUAGE)).thenReturn("en");
        when(requestMock.getParameter(FIRST_NAME)).thenReturn("FIRST_NAME");
        when(requestMock.getParameter(LAST_NAME)).thenReturn("LAST_NAME");
        when(requestMock.getParameter(MIDDLE_NAME)).thenReturn("MIDDLE_NAME");
        when(requestMock.getParameter(EMAIL)).thenReturn("EMAIL@EMAIL.COM");
        when(requestMock.getParameter(PASSWORD_MD5)).thenReturn("PASSWORD_MD5");
        when(requestMock.getParameter(ACTION)).thenReturn("create");
        when(entityListServiceMock.insertEntity(any(Account.class))).thenReturn(affected);
        assertEquals((REDIRECT + ":" + COMMAND_ADMIN_ACCOUNT_LIST + "?" + PAGE + "=" + COMMAND_ADMIN_ACCOUNT_LIST)
                , commandMock.execute(requestMock));
    }

    @DisplayName("When request parameters are valid and ")
    @ParameterizedTest(name = "operation update account return {0} affected rows quantity")
    @ValueSource(ints = {0, 1})
    void executeRequestUpdateAccount(int affected) {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(LANGUAGE)).thenReturn("en");
        when(requestMock.getParameter(FIRST_NAME)).thenReturn("FIRST_NAME");
        when(requestMock.getParameter(LAST_NAME)).thenReturn("LAST_NAME");
        when(requestMock.getParameter(MIDDLE_NAME)).thenReturn("MIDDLE_NAME");
        when(requestMock.getParameter(EMAIL)).thenReturn("EMAIL@EMAIL.COM");
        when(requestMock.getParameter(PASSWORD_MD5)).thenReturn("PASSWORD_MD5");
        when(requestMock.getParameter(ACTION)).thenReturn("update");
        when(requestMock.getParameter(ID)).thenReturn("1");
        when(entityListServiceMock.updateEntity(any(Account.class))).thenReturn(affected);
        assertEquals((REDIRECT + ":" + COMMAND_ADMIN_ACCOUNT_LIST + "?" + PAGE + "=" + COMMAND_ADMIN_ACCOUNT_LIST)
                , commandMock.execute(requestMock));
    }

    @DisplayName("When request parameters are valid and ")
    @ParameterizedTest(name = "operation delete account return {0} affected rows quantity")
    @ValueSource(ints = {0, 1})
    void executeRequestDeleteAccount(int affected) {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(LANGUAGE)).thenReturn("en");
        when(requestMock.getParameter(FIRST_NAME)).thenReturn("FIRST_NAME");
        when(requestMock.getParameter(LAST_NAME)).thenReturn("LAST_NAME");
        when(requestMock.getParameter(MIDDLE_NAME)).thenReturn("MIDDLE_NAME");
        when(requestMock.getParameter(EMAIL)).thenReturn("EMAIL@EMAIL.COM");
        when(requestMock.getParameter(PASSWORD_MD5)).thenReturn("PASSWORD_MD5");
        when(requestMock.getParameter(ACTION)).thenReturn("delete");
        when(requestMock.getParameter(ID)).thenReturn("1");
        when(entityListServiceMock.deleteEntity(anyInt())).thenReturn(affected);
        assertEquals((REDIRECT + ":" + COMMAND_ADMIN_ACCOUNT_LIST + "?" + PAGE + "=" + COMMAND_ADMIN_ACCOUNT_LIST)
                , commandMock.execute(requestMock));
    }

    @DisplayName("have to catch ApplicationException when delete")
    @Test
    void executeThrowApplicationExceptionWhenDeleteTest() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(LANGUAGE)).thenReturn("en");
        when(requestMock.getParameter(FIRST_NAME)).thenReturn("FIRST_NAME");
        when(requestMock.getParameter(LAST_NAME)).thenReturn("LAST_NAME");
        when(requestMock.getParameter(MIDDLE_NAME)).thenReturn("MIDDLE_NAME");
        when(requestMock.getParameter(EMAIL)).thenReturn("EMAIL@EMAIL.COM");
        when(requestMock.getParameter(PASSWORD_MD5)).thenReturn("PASSWORD_MD5");
        when(requestMock.getParameter(ACTION)).thenReturn("delete");
        when(requestMock.getParameter(ID)).thenReturn("1");
        when(entityListServiceMock.deleteEntity(anyInt())).thenThrow(ApplicationException.class);
        assertThrows(ApplicationException.class, () -> commandMock.execute(requestMock));
    }

    @DisplayName("have to catch ApplicationException when update")
    @Test
    void executeThrowApplicationExceptionWhenUpdateTest() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(LANGUAGE)).thenReturn("en");
        when(requestMock.getParameter(FIRST_NAME)).thenReturn("FIRST_NAME");
        when(requestMock.getParameter(LAST_NAME)).thenReturn("LAST_NAME");
        when(requestMock.getParameter(MIDDLE_NAME)).thenReturn("MIDDLE_NAME");
        when(requestMock.getParameter(EMAIL)).thenReturn("EMAIL@EMAIL.COM");
        when(requestMock.getParameter(PASSWORD_MD5)).thenReturn("PASSWORD_MD5");
        when(requestMock.getParameter(ACTION)).thenReturn("update");
        when(requestMock.getParameter(ID)).thenReturn("1");
        when(entityListServiceMock.updateEntity(any(Account.class))).thenThrow(ApplicationException.class);
        assertThrows(ApplicationException.class, () -> commandMock.execute(requestMock));
    }

    @DisplayName("have to catch ApplicationException when insert")
    @Test
    void executeThrowApplicationExceptionWhenInsertTest() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(LANGUAGE)).thenReturn("en");
        when(requestMock.getParameter(FIRST_NAME)).thenReturn("FIRST_NAME");
        when(requestMock.getParameter(LAST_NAME)).thenReturn("LAST_NAME");
        when(requestMock.getParameter(MIDDLE_NAME)).thenReturn("MIDDLE_NAME");
        when(requestMock.getParameter(EMAIL)).thenReturn("EMAIL@EMAIL.COM");
        when(requestMock.getParameter(PASSWORD_MD5)).thenReturn("PASSWORD_MD5");
        when(requestMock.getParameter(ACTION)).thenReturn("create");
        when(entityListServiceMock.insertEntity(any(Account.class))).thenThrow(ApplicationException.class);
        assertThrows(ApplicationException.class, () -> commandMock.execute(requestMock));
    }

}