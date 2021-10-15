package com.ipc.server.ffmpeg;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @Author 胡学汪
 * @Description 基于FFmpeg的流采集器
 * @Date 创建于 2021/9/10 13:57
 */
@Slf4j
@Component
public class FFmpegGatherer {

    @Autowired
    private FFmpegProcessManager fFmpegProcessManager;
    @Autowired
    private FFmpegExecutor fFmpegExecutor;

    public void execute(String deviceId, FFmpegCommand fFmpegCommand) {
        LaunchFFmpegTask launchTask = new LaunchFFmpegTask(deviceId, fFmpegCommand);
        Future<Process> future = fFmpegExecutor.getLaunchExecutor().submit(launchTask);
        try {
            Process process = future.get();
            if (process != null) {
                fFmpegProcessManager.put(deviceId, process);
            }
        } catch (Exception e) {
            log.error("FFmpeg gatherer execute failed, deviceId: {}, ex: {}", deviceId, e);
        }
    }

    /**
     * 启动FFMpeg进程任务
     */
    private class LaunchFFmpegTask implements Callable<Process> {

        private String deviceId;
        private FFmpegCommand fFmpegCommand;

        public LaunchFFmpegTask(String deviceId, FFmpegCommand fFmpegCommand) {
            this.deviceId = deviceId;
            this.fFmpegCommand = fFmpegCommand;
        }

        @Override
        public Process call() {

            Process process = null;
            try {
                List<String> command = fFmpegCommand.getCommandList();
                log.debug("ffmpeg推流命令：" + StringUtils.join(command.toArray(), " "));

                ProcessBuilder processBuilder = new ProcessBuilder(command);
                processBuilder.redirectErrorStream(true);
                process = processBuilder.start();

                DumpFFmpegTask dumpFFmpegTask = new DumpFFmpegTask(deviceId, process);
                fFmpegExecutor.runDump(deviceId, dumpFFmpegTask);

                log.info("Launch FFmpeg success: deviceId: {}", deviceId);
            } catch (Exception ex) {
                log.error("Launch FFmpeg failed: deviceId - {}, ex - {}", deviceId, ex);

                if (process != null) {
                    process.destroyForcibly();
                    process = null;
                }

            }

            return process;
        }

    }

    /**
     * 消耗FFMpeg输出流和错误流任务
     */
    private class DumpFFmpegTask implements Runnable {

        private String deviceId;
        private Process process;

        public DumpFFmpegTask(String deviceId, Process process) {
            this.deviceId = deviceId;
            this.process = process;
        }

        @Override
        public void run() {
            // 处理ffmpeg输出流和错误流，防止缓冲区写满，导致进程阻塞
            try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    log.debug("[{}] 推流信息[{}]", deviceId, line);
                }
                log.info("FFmpeg dump thread stop, deviceId: {}", deviceId);
            } catch (IOException ex) {
                log.error("FFmpeg dump error, deviceId: {}, ex: {}", deviceId, ex);
            } finally {
                process.destroyForcibly();
            }
        }

    }

}
