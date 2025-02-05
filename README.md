# SpringBoot之微服务日志链路追踪

## 简介

在微服务里，业务出现问题或者程序出的任何问题，都少不了查看日志，一般我们使用 `ELK`
相关的日志收集工具，服务多的情况下，业务问题也是有些难以排查，只能确定大致时间定位相关日志。`log-trace-spring-boot-starter` 解决多个服务调用日志的问题，它可以将一个完整的调用链给整合为一个完整有序的日志。

支持组件：

- spring cloud gateway 调用
- feign 调用
- restTemplate 调用

日志输出格式：

```
2022-04-09 22:16:05.796  INFO [log-trace-service-a-demo,ac8ffaaed5f343da,log-trace-gateway-demo,,] 88948 --- [nio-8081-exec-7] c.p.l.t.service.a.demo.TestController    : controller test2 执行 ac8ffaaed5f343da
2022-04-09 22:16:05.569  INFO [log-trace-service-a-demo,04cf5392dc5c4881,log-trace-gateway-demo,,] 88948 --- [nio-8081-exec-9] c.p.l.t.service.a.demo.TestController    : controller test2 执行 04cf5392dc5c4881
2022-04-09 22:16:05.183  INFO [log-trace-service-a-demo,86b5c555ce4f4451,log-trace-gateway-demo,,] 88948 --- [nio-8081-exec-1] c.p.l.t.service.a.demo.TestController    : controller test2 执行 86b5c555ce4f4451
```

我们可以通过 `86b5c555ce4f4451` id 进行查询链路上的所有日志信息。

`log-trace-service-a-demo` 为当前应用。
`log-trace-gateway-demo` 为上游应用。

当然这些参数可以基于业务定制的。

## 功能使用

### 添加依赖

```
<dependency>
  <groupId>com.pig4cloud.plugin</groupId>
  <artifactId>log-trace-spring-boot3-starter</artifactId>
  <version>3.2.0</version>
</dependency>
```

### 配置应用

<img src='https://minio.pigx.top/oss/202403/1709779710.png' alt='1709779710'/>

这里以3个微服务来举例子。

1. `log-trace-gateway-demo` 充当网关功能
2. `log-trace-service-a-demo` 充当服务A
3. `log-trace-service-b-demo` 充当服务B

**调用链路为:**

`log-trace-gateway-demo` -> `log-trace-service-a-demo` `TestController#test`
-> `log-trace-service-b-demo` `TestController#test`

访问网关地址: `http://127.0.0.1:8000/a/test`

**网关日志如下:**

```
2022-04-09 22:16:05.434 DEBUG [33b07a9c5f324375,this] 89996 --- [nio-8000-exec-1] c.p.l.t.s.i.gateway.TracePregatewayFilter      : gateway traceid 33b07a9c5f324375
```

网关转发至服务A

**服务A 日志如下:**

```
2022-04-09 22:16:05.476  INFO [log-trace-service-a-demo,33b07a9c5f324375,log-trace-gateway-demo,,] 88948 --- [nio-8081-exec-5] c.p.l.t.service.a.demo.TestController    : controller test2 执行 33b07a9c5f324375
```

服务A 调用 服务B

**服务B 日志如下:**

```
2022-04-09 22:16:05.478  INFO [log-trace-service-b-demo,33b07a9c5f324375,log-trace-service-a-demo,,] 88952 --- [nio-8082-exec-3] c.p.l.t.servcie.b.demo.TestController    : header traceId 33b07a9c5f324375
2022-04-09 22:16:05.478  INFO [log-trace-service-b-demo,33b07a9c5f324375,log-trace-service-a-demo,,] 88952 --- [nio-8082-exec-3] c.p.l.t.servcie.b.demo.TestController    : controller test 执行 33b07a9c5f324375
2022-04-09 22:16:05.478  INFO [log-trace-service-b-demo,33b07a9c5f324375,log-trace-service-a-demo,,] 88952 --- [nio-8082-exec-3] c.p.l.trace.servcie.b.demo.TestService   : test 方法执行 33b07a9c5f324375
2022-04-09 22:16:05.478  INFO [log-trace-service-b-demo,33b07a9c5f324375,log-trace-service-a-demo,,] 88952 --- [nio-8082-exec-3] c.p.l.trace.servcie.b.demo.TestService   : test1 方法执行 33b07a9c5f324375
```

这样可以在第三方日志平台按照一个id进行查询了。

如 `ELK` 通过 `33b07a9c5f324375` id 查询出相关的所有链路调用。

### 配置输出格式

**目前支持以上参数:**

```
X-B3-ParentName 上游服务名称
X-B3-TraceId 为一个请求分配的ID号，用来标识一条请求链路。
```

**通过 `application.properties` 进行配置。**

```
spring.trace.log.format=X-B3-TraceId,X-B3-ParentName


`spring.trace.log.format` 配置参数顺序将影响日志输出格式。

不配置将按照默认格式输出。
```

日志输出如下:

```
2022-04-09 22:15:57.434 DEBUG [33b07a9c5f324375,this] 89996 --- [nio-8000-exec-1] c.p.l.t.s.i.gateway.TracePregatewayFilter      : gateway traceid 33b07a9c5f324375
```
