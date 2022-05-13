module cpe_client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.net.http;
    requires org.seleniumhq.selenium.api;
    requires org.seleniumhq.selenium.chrome_driver;
    requires org.apache.commons.text;

    opens cpe_client.main to javafx.fxml;
    exports cpe_client.main;
}