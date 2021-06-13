package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.model.AccountSignedActivities;
import ua.traning.rd.java.finalproject.core.model.LoggedAccount;
import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static ua.traning.rd.java.finalproject.Constants.*;

@ExtendWith(MockitoExtension.class)
class UserTimerCommandTest {

    @Mock
    private HttpServletRequest requestMock;
    @Mock
    private HttpSession sessionMock;
    @Mock
    private ServletContext servletContextMock;
    @Mock
    private LoggedAccount userLoggedMock;
    @Mock
    private Account userMock;

    @Mock
    List<AccountSignedActivities> resultListMock;
    @Mock
    EntityListService<AccountSignedActivities> entityListServiceMock;
    @InjectMocks
    private UserTimerCommand commandMock;

    @DisplayName("have to return COMMAND_USER_TO_PAGE_TIMER when")
    @ParameterizedTest(name = "page parameter {0}")
    @ValueSource(strings = {"page", "/page"})
    void executeTest(String page) {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(LOGGED_ACCOUNT)).thenReturn(userLoggedMock);
        when(sessionMock.getAttribute(LANGUAGE)).thenReturn("en");
        when(userLoggedMock.getAccount()).thenReturn(userMock);
        when(userMock.getId()).thenReturn(1);
        when(entityListServiceMock.getByStoredProc(CALL_GET_USER_ACTIVITIES_AND_RECORDS,
                Arrays.asList(userMock.getId(), -1, 0))).thenReturn(resultListMock);
        when(resultListMock.size()).thenReturn(30);
        when(sessionMock.getAttribute(PAGE_NUMBER)).thenReturn(1);
        when(requestMock.getParameter(PAGE)).thenReturn(page);
        when(requestMock.getRequestURI()).thenReturn("/page");
        assertEquals(COMMAND_USER_TO_PAGE_TIMER, commandMock.execute(requestMock));
    }

    @DisplayName("have to return COMMAND_USER_TO_PAGE_TIMER when")
    @ParameterizedTest(name = "page parameter {0}")
    @NullSource
    void executePageIsNullTest(String page) {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(LOGGED_ACCOUNT)).thenReturn(userLoggedMock);
        when(sessionMock.getAttribute(LANGUAGE)).thenReturn("en");
        when(userLoggedMock.getAccount()).thenReturn(userMock);
        when(userMock.getId()).thenReturn(1);
        when(entityListServiceMock.getByStoredProc(CALL_GET_USER_ACTIVITIES_AND_RECORDS,
                Arrays.asList(userMock.getId(), -1, 0))).thenReturn(resultListMock);
        when(resultListMock.size()).thenReturn(30);
        when(sessionMock.getAttribute(PAGE_NUMBER)).thenReturn(1);
        when(requestMock.getParameter(PAGE)).thenReturn(page);
        assertEquals(COMMAND_USER_TO_PAGE_TIMER, commandMock.execute(requestMock));
    }

    @DisplayName("have to return COMMAND_USER_TO_PAGE_TIMER when")
    @ParameterizedTest(name = "pageNumber attribute {0}")
    @NullSource
    void executePageNumberIsNullTest(String pageNumber) {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(LOGGED_ACCOUNT)).thenReturn(userLoggedMock);
        when(sessionMock.getAttribute(LANGUAGE)).thenReturn("en");

        when(userLoggedMock.getAccount()).thenReturn(userMock);
        when(userMock.getId()).thenReturn(1);
        when(entityListServiceMock.getByStoredProc(CALL_GET_USER_ACTIVITIES_AND_RECORDS,
                Arrays.asList(userMock.getId(), -1, 0))).thenReturn(resultListMock);
        when(resultListMock.size()).thenReturn(30);
        when(sessionMock.getAttribute(PAGE_NUMBER)).thenReturn(pageNumber);
        assertEquals(COMMAND_USER_TO_PAGE_TIMER, commandMock.execute(requestMock));
    }

    @DisplayName("have to catch ApplicationException")
    @Test
    void executeThrowApplicationExceptionTest() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(LOGGED_ACCOUNT)).thenReturn(userLoggedMock);
        when(sessionMock.getAttribute(LANGUAGE)).thenReturn("en");

        when(userLoggedMock.getAccount()).thenReturn(userMock);
        when(userMock.getId()).thenReturn(1);
        when(entityListServiceMock.getByStoredProc(CALL_GET_USER_ACTIVITIES_AND_RECORDS,
                Arrays.asList(userMock.getId(), -1, 0))).thenThrow(ApplicationException.class);
        assertThrows(ApplicationException.class, () -> commandMock.execute(requestMock));
    }


}