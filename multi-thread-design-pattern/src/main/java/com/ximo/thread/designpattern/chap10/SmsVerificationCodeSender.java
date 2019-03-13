package com.ximo.thread.designpattern.chap10;

import com.ximo.thread.designpattern.chap10.thread.specific.ThreadSpecificSecureRandom;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.text.DecimalFormat;
import java.util.concurrent.*;

/**
 * @author 朱文赵
 * @date 2018/7/13 12:11
 * @description 短信验证码发送者
 */
@Slf4j
public class SmsVerificationCodeSender {

    private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(1,
            Runtime.getRuntime().availableProcessors(),
            60, TimeUnit.SECONDS,
            new SynchronousQueue<>(), r -> {
                Thread thread = new Thread(r, "sms-verification-code");
                thread.setDaemon(true);
                return thread;
            }, new ThreadPoolExecutor.DiscardPolicy());

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();

    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE =
            new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().daemon(true).namingPattern("sms-verification-code-thread-%d").build());

    /**
     * 生成验证码发送到手机号中
     *
     * @param msisdn 需要发送的手机号
     */
    public void sendVerificationSms(final String msisdn) {
        Runnable sendSmsTask = () -> {
            int verificationCode = ThreadSpecificSecureRandom.INSTANCE.nextInt(999999);
            DecimalFormat df = new DecimalFormat("000000");
            String formatVerificationCode = df.format(verificationCode);
            sendSms(msisdn, formatVerificationCode);
        };
        //提交任务
        SCHEDULED_EXECUTOR_SERVICE.submit(sendSmsTask);
    }

    private void sendSms(String msisdn, String verificationCode) {
        log.info("短信发送开始，receiver：{}，验证码：{}", msisdn, verificationCode);
    }
}
