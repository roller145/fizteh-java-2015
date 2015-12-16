package ru.fizteh.fivt.students.roller145.MiniORM;

import junit.framework.TestCase;
import org.junit.Test;
import ru.fizteh.fivt.students.roller145.MiniORM.Annotations.Column;
import ru.fizteh.fivt.students.roller145.MiniORM.Annotations.PrimaryKey;
import ru.fizteh.fivt.students.roller145.MiniORM.Annotations.Table;

/**
 * Created by riv on 16.12.15.
 */
public class AnnotatedFieldTest extends TestCase {

    @Table (name = "dolls")
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
    @Test
    public void testResolve() throws Exception {
        Doll dolly = new Doll(1,"Dolly");
        AnnotatedField intField = new AnnotatedField(Doll.class.getDeclaredFields()[0]);
        assertEquals(intField.getH2Type(), "INT");
        assertEquals(intField.getColumnName(),"ID" );
        assertEquals(intField.getElement(), Doll.class.getDeclaredFields()[0]);
    }
}