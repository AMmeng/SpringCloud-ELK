package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SpringcloudConsumeKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudConsumeKafkaApplication.class, args);
    }

    @RestController
    public class ConsumerController {

        @Autowired
        HelloRemote HelloRemote;
        Logger logger = LoggerFactory.getLogger(SpringcloudConsumeKafkaApplication.class);
        @RequestMapping("/hello")
        public String index(String name) {
            logger.info("大家好我是"+name);
            return HelloRemote.hello(name);
        }
    }

    @FeignClient(name= "spring-cloud-producer")
    public interface HelloRemote {
        @RequestMapping(value = "/hello")
        String hello(@RequestParam(value = "name") String name);
    }

}
