package cpe_client.uvacrawler;

import javax.net.ssl.SSLContext;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class account {

    private static String username="finalproject";
    private static String password="";//be sure to add after pull, and delete before push

    private static String cookie="";//b985b4592acb7c5112cce9e4729765d0=cookie

    public static HttpRequest.BodyPublisher ofFormData(Map<Object, Object> data) {
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


    public static String loginAndGetCookie(){
        try {
            CookieHandler.setDefault(new CookieManager());

            HttpCookie sessionCookie = new HttpCookie("b985b4592acb7c5112cce9e4729765d0", "-");
            sessionCookie.setPath("/");
            sessionCookie.setVersion(0);

            HttpClient httpClient=HttpClient.newBuilder().cookieHandler(CookieHandler.getDefault()).build();

            Map data = new HashMap<String, String>() {{
                put("username", account.username);
                put("passwd", account.password);
                put("op2","login");
                put("lang","english");
                put("force_session","1");
                put("return","B%3AaHR0cDovL29ubGluZWp1ZGdlLm9yZy9pbmRleC5waHA%2Fb3B0aW9uPWNvbV9jb21wcm9maWxlciZhbXA7SXRlbWlkPTM%3D");
                put("message","0");
                put("loginfrom","loginmodule");
                put("cbsecuritym3","cbm_1b4ccbde_015b0dc0_d0a940afbb6392a4c6243c1540f69f07");
                put("jbebcb78d08ee0af96b9269625b0636ca","1");
                put("Submit","Login");
            }};

            HttpRequest request= HttpRequest.newBuilder().uri(URI.create("https://onlinejudge.org/index.php?option=com_comprofiler&task=login")).header("Content-Type","application/x-www-form-urlencoded").POST(ofFormData(data)).build();
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
