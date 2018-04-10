package com.demo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/*
    使用window本地的kafka
    从队列中取出数据  发送给kafka

 */
public class DataSender implements  Runnable{


    private Logger logger=LoggerFactory.getLogger(DataSender.class);

    private BlockingQueue<String> queue;

    private KafkaProducer producer;

    public DataSender(BlockingQueue<String> queue) {
        this.queue = queue;
        Properties pro = new Properties();
        pro.setProperty("bootstrap.servers", "localhost:9092");

        pro.setProperty("enable.idempotence", "true");

        pro.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        pro.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<String,String>(pro);
    }

    @Override
    public void run() {

        while (true){
            try {
                //从队里中取出数据
                String json = queue.take();
                logger.info("从队列中取出host数据：{}",json);
                //数据发送给kafka
                Future<RecordMetadata> result = producer.send(new ProducerRecord("test", "localhost", json));

                RecordMetadata recordMetadata = result.get();

                long offset = recordMetadata.offset();

                int partition = recordMetadata.partition();

                String topic = recordMetadata.topic();

                logger.info("record save into {},offset:{},partition:{}",topic,offset,partition);


            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
