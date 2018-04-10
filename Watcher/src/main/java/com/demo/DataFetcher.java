package com.demo;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

import java.util.concurrent.LinkedBlockingQueue;

/*
采集主机上的数据 采集完以后发给阻塞队列

 */
public class DataFetcher implements Runnable {


    private Logger logger = LoggerFactory.getLogger(DataFetcher.class);

    private BlockingQueue<String> queue;

    public DataFetcher(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        while (true) {
            //

            try {
                String json = getHostData();
                Thread.sleep(5000);
                logger.info("get host data: {}", json);
                //发送数据到队列
                queue.put(json);
            } catch (InterruptedException e) {
                //e.printStackTrace();
                logger.error("error:",e);
            }


        }
    }

    private String getHostData() {
        //模拟数据的产生
        HashMap<String, String> map = new HashMap<String, String>();
        //Runtime runtime = new Runtime();
        Random random = new Random();
        map.put("cpu", random.nextInt(100)+"");
        map.put("memory", random.nextInt(100)+"");
        Gson gson = new Gson();
        //利用gson框架把map变成json
        return gson.toJson(map);

    }

    public static void main(String[] args) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("ls");
        process.getInputStream();
    }
}
