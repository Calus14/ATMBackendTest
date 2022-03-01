package codingtest.backendtest.src.controller;

import codingtest.backendtest.src.domain.api.IpLocatorApi;
import codingtest.backendtest.src.domain.dto.GeoData;
import codingtest.backendtest.src.service.IpLocatorService;
import com.google.common.net.InetAddresses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/ipAddressLookup")
@Slf4j
/**
 * Concrete implementation of the IpLocatorAPI that uses the IpLocator Service
 *
 * Due to time im not going to wrap everything in a response body but i would add a rest exception handler for
 * each of these in a response body just the string
 */
public class DefaultIpLocatorController implements IpLocatorApi {

    @Autowired
    IpLocatorService ipLocatorService;

    @Override
    public String getCountryAndCityFromIpAddress(String ipAddress) {
        if(!InetAddresses.isInetAddress(ipAddress)){
            return ("Invalid ip address");
        }

        // Instead of using a gson converter i will just make a small method to get the json string
        try {
            GeoData data = ipLocatorService.getGeoDataAndPersistForIpAddress(ipAddress);
            return getCountryAndCityAsJson(data);
        }
        catch (Exception e){
            return ("Failed to get GeoData with error "+e);
        }
    }

    @Override
    public List<String> getAllStoredData() {
        Collection<GeoData> allGeoData = ipLocatorService.getAllKnownIpAddresses();
        List<String>  geoDataAsString = new ArrayList<>();
        for(GeoData data : allGeoData){
            geoDataAsString.add(getCountryAndCityAsJson(data));
        }
        return geoDataAsString;
    }

    @Override
    public List<String> getAllStoredDataInCountry(String country) {
        List<GeoData> allGeoData = (List)ipLocatorService.getAllKnownIpAddresses();
        List<GeoData> filteredGeoData;
        List<String> geoDataAsString = new ArrayList<>();
        if(country.length() == 2){
            filteredGeoData = ipLocatorService.filterByCountryCode(allGeoData, country);
        }
        else if(country.length() == 3){
            filteredGeoData = ipLocatorService.filterByCountryCode3(allGeoData, country);
        }
        else{
            filteredGeoData = ipLocatorService.filterByCountry(allGeoData, country);
        }
        for(GeoData data : filteredGeoData){
            geoDataAsString.add(getCountryAndCityAsJson(data));
        }
        return geoDataAsString;
    }

    @Override
    public List<String> getAllStoredDataInCountryAndCity(String country, String city) {
        List<GeoData> allGeoData = (List)ipLocatorService.getAllKnownIpAddresses();
        List<GeoData> filteredGeoData;
        List<String> geoDataAsString = new ArrayList<>();
        if(country.length() == 2){
            filteredGeoData = ipLocatorService.filterByCountryCode(allGeoData, country);
        }
        else if(country.length() == 3){
            filteredGeoData = ipLocatorService.filterByCountryCode3(allGeoData, country);
        }
        else{
            filteredGeoData = ipLocatorService.filterByCountry(allGeoData, country);
        }
        filteredGeoData = ipLocatorService.filterByCity(filteredGeoData, city);
        for(GeoData data : filteredGeoData){
            geoDataAsString.add(getCountryAndCityAsJson(data));
        }
        return geoDataAsString;
    }

    private String getCountryAndCityAsJson(GeoData data){
        return "{ \"country\":\""+data.getCountry()+"\", \"city\":\""+data.getCity()+"\"}";
    }
}
