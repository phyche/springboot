package com.example.springboot.crawler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * 网页爬虫
 */
public class URLDemo {

    public static void main(String args[]) {
         //确定爬取的网页地址，此处为当当网搜机械表显示的网页
         //网址为        http://search.dangdang.com/?key=%BB%FA%D0%B5%B1%ED&act=input
         String strurl="http://search.dangdang.com/?key=%BB%FA%D0%B5%B1%ED&act=input";
         //建立url爬取核心对象
         try {
             URL url=new URL(strurl);
             //通过url建立与网页的连接
             URLConnection conn=url.openConnection();
             //通过链接取得网页返回的数据
             InputStream is=conn.getInputStream();

             System.out.println(conn.getContentEncoding());
             //一般按行读取网页数据，并进行内容分析
             //因此用BufferedReader和InputStreamReader把字节流转化为字符流的缓冲流
             //进行转换时，需要处理编码格式问题
             BufferedReader br=new BufferedReader(new InputStreamReader(is,"UTF-8"));

             //按行读取并打印
             String line=null;
             while((line=br.readLine())!=null){
                     System.out.println(line);
             }

             br.close();
         } catch (Exception e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
    }
}
