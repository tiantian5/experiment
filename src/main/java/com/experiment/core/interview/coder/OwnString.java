package com.experiment.core.interview.coder;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author tzw
 * @description 实现自己的一个String类
 *
 * <p>
 *     我们可以加载自己的String类，但无法加载自己的java.lang.String类。虽然我们可以破坏双亲委派模型，
 *     但JVM规定了以java.开头的类必须由BootstrapClassLoader加载！所以我们无法加载自己的java.lang.String类
 *
 *     代码实现中为了区分自定义的String和java.lang.String，所有用到java.lang.String的地方均采用全限定名表示
 *
 *     1、要实现自己的String，需要自定义类加载器，然后重写loadClass()即可
 *     2、熟悉双亲委派模型
 *     3、熟悉JVM类加载机制
 * </p>
 *
 * @create 2020-12-01 11:20 上午
 **/
public class OwnString extends ClassLoader {

    private static final java.lang.String STR = "String";

    private java.lang.String classPath;

    public OwnString(java.lang.String classPath) {
        this.classPath = classPath;
    }

    @Override
    protected Class<?> loadClass(java.lang.String name, boolean resolve)
            throws ClassNotFoundException {
        // 当要加载String类时，直接调用自己的findClass来加载，不进行双亲委派逻辑
        if (name.equals(STR)) {
            return findClass(name);
        } else {
            // 其他类的加载依然遵循双亲委派原则
            return super.loadClass(name, resolve);
        }
    }

    @Override
    protected Class<?> findClass(java.lang.String name) throws ClassNotFoundException {
        byte[] classData = getClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            //直接生成class对象
            return defineClass(name, classData, 0, classData.length);
        }
    }

    private byte[] getClassData(java.lang.String className) {
        // 读取类文件的字节
        java.lang.String path = classPath + className + ".class";
        try (InputStream inputStream = new FileInputStream(path);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            int read;
            // 读取类文件的字节码
            while ((read = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, read);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(java.lang.String[] args) throws Exception {
        OwnString customStringClassTest = new OwnString("/Users/tangzhaowei/Desktop/code/experiment/src/main/java/com/experiment/core/interview/ownstring/ownstringString.class");
        customStringClassTest.loadClass("String");
        String myString = new String();
        myString.sayHello();
    }

    public static class String {
        public void sayHello() {
            System.out.println("Hello world!");
        }
    }

}