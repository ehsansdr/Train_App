package com.example.trainproject.base.Service;

import org.springframework.stereotype.Service;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Service
public class MessageService {
    // this class store the given value in the file that is given in this case and store and add that in the
    // file that give to him and set the last update in the top

    private static final String PROPERTIES_FILE = "src/main/resources/i18n/messages_fa.properties";

    public void updateMessage(String key, String value) {
        Properties properties = new Properties();
        try {
            // Load existing properties
            properties.load(getClass().getClassLoader().getResourceAsStream("i18n/messages_fa.properties"));

            // Add or update the property
            // if you get english that will not decoded but with persian or another language yes
            String encodedText = URLEncoder.encode(value, "UTF-8");
            properties.setProperty(key, encodedText);

            // Write back to the file
            try (FileOutputStream out = new FileOutputStream(PROPERTIES_FILE)) {
                properties.store(out, "Updated messages_fa.properties");
            }
            System.out.println("Message updated successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to decode the value for a given key
    public String getDecodedMessage(String key) {
        Properties properties = new Properties();
        String decodedValue = null;

        try {
            // Load existing properties file
            Reader reader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("i18n/messages_fa.properties"), StandardCharsets.UTF_8);
            properties.load(reader);

            // Retrieve and decode the value for the given key
            String encodedValue = properties.getProperty(key);

            if (encodedValue != null) {
                decodedValue = URLDecoder.decode(encodedValue, "UTF-8");
            } else {
                System.out.println("Key not found: " + key);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return decodedValue;
    }
}
