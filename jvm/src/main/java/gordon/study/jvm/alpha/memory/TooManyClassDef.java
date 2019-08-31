package gordon.study.jvm.alpha.memory;

import gordon.study.jvm.alpha.memory.cglib.CglibBean;

import java.util.HashMap;

/**
 * -XX:MaxMetaspaceSize=50m
 */
public class TooManyClassDef {

    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            if (i % 1000 == 0) {
                System.out.println(i);
            }
            CglibBean bean = new CglibBean("gordon.study.jvm.alpha.memory.Bean" + i, new HashMap());
        }
    }
}

/*
Caused by: java.lang.OutOfMemoryError: Metaspace
 */