package codingtest.backendtest;

import codingtest.backendtest.src.domain.dto.GeoData;
import codingtest.backendtest.src.service.DefaultGeoJsCommunicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendtestApplicationTests {

    @Autowired
    DefaultGeoJsCommunicationService defaultGeoJsCommunicationService;

    static final String goodTestIp = "66.232.236.211";

    static final String basicIp = "127.1.1.1";

    static final String badIpAddress = "12343234234";

    /**
     * Basic test taht will check that this connection method works and retrieves data correctly.
     */
    @Test
    void testgetGeodataFromIP() {
        GeoData testData = defaultGeoJsCommunicationService.getGeodataFromIP(goodTestIp);
        assert(testData.getCountry_code().equals("US"));

        testData = defaultGeoJsCommunicationService.getGeodataFromIP(basicIp);
        assert(testData.getOrganization_name().equals("Unknown"));

        testData = defaultGeoJsCommunicationService.getGeodataFromIP(badIpAddress);
        assert(testData == null);

    }

}
