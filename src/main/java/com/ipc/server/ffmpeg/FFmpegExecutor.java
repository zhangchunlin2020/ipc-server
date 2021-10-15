package com.ipc.server.ffmpeg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author 胡学汪
 * @Description FFmpeg执行器
 * @Date 创建于 2021/9/17 13:57
 */
@Slf4j
@Component
public class FFmpegExecutor {

    private static final String DUMP_FFMPEG_THREAD_PREFIX = "ffmpeg-dump-";

    @Autowired
    private FFmpegProperties fFmpegProperties;

    private ThreadPoolExecutor launchExecutor;

    private FFmpegThreadFactory defaultDumpThreadFactory = new FFmpegThreadFactory(DUMP_FFMPEG_THREAD_PREFIX, true);

    @PostConstruct
    public void init() {
        launchExecutor = new ThreadPoolExecutor(fFmpegProperties.getLaunchThreadSize(), fFmpegProperties.getLaunchThreadSize(), 2000L, TimeUnit.MICROSECONDS,
                new LinkedBlockingQueue<Runnable>(), new FFmpegThreadFactory("ffmpeg-launch-", true));
        launchExecutor.allowCoreThreadTimeOut(true);
    }

    private class FFmpegThreadFactory implements ThreadFactory {

        private AtomicInteger threadNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final String namePrefix;
        private final boolean daemon;

        public FFmpegThreadFactory(String namePrefix, boolean daemon) {
            SecurityManager s = System.getSecurityManager();
            this.group = s != null ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.namePrefix = namePrefix;
            this.daemon = daemon;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(this.group, r, this.namePrefix + this.threadNumber.getAndIncrement());
            t.setDaemon(this.daemon);
            return t;
        }

    }

    public ThreadPoolExecutor getLaunchExecutor() {
        return launchExecutor;
    }

    public void runDump(String key, Runnable runnable) {
        new FFmpegThreadFactory(DUMP_FFMPEG_THREAD_PREFIX + key + "-", true).newThread(runnable).start();
    }

}
