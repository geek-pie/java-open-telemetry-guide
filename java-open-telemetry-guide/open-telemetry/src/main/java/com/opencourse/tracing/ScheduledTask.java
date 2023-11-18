package com.opencourse.tracing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author xujianxing@sensetime.com
 * @date 2023年09月12日 10:53
 */
@Component
public class ScheduledTask {
    private Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

//    @Scheduled(fixedRate = 3000)
    public void scheduledTask() {
        logger.error("测试Tracing:" + LocalDateTime.now());
    }

}
