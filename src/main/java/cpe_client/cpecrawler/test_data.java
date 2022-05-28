package cpe_client.cpecrawler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.security.SecureRandom;
import java.util.*;
import org.apache.commons.text.StringEscapeUtils;

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
                (string[]) -> 那次考試的題目 ex. {"12911","11792"}
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
            res=res.replaceAll("[\\s]*([0-9]*?)\\:[[a-z][A-Z]['!?()]]*[\\s]*","$1\n");
            String[] ret=res.split("\n");
            return ret;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public static String getAcceptCode(String problemId){
        /*
            回傳 AC code
            args:
                (String) problemId-> 題號 ex "12908"
            return value:
                (string) -> AC code in c++ ex. "//uva12908\n#include <algorithm>\n#include <iostream>\n#include <vector>\n\nusing namespace std;\n\nint main() {\n    int input = 0;\n    vector<int> table;\n\n    for (int i = 0; i <= 20000; i++) {\n        table.push_back((i * (i + 1)) / 2);\n    }\n\n    while (cin >> input && input) {\n        vector<int>::iterator itTable = upper_bound(table.begin(), table.end(), input);\n        cout << *itTable - input << " " << itTable - table.begin() << endl;\n    }\n\n    return 0;\n}"
         */
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            HttpClient httpClient=HttpClient.newBuilder().sslContext(sslContext).build();
            HttpRequest request= HttpRequest.newBuilder().uri(URI.create("https://cpe.cse.nsysu.edu.tw/cpe/file/attendance/problemPdf/"+problemId+".php")).GET().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String res=response.body().toString().split("<pre class=\"prettyprint\">")[1].split("</pre>")[0];
            res=StringEscapeUtils.unescapeHtml4(res);
            return res;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public static String[][] getProblemTestCases(String problemId){
        /*
            回傳 官方測資
            args:
                (String) problemId-> 題號 ex "12908"
            return value:
                (string)[2][2] -> 測資,二維2*2 string array ex. {{"{input(case a)}","{output(case a)}"},{"{input(case b)}","{output(case b)}"}}
        */
        try {
            String[][] ret={{"{input(case a)}","{output(case a)}"},{"{input(case b)}","{output(case b)}"}};

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            HttpClient httpClient=HttpClient.newBuilder().sslContext(sslContext).build();
            HttpRequest request= HttpRequest.newBuilder().uri(URI.create("https://cpe.cse.nsysu.edu.tw/cpe/file/attendance/problemPdf/testData/uva"+problemId+"a.php")).GET().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String[] res=response.body().toString().split("<pre>");

            ret[0][0]=res[1].split("</pre>")[0];
            ret[0][1]=res[2].split("</pre>")[0];

            request= HttpRequest.newBuilder().uri(URI.create("https://cpe.cse.nsysu.edu.tw/cpe/file/attendance/problemPdf/testData/uva"+problemId+"b.php")).GET().build();
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            res=response.body().toString().split("<pre>");

            ret[1][0]=res[1].split("</pre>")[0];
            ret[1][1]=res[2].split("</pre>")[0];

            return ret;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

}
