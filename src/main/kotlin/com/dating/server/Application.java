package com.dating.server;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.ipc.netty.NettyContext;
import reactor.ipc.netty.http.server.HttpServer;

/**
 * Created by dakishin@mail.com  14.04.18.
 */

@Configuration
@ComponentScan
@EnableWebFlux
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
@SpringBootApplication
public class Application {

    @Value("${server.port:8080}")
    private int port = 8080;

    public static void main(String[] args) throws Exception {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                Application.class)) {
            context.getBean(NettyContext.class).onClose().block();
        }
    }

    @Profile("default")
    @Bean
    public NettyContext nettyContext(ApplicationContext context) {
        HttpHandler handler = WebHttpHandlerBuilder.applicationContext(context).build();
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(handler);
        HttpServer httpServer = HttpServer.create("localhost", this.port);
        return httpServer.newHandler(adapter).block();
    }

}