package cpe_client.main;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.dansoftware.pdfdisplayer.PDFDisplayer;
import javafx.scene.layout.*;
import javax.net.ssl.*;
import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.*;


public class Mock_TestController {
    public static void execute(){
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    @Override
                    public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                            throws CertificateException {}

                    @Override
                    public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                            throws CertificateException {}

                }
        };

        SSLContext sc=null;
        try {
            sc = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier validHosts = new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        };
        // All hosts will be valid
        HttpsURLConnection.setDefaultHostnameVerifier(validHosts);
    }

    @FXML
    private Label welcomeText;

    @FXML
    public static ComboBox testDataSelector;

    @FXML
    protected void onSubmitButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    public ComboBox problemSelector;
    public Button othersSwitch;
    public SplitPane splitPane;
    public ScrollPane codingPane, othersPane;

    @FXML
    public void initialize() throws IOException {
        // test
        splitPane.getItems().removeAll(codingPane, othersPane);

    /* PDF Section */
        PDFDisplayer displayer = new PDFDisplayer();
        splitPane.getItems().addAll(displayer.toNode(), codingPane, othersPane);
        problemSelector.getItems().addAll("Problem 1", "Problem 2", "Problem 3", "Problem 4", "Problem 5", "Problem 6", "Problem 7");
        Mock_TestController.execute();
        problemSelector.setOnAction((e) -> {
            //System.out.println(problemSelector.getSelectionModel().getSelectedItem());
            String selected = problemSelector.getSelectionModel().getSelectedItem().toString();
            try {

                if (selected == "Problem 1") {
                    displayer.loadPDF(new URL("https://cpe.cse.nsysu.edu.tw/cpe/file/attendance/problemPdf/12908.pdf"));
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

    /* othersButton */
        othersSwitch.setOnAction((e) -> {
            if (splitPane.getItems().size() == 3){
                splitPane.getItems().remove(2);
            }else{
                splitPane.getItems().add(othersPane);
            }
        });
    }
}