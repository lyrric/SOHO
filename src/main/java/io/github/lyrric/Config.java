package io.github.lyrric;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.ArrayList;
import java.util.List;

public class Config {

    public static final List<Header> HEADERS = new ArrayList<Header>();


    static {
        HEADERS.add(new BasicHeader("Host", "sapph5api.leqilucky.com"));
        HEADERS.add(new BasicHeader("Accept", "application/json, text/plain, */*"));
        HEADERS.add(new BasicHeader("Origin", "https://sapph5.lqlucky.com"));
        HEADERS.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 NetType/WIFI MicroMessenger/7.0.20.1781(0x6700143B) WindowsWechat(0x6307062c)"));
        HEADERS.add(new BasicHeader("Content-Type", "application/x-www-form-urlencoded"));

    }
}
