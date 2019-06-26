package gordon.study.jvm.alpha.classload;

public class ClassLoaderTest02 {
    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader loader = ClassLoaderTest02.class.getClassLoader();

        //使用ClassLoader.loadClass()来加载类，不会执行初始化块
//        loader.loadClass("gordon.study.jvm.alpha.classload.Test2");
        //使用Class.forName()来加载类，默认会执行初始化块
//        Class.forName("gordon.study.jvm.alpha.classload.Test2");
        //使用Class.forName()来加载类，并指定ClassLoader，初始化时不执行静态块
        Class.forName("gordon.study.jvm.alpha.classload.Test2", false, loader);

        System.out.println(Test2.class.getClassLoader());
    }
}
