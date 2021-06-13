package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.model.LoggedAccount;
import ua.traning.rd.java.finalproject.core.service.LoginService;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;
import ua.traning.rd.java.finalproject.servlet.exception.ServiceException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static ua.traning.rd.java.finalproject.Constants.*;

@ExtendWith(MockitoExtension.class)
class LoginCommandTest {
    @Mock
    private Account accountMock;
    @Mock
    private HttpServletRequest requestMock;
    @Mock
    private HttpSession sessionMock;
    @Mock
    private ServletContext servletContextMock;
    @Mock
    private LoginService loginServiceMock;
    @Mock
    private LoggedAccount loggedAccountMock;
    @Mock
    private HashMap<String, LoggedAccount> loggedAccountsMock;

    @InjectMocks
    private LoginCommand loginCommandMock;


    @DisplayName("have to return LOGIN_JSP.jsp when")
    @ParameterizedTest(name = "password is {0}")
    @NullAndEmptySource
    void executeEmailNotEmptyTest(String password) {
        when(requestMock.getParameter(EMAIL)).thenReturn("email");
        when(requestMock.getParameter(PASSWORD)).thenReturn(password);
        when(requestMock.getSession()).thenReturn(sessionMock);
        assertEquals(LOGIN_JSP, loginCommandMock.execute(requestMock));
    }

    @DisplayName("have to return LOGIN_JSP.jsp when")
    @ParameterizedTest(name = "email is {0}")
    @NullAndEmptySource
    void executeEmailNullOrEmptyTest(String email) {
        when(requestMock.getParameter(EMAIL)).thenReturn(email);
        when(requestMock.getParameter(PASSWORD)).thenReturn("password");
        when(requestMock.getSession()).thenReturn(sessionMock);
        assertEquals(LOGIN_JSP, loginCommandMock.execute(requestMock));
    }

    @DisplayName("have to return LOGIN_JSP.jsp when user already logged in")
    @Test
    void executeEmailAndPasswordNotEmptyTest() {
        when(requestMock.getParameter(EMAIL)).thenReturn("email");
        when(requestMock.getParameter(PASSWORD)).thenReturn("password");
        when(requestMock.getSession()).thenReturn(sessionMock);
        try (MockedStatic<CommandUtility> utilities = Mockito.mockStatic(CommandUtility.class)) {
            utilities.when(() -> CommandUtility.checkUserIsLogged(requestMock, "email")).thenReturn(true);
            assertEquals(LOGIN_JSP, loginCommandMock.execute(requestMock));
        }
    }

    @DisplayName("have to return LOGIN_JSP when throws ServiceException ")
    @Test
    void executeThrowServiceExceptionTest() {
        when(requestMock.getParameter(EMAIL)).thenReturn("email");
        when(requestMock.getParameter(PASSWORD)).thenReturn("password");
        when(loginServiceMock.checkAccount("email", "password")).thenThrow(ServiceException.class);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getServletContext()).thenReturn(servletContextMock);
        when(servletContextMock.getAttribute(ALL_LOGGED_ACCOUNTS)).thenReturn(loggedAccountsMock);
        when(loggedAccountsMock.values()).thenReturn(Collections.emptyList());
        assertEquals(LOGIN_JSP, loginCommandMock.execute(requestMock));
    }

    @DisplayName("have to catch ApplicationException")
    @Test
    void executeThrowApplicationExceptionTest() {
        when(requestMock.getParameter(EMAIL)).thenReturn("email");
        when(requestMock.getParameter(PASSWORD)).thenReturn("password");
        when(loginServiceMock.checkAccount("email", "password")).thenThrow(ApplicationException.class);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getServletContext()).thenReturn(servletContextMock);
        when(servletContextMock.getAttribute(ALL_LOGGED_ACCOUNTS)).thenReturn(loggedAccountsMock);
        when(loggedAccountsMock.values()).thenReturn(Collections.emptyList());
        assertThrows(ApplicationException.class, () -> loginCommandMock.execute(requestMock));
    }

    @DisplayName("When request parameters are valid have to return redirect")
    @Test
    void executeTest() {
        when(requestMock.getParameter(EMAIL)).thenReturn("email");
        when(requestMock.getParameter(PASSWORD)).thenReturn("password");
        when(loginServiceMock.checkAccount("email", "password")).thenReturn(accountMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(requestMock.getSession(false)).thenReturn(sessionMock);
        when(sessionMock.getServletContext()).thenReturn(servletContextMock);
        when(servletContextMock.getAttribute(ALL_LOGGED_ACCOUNTS)).thenReturn(loggedAccountsMock);
        when(sessionMock.getId()).thenReturn("1");
        when(loggedAccountsMock.get("1")).thenReturn(loggedAccountMock);
        when(loggedAccountsMock.values()).thenReturn(Collections.emptyList());
        when(accountMock.getStatus()).thenReturn(true);
        when(accountMock.toString()).thenReturn("accountMock");
        assertEquals((REDIRECT + ":" + COMMAND_USER_SECTION), loginCommandMock.execute(requestMock));
    }
}