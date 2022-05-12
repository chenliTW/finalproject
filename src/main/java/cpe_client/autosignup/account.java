package cpe_client.autosignup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
public class account {

    public static String cookie="";//ci_session

    public static String popLoginPageandGetCookie(){
        ChromeOptions options=new ChromeOptions();
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.41 Safari/537.36");

        if(System.getProperty("os.name").equals("Mac OS X")){//better than Windows
            System.setProperty("webdriver.chrome.driver","./webdriver/chromedriver");
        }else{
            System.setProperty("webdriver.chrome.driver","./webdriver/chromedriver.exe");
        }

        WebDriver driver = new ChromeDriver(options);
        driver.get("https://cpe.cse.nsysu.edu.tw/cpe/");

        while (true){

            if(driver.getCurrentUrl().equals("https://cpe.cse.nsysu.edu.tw/cpe/newest")){
                break;
            }
            try {
                Thread.sleep(100);
            }catch (Exception e){

            }
        }

        String ret=driver.manage().getCookies().toString();

        driver.close();

        account.cookie=ret.split("; ")[0].split("ci_session=")[1];
        return account.cookie;

    }
}
