package com.demo;

import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) {

        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>(100);

        DataFetcher fetcher = new DataFetcher(queue);
        DataSender  sender = new DataSender(queue);

        new Thread(fetcher).start();
        new Thread(sender).start();

    }
}
