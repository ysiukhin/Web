package ua.traning.rd.java.finalproject.core.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.traning.rd.java.finalproject.core.dao.DbService;
import ua.traning.rd.java.finalproject.core.dao.DbServiceException;
import ua.traning.rd.java.finalproject.core.dao.DbServiceImpl;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.model.AccountBuilder;
import ua.traning.rd.java.finalproject.h2.DataSourceH2;
import ua.traning.rd.java.finalproject.jdbc.dao.DaoJdbc;
import ua.traning.rd.java.finalproject.jdbc.sessionmanager.SessionManagerJdbc;
import ua.traning.rd.java.finalproject.servlet.exception.DaoException;


import javax.sql.DataSource;

import java.sql.*;
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static ua.traning.rd.java.finalproject.Constants.*;

@ExtendWith(MockitoExtension.class)
class EntityListServiceTest {

    @Mock
    private DataSource mockSource;
    @Mock
    private Connection mockConnection;
    @Mock
    private CallableStatement mockCallableStatement;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    public static final Logger LOGGER = LogManager.getLogger(EntityListServiceTest.class);

    public static final String CREATE_TABLE_ACCOUNT = "create table if not exists account " +
            "( id int auto_increment primary key, " +
            "first_name varchar(30) not null, " +
            "last_name varchar(30) not null, " +
            "middle_name varchar(30) null, " +
            "email varchar(50) not null, " +
            "md5 varchar(32) not null, status tinyint default 1not null )";

    private static final int TEST_ROWS_QUANTITY = 5;
    public static final String SELECT_ALL_FROM_ACCOUNT = "select * from account";
    public static final String UPDATE_ACCOUNT_SET_MIDDLE_NAME_NULL_BY_ID = "UPDATE account SET middle_name = null WHERE id = ?";

    private DataSource dataSource;

    private void sqlQueryCreateTable(String sqlQuery) {
        DbService<Account> dbService =
                new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(dataSource), Account.class));
        try {
            dbService.updateBean(sqlQuery, Collections.emptyList());
            LOGGER.info("{} query action complete successful", sqlQuery);
        } catch (DaoException e) {
            LOGGER.error("{} query action got error: {}", sqlQuery, e.getMessage(), e);
        }
    }

    private Account getNewAccount(int i) {
        return new AccountBuilder()
                .addFirstName(String.format("tester%d_first_name", i))
                .addLastName(String.format("tester%d_last_name", i))
                .addMiddleName(String.format("tester%d_middle_name", i))
                .addEmail(String.format("tester%d@email.com", i))
                .addMd5(ServiceUtils.getMd5(String.valueOf(i)))
                .build();
    }

    @BeforeEach
    void setUp() {
        dataSource = new DataSourceH2();
        sqlQueryCreateTable(CREATE_TABLE_ACCOUNT);
        EntityListService<Account> accountService = new EntityListService<>(Account.class, dataSource);
        for (int i = 0; i < TEST_ROWS_QUANTITY; i++) {
            accountService.insertEntity(getNewAccount(i));
        }
    }

    @AfterEach
    void tearDown() {
        sqlQueryCreateTable("drop table account");
    }

    @Test
    @DisplayName("have to return total entities quantity")
    void totalEntityQuantityTest() {
        assertEquals(TEST_ROWS_QUANTITY, new EntityListService<>(Account.class, dataSource).totalEntityQuantity());
    }

    @Test
    @DisplayName("totalEntityQuantity(): have to catch DbServiceException exception when throws SQLException")
    void totalEntityQuantityExceptionTest() throws SQLException {
        given(mockSource.getConnection()).willReturn(mockConnection);
        given(mockConnection.isValid(5)).willReturn(true);
        given(mockConnection.prepareStatement(anyString())).willReturn(mockPreparedStatement);
        given(mockPreparedStatement.executeQuery()).willThrow(SQLException.class);
        assertThrows(DbServiceException.class, () -> new EntityListService<>(Account.class, mockSource)
                .totalEntityQuantity());
    }


    @Test
    @DisplayName("have to return all entities List object")
    void totalEntityQuantityBySqlTest() {
        assertEquals(TEST_ROWS_QUANTITY, new EntityListService<>(Account.class, dataSource)
                .totalEntityQuantityBySql(SELECT_ALL_FROM_ACCOUNT).size());
    }

    @Test
    @DisplayName("totalEntityQuantityBySql(): have to catch DbServiceException exception when throws SQLException")
    void totalEntityQuantityBySqlExceptionTest() throws SQLException {
        given(mockSource.getConnection()).willReturn(mockConnection);
        given(mockConnection.isValid(5)).willReturn(true);
        given(mockConnection.prepareStatement(anyString())).willReturn(mockPreparedStatement);
        given(mockPreparedStatement.executeQuery()).willThrow(SQLException.class);
        assertThrows(DbServiceException.class, () -> new EntityListService<>(Account.class, mockSource)
                .totalEntityQuantityBySql(SELECT_ALL_FROM_ACCOUNT));
    }


    @Test
    @DisplayName("have to return entities in range")
    void getInRangeByRowNumberTest() {
        assertEquals(2, new EntityListService<>(Account.class, dataSource)
                .getInRangeByRowNumber(2, 2).size());
    }

    @Test
    @DisplayName("getInRangeByRowNumber(): have to catch DbServiceException exception when throws SQLException")
    void getInRangeByRowNumberExceptionTest() throws SQLException {
        given(mockSource.getConnection()).willReturn(mockConnection);
        given(mockConnection.isValid(5)).willReturn(true);
        given(mockConnection.prepareStatement(anyString())).willReturn(mockPreparedStatement);
        given(mockPreparedStatement.executeQuery()).willThrow(SQLException.class);
        assertThrows(DbServiceException.class, () -> new EntityListService<>(Account.class, mockSource)
                .getInRangeByRowNumber(2, 2));
    }

    @Test
    @DisplayName("have to return entities in range")
    void getInRangeByRowNumberSqlTest() {
        assertEquals(2, new EntityListService<>(Account.class, dataSource).getInRangeByRowNumber(2, 2,
                SELECT_ALL_FROM_ACCOUNT + SQL_LIMIT_OFFSET_BOUNDS).size());
    }

    @Test
    @DisplayName("have to insert new entity and set new entity ID generated by DB")
    void insertEntityTest() {
        Account newAccount = getNewAccount(6);
        int result = new EntityListService<>(Account.class, dataSource).insertEntity(newAccount);
        assertEquals(newAccount.getId(), result);
    }

    @Test
    @DisplayName("insertEntity(): have to catch DbServiceException exception when throws SQLException")
    void insertEntityExceptionTest() throws SQLException {
        given(mockSource.getConnection()).willReturn(mockConnection);
        given(mockConnection.isValid(5)).willReturn(true);
        given(mockConnection.prepareStatement(anyString())).willReturn(mockPreparedStatement);
        given(mockPreparedStatement.executeUpdate()).willThrow(SQLException.class);
        assertThrows(DbServiceException.class, () -> new EntityListService<>(Account.class, mockSource)
                .insertEntity(getNewAccount(6)));
    }


    @Test
    @DisplayName("have to update DB stored data with provided entity values")
    void updateEntityTest() throws Exception {
        int defaultId = 1;
        DbService<Account> dbService = new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(dataSource), Account.class));
        Account account = dbService.getBeansById(defaultId).orElseThrow(Exception::new);
        account.setMiddleName(null);
        assertEquals(1, new EntityListService<>(Account.class, dataSource).updateEntity(account));
        assertNull(dbService.getBeansById(defaultId).orElseThrow(Exception::new).getMiddleName());
    }

    @Test
    @DisplayName("updateEntity(): have to catch DbServiceException exception when throws SQLException")
    void updateEntityExceptionTest() throws SQLException {
        given(mockSource.getConnection()).willReturn(mockConnection);
        given(mockConnection.isValid(5)).willReturn(true);
        given(mockConnection.prepareStatement(anyString())).willReturn(mockPreparedStatement);
        given(mockPreparedStatement.executeUpdate()).willThrow(SQLException.class);
        assertThrows(DbServiceException.class, () -> new EntityListService<>(Account.class, mockSource)
                .updateEntity(getNewAccount(6)));
    }


    @Test
    @DisplayName("have to update DB stored data by given SQL query action")
    void updateEntityBySqlTest() throws Exception {
        int defaultId = 1;
        assertEquals(1, new EntityListService<>(Account.class, dataSource)
                .updateEntity(UPDATE_ACCOUNT_SET_MIDDLE_NAME_NULL_BY_ID, defaultId));
        assertNull(new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(dataSource), Account.class))
                .getBeansById(defaultId).orElseThrow(Exception::new).getMiddleName());
    }

    @Test
    @DisplayName("updateEntityBySql(): have to catch DbServiceException exception when throws SQLException")
    void updateEntityBySqlExceptionTest() throws SQLException {
        given(mockSource.getConnection()).willReturn(mockConnection);
        given(mockConnection.isValid(5)).willReturn(true);
        given(mockConnection.prepareStatement(anyString())).willReturn(mockPreparedStatement);
        given(mockPreparedStatement.executeUpdate()).willThrow(SQLException.class);
        assertThrows(DbServiceException.class, () -> new EntityListService<>(Account.class, mockSource)
                .updateEntity(UPDATE_ACCOUNT_SET_MIDDLE_NAME_NULL_BY_ID, 1));
    }

    @Test
    @DisplayName("have to delete from Db data by given ID")
    void deleteEntityTest() {
        assertEquals(1, new EntityListService<>(Account.class, dataSource).deleteEntity(1));
        assertEquals(4, new EntityListService<>(Account.class, dataSource).totalEntityQuantity());
    }

    @Test
    @DisplayName("deleteEntity(): have to catch DbServiceException exception when throws SQLException")
    void deleteEntityExceptionTest() throws SQLException {
        given(mockSource.getConnection()).willReturn(mockConnection);
        given(mockConnection.isValid(5)).willReturn(true);
        given(mockConnection.prepareStatement(anyString())).willReturn(mockPreparedStatement);
        given(mockPreparedStatement.executeUpdate()).willThrow(SQLException.class);
        assertThrows(DbServiceException.class, () -> new EntityListService<>(Account.class, mockSource)
                .deleteEntity(1));
    }


    @Test
    @DisplayName("have to execute stored procedure")
    void getByStoredProcTest() throws SQLException {
        given(mockSource.getConnection()).willReturn(mockConnection);
        given(mockConnection.isValid(5)).willReturn(true);
        given(mockConnection.prepareCall(CALL_GET_USER_ACTIVITIES_AND_RECORDS)).willReturn(mockCallableStatement);
        given(mockCallableStatement.executeQuery()).willReturn(mockResultSet);
        given(mockResultSet.next()).willReturn(false);
        assertEquals(0, new EntityListService<>(Account.class, mockSource)
                .getByStoredProc(CALL_GET_USER_ACTIVITIES_AND_RECORDS, Collections.emptyList()).size());

    }

//    @ParameterizedTest(name = "пользователь отключен от контекста (detached) перед загрузкой: {0}")
//    @ValueSource(booleans = {false, true})
//    void shouldCorrectSaveAndLoadUserWithExpectedQueriesCount(boolean userDetachedBeforeGet) {

    static Stream<Class<? extends Throwable>> exceptionProvider() {
        return Stream.of(InstantiationException.class, IllegalAccessException.class, SQLException.class);
    }

    @DisplayName("getByStoredProc(): have to catch DbServiceException exception when throws SQLException")
    @Test
    void getByStoredProcExceptionTest() throws SQLException {
        given(mockSource.getConnection()).willReturn(mockConnection);
        given(mockConnection.isValid(5)).willReturn(true);
        given(mockConnection.prepareCall(CALL_GET_USER_ACTIVITIES_AND_RECORDS)).willReturn(mockCallableStatement);
        given(mockCallableStatement.executeQuery()).willThrow(SQLException.class);
//        given(mockResultSet.next()).willReturn(false);
        assertThrows(DbServiceException.class, () -> {
            new EntityListService<>(Account.class, mockSource)
                    .getByStoredProc(CALL_GET_USER_ACTIVITIES_AND_RECORDS, Collections.emptyList());
        });
    }


//    //throw Exceptions when stored procedure execution call and catch DbServiceException
//    @DisplayName("have to catch DbServiceException exception when ")
//    @ParameterizedTest(name = "throws {0} during stored procedure call")
//    @MethodSource("exceptionProvider")
//    void getByStoredProcExceptionTest(Class<? extends Throwable> exception) throws SQLException {
//        given(mockSource.getConnection()).willReturn(mockConnection);
//        given(mockConnection.isValid(5)).willReturn(true);
//        given(mockConnection.prepareCall(CALL_GET_USER_ACTIVITIES_AND_RECORDS)).willReturn(mockCallableStatement);
//        given(mockCallableStatement.executeQuery()).willThrow(exception);
////        given(mockResultSet.next()).willReturn(false);
//        assertThrows(DbServiceException.class,() -> { new EntityListService<>(Account.class, mockSource)
//                .getByStoredProc(CALL_GET_USER_ACTIVITIES_AND_RECORDS, Collections.emptyList());});
//
//    }

}