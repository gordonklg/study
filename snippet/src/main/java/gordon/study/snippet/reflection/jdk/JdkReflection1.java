package gordon.study.snippet.reflection.jdk;

import gordon.study.snippet.reflection.model.RefModelB;

import java.lang.reflect.Method;

public class JdkReflection1 {

    public static void main(String[] args) throws Exception {
        RefModelB b = (RefModelB) Class.forName("gordon.study.snippet.reflection.model.RefModelB").newInstance();
        System.out.println(b.getClass());

        Class superClazz = b.getClass().getSuperclass();
        System.out.println(superClazz);

        Method methodB = superClazz.getDeclaredMethod("methodB");
        methodB.setAccessible(true);
        methodB.invoke(b);

        System.out.println("---------superClazz.getDeclaredMethods()-------------------");
        for (Method method : superClazz.getDeclaredMethods()) {
            inspectMethod(method);
        }

        System.out.println("---------clazz.getDeclaredMethods()-------------------");
        for (Method method : b.getClass().getDeclaredMethods()) {
            inspectMethod(method);
        }

        System.out.println("---------clazz.getMethods()-------------------");
        for (Method method : b.getClass().getMethods()) {
            inspectMethod(method);
        }
    }

    private static void inspectMethod(Method method) {
        System.out.println(method);
    }
}
