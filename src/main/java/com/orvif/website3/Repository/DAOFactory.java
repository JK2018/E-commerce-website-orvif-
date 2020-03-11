package com.orvif.website3.Repository;


import com.orvif.website3.service.MCrypt;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.StringReader;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;
import org.w3c.dom.Document;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.sql.DriverManager;

@Component
public class DAOFactory {



    public static DAOFactory getInstance() throws DAOConfigurationException {
        return new DAOFactory();
    }


    /**
     * Call execWebService(String param, boolean debug) with debug set to False
     *
     * @param param
     * @return
     */
    public Document execWebService(String param) {
        return this.execWebService(param, false);
    }



    public Document execWebService(String param, boolean debug) {
        try {
            if (debug) {
                Logger.getLogger(getClass().getName()).info("Calling... " + param);
            }
            StringBuilder response = new StringBuilder();
            MCrypt mcrypt = new MCrypt();
            String encrypted = MCrypt.bytesToHex(mcrypt.encrypt(param));
            param = "C=" + encrypted;
            URL url = new URL("http://intranet.orvif.fr/msg/index.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", String.valueOf(param.length()));
            conn.setReadTimeout(5 * 1000);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(param.getBytes());
            os.flush();
            os.close();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
            if (debug) {
                Logger.getLogger(getClass().getName()).info("Web service string result : " + response);
            }
            // result parsing
            DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            return  parser.parse(new InputSource(new StringReader(response.toString())));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not get web service result.");
        }
    }














}
