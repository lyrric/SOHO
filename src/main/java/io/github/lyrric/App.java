package io.github.lyrric;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class App {

    private static String ukey ="85be376d0670f10bb8a77916bf774e53";
    private static String tokenId="3bb5594fb56bf13512c7db5579ad803a";
    private static String productID="30660e54-e048-409e-bb21-2d9d96381b84";

    private static final AtomicBoolean status = new AtomicBoolean(false);

    static final ExecutorService pool = Executors.newFixedThreadPool(5);

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

        System.out.println("开始抢购中......");
        MyTask task = new MyTask();
        for (int i = 0; i < 5; i++) {
            pool.submit(task);
        }
        pool.shutdown();
        if (!status.get()) {
            System.out.println("抢购失败");
        }
    }


    static class MyTask implements Runnable {
        @Override
        public void run() {
            long start = System.currentTimeMillis();
            while (!status.get() && System.currentTimeMillis() - start < 5000) {
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
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(post);
        HttpEntity httpEntity = response.getEntity();
        String json =  EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (jsonObject.containsKey("ErrMsg") && jsonObject.getString("ErrMsg") != null) {
            System.out.println(LocalDateTime.now() + " 抢购失败:" + jsonObject.getString("ErrMsg"));
        }else{
            System.out.println(LocalDateTime.now() + " 抢购成功！");
            status.set(true);
        }
    }
}
