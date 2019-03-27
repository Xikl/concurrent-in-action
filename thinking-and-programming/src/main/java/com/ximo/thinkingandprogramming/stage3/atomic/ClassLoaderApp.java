package com.ximo.thinkingandprogramming.stage3.atomic;

/**
 * @author 朱文赵
 * @date 2019/3/26 12:15
 */
public class ClassLoaderApp {

    /**
     * null
     * sun.misc.Launcher$AppClassLoader@18b4aac2
     * sun.misc.Launcher$ExtClassLoader@19469ea2
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(String.class.getClassLoader());
        ClassLoader classLoader = ClassLoaderApp.class.getClassLoader();
        while (classLoader != null) {
            System.out.println(classLoader);
            classLoader = classLoader.getParent();
        }
    }


}
