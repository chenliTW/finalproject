package cpe_client.uvacrawler;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class account {

    private static String username="finalproject";
    private static String password="";//be sure to add after pull, and delete before push

    public static String cookie="";//b985b4592acb7c5112cce9e4729765d0=cookie

    public static HttpRequest.BodyPublisher ofFormData(Map<Object, Object> data) {
        // HTTP post form data 生成器
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }


    private static HashMap<String, String> getLoginFormData() throws IOException, InterruptedException {
        // 拿csrf token並生成表單
        //oh no , online judge has csrf protection or not(? it csrf token expire base on time but not request(?
        HttpClient httpClient=HttpClient.newHttpClient();
        HttpRequest request= HttpRequest.newBuilder().uri(URI.create("https://onlinejudge.org/")).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String res=response.body().toString();

        Matcher m3=Pattern.compile("name=\"cbsecuritym3\" value=\"(.*?)\"").matcher(res);
        m3.find();
        Matcher v1=Pattern.compile("name=\"cbsecuritym3\" value=\".*?\" />[\\s\\S]*?<input type=\"hidden\" name=\"(.*?)\"").matcher(res);
        v1.find();

        HashMap<String, String> data = new HashMap<String, String>() {{
            put("cbsecuritym3",m3.group(1));
            put(v1.group(1),"1");
            put("op2","login");
            put("lang","english");
            put("force_session","1");
            put("return","B%3AaHR0cDovL29ubGluZWp1ZGdlLm9yZy9pbmRleC5waHA%2Fb3B0aW9uPWNvbV9jb21wcm9maWxlciZhbXA7SXRlbWlkPTM%3D");
            put("message","0");
            put("loginfrom","loginmodule");
            put("Submit","Login");
        }};

        return data;
    }

    public static String loginAndGetCookie(){
        /*
             登入

            args:

            return value:
                (string) -> Cookie
         */
        try {
            CookieHandler.setDefault(new CookieManager());

            HttpCookie sessionCookie = new HttpCookie("b985b4592acb7c5112cce9e4729765d0", "-");
            sessionCookie.setPath("/");
            sessionCookie.setVersion(0);

            ((CookieManager) CookieHandler.getDefault()).getCookieStore().add(new URI("https://onlinejudge.org/"), sessionCookie);

            HttpClient httpClient=HttpClient.newBuilder().cookieHandler(CookieHandler.getDefault()).build();

            Map data = getLoginFormData();
            data.put("username", account.username);
            data.put("passwd", account.password);

            HttpRequest request= HttpRequest.newBuilder().uri(URI.create("https://onlinejudge.org/index.php?option=com_comprofiler&task=login"))
                    .header("Content-Type","application/x-www-form-urlencoded")
                    .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.74 Safari/537.36")
                    .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                    .POST(ofFormData(data)).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String res= String.valueOf(response.headers().firstValue("set-cookie")).split(";")[0].split("\\[")[1].split("=")[1];

            account.cookie=res;

            return res;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }

    }

}
