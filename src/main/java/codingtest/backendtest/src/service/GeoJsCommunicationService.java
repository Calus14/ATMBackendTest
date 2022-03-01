package codingtest.backendtest.src.service;

import codingtest.backendtest.src.domain.dto.GeoData;

/**
 * Abstract interface so spring boot can make use of dependancy injection by just having beans the interface type
 *
 * To keep it simple this will handle calling to get the geo data from geojs.io and how to extract the countryCode and city.
 * This seems overly complex but this allows for extendability for things like persistant layers, extraction and mutation of
 * data and other useful features. When designing a backend always plan for 2 years from now not 2 weeks
 */
public interface GeoJsCommunicationService {

    /**
     * Calls the geojs.io endpoint with .json for a single ip address and uses returns the java object of the json
     * text
     * @param IPAddress
     */
    GeoData getGeodataFromIP(String IPAddress);
}
