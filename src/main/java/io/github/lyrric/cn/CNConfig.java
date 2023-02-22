package io.github.lyrric.cn;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CNConfig {

    public static final List<Header> HEADERS = new ArrayList<Header>();
    public static final Map<String,String> HEADERS1 = new HashMap<>();


    static {
        HEADERS.add(new BasicHeader("Host", "zqapi.chennew.com"));
        HEADERS.add(new BasicHeader("Accept", "application/json, text/plain, */*"));
        HEADERS.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 NetType/WIFI MicroMessenger/7.0.20.1781(0x6700143B) WindowsWechat(0x6307062c)"));
        HEADERS.add(new BasicHeader("Content-Type", "application/json"));
        HEADERS.add(new BasicHeader("referer", "https://servicewechat.com/wxbf2944f85e9a0d79/72/page-frame.html"));
        HEADERS.add(new BasicHeader("xweb_xhr", "1"));

        HEADERS1.put("Host", "zqapi.chennew.com");
        HEADERS1.put ("Accept", "application/json, text/plain, */*");
        HEADERS1.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 NetType/WIFI MicroMessenger/7.0.20.1781(0x6700143B) WindowsWechat(0x6307062c)");
        HEADERS1.put ("Content-Type", "application/json");
        HEADERS1.put ("referer", "https://servicewechat.com/wxbf2944f85e9a0d79/72/page-frame.html");
        HEADERS1.put ("xweb_xhr", "1");
    }
}
