package com.dating.server;


/**
 * Created by dakishin@mail.com  14.04.18.
 */

//@Configuration
//@ComponentScan
//@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
//@SpringBootApplication
//public class Application {
//
//    @Value("${server.port:8080}")
//    private int port = 8080;
//
//    public static void main(String[] args) throws Exception {
//        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
//                Application.class)) {
//            context.getBean(NettyContext.class).onClose().block();
//        }
//    }
//
//    @Profile("default")
//    @Bean
//    public NettyContext nettyContext(ApplicationContext context) {
//        HttpHandler handler = WebHttpHandlerBuilder.applicationContext(context).build();
//        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(handler);
//        HttpServer httpServer = HttpServer.create("localhost", this.port);
//        return httpServer.newHandler(adapter).block();
//    }
//
//}

