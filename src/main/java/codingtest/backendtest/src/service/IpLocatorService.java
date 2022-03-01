package codingtest.backendtest.src.service;

import codingtest.backendtest.src.domain.dto.GeoData;

import java.util.Collection;
import java.util.List;

/**
 * Meat and potatoes of our service that will be called by our controller. Will handle HOW we handle using the GeoJs
 * Communication Service. How we persist our data in cache and externalize the methods so that we can improve performance
 * via multi threading, and considerations for horizontal scaling.
 */
public interface IpLocatorService {

    /**
     * takes an ip address and if it is not in the cache will call geojs service to get the geo data and then persist
     * it in the persistance layer as well as return it to the controller to be passed back to the caller
     * @param ipAddress you know what this is
     * @return the GeoData for the controller to us
     */
    GeoData getGeoDataAndPersistForIpAddress(String ipAddress) throws Exception;

    /**
     * Queries the persitant layer (for this will be in cache) to return all GeoData for the controller to then extract what data
     * it wants and returns from there
     * @return All geo data stored in all pods in cache memory
     */
    Collection<GeoData> getAllKnownIpAddresses();

    /**
     * Filters for users of this service to enable functionality
     */
    List<GeoData> filterByCountry(List<GeoData> initialList, String country);
    List<GeoData> filterByCountryCode(List<GeoData> initialList, String countryCode);
    List<GeoData> filterByCountryCode3(List<GeoData> initialList, String countryCode3);
    List<GeoData> filterByCity(List<GeoData> initialList, String city);
}
