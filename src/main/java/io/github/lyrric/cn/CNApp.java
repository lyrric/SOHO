package io.github.lyrric.cn;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class CNApp {

    private static String authorization="bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJUb2tlbkRhdGEiOiJ7XCJVc2VySWRcIjoxMTk3NDJ9IiwiZXhwIjoxNjkyNjA1OTQ1LCJpc3MiOiJjaGVubmV3LmNvbSIsImF1ZCI6ImNoZW5uZXcuY29tIn0.l_M3Nq63ExmUhTCpiKM_GXoRRWZcyTCMdzbB91N8gxE";
    private static String sn="5827920230222151657";

    private static final AtomicBoolean status = new AtomicBoolean(false);

    static final ExecutorService pool = Executors.newFixedThreadPool(10);

    private static final String URL = "https://zqapi.chennew.com/api/Qianggou/Task";

    public static void main(String[] args) throws InterruptedException, IOException {
        /**
         * User-MEId: 16770522303652305146
         * Authorization: bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJUb2tlbkRhdGEiOiJ7XCJVc2VySWRcIjoxMTk3NDJ9IiwiZXhwIjoxNjkyNjA1OTQ1LCJpc3MiOiJjaGVubmV3LmNvbSIsImF1ZCI6ImNoZW5uZXcuY29tIn0.l_M3Nq63ExmUhTCpiKM_GXoRRWZcyTCMdzbB91N8gxE
         */
        Scanner sc = new Scanner(System.in);
        System.out.println("Authorization：");
        authorization =  sc.nextLine().trim();
        System.out.println("请输入商品sn：");
        sn =  sc.nextLine().trim();
        System.out.println("按回车键开始抢购");
        sc.nextLine();
        CNConfig.HEADERS1.put("Authorization", authorization);
        CNConfig.HEADERS1.put("User-MEId", "16770522303652305146");
        start();
   /*     Date startDate = DateUtil.parseDateTime("   2023-02-22 15:59:57").toJdkDate();
        Timer timer = new Timer(false);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                start();
            }
        }, startDate);*/


    }


    private static void start(){
        System.out.println(LocalDateTime.now()+"-----开始抢购中......");
        MyTask task = new MyTask();
        for (int i = 0; i < 10; i++) {
            pool.submit(task);
        }
        pool.shutdown();
        try {
            pool.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (!status.get()) {
            System.out.println(LocalDateTime.now()+"-----抢购失败");
        }else{
            System.out.println(LocalDateTime.now()+"-----抢购成功");
        }
    }

    static class MyTask implements Runnable {
        @Override
        public void run() {
            long start = System.currentTimeMillis();
            while (!status.get() && System.currentTimeMillis() - start < 6000) {
                try {
                    post();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void post() throws IOException {

        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("sn", sn);
        reqMap.put("source", 1);
        HttpResponse execute = HttpUtil.createPost(URL).body(JSONObject.toJSONString(reqMap)).addHeaders(CNConfig.HEADERS1).execute();
        String body = execute.body();
        JSONObject jsonObject = JSONObject.parseObject(body);
        System.out.println(body);
        if (jsonObject.getIntValue("code") != 1000) {
            System.out.println(Thread.currentThread().getId() + "-----" + LocalDateTime.now() + " 抢购失败:" + jsonObject.getString("msg"));
        } else {
            System.out.println(Thread.currentThread().getId() + "-----" + LocalDateTime.now() + " 抢购成功！");
            status.set(true);
        }
    }
}
