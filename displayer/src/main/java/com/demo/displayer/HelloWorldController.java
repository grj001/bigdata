package com.demo.displayer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ExecutionException;

@Controller
public class HelloWorldController {
    private Logger logger = LoggerFactory.getLogger(HelloWorldController.class);
    @Autowired
    private KafkaTemplate kafkaTemplate;


    //用来接受最新的数据
    private String lastestData="";
    @RequestMapping("/hello")
    @ResponseBody
    String hello(){
        return "hello world ";

    }
    @RequestMapping("/data")
    @ResponseBody
    String getData(){
        return lastestData;

    }


    @RequestMapping("/refresh")
    @ResponseBody
    String intoJsp(){
        return "refresh.jsp";

    }


    @RequestMapping("/send/{value}")
    @ResponseBody
    void send(@PathVariable String value) throws ExecutionException, InterruptedException {
        logger.info("ready to send {}",value);

        kafkaTemplate.send("test", "web", value);

    }





    @KafkaListener(topics = {"test"})
    void listen(ConsumerRecord<String,String> record){
        logger.info("record received :{}",record.value());
        //更新最新的数据
        lastestData=record.value();
    }


}
