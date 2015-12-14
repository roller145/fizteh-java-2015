package ru.fizteh.fivt.students.roller145.MiniORM.Annotations;

import  java.lang.annotation.*;
/**
 * Created by riv on 14.12.15.
 */

@Target(value=ElementType.FIELD)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface PrimaryKey {
}
