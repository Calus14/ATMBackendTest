package codingtest.backendtest.src.service;

import codingtest.backendtest.src.domain.dto.GeoData;
import codingtest.backendtest.src.persistence.GeoDataCachePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class DefaultIpLocatorService implements IpLocatorService{

    @Autowired
    GeoJsCommunicationService geoJsCommunicationService;
    @Autowired
    GeoDataCachePersistence geoDataCachePersistence;

    @Override
    public GeoData getGeoDataAndPersistForIpAddress(String ipAddress) throws Exception {
        if(geoDataCachePersistence.containsGeoData(ipAddress)){
            return geoDataCachePersistence.getGeoData(ipAddress);
        }
        else{
            GeoData retrievedData = geoJsCommunicationService.getGeodataFromIP(ipAddress);
            // Normally add more error handling here... but for brevity no.
            geoDataCachePersistence.addGeoData(retrievedData);
            return retrievedData;
        }
    }

    @Override
    public Collection<GeoData> getAllKnownIpAddresses() {
        return geoDataCachePersistence.getAllGeoData();
    }

    @Override
    public List<GeoData> filterByCountry(List<GeoData> initialList, String country) {
        List<GeoData> filteredList = new ArrayList<>();
        initialList.forEach(data -> {
            if(data.getCountry().equalsIgnoreCase(country)){
                filteredList.add(data);
            }
        });
        return filteredList;
    }

    @Override
    public List<GeoData> filterByCountryCode(List<GeoData> initialList, String countryCode) {
        List<GeoData> filteredList = new ArrayList<>();
        initialList.forEach(data -> {
            if(data.getCountry_code().equalsIgnoreCase(countryCode)){
                filteredList.add(data);
            }
        });
        return filteredList;
    }

    @Override
    public List<GeoData> filterByCountryCode3(List<GeoData> initialList, String countryCode3) {
        List<GeoData> filteredList = new ArrayList<>();
        initialList.forEach(data -> {
            if(data.getCountry_code3().equalsIgnoreCase(countryCode3)){
                filteredList.add(data);
            }
        });
        return filteredList;
    }

    @Override
    public List<GeoData> filterByCity(List<GeoData> initialList, String city) {
        List<GeoData> filteredList = new ArrayList<>();
        initialList.forEach(data -> {
            if(data.getCity().equalsIgnoreCase(city)){
                filteredList.add(data);
            }
        });
        return filteredList;
    }
}
