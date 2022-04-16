package cpe_client.autosignup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class account {

    public static String cookie="";

    public static String popLoginPageandGetCookie(){
        if(System.getProperty("os.name").equals("Mac OS X")){//better than Windows
            System.setProperty("webdriver.chrome.driver","./webdriver/chromedriver");
        }else{
            System.setProperty("webdriver.chrome.driver","./webdriver/chromedriver.exe");
        }

        WebDriver driver = new ChromeDriver();
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

        account.cookie=ret;

        return ret;

    }
}
