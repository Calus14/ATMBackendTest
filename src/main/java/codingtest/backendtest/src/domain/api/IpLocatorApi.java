package codingtest.backendtest.src.domain.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * The API Interface that enabled dependancy injection and is the contract for all endpoints for this service
 * The default implenetation of this interace will be in the controller package
 */
public interface IpLocatorApi {

    /**
     * Queries geojs.io and returns them the city and country. Then persists the data into in cache memory.
     * @param ipAddress valid IP Address in IPv4 or IPv6
     * @return string representation of the country (full name) and the city as given by geojs
     */
    @RequestMapping(value = "findInfo", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    String getCountryAndCityFromIpAddress(@RequestParam String ipAddress);

    /**
     * @return All queried ip address in the form of an JsonString
     */
    @RequestMapping(value = "getAllInfo", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    List<String> getAllStoredData();

    /**
     * @param country country code as full, 3 character code, or 2 character code
     * @return all Queried ip address in the form of a json string within a given country
     */
    @RequestMapping(value = "getInfo/country", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    List<String> getAllStoredDataInCountry(@RequestParam String country);

    /**
     * @param country country code as full, 3 character code, or 2 character code
     * @param city city spelled correctly
     * @return all Queried ip address in the form of a json string within a given country
     */
    @RequestMapping(value = "getInfo/country/city", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    List<String> getAllStoredDataInCountryAndCity(@RequestParam String country, @RequestParam String city);

}
