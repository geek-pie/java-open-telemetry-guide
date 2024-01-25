
# 指标类型（Metrics Types）

无论何种系统，都是具备一些共性特征。指标的数据类型是有限的。这里列举5个常见指标数据类型。

计数度量器（Counter）：这是最好理解也是最常用的指标形式，计数器就是对有相同量纲、可加减数值的合计量，譬如业务指标像销售额、货物库存量、职工人数等等；技术指标像服务调用次数、网站访问人数等都属于计数器指标。

瞬态度量器（Gauge）：瞬态度量器比计数器更简单，它就表示某个指标在某个时点的数值。譬如当前 Java 虚拟机堆内存的使用量，这就是一个瞬态度量器；又譬如，网站访问人数是计数器，而网站在线人数则是瞬态度量器。

吞吐率度量器（Meter）：吞吐率度量器顾名思义是用于统计单位时间的吞吐量，即单位时间内某个事件的发生次数。譬如交易系统中常以 TPS 衡量事务吞吐率，即每秒发生了多少笔事务交易。

直方图度量器（Histogram）：直方图是常见的二维统计图，它的两个坐标分别是统计样本和该样本对应的某个属性的度量，以长条图的形式表示具体数值。

采样点分位图度量器（Quantile Summary）：分位图是统计学中通过比较各分位数的分布情况的工具，用于验证实际值与理论值的差距，评估理论值与实际值之间的拟合度。譬如，我们说“高考成绩一般符合正态分布”，这句话的意思是：高考成绩高低分的人数都较少，中等成绩的较多，将人数按不同分数段统计，得出的统计结果一般能够与正态分布的曲线较好地拟合。


# 理解直方图度量器（Histogram）
直方图度量器（Histogram）是一种常见的度量工具，用于统计和可视化数据的分布情况。它将数据分组到不同的区间（也称为“桶”或“箱”），然后计算每个区间中数据的频数或频率。直方图可以帮助我们理解数据的分布特征，比如数据的集中趋势、离散程度以及异常值等。

下面是一个使用直方图度量器的场景：

场景：
假设你是一个电商网站的后端开发工程师，你需要监控用户搜索商品时的响应时间分布。通过应用直方图度量器，你可以收集用户每次搜索请求的响应时间，并将其分组到不同时间段（比如0-100ms, 100-200ms, 200-300ms, ...）中，然后绘制直方图来展示搜索请求的响应时间分布情况。这有助于你了解用户在不同时间段的搜索请求响应情况，以便及时发现和解决潜在的性能问题。

接下来，我将为你展示如何在Spring Boot中创建一个简单的例子，用于收集用户搜索请求的响应时间，并通过直方图度量器来统计和展示数据的分布情况。

```java
// 首先，创建一个用于统计响应时间的直方图度量器
import io.micrometer.core.instrument.DistributionSummary;

// 创建一个 Spring Boot 控制器类，用于处理用户搜索请求
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
    
    // 使用直方图度量器来统计搜索请求的响应时间
    private final DistributionSummary searchResponseTime = DistributionSummary
        .builder("search.response.time")
        .publishPercentiles(0.5, 0.95) // 计算响应时间的中位数和95th百分位数
        .register();
    
    @GetMapping("/search")
    public String searchProduct(@RequestParam String keyword) {
        long startTime = System.currentTimeMillis();
        
        // 在这里执行搜索逻辑，这里假设耗时为100ms
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        long elapsedTime = System.currentTimeMillis() - startTime;
        
        // 记录搜索请求的响应时间
        searchResponseTime.record(elapsedTime);
        
        return "Showing results for: " + keyword;
    }
}
```

在上面的例子中，我们使用了Micrometer库中的`DistributionSummary`来创建一个直方图度量器来统计搜索请求的响应时间。每当有搜索请求到达时，我们记录请求的处理时间，并使用直方图度量器进行统计。接着，你可以使用Micrometer库的其他工具将数据导出到各种监控系统中，比如Prometheus，以便进一步分析和可视化直方图数据。

希望这个例子能帮到你了解直方图度量器在实际场景中的应用，以及在Spring Boot中的简单实现。
