package io.github.lyrric;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class App {

    private static String ukey ="85be376d0670f10bb8a77916bf774e53";
    private static String tokenId="fd70bf4f1aa6025d2990574cd975cc18";
    private static String productID="bad0b5f1-36e9-441a-b33c-017af055e87d";

    private static final AtomicBoolean status = new AtomicBoolean(false);

    static final ExecutorService pool = Executors.newFixedThreadPool(10);

    private static final String URL = "https://sapph5api.leqilucky.com/Sale/Task/SoHo";

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        /*System.out.println("请输入ukey：");
        ukey =  sc.nextLine().trim();*/
        System.out.println("请输入tokenId：");
        tokenId =  sc.nextLine().trim();
        System.out.println("请输入productID：");
        productID =  sc.nextLine().trim();
        System.out.println("按回车键开始抢购");
        sc.nextLine();

        Date startDate = DateUtil.parseDateTime("   2023-02-22 15:59:57").toJdkDate();
        Timer timer = new Timer(false);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(LocalDateTime.now()+"    开始抢购中......");
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
                    System.out.println(LocalDateTime.now()+"    抢购失败");
                }else{
                    System.out.println(LocalDateTime.now()+"    抢购成功");
                }
            }
        }, startDate);


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

        String params = String.format("uname=h5&ukey=%s&tokenid=%s&productID=%s&isFreeCard=true&isFirst=true&IsCfmdLimitArea=false&isFreeCardLimitRemind=false",
                ukey, tokenId, productID);
        HttpPost post = new HttpPost(URL + "?" + params);
        List<Header> headers = Config.HEADERS;

        post.setHeaders(headers.toArray(new Header[0]));
        try (CloseableHttpClient httpClient = HttpClients.createDefault()){
            System.out.println(Thread.currentThread().getId() + "-----" + LocalDateTime.now() + " 开始抢购");
            CloseableHttpResponse response = httpClient.execute(post);
            HttpEntity httpEntity = response.getEntity();
            String json =  EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
            JSONObject jsonObject = JSONObject.parseObject(json);
            if (jsonObject.containsKey("ErrMsg") && jsonObject.getString("ErrMsg") != null) {
                System.out.println(Thread.currentThread().getId() + "-----" + LocalDateTime.now() + " 抢购失败:" + jsonObject.getString("ErrMsg"));
            }else{
                System.out.println(Thread.currentThread().getId() + "-----" + LocalDateTime.now() + " 抢购成功！");
                status.set(true);
            }
        }
    }
}
