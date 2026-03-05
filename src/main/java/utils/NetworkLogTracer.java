package utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.logging.LogEntry;
import java.util.List;
import java.util.stream.Collectors;

public class NetworkLogTracer {

    private AndroidDriver driver;

    public NetworkLogTracer(AndroidDriver driver) {
        this.driver = driver;
    }

    /**
     * Filters the Logcat for common networking keywords (HTTP, GET, POST, API)
     * Note: AccuWeather uses OkHttp; if they have logging enabled, it will appear here.
     */
    public void printNetworkTraffic() {
        System.out.println("--- STARTING NETWORK TRACE ---");

        // Retrieve the latest log entries from the device
        List<LogEntry> logEntries = driver.manage().logs().get("logcat").getAll();

        // Filter logs for networking activities
        List<String> networkLogs = logEntries.stream()
                .map(LogEntry::getMessage)
                .filter(msg -> msg.contains("HTTP") ||
                        msg.contains("api.accuweather.com") ||
                        msg.contains("GET") ||
                        msg.contains("POST"))
                .collect(Collectors.toList());

        if (networkLogs.isEmpty()) {
            System.out.println("No direct API logs found. Note: Production apps often hide logs.");
        } else {
            networkLogs.forEach(log -> System.out.println("[NETWORK LOG]: " + log));
        }

        System.out.println("--- END OF NETWORK TRACE ---");
    }
}