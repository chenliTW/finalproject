package cpe_client.main;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.dansoftware.pdfdisplayer.PDFDisplayer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javax.net.ssl.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.*;
import org.fife.ui.rsyntaxtextarea.*;
import javafx.embed.swing.SwingNode;
import org.fife.ui.rtextarea.RTextScrollPane;

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
    public ComboBox problemSelector, examdateSelector;
    public Button othersSwitch;
    public SplitPane splitPane;
    public VBox othersBox, codingPane;
    public SwingNode codingPaneSwingNode;
    @FXML
    public void initialize() throws IOException {
        // test
        splitPane.getItems().removeAll(codingPane, othersBox);

    /* PDF Section */
        PDFDisplayer displayer = new PDFDisplayer();
        splitPane.getItems().addAll(displayer.toNode(), codingPane, othersBox);
        String[] examdates = cpe_client.cpecrawler.test_data.getHistoryTestDates();
        String[] problems = new String[7];
        for (int i = examdates.length-2; i >= examdates.length-16; i--){
            examdateSelector.getItems().add(examdates[i].substring(0,examdates[i].length()-1));
        }

        problemSelector.getItems().addAll("Problem 1", "Problem 2", "Problem 3", "Problem 4", "Problem 5", "Problem 6", "Problem 7");
        Mock_TestController.execute();
        examdateSelector.setOnAction((e) -> {
            String[] tmp = cpe_client.cpecrawler.test_data.getTestProblems((String) examdateSelector.getValue());
            for (int i = 0; i < 7; i++){
                problems[i] = tmp[i];
                //System.out.println(problems[i]);
            }
            examdateSelector.setDisable(true);
        });

        problemSelector.setOnAction((e) -> {
            //System.out.println(problemSelector.getSelectionModel().getSelectedItem());
            String selected = problemSelector.getSelectionModel().getSelectedItem().toString();
            try {
                if (selected == "Problem 1") {
                    displayer.loadPDF(new URL("https://cpe.cse.nsysu.edu.tw/cpe/file/attendance/problemPdf/"+problems[0]+".pdf"));
                } else if (selected == "Problem 2") {
                    displayer.loadPDF(new URL("https://cpe.cse.nsysu.edu.tw/cpe/file/attendance/problemPdf/"+problems[1]+".pdf"));
                } else if (selected == "Problem 3") {
                    displayer.loadPDF(new URL("https://cpe.cse.nsysu.edu.tw/cpe/file/attendance/problemPdf/"+problems[2]+".pdf"));
                } else if (selected == "Problem 4") {
                    displayer.loadPDF(new URL("https://cpe.cse.nsysu.edu.tw/cpe/file/attendance/problemPdf/"+problems[3]+".pdf"));
                } else if (selected == "Problem 5") {
                    displayer.loadPDF(new URL("https://cpe.cse.nsysu.edu.tw/cpe/file/attendance/problemPdf/"+problems[4]+".pdf"));
                } else if (selected == "Problem 6") {
                    displayer.loadPDF(new URL("https://cpe.cse.nsysu.edu.tw/cpe/file/attendance/problemPdf/"+problems[5]+".pdf"));
                } else if (selected == "Problem 7") {
                    displayer.loadPDF(new URL("https://cpe.cse.nsysu.edu.tw/cpe/file/attendance/problemPdf/"+problems[6]+".pdf"));
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
                splitPane.getItems().add(othersBox);
            }
        });

    /* coding area */
        JPanel cp = new JPanel(new BorderLayout());
        RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
        RTextScrollPane sp = new RTextScrollPane(textArea);
        //textArea.setCodeFoldingEnabled(true);
        cp.add(sp);
        codingPaneSwingNode.setContent(cp);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);

    }
}