package com.ipc.server.ffmpeg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 胡学汪
 * @Description FFmpegCommand Builder类
 * @Date 创建于 2021/9/17 14:26
 */
@Component
public class FFmpegCommandBuilder {

    @Autowired
    private FFmpegProperties fFmpegProperties;

    public FFmpegCommand buildFlvCommand(String input, String output) {
        List<String> commandList = new ArrayList<>();
        commandList.add(fFmpegProperties.getPath());
        commandList.add("-rtsp_transport");
        commandList.add("tcp");
        commandList.add("-i");
        commandList.add(input);
        commandList.add("-vcodec");
        commandList.add("libx264");
        commandList.add("-tune");
        commandList.add("zerolatency");
//        commandList.add("-acodec");
//        commandList.add("copy");
        commandList.add("-an");
        commandList.add("-vf");
        commandList.add("scale=384:216");
        //commandList.add("scale=1280:720");
        commandList.add("-f");
        commandList.add("flv");
        commandList.add(output);
        return new FFmpegCommand(commandList);
    }

    public FFmpegCommand buildMpegtsCommand(String input, String output, String s) {
        List<String> commandList = new ArrayList<>();
        commandList.add(fFmpegProperties.getPath());
        commandList.add("-i");
        commandList.add(input);
        commandList.add("-q");
        commandList.add("0");
        commandList.add("-f");
        commandList.add("mpegts");
        commandList.add("-codec:v");
        commandList.add("mpeg1video");
        commandList.add("-s");
        commandList.add(s);
        commandList.add(output);
        return new FFmpegCommand(commandList);
    }

}
