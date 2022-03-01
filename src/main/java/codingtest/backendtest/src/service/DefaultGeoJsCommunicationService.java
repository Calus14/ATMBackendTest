package codingtest.backendtest.src.service;

import codingtest.backendtest.src.domain.dto.GeoData;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * Default implementation that calls to geojs.io
 */
@Slf4j
@Service
public class DefaultGeoJsCommunicationService implements GeoJsCommunicationService{

    final String geoJsUrl = "https://get.geojs.io/v1/ip/geo/";

    @Override
    public GeoData getGeodataFromIP(String IPAddress) {
        log.info("Attempting to get the GeoData from GeoJs");
        GeoData ipAddressGeoData = null;
        HttpURLConnection con;
        String responseBody = "";
        try {
            URL url = new URL(geoJsUrl + IPAddress);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            // Executes the Http call
            log.info("Trying to connect to " + url.toString());
            int status = con.getResponseCode();
            log.info("Got status code of " + status);
            // If its not a 200 throw an error
            if (status / 100 != 2) {
                throw new Exception("Failed to get a 200 from geojs.io");
            }

            // Java is slow this way but we will need to read the input stream and add that to a single string
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseBody += inputLine;
            }
            log.info("responseBody is " + responseBody);
            // Another java thing is to keep things clean
            in.close();
            con.disconnect();
        } catch (Exception e) {
            log.error("Failed to create URL or execute the request: " + e.getMessage());
            return null;
        }

        try {
            ipAddressGeoData = new Gson().fromJson(responseBody, GeoData.class);
            return ipAddressGeoData;
        } catch (Exception e) {
            log.error("Failed to convert the returned json from geojs to a geoData class, body was " + responseBody);
            return null;
        }
    }
}
