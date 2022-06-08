package cpe_client.main;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.dansoftware.pdfdisplayer.PDFDisplayer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javax.net.ssl.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.*;
import java.util.Scanner;

import org.asynchttpclient.HttpResponseStatus;
import org.fife.ui.rsyntaxtextarea.*;
import javafx.embed.swing.SwingNode;
import org.fife.ui.rtextarea.RTextScrollPane;

public class Mock_TestController {
    String nowProblem;
    String[] problems;
    String[][] testcases;
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
    public ComboBox problemSelector, examdateSelector, testCaseSelector, languageSelector;
    public Button othersSwitch, submmitButton;
    public SplitPane splitPane;
    public VBox othersBox, codingPane;
    public SwingNode codingPaneSwingNode;
    public TextArea inputBox, outputBox;
    public Label judgeResult;

    @FXML
    public void initialize() throws IOException {
    /* Init */
        splitPane.getItems().removeAll(codingPane, othersBox);
        problemSelector.setDisable(true);
        testCaseSelector.setDisable(true);
        inputBox.setDisable(true);
    /* PDF Section */
        PDFDisplayer displayer = new PDFDisplayer();
        splitPane.getItems().addAll(displayer.toNode(), codingPane, othersBox);
        String[] examdates = cpe_client.cpecrawler.test_data.getHistoryTestDates();

        for (int i = examdates.length-2; i >= examdates.length-16; i--){
            examdateSelector.getItems().add(examdates[i].substring(0,examdates[i].length()-1));
        }

        problemSelector.getItems().addAll("Problem 1", "Problem 2", "Problem 3", "Problem 4", "Problem 5", "Problem 6", "Problem 7");
        Mock_TestController.execute();

    /* 選擇場次 */
        examdateSelector.setOnAction((e) -> {
            problems = cpe_client.cpecrawler.test_data.getTestProblems((String) examdateSelector.getValue());
            examdateSelector.setDisable(true);
            problemSelector.setDisable(false);
        });

    /* 選擇題目*/

        problemSelector.setOnAction((e) -> {
            inputBox.setDisable(false);
            //System.out.println(problemSelector.getSelectionModel().getSelectedItem());
            String selected = problemSelector.getSelectionModel().getSelectedItem().toString();
            try {
                if (selected == "Problem 1") {
                    displayer.loadPDF(new URL("https://cpe.cse.nsysu.edu.tw/cpe/file/attendance/problemPdf/"+problems[0]+".pdf"));
                    nowProblem = problems[0];
                } else if (selected == "Problem 2") {
                    displayer.loadPDF(new URL("https://cpe.cse.nsysu.edu.tw/cpe/file/attendance/problemPdf/"+problems[1]+".pdf"));
                    nowProblem = problems[1];
                } else if (selected == "Problem 3") {
                    displayer.loadPDF(new URL("https://cpe.cse.nsysu.edu.tw/cpe/file/attendance/problemPdf/"+problems[2]+".pdf"));
                    nowProblem = problems[2];
                } else if (selected == "Problem 4") {
                    displayer.loadPDF(new URL("https://cpe.cse.nsysu.edu.tw/cpe/file/attendance/problemPdf/"+problems[3]+".pdf"));
                    nowProblem = problems[3];
                } else if (selected == "Problem 5") {
                    displayer.loadPDF(new URL("https://cpe.cse.nsysu.edu.tw/cpe/file/attendance/problemPdf/"+problems[4]+".pdf"));
                    nowProblem = problems[4];
                } else if (selected == "Problem 6") {
                    displayer.loadPDF(new URL("https://cpe.cse.nsysu.edu.tw/cpe/file/attendance/problemPdf/"+problems[5]+".pdf"));
                    nowProblem = problems[5];
                } else if (selected == "Problem 7") {
                    displayer.loadPDF(new URL("https://cpe.cse.nsysu.edu.tw/cpe/file/attendance/problemPdf/"+problems[6]+".pdf"));
                    nowProblem = problems[6];
                }
                testCaseSelector.setDisable(false);
                testCaseSelector.setValue(null);
                inputBox.setText("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    /* 選擇測資 */


        testCaseSelector.getItems().addAll("自訂測資", "官方測資 A", "官方測資 B");
        testCaseSelector.setOnAction((e) -> {
            testcases = cpe_client.cpecrawler.test_data.getProblemTestCases(nowProblem);
            if (testCaseSelector.getValue().equals("自訂測資")){
                inputBox.setEditable(true);
                inputBox.setText("");
            } else if (testCaseSelector.getValue().equals("官方測資 A")){
                inputBox.setEditable(false);
                inputBox.setText(testcases[0][0]);
            } else if (testCaseSelector.getValue().equals("官方測資 B")){
                inputBox.setEditable(false);
                inputBox.setText(testcases[1][0]);
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
        /* Submmit Code and Compile */
        languageSelector.getItems().addAll("C++", "Java", "Python");

        submmitButton.setOnAction((e) -> {
            String correctOutput, myOutput;

            if (testCaseSelector.getValue().equals("官方測資 A")){
                correctOutput = testcases[0][1];
            }else if (testCaseSelector.getValue().equals("官方測資 B")){
                correctOutput = testcases[1][1];
            }else{
                correctOutput = cpe_client.localjudge.executer.execute(cpe_client.cpecrawler.test_data.
                        getAcceptCode(nowProblem), "C++", inputBox.getText());
            }
            myOutput = cpe_client.localjudge.executer.execute(textArea.getText(), languageSelector.getValue().toString(), inputBox.getText());
            outputBox.setText("Your Output:\n\n" + myOutput + "\n\nCorrectOutput:\n" + correctOutput);
            // Compare
            Scanner correctScanner, myScanner;
            correctScanner = new Scanner(correctOutput);
            myScanner = new Scanner(myOutput);
            boolean isAC=true;
            if (myOutput.indexOf("CE/RE")==-1) {
                while (correctScanner.hasNextLine() || myScanner.hasNextLine()) {
                    if (!correctScanner.hasNextLine() || !myScanner.hasNextLine() ||
                    !correctScanner.nextLine().equals(myScanner.nextLine())) {
                       // System.out.println("Your Output:\n" + myOutput + "\n\nCorrectOutput:\n" + correctOutput);
                        isAC = false;
                        break;
                    }
                }
                if (isAC) {
                    judgeResult.setText("AC");
                } else {
                    if (myOutput.indexOf("TLE") == 0) {
                        judgeResult.setText("TLE");
                    }else{
                        judgeResult.setText("WA");
                    }
                }
            }else{
                judgeResult.setText("CE/RE");
            }

            JOptionPane.showMessageDialog(null, "送出完成");
        });
    }
}