package com.ximo.concurrent.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author 朱文赵
 * @date 2018/6/17
 * @description
 */
public class ThreadLocalTest {

    /** 创建一个线程局部变量 添加一个格式化日期的值 */
    private static final ThreadLocal<SimpleDateFormat> SIMPLE_DATE_FORMAT_THREAD_LOCAL =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    /**
     * 获得当前线程的随机值
     *
     * @return 随机值
     */
    private static int random() {
        return ThreadLocalRandom.current().nextInt(10);
    }

    private static String format() {
        return SIMPLE_DATE_FORMAT_THREAD_LOCAL.get().format(new Date());
    }

}
