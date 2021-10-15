package com.ipc.server.ffmpeg;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 胡学汪
 * @Description FFmpeg命令封装类
 * @Date 创建于 2021/9/17 13:57
 */
@Data
public class FFmpegCommand {

    private List<String> commandList = new ArrayList<>();

    public FFmpegCommand() {
    }

    public FFmpegCommand(List<String> commandList) {
        this.commandList = commandList;
    }

}
