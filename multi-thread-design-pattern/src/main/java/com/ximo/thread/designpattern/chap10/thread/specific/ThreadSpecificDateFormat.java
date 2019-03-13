package com.ximo.thread.designpattern.chap10.thread.specific;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 朱文赵
 * @date 2018/7/24 12:11
 * @description
 */
public class ThreadSpecificDateFormat {

    private static final ThreadLocal<SimpleDateFormat> SIMPLE_DATE_FORMAT_THREAD_LOCAL =
            ThreadLocal.withInitial(SimpleDateFormat::new);

    public static Date parse(String timeStamp, String pattern) throws ParseException {
        SimpleDateFormat simpleDateFormat = SIMPLE_DATE_FORMAT_THREAD_LOCAL.get();
        simpleDateFormat.applyLocalizedPattern(pattern);
        return simpleDateFormat.parse(timeStamp);
    }

    public static void main(String[] args) {
        try {
            Date date = ThreadSpecificDateFormat.parse(String.valueOf(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss");
            System.out.println(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


}
