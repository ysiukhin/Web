package ua.traning.rd.java.finalproject.core.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.traning.rd.java.finalproject.core.dao.DbService;
import ua.traning.rd.java.finalproject.core.dao.DbServiceImpl;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.model.AccountBuilder;
import ua.traning.rd.java.finalproject.core.service.testmodel.LinkedTable;
import ua.traning.rd.java.finalproject.core.service.testmodel.PrimaryTable;
import ua.traning.rd.java.finalproject.h2.DataSourceH2;
import ua.traning.rd.java.finalproject.jdbc.dao.DaoJdbc;
import ua.traning.rd.java.finalproject.jdbc.sessionmanager.SessionManagerJdbc;
import ua.traning.rd.java.finalproject.servlet.exception.DaoException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static ua.traning.rd.java.finalproject.Constants.CALL_GET_USER_ACTIVITIES_AND_RECORDS;
import static ua.traning.rd.java.finalproject.Constants.SQL_LIMIT_OFFSET_BOUNDS;

@ExtendWith(MockitoExtension.class)
class EntityListServiceImplTest {

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

    public static final Logger LOGGER = LogManager.getLogger(EntityListServiceImplTest.class);

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
        EntityListServiceImpl<Account> accountService = new EntityListServiceImpl<>(Account.class, dataSource);
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
        assertEquals(TEST_ROWS_QUANTITY, new EntityListServiceImpl<>(Account.class, dataSource).totalEntityQuantity());
    }

    @Test
    @DisplayName("totalEntityQuantity(): have to catch DaoException exception when throws SQLException")
    void totalEntityQuantityExceptionTest() throws SQLException {
        given(mockSource.getConnection()).willReturn(mockConnection);
        given(mockConnection.isValid(5)).willReturn(true);
        given(mockConnection.prepareStatement(anyString())).willReturn(mockPreparedStatement);
        given(mockPreparedStatement.executeQuery()).willThrow(SQLException.class);
        assertThrows(DaoException.class, () -> new EntityListServiceImpl<>(Account.class, mockSource)
                .totalEntityQuantity());
    }


    @Test
    @DisplayName("have to return all entities List object")
    void totalEntityQuantityBySqlTest() {
        assertEquals(TEST_ROWS_QUANTITY, new EntityListServiceImpl<>(Account.class, dataSource)
                .totalEntityQuantityBySql(SELECT_ALL_FROM_ACCOUNT).size());
    }

    @Test
    @DisplayName("totalEntityQuantityBySql(): have to catch DaoException exception when throws SQLException")
    void totalEntityQuantityBySqlExceptionTest() throws SQLException {
        given(mockSource.getConnection()).willReturn(mockConnection);
        given(mockConnection.isValid(5)).willReturn(true);
        given(mockConnection.prepareStatement(anyString())).willReturn(mockPreparedStatement);
        given(mockPreparedStatement.executeQuery()).willThrow(SQLException.class);
        assertThrows(DaoException.class, () -> new EntityListServiceImpl<>(Account.class, mockSource)
                .totalEntityQuantityBySql(SELECT_ALL_FROM_ACCOUNT));
    }


    @Test
    @DisplayName("have to return entities in range")
    void getInRangeByRowNumberTest() {
        assertEquals(2, new EntityListServiceImpl<>(Account.class, dataSource)
                .getInRangeByRowNumber(2, 2).size());
    }

    @Test
    @DisplayName("getInRangeByRowNumber(): have to catch DaoException exception when throws SQLException")
    void getInRangeByRowNumberExceptionTest() throws SQLException {
        given(mockSource.getConnection()).willReturn(mockConnection);
        given(mockConnection.isValid(5)).willReturn(true);
        given(mockConnection.prepareStatement(anyString())).willReturn(mockPreparedStatement);
        given(mockPreparedStatement.executeQuery()).willThrow(SQLException.class);
        assertThrows(DaoException.class, () -> new EntityListServiceImpl<>(Account.class, mockSource)
                .getInRangeByRowNumber(2, 2));
    }

    @Test
    @DisplayName("have to return entities in range")
    void getInRangeByRowNumberSqlTest() {
        assertEquals(2, new EntityListServiceImpl<>(Account.class, dataSource).getInRangeByRowNumber(2, 2,
                SELECT_ALL_FROM_ACCOUNT + SQL_LIMIT_OFFSET_BOUNDS).size());
    }

    @Test
    @DisplayName("have to insert new entity and set new entity ID generated by DB")
    void insertEntityTest() {
        Account newAccount = getNewAccount(6);
        int result = new EntityListServiceImpl<>(Account.class, dataSource).insertEntity(newAccount);
        assertEquals(newAccount.getId(), result);
    }


    @DisplayName("insertEntity(): have to catch DaoException exception when throws SQLException")
    @ParameterizedTest(name = "when rollback() throws SQLException = {0}")
    @ValueSource(booleans = {false, true})
    void insertEntityExceptionTest(boolean rollbackSuccessful) throws SQLException {
        given(mockSource.getConnection()).willReturn(mockConnection);
        given(mockConnection.isValid(5)).willReturn(true);
        given(mockConnection.prepareStatement(anyString())).willReturn(mockPreparedStatement);
        given(mockPreparedStatement.executeUpdate()).willThrow(SQLException.class);
        if (rollbackSuccessful) {
            doThrow(SQLException.class).when(mockConnection).rollback(null);
        }
        assertThrows(DaoException.class, () -> new EntityListServiceImpl<>(Account.class, mockSource)
                .insertEntity(getNewAccount(6)));
    }


    @Test
    @DisplayName("have to update DB stored data with provided entity values")
    void updateEntityTest() throws Exception {
        int defaultId = 1;
        DbService<Account> dbService = new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(dataSource), Account.class));
        Account account = dbService.getBeansById(defaultId).orElseThrow(Exception::new);
        account.setMiddleName(null);
        assertEquals(1, new EntityListServiceImpl<>(Account.class, dataSource).updateEntity(account));
        assertNull(dbService.getBeansById(defaultId).orElseThrow(Exception::new).getMiddleName());
    }

    @DisplayName("updateEntity(): have to catch DaoException exception when throws SQLException")
    @ParameterizedTest(name = "when rollback() throws SQLException = {0}")
    @ValueSource(booleans = {false, true})
    void updateEntityExceptionTest(boolean rollbackSuccessful) throws SQLException {
        given(mockSource.getConnection()).willReturn(mockConnection);
        given(mockConnection.isValid(5)).willReturn(true);
        given(mockConnection.prepareStatement(anyString())).willReturn(mockPreparedStatement);
        given(mockPreparedStatement.executeUpdate()).willThrow(SQLException.class);
        if (rollbackSuccessful) {
            doThrow(SQLException.class).when(mockConnection).rollback(null);
        }
        assertThrows(DaoException.class, () -> new EntityListServiceImpl<>(Account.class, mockSource)
                .updateEntity(getNewAccount(6)));
    }


    @Test
    @DisplayName("have to update DB stored data by given SQL query action")
    void updateEntityBySqlTest() throws Exception {
        int defaultId = 1;
        assertEquals(1, new EntityListServiceImpl<>(Account.class, dataSource)
                .updateEntity(UPDATE_ACCOUNT_SET_MIDDLE_NAME_NULL_BY_ID, defaultId));
        assertNull(new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(dataSource), Account.class))
                .getBeansById(defaultId).orElseThrow(Exception::new).getMiddleName());
    }


    @DisplayName("updateEntityBySql(): have to catch DaoException exception when throws SQLException")
    @ParameterizedTest(name = "when rollback() throws SQLException = {0}")
    @ValueSource(booleans = {false, true})
    void updateEntityBySqlExceptionTest(boolean rollbackSuccessful) throws SQLException {
        given(mockSource.getConnection()).willReturn(mockConnection);
        given(mockConnection.isValid(5)).willReturn(true);
        given(mockConnection.prepareStatement(anyString())).willReturn(mockPreparedStatement);
        given(mockPreparedStatement.executeUpdate()).willThrow(SQLException.class);
        if (rollbackSuccessful) {
            doThrow(SQLException.class).when(mockConnection).rollback(null);
        }
        assertThrows(DaoException.class, () -> new EntityListServiceImpl<>(Account.class, mockSource)
                .updateEntity(UPDATE_ACCOUNT_SET_MIDDLE_NAME_NULL_BY_ID, 1));
    }

    @Test
    @DisplayName("have to delete from Db data by given ID")
    void deleteEntityTest() {
        assertEquals(1, new EntityListServiceImpl<>(Account.class, dataSource).deleteEntity(1));
        assertEquals(4, new EntityListServiceImpl<>(Account.class, dataSource).totalEntityQuantity());
    }

    @DisplayName("deleteEntity(): have to catch DaoException exception when throws SQLException")
    @ParameterizedTest(name = "when rollback() throws SQLException = {0}")
    @ValueSource(booleans = {false, true})
    void deleteEntityExceptionTest(boolean rollbackSuccessful) throws SQLException {
        given(mockSource.getConnection()).willReturn(mockConnection);
        given(mockConnection.isValid(5)).willReturn(true);
        given(mockConnection.prepareStatement(anyString())).willReturn(mockPreparedStatement);
        given(mockPreparedStatement.executeUpdate()).willThrow(SQLException.class);
        if (rollbackSuccessful) {
            doThrow(SQLException.class).when(mockConnection).rollback(null);
        }
        assertThrows(DaoException.class, () -> new EntityListServiceImpl<>(Account.class, mockSource)
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
        assertEquals(0, new EntityListServiceImpl<>(Account.class, mockSource)
                .getByStoredProc(CALL_GET_USER_ACTIVITIES_AND_RECORDS, Collections.emptyList()).size());

    }

    @DisplayName("getByStoredProc(): have to catch DaoException exception when throws SQLException")
    @Test
    void getByStoredProcExceptionTest() throws SQLException {
        given(mockSource.getConnection()).willReturn(mockConnection);
        given(mockConnection.isValid(5)).willReturn(true);
        given(mockConnection.prepareCall(CALL_GET_USER_ACTIVITIES_AND_RECORDS)).willReturn(mockCallableStatement);
        doNothing().when(mockCallableStatement).setObject(anyInt(), any());
        given(mockCallableStatement.executeQuery()).willThrow(SQLException.class);
        assertThrows(DaoException.class, () -> new EntityListServiceImpl<>(Account.class, mockSource)
                .getByStoredProc(CALL_GET_USER_ACTIVITIES_AND_RECORDS, Collections.singletonList(1)));
    }

    @DisplayName(value = "Testing @Linked annotation strategy - 'active'")
    @Test
    void annotationLinkedStrategyActiveTest() {
        final int defaultId = 1;
        sqlQueryCreateTable("create table if not exists linked_table " +
                "( id int auto_increment primary key, primary_table_id int not null )");
        EntityListServiceImpl<LinkedTable> linkedService = new EntityListServiceImpl<>(LinkedTable.class, dataSource);
        for (int j = 0; j < TEST_ROWS_QUANTITY; j++) {
            LinkedTable linked = new LinkedTable();
            linked.setPrimaryTableId(defaultId);
            linkedService.insertEntity(linked);
        }

        sqlQueryCreateTable("create table if not exists primary_table ( id int auto_increment primary key )");
        EntityListServiceImpl<PrimaryTable> service = new EntityListServiceImpl<>(PrimaryTable.class, dataSource);
        PrimaryTable table = new PrimaryTable();
        table.setId(defaultId);
        service.insertEntity(table);
        PrimaryTable result = service.getById(defaultId);

        assertEquals(TEST_ROWS_QUANTITY, result.getLinkedTableList().size());
        sqlQueryCreateTable("drop table primary_table");
        sqlQueryCreateTable("drop table linked_table");
    }


//    static Stream<Class<? extends Throwable>> exceptionProvider() {
//        return Stream.of(InstantiationException.class, IllegalAccessException.class, SQLException.class);
//    }


//    //throw Exceptions when stored procedure execution call and catch DaoException
//    @DisplayName("have to catch DaoException exception when ")
//    @ParameterizedTest(name = "throws {0} during stored procedure call")
//    @MethodSource("exceptionProvider")
//    void getByStoredProcExceptionTest(Class<? extends Throwable> exception) throws SQLException {
//        given(mockSource.getConnection()).willReturn(mockConnection);
//        given(mockConnection.isValid(5)).willReturn(true);
//        given(mockConnection.prepareCall(CALL_GET_USER_ACTIVITIES_AND_RECORDS)).willReturn(mockCallableStatement);
//        given(mockCallableStatement.executeQuery()).willThrow(exception);
////        given(mockResultSet.next()).willReturn(false);
//        assertThrows(DaoException.class,() -> { new EntityListService<>(Account.class, mockSource)
//                .getByStoredProc(CALL_GET_USER_ACTIVITIES_AND_RECORDS, Collections.emptyList());});
//
//    }

}