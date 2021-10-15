package com.ipc.server;

import com.ipc.server.ffmpeg.FFmpegCommandBuilder;
import com.ipc.server.ffmpeg.FFmpegGatherer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/9/11 13:57
 */
@Component
public class IPCRunnerTest implements ApplicationRunner {

    @Autowired
    private FFmpegGatherer fFmpegGatherer;
    @Autowired
    private FFmpegCommandBuilder fFmpegCommandBuilder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        fFmpegGatherer.execute("d1", fFmpegCommandBuilder.buildFlvCommand("rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov", "http://127.0.0.1:8081/data/receive/d1"));
//        fFmpegGatherer.execute("d1", fFmpegCommandBuilder.buildFlvCommand("rtsp://admin:qwer@1234@192.168.1.108:554/cam/realmonitor?channel=1&subtype=0", "rtmp://192.168.11.129:1935/mylive/1"));
    }

}
