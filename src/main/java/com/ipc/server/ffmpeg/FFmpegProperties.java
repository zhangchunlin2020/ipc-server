package com.ipc.server.ffmpeg;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author 胡学汪
 * @Description FFmpeg配置
 * @Date 创建于 2021/9/12 13:57
 */
@Data
@Component
@ConfigurationProperties(prefix = "ffmpeg")
public class FFmpegProperties {

    private String path;
    private Integer launchThreadSize;

}
