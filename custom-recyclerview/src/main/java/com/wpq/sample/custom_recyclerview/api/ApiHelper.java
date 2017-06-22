package com.wpq.sample.custom_recyclerview.api;

import com.wpq.sample.custom_recyclerview.bean.Girl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wpq
 * @version 1.0
 */
public class ApiHelper {

    /**
     * 解析妹子
     * @param response show.htm页面内容
     * @return
     */
    public static List<Girl> parseGirls(String response) {
        Document document = Jsoup.parse(response);
        Elements elements = document.select("div[class=img_single] > a");
        List<Girl> list = new ArrayList<>();
        Girl data;
        for (Element element : elements) {
            data = new Girl();
            data.setId(element.attr("href").substring(element.attr("href").lastIndexOf("/") + 1));
            data.setTitle(element.select("img").attr("title"));
            data.setUrl(element.select("img").attr("src"));
            list.add(data);
        }
        return list;
    }

}
