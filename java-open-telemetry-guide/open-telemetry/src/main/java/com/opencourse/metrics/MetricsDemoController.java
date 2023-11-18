package com.opencourse.metrics;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MetricsDemoController {

    @Autowired
    private MeterRegistry registry;


    @GetMapping(value = "/helloCounted", produces = "application/json;charset=UTF-8")
    public String helloCounted() {
        Counter counter = registry.counter("api.hello.counted", "http.status", "200");
        counter.increment();
        return "helloCounted";
    }

    @GetMapping(value = "/helloCounted2", produces = "application/json;charset=UTF-8")
    public String helloCounted2() {
        Counter counter = registry.counter("api.hello.counted", "http.status", "200");
        counter.increment();
        return "helloCounted";
    }
}
