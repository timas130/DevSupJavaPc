package com.sup.dev.java_pc.sql;


import com.sup.dev.java.classes.collections.AnyArray;
import com.sup.dev.java.libs.debug.Debug;

public class ResultRows {

    public final int rowsCount;
    public final int fieldsCount;
    public AnyArray values;

    public ResultRows(int rowsCount, AnyArray values) {
        this.rowsCount = rowsCount;
        this.fieldsCount = rowsCount == 0 ? 0 : (values.size() / rowsCount);
        this.values = values;
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public String toString(){
        StringBuilder s = new StringBuilder("ResultRows:");
        for (Object o : values.getList())
            s.append(" ").append(o);
        return s.toString();
    }

    public <K> K next(){
         K k = values.next();
        return k;
    }

    public Long[] asLongArray(){
        Long[] a = new Long[rowsCount];
        for(int i =0; i < rowsCount; i++) a[i] = next();
        return a;
    }

}
