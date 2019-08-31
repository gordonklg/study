package gordon.study.jvm.alpha.classload;

import java.io.*;
import java.net.URL;

public class MyClassLoader extends ClassLoader {
    private String root;

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = loadClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            return defineClass(name, classData, 0, classData.length);
        }
    }

    private byte[] loadClassData(String className) {
        String fileName = root + File.separatorChar
                + className.replace('.', File.separatorChar) + ".class";
        try {
            InputStream ins = new FileInputStream(fileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = 0;
            while ((length = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public static void main(String[] args)  {

        MyClassLoader classLoader = new MyClassLoader();
        URL url = MyClassLoader.class.getResource("/alpha/classload");
        // URL url = MyClassLoader.class.getClassLoader().getResource("alpha/classload");
        classLoader.setRoot(url.getPath());

        Class<?> testClass = null;
        try {
            testClass = classLoader.loadClass("gordon.study.jvm.alpha.classload.Test2");
            Object object = testClass.newInstance();
            System.out.println(object.getClass().getClassLoader());

            testClass = classLoader.loadClass("gordon.study.jvm.alpha.classload.Test3");
            object = testClass.newInstance();
            System.out.println(object.getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
