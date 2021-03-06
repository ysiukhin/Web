package ua.traning.rd.java.finalproject.servlet.controller.command.action;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.traning.rd.java.finalproject.core.model.Kind;
import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static ua.traning.rd.java.finalproject.Constants.*;

@ExtendWith(MockitoExtension.class)
class KindActionCommandTest {
    @Mock
    private HttpServletRequest requestMock;
    @Mock
    private HttpSession sessionMock;
    @Mock
    EntityListService<Kind> entityListServiceMock;

    @InjectMocks
    private KindActionCommand commandMock;

    @DisplayName("When request parameters are not valid have to redirect")
    @Test
    void executeNotValidParametersTest() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(LANGUAGE)).thenReturn("en");
        when(requestMock.getParameter(KIND_ENGLISH)).thenReturn(EMPTY_STRING);
        when(requestMock.getParameter(KIND_RUSSIAN)).thenReturn(EMPTY_STRING);
        assertEquals((REDIRECT + ":" + COMMAND_ADMIN_KIND_LIST + "?" + PAGE + "=" + COMMAND_ADMIN_KIND_LIST)
                , commandMock.execute(requestMock));
    }

    @DisplayName("When request parameters are valid and ")
    @ParameterizedTest(name = "operation create new category return {0} affected rows quantity")
    @ValueSource(ints = {0, 1})
    void executeRequestInsertActivity(int affected) {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(LANGUAGE)).thenReturn("en");
        when(requestMock.getParameter(KIND_ENGLISH)).thenReturn("Outdoor traning");
        when(requestMock.getParameter(KIND_RUSSIAN)).thenReturn("?????????????? ????????????????????");
        when(requestMock.getParameter(ACTION)).thenReturn("create");
        when(entityListServiceMock.insertEntity(any(Kind.class))).thenReturn(affected);
        assertEquals((REDIRECT + ":" + COMMAND_ADMIN_KIND_LIST + "?" + PAGE + "=" + COMMAND_ADMIN_KIND_LIST)
                , commandMock.execute(requestMock));
    }

    @DisplayName("When request parameters are valid and ")
    @ParameterizedTest(name = "operation update new category return {0} affected rows quantity")
    @ValueSource(ints = {0, 1})
    void executeRequestUpdateActivity(int affected) {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(LANGUAGE)).thenReturn("en");
        when(requestMock.getParameter(KIND_ENGLISH)).thenReturn("Outdoor traning");
        when(requestMock.getParameter(KIND_RUSSIAN)).thenReturn("?????????????? ????????????????????");
        when(requestMock.getParameter(ACTION)).thenReturn("update");
        when(requestMock.getParameter(ID)).thenReturn("1");
        when(entityListServiceMock.updateEntity(any(Kind.class))).thenReturn(affected);
        assertEquals((REDIRECT + ":" + COMMAND_ADMIN_KIND_LIST + "?" + PAGE + "=" + COMMAND_ADMIN_KIND_LIST)
                , commandMock.execute(requestMock));
    }

    @DisplayName("When request parameters are valid and ")
    @ParameterizedTest(name = "operation delete new category return {0} affected rows quantity")
    @ValueSource(ints = {0, 1})
    void executeRequestDeleteActivity(int affected) {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(LANGUAGE)).thenReturn("en");
        when(requestMock.getParameter(KIND_ENGLISH)).thenReturn("Outdoor traning");
        when(requestMock.getParameter(KIND_RUSSIAN)).thenReturn("?????????????? ????????????????????");
        when(requestMock.getParameter(ACTION)).thenReturn("delete");
        when(requestMock.getParameter(ID)).thenReturn("1");
        when(entityListServiceMock.deleteEntity(anyInt())).thenReturn(affected);
        assertEquals((REDIRECT + ":" + COMMAND_ADMIN_KIND_LIST + "?" + PAGE + "=" + COMMAND_ADMIN_KIND_LIST)
                , commandMock.execute(requestMock));
    }

    @DisplayName("have to catch ApplicationException when delete")
    @Test
    void executeThrowApplicationExceptionWhenDeleteTest() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(LANGUAGE)).thenReturn("en");
        when(requestMock.getParameter(KIND_ENGLISH)).thenReturn("Outdoor traning");
        when(requestMock.getParameter(KIND_RUSSIAN)).thenReturn("?????????????? ????????????????????");
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
        when(requestMock.getParameter(KIND_ENGLISH)).thenReturn("Outdoor traning");
        when(requestMock.getParameter(KIND_RUSSIAN)).thenReturn("?????????????? ????????????????????");
        when(requestMock.getParameter(ACTION)).thenReturn("update");
        when(requestMock.getParameter(ID)).thenReturn("1");
        when(entityListServiceMock.updateEntity(any(Kind.class))).thenThrow(ApplicationException.class);
        assertThrows(ApplicationException.class, () -> commandMock.execute(requestMock));
    }

    @DisplayName("have to catch ApplicationException when insert")
    @Test
    void executeThrowApplicationExceptionWhenInsertTest() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(LANGUAGE)).thenReturn("en");
        when(requestMock.getParameter(KIND_ENGLISH)).thenReturn("Outdoor traning");
        when(requestMock.getParameter(KIND_RUSSIAN)).thenReturn("?????????????? ????????????????????");
        when(requestMock.getParameter(ACTION)).thenReturn("create");
        when(entityListServiceMock.insertEntity(any(Kind.class))).thenThrow(ApplicationException.class);
        assertThrows(ApplicationException.class, () -> commandMock.execute(requestMock));
    }
}