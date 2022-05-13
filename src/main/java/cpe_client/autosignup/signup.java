package cpe_client.autosignup;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.SecureRandom;


public class signup {

    private static TrustManager[] trustAllCerts = new TrustManager[]{
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

    public static void signup() {
        /*
            自動用引導使用者登入後拿到的cookie報名cpe (目前只有 東華大學 資工 報名 東華大學場)
         */
        try {
            CookieHandler.setDefault(new CookieManager());

            HttpCookie sessionCookie = new HttpCookie("ci_session", account.cookie);

            sessionCookie.setPath("/");
            sessionCookie.setVersion(0);

            ((CookieManager) CookieHandler.getDefault()).getCookieStore().add(new URI("https://cpe.cse.nsysu.edu.tw/"),
                    sessionCookie);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            HttpClient httpClient=HttpClient.newBuilder().cookieHandler(CookieHandler.getDefault()).sslContext(sslContext).build();//.cookieHandler(CookieHandler.getDefault()).build();

            String data="isSend=yes&mySchoolID=45&myDepartment=%E8%B3%87%E8%A8%8A%E5%B7%A5%E7%A8%8B%E5%AD%B8%E7%B3%BB&myGrade=111&optionsRadios1=1&site=21&yesExam=%E5%A0%B1%E5%90%8D";

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://cpe.cse.nsysu.edu.tw/cpe/newest"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.41 Safari/537.36")
                    .POST(HttpRequest.BodyPublishers.ofString(data)).build();
            while(true) {
                try {
                    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    break;
                }catch (Exception E){

                }
            }
        }catch (Exception E){
            System.out.println(E);
        }
    }

}
