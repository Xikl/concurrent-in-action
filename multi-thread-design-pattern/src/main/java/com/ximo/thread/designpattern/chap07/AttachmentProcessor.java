package com.ximo.thread.designpattern.chap07;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author 朱文赵
 * @date 2018/8/6 12:04
 * @description 附件处理器
 */
public class AttachmentProcessor {

    private static final String ATTACHMENT_STORE_BASE_DIR = "";

    private final Queue<File> queue = new ArrayBlockingQueue<>(200);





}
