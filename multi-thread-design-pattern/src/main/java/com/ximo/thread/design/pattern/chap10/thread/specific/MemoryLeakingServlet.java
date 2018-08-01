package com.ximo.thread.design.pattern.chap10.thread.specific;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 朱文赵
 * @date 2018/7/30 12:15
 * @description
 */
public class MemoryLeakingServlet extends HttpServlet {

    /** 一个用来存储单个线程的访问量的计数器 */
    private static final ThreadLocal<AtomicInteger> THREAD_LOCAL_COUNTER =
            ThreadLocal.withInitial(AtomicInteger::new);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter printWriter = resp.getWriter();

        printWriter.write(String.valueOf(THREAD_LOCAL_COUNTER.get().getAndIncrement()));
        printWriter.close();
    }
}
