package cpe_client.uvacrawler;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class oj {

    public static String submitCodeAndReturnId(String code,String problemId,String language){
        /*
            !!!CALL 之前要先 CALL login!!!
            傳code到oj
            args:
                (string)code->程式碼 ex. "a=input()\nprint(a)"
                (string)problemId->題目Id ex. "12908"
                (string)language->要judge的語言 ex. "c++"
            return value:
                (string) -> OJ Submit流水號(Id)
         */
        String languageId="";

        if(language.equals("C++")){
            languageId="3";
        }else if(language.equals("Java")){
            languageId="2";
        }else if(language.equals("Python")){
            languageId="6";
        }

        try {
            CookieHandler.setDefault(new CookieManager());
            HttpCookie sessionCookie = new HttpCookie("b985b4592acb7c5112cce9e4729765d0", account.cookie);
            sessionCookie.setPath("/");
            sessionCookie.setVersion(0);

            ((CookieManager) CookieHandler.getDefault()).getCookieStore().add(new URI("https://onlinejudge.org/"),
                    sessionCookie);

            HttpClient httpClient=HttpClient.newBuilder().cookieHandler(CookieHandler.getDefault()).build();


            MultiPartBodyPublisher publisher = new MultiPartBodyPublisher()
                    .addPart("problemid", "")
                    .addPart("category","")
                    .addPart("localid",problemId)
                    .addPart("language",languageId)
                    .addPart("code",code);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=25&page=save_submission"))
                    .header("Content-Type", "multipart/form-data; boundary=" + publisher.getBoundary())
                    .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.74 Safari/537.36")
                    .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                    .POST(publisher.build())
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            String retID=response.headers().toString().split("ID.")[1].split("\\]")[0];

            return retID;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }

    }

    public static String getResultById(String Id){
        /*
            !!!CALL 之前要先 CALL login!!!
            拿judge結果
            args:
                (string)Id -> OJ Submit流水號(Id)
            return value:
                (string) -> 結果 ex. "AC"/"queue"
         */

        try {
            CookieHandler.setDefault(new CookieManager());
            HttpCookie sessionCookie = new HttpCookie("b985b4592acb7c5112cce9e4729765d0", account.cookie);
            sessionCookie.setPath("/");
            sessionCookie.setVersion(0);

            ((CookieManager) CookieHandler.getDefault()).getCookieStore().add(new URI("https://onlinejudge.org/"),
                    sessionCookie);

            HttpClient httpClient=HttpClient.newBuilder().cookieHandler(CookieHandler.getDefault()).build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=9"))
                    .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.74 Safari/537.36")
                    .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            String result=response.body().toString().split(Id)[1].split("tr")[0].split("<td>")[2].split("</td>")[0];

            return result;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

}
