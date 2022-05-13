package cpe_client.main;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import com.dansoftware.pdfdisplayer.PDFDisplayer;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.*;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpClient;
import java.security.SecureRandom;
import java.util.ResourceBundle;

public class Mock_TestController {
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

    @FXML
    private Label welcomeText;

    @FXML
    public static ComboBox testDataSelector;

    @FXML
    protected void onSubmitButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    public AnchorPane masterPane;
    public ComboBox problemSelector;

    @FXML
    public void initialize() throws IOException {
       // try {
         //   SSLContext sslContext = SSLContext.getInstance("TLS");
         //   sslContext.init(null, trustAllCerts, new SecureRandom());
         //   HttpClient httpClient = HttpClient.newBuilder().sslContext(sslContext).build();
            PDFDisplayer displayer = new PDFDisplayer();
            masterPane.getChildren().add(displayer.toNode());
            problemSelector.getItems().addAll("Problem 1", "Problem 2", "Problem 3", "Problem 4", "Problem 5", "Problem 6", "Problem 7");
            problemSelector.setOnAction((e) -> {
                //System.out.println(problemSelector.getSelectionModel().getSelectedItem());
                String selected = problemSelector.getSelectionModel().getSelectedItem().toString();
                try {
                    if (selected == "Problem 1") {
                        displayer.loadPDF(new URL("https://onlinejudge.org/external/129/12908.pdf"));
                    } else if (selected == "Problem 2") {
                        displayer.loadPDF(new URL("https://cpe.cse.nsysu.edu.tw/cpe/file/attendance/problemPdf/10642.pdf"));
                    } else if (selected == "Problem 3") {
                        displayer.loadPDF(new URL("https://cpe.cse.nsysu.edu.tw/cpe/file/attendance/problemPdf/13171.pdf"));
                    } else if (selected == "Problem 4") {
                       // displayer.loadPDF(new URL(""));
                    } else if (selected == "Problem 5") {

                    } else if (selected == "Problem 6") {

                    } else if (selected == "Problem 7") {

                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            //cpe_client.cpecrawler.test_data.getHistoryTestDates();
       // } catch(Exception e){
       //     System.out.println(e);
       // }
    }
}