package ru.fizteh.fivt.students.roller145.MiniORM;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import ru.fizteh.fivt.students.roller145.MiniORM.Annotations.Column;
import ru.fizteh.fivt.students.roller145.MiniORM.Annotations.PrimaryKey;
import ru.fizteh.fivt.students.roller145.MiniORM.Annotations.Table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by riv on 16.12.15.
 */
public class DataBaseServiceTest extends TestCase {
    private static DataBaseService dbservice = null;

    @Table(name ="dolls")
    public static class Doll {
        @PrimaryKey
        @Column(name = "ID")
        public Integer id;
        @Column(name = "NAME")
        public String name;

        public Doll(int id, String name) {
            this.id = id;
            this.name = name;
        }
        @Override
        public boolean equals(Object doll) {
            return doll instanceof Doll && id.equals(((Doll) doll).id) && name.equals(((Doll) doll).name);
        }
        @Override
        public String toString() {
            return "(" + id + ", " + name + ")";
        }

    }

    @Before
    private static void  prepare(){
        try {
            dbservice = new DataBaseService(Doll.class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @After
    private static void close() throws SQLException {
        dbservice.finalize();
    }


    public void testTheBases() throws Exception {
        dbservice.createTable();
        dbservice.insert(new Doll(1, "ANN"));
        assertEquals(dbservice.queryById(1), "ANN");
        dbservice.insert(new Doll(2, "KATE"));
        dbservice.insert(new Doll(3, "MARY"));
        List<Doll> res  = new ArrayList<>();
        res.add(new Doll(1,"ANN"));
        res.add(new Doll(2,"KATE"));
        res.add(new Doll(3,"MARY"));
        assertEquals(dbservice.queryForAll(),res);
    }
}