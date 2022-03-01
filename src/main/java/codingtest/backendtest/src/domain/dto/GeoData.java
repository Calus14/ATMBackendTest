package codingtest.backendtest.src.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
/**
 * POJO Object, lombok @Data will generate hashcode, equals, getters and setters and a NoArgs Constructor for this.
 *
 * The Philosophy behind this is to always have objects that represent the data we will be receiving. this allows for
 * us to add methods for data mutation, graphing, even Persistance layers and overriding hashcodes etc if need be.
 *
 * Final note Java Jackson library will use this on reflection to get every field and create the object.
 */
public class GeoData {

    /**
     * Just pulled all of these from the website
     */
    String ip;
    String country;
    String country_code;
    String country_code3;
    String continent_code;
    String city;
    String region;
    String latitude;
    String longitude;
    Integer accuracy;
    String timezone;
    String organization;
    Integer asn;
    String organization_name;
}
