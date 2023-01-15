package com.joannava.kafka.katas;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
    
    public static Properties getProperties() {

        String rootPath = Thread.currentThread()
                .getContextClassLoader()
                .getResource("app.properties")
                .getPath();

        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(rootPath));
        } catch (IOException e) {
        
        }
        return appProps;
    }
}
