package com.pcz.task.xxl.job.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author picongzhi
 */
@Data
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProps {
    /**
     * 调度中心配置
     */
    private XxlJobAdminProps admin;

    /**
     * 执行器配置
     */
    private XxlJobExecutorProps executor;

    /**
     * 与调度中心交互的token
     */
    private String accessToken;

    @Data
    public static class XxlJobAdminProps {
        /**
         * 调度中心地址
         */
        private String address;
    }

    @Data
    public static class XxlJobExecutorProps {
        /**
         * 执行器名称
         */
        private String appName;

        /**
         * 执行器IP
         */
        private String ip;

        /**
         * 执行器端口
         */
        private int port;

        /**
         * 执行器日志
         */
        private String logPath;

        /**
         * 执行器日志保存天数
         */
        private int logRetentionDays;
    }
}
