package com.opencourse.tracing;


import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xujianxing@sensetime.com
 * @date 2023年09月04日 12:04
 */
@RestController
public class HelloWorldController {

    private Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @GetMapping(value = "/get", produces = "application/json;charset=UTF-8")
    public String getTracing() {
        logger.error("测试Tracing");
        new Thread(() -> logger.error("在一个新的线程中测试Tracing")).start();
        return "ok";
    }


    @GetMapping(value = "/getByManual", produces = "application/json;charset=UTF-8")
    public String getByManual() {

        OpenTelemetry openTelemetry = GlobalOpenTelemetry.get();
        logger.error("getByManual测试Tracing");

        //获取Tracer，这是线程安全的
        Tracer tracer = openTelemetry.getTracer(HelloWorldController.class.getName());
        Context context = Context.current();

        new Thread(new Runnable() {
            @Override
            public void run() {

                //创建一个新的span
                Span span = tracer.spanBuilder("新线程中建立一个span").setParent(context).startSpan();
                System.out.println("直接打印Traceid：" + span.getSpanContext().getTraceId());

                //opentelemetry-log4j的扩展不支持在新线程中将trace_id和span_id设置到MDC中，只能手工设置
                MDC.put("trace_id", span.getSpanContext().getTraceId());
                MDC.put("span_id", span.getSpanContext().getSpanId());
                logger.error("新线程中测试Tracing");
                try {
                    //模拟
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                span.end();
            }
        }

        ).start();

        try {
            //等待子线程执行完成
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "ok";

    }

}



