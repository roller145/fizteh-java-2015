package ru.fizteh.fivt.students.roller145.MiniORM;
import com.google.common.base.CaseFormat;
import com.tngtech.java.junit.dataprovider.internal.DataConverter;
import ru.fizteh.fivt.students.roller145.MiniORM.Annotations.Column;
import ru.fizteh.fivt.students.roller145.MiniORM.Annotations.PrimaryKey;
import ru.fizteh.fivt.students.roller145.MiniORM.Annotations.Table;

import javax.management.OperationsException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by riv on 14.12.15.
 */
public class DataBaseService <T,K>{
    static final String dataBaseNane = "RIVdatabase";
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private Field primaryKey;
    private List<AnnotatedField> columns;
    private Class<T> typeClass;
    private String tableName;

    private boolean hasTableYet;

    private void connect() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Class.forName("org.h2.Driver").newInstance();
        connection = DriverManager.getConnection("jdbc:h2:test",
                "sa", "");
        statement = connection.createStatement();
    }

    protected void finalize ( ) throws SQLException {
        resultSet.close();
        statement.close();
        connection.close();
    }

    DataBaseService(Class<T> elementClass) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        try{
            this.connect();
        }catch (Exception e){
            e.printStackTrace();
        }
        columns = new ArrayList<>();
        typeClass = elementClass;
        Table table = typeClass.getAnnotation(Table.class);
        if (table == null) {
            throw new IllegalArgumentException("Class should be annotated with Table");
        }
        tableName = table.name();
        if (tableName.equals("")) {
            tableName = "MY_TABLE";
        }
        for (Field elem : typeClass.getDeclaredFields()) {
            if (elem.getAnnotation(Column.class) != null) {
                columns.add(new AnnotatedField(elem));
            }
            if (elem.getAnnotation(PrimaryKey.class) != null) {
                if (elem.getAnnotation(Column.class) == null) {
                    throw new IllegalArgumentException("Primary key should be column");
                }
                if (primaryKey != null) {
                    throw new IllegalArgumentException("Primary key should be one");
                }
                primaryKey = elem;
            }
        }
        try{
            resultSet = connection.getMetaData().getTables(null, null,
                            CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_UNDERSCORE, tableName), null);
            if (resultSet.next()) {
                hasTableYet = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    T queryById(K id) {

        throw new UnsupportedOperationException();
    }

    T queryForAll() {

        throw new UnsupportedOperationException();
    }

    void insert(T key) {

        throw new UnsupportedOperationException();
    }

    void update(T key) {

        throw new UnsupportedOperationException();
    }

    void delete(T key){

        throw new UnsupportedOperationException();
    }

    void createTable() throws OperationsException {
        if (hasTableYet) {
            throw new OperationsException("Невозможно повторно создать таблицу");
        }
        StringBuilder newRequest = new StringBuilder();
        newRequest.append("CREATE TABLE ").append(tableName).append(" (");
        for (AnnotatedField field : columns) {
            newRequest.append(field.getColumnName()).append(" ");
            newRequest.append(field.getH2Type()).append(" ");
            if (field.getElement().isAnnotationPresent(PrimaryKey.class)) {
                newRequest.append("NOT NULL PRIMARY KEY ");
            }
            newRequest.append(" , ");
        }
    }

    void dropTable(){

        throw new UnsupportedOperationException();
    }
}
