package com.mobiledev.money.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonHelper {

    private static String getUrlContent(String url, int timeout) {
        StringBuilder content = new StringBuilder();
        HttpURLConnection http = null;
        
        try {
            http = (HttpURLConnection) (new URL(url)).openConnection();
            http.setRequestMethod("GET");
            http.setConnectTimeout(timeout);
            
            int status = http.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    // Read response to string
                    BufferedReader bufferReader = new BufferedReader(new InputStreamReader(http.getInputStream()));
                    String line;
                    
                    while ((line = bufferReader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    
                    bufferReader.close();
            }

        } catch (MalformedURLException ex) { 
            Logger.getLogger(JsonHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JsonHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (http != null) {
                try {
                    http.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(JsonHelper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    
        return content.toString();
    }
    
    public static Object getJson(String url, int timeout) {
        Object json = null;
        
        try {
            String jsonString = getUrlContent(url, timeout);
            json = new JSONParser().parse(jsonString);
        } catch (ParseException ex) {
            Logger.getLogger(JsonHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return json;
    }
    
    public static Object getOrderedJson(String url, int timeout) {
        Object json = null;
        
        try {
            String jsonString = getUrlContent(url, timeout);
            
            ContainerFactory orderedKeyFactory = new ContainerFactory() {
                @Override
                public Map createObjectContainer() {
                    return new LinkedHashMap();
                }
                
                @Override
                public List creatArrayContainer() {
                    return new LinkedList(); //To change body of generated methods, choose Tools | Templates.
                }
                
            };
            
            json = new JSONParser().parse(jsonString, orderedKeyFactory);
        } catch (ParseException ex) {
            Logger.getLogger(JsonHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return json;
    }
    
    public static String getJsonString(Object object) throws JsonProcessingException {
        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = writer.writeValueAsString(object);

        return json;
    }
}
