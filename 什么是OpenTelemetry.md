# 什么是可观测性

可观测性是指在软件系统中收集、分析和理解系统的运行状态、行为和性能的能力。通过可观测性，开发人员和运维人员可以实时监测系统，检测问题并采取相应的措施。

为了实现可观测性，常用的技术包括：


日志记录（Logging）：将系统事件、错误和状态信息记录到日志中，以便后续检查和分析。



指标度量（Metrics）：通过收集和记录系统的各种指标，如CPU利用率、内存消耗、请求处理时间等，评估系统的性能和效率。



实时追踪（Tracing）：跟踪系统中的请求和操作，记录它们所经过的组件和服务，并计算每个步骤的执行时间，以便进行性能分析和瓶颈定位。



分布式事务追踪（Distributed tracing）：用于跟踪分布式系统的请求路径和调用关系，帮助识别和解决跨组件或跨服务的性能问题。



集中式监控（Centralized monitoring）：建立集中式监控平台，收集和汇总从各个组件和服务中收集的指标和日志，并提供可视化和告警功能。



可视化工具（Visualization tools）：通过图表、仪表盘等可视化方式展示系统的运行状态和指标，方便用户理解和分析。



在说明OpenTelemetry之前，应该了解可观测性。

一个可观测系统至少包含4部分：  
数据的生产、收集、存储与处理、展示。







# 为什么会有OpenTelemetry？

可观测性的重新兴起导致了许多技术供应商的涌现，他们开源了各种各样的组件来实现可观测性（其中最著名的就是**OpenCensus**和**OpenTracing**）。但是由于各个技术工具的实现者对于可观测性的理解是不一致的，因此造成的结果就是每个工具都有自己的特点，从而导致了行业和开源社区的碎片化以及相互的不兼容。我们在面对如此众多的工具和开源框架的时候，如果要实现系统的可观测性，就需要花费大量时间进行组织和调研来确定选用哪种工具。由于各个开源工具无论是API还是协议都可能是相互不兼容的，如果在后期想要转换到另一种工具就会很麻烦。

为了解决这些问题，Google、Microsoft、Uber和其他一些公司决定合并并推出一个统一的、标准化的可观测性框架，这就是OpenTelemetry。OpenTelemetry的目标是提供一个一致的、可扩展的、可插拔的观测解决方案，以方便开发人员在分布式系统中收集和分析数据。

2019年，OpenTelemetry宣布将两个项目合并。

（图片）

# OpenTelemetry在可观测性中的定位

前面提到，一个可观测性包含了四个部分。OpenTelemetry的定位主要在前两部分：数据产生和收集。因此它叫做OpenTelemetry（开放遥测）而非OpenObserve。




在一开始，其目标是整合这两个项目，OpenTelemetry野心还不至于此，他的目标是打造成一个适合于云原生基础的可观测框架。















# 可观测性

opentelemetry是一个客户端，或者部分服务器。  


opentelemetry规范化了两部分。数据的生产和收集。  

因此处理和展示不同的  
https://www.jaegertracing.io/docs/1.49/architecture/  

https://github.com/grafana/loki/issues/6812  

通常来说，一个完整的有两个组成：  

*客户端  

* 服务器：服务器收集到各种指标以后，进行处理，然后可视化展示到前端。  

我们以jaeger为例，一个完整的可观测系统有如下组成：  

https://www.jaegertracing.io/docs/1.49/architecture/  
#关键的术语与缩写  

## Exporter

exporter通常集中在SDK中，随着应用启动，一般来说他是一个Http服务，将SDK产生的数据通过http或者grpc请求传递到后端。  

##收集器（Collector）  
OpenTelemetry 收集器是一套组件，可以从 OpenTelementry 或其他监测/追踪库（Jaeger、Prometheus 等）执行的进程收集 traces、metrics 和其他遥测数据（例如，日志），并进行聚合和智能采样，导出 traces 和 metrics 到一个或多个监控/追踪后端。收集器允许丰富和转换所收集的遥测数据（例如，添加额外的属性或删除个人信息）。  
OpenTelemetry 收集器有两种主要的操作模式：代理（与应用程序一起在本地运行的守护进程），收集器（独立运行的服务）。  

收集器是OpenTelemetry独有的概念。  
因为收集器并不是必须的。通常来说，通过OTEL所采集到的数据可以直接发给后端，或者由后端服务主动采集从应用采集数据。但是在某些情况下，可能还需要一个中间层，不直接采集。从而实现更加灵活的功能。  

OTEL：OpenTelemetry的缩写  

OTLP：The OpenTelemetry Protocol的缩写。OTLP 定义了遥测数据的编码以及用于在客户端和服务器之间交换数据的协议。这里的服务器，  
有两个含义，一个指的是像jaeger这样的后端服务器，一个就是`OpenTelemetry Collector`  







## 知识补充

### OpenTracing

OpenTracing ([https://opentracing.io](https://opentracing.io)) 项目，始于 2016 年，专注于

关于解决增加采用分布式跟踪作为一种手段的问题

用户更好地了解他们的系统。 项目确定的挑战之一

是否由于成本工具和缺乏一致的

第三方库中的质量检测。 OpenTracing 提供了一个规范

应用程序编程接口 (API) 来解决这个问题。 这个 API 可以

独立于生成分布式跟踪的实现来利用，

因此允许应用程序开发人员和库作者嵌入对该 API 的调用

在他们的代码中。

https://jimmysong.io/kubernetes-handbook/practice/opentracing.html

OpenTracing只是定义了一些API，并且是没有实现的。可以动态决定

有了这个 API 的规范，OpenTracing 还提供了语义

公约。 这些约定描述了提高质量的指南

仪器发出的遥测数据。 我们将进一步讨论语义约定

探索 OpenTelemetry 的概念。

核心：API+数据约定。

https://www.jaegertracing.io/docs/1.21/opentelemetry/  

https://opentelemetry.io/docs/specs/otlp/  

#Refence  
https://www.jaegertracing.io/docs/1.49/architecture/  
https://www.packtpub.com/product/cloud-native-observability-with-opentelemetry/9781801077705  
https://www.doc88.com/p-59199400684782.html

https://cloud.tencent.com/developer/article/1548584
