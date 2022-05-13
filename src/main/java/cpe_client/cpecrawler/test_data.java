package cpe_client.cpecrawler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.security.SecureRandom;
import java.util.*;

public class test_data {

    private static TrustManager[] trustAllCerts = new TrustManager[]{
            /*
            相信一切的trust
            mannager,當Httpclient用它,Httpclient就不會噴不信任cert的error
             */
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
    };

    public static String[] getHistoryTestDates(){
        /*
            回傳歷史場次的日期
            args:

            return value:
                (string[]) -> 歷屆考試時間 ex. {"2020-10-20","2021-12-21"}
         */
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            HttpClient httpClient=HttpClient.newBuilder().sslContext(sslContext).build();
            HttpRequest request= HttpRequest.newBuilder().uri(URI.create("https://cpe.cse.nsysu.edu.tw/cpe/test_data")).GET().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String res=response.body().toString().split("<ul>")[1].split("</ul>")[0].split("請選擇考試時間")[1];
            res=res.replaceAll("<li><a href=\".*?\">(.*?)</a></li>","$1").replaceAll(" *?","").replaceAll("\t","").replaceAll("\n\n","\n");
            String[] ret=Arrays.copyOfRange(res.split("\n"),1,res.split("\n").length);
            return ret;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }

    }

    public static String[] getTestProblems(String testDate){
        /*
        回傳歷史場次的日期
            args:
                (String) testDate-> ex "2021-12-21"
            return value:
                (string[]) -> 那次考試的題目 ex. {"12911: Subset sum","11792: Krochanska is Here!"}
         */
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            HttpClient httpClient=HttpClient.newBuilder().sslContext(sslContext).build();
            HttpRequest request= HttpRequest.newBuilder().uri(URI.create("https://cpe.cse.nsysu.edu.tw/cpe/test_data/"+testDate)).GET().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String res=response.body().toString().split("<th><center>考生答對率 \\(F\\)</center></th>")[1].split("</table>")[0];
            res=res.replaceAll("<tr>[\\s\\S]*?pdf\">([\\s\\S]*?)<\\/a>[\\s\\S]*?<\\/tr>","$1");
            res=res.replaceAll(" ","");
            res=res.replaceAll("\t","");
            res=res.replaceAll("[\\s]*([0-9]*?\\:[[a-z][A-Z]]*)[\\s]*","$1\n");
            String[] ret=res.split("\n");
            return ret;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

}
