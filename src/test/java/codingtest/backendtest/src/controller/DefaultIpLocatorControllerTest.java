package codingtest.backendtest.src.controller;

import codingtest.backendtest.src.domain.dto.GeoData;
import codingtest.backendtest.src.service.IpLocatorService;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DefaultIpLocatorControllerTest {

    @Mock
    IpLocatorService ipLocatorMock;

    @InjectMocks
    private DefaultIpLocatorController sut;

    private final String goodIpAddress = "73.22.69.37";
    private final String baddIpAddress = "78575.23432a";

    private GeoData getDummyDataForGoodIp(){
        GeoData dummyData = new GeoData();
        dummyData.setIp(goodIpAddress);
        dummyData.setCountry("United States");
        dummyData.setCountry_code("US");
        dummyData.setCountry_code3("USA");
        dummyData.setCity("Hinsdale");
        return dummyData;
    }

    @Test
    public void getCountryAndCityFromIpAddressTest() throws Exception {
        GeoData dummyData = getDummyDataForGoodIp();
        when(ipLocatorMock.getGeoDataAndPersistForIpAddress(goodIpAddress)).thenReturn(dummyData);

        // Test that invalid ip addresses return an error string (Normally this would be a 500 with a better response but for time...
        String badIpResponse = sut.getCountryAndCityFromIpAddress(baddIpAddress);
        verify(ipLocatorMock, never()).getGeoDataAndPersistForIpAddress(anyString());
        assertTrue(badIpResponse.contains("Invalid ip address"));

        // Test that the return is in the right form
        String testResponse = sut.getCountryAndCityFromIpAddress(goodIpAddress);
        verify(ipLocatorMock).getGeoDataAndPersistForIpAddress(goodIpAddress);
        GeoData testReturnData = new Gson().fromJson(testResponse, GeoData.class);
        assertEquals(testReturnData.getCountry(), dummyData.getCountry());
        assertEquals(testReturnData.getCity(), dummyData.getCity());

    }

    @Test
    public void getAllStoredDataTest() throws Exception {
        GeoData dummyData = getDummyDataForGoodIp();
        List<GeoData> dummyList = new ArrayList<>();
        dummyList.add(dummyData);
        when(ipLocatorMock.getAllKnownIpAddresses()).thenReturn(dummyList);

        // Test the return is in the right form
        List<String> testResponse = sut.getAllStoredData();
        verify(ipLocatorMock).getAllKnownIpAddresses();
        GeoData testReturnData = new Gson().fromJson(testResponse.get(0), GeoData.class);
        assertEquals(testReturnData.getCountry(), dummyData.getCountry());

        assertTrue(testResponse.size() == 1);
        assertEquals(testReturnData.getCountry(), dummyData.getCountry());
        assertEquals(testReturnData.getCity(), dummyData.getCity());
    }

    @Test
    public void getAllStoredDataInCountryTest() throws Exception {
        GeoData dummyData = getDummyDataForGoodIp();
        List<GeoData> dummyList = new ArrayList<>();
        dummyList.add(dummyData);
        when(ipLocatorMock.getAllKnownIpAddresses()).thenReturn(dummyList);

        // Test that no responses are given for a bad country code
        List<String> filteredCountries = sut.getAllStoredDataInCountry("Test");
        verify(filteredCountries.size() == 0);

        // Test that filtering works well for US Code
        String validCountry = "United States";
        filteredCountries = sut.getAllStoredDataInCountry(validCountry);
        verify(ipLocatorMock).filterByCountry(dummyList, validCountry);
        verify(filteredCountries.size() == 1);

        String validCountryCode = "US";
        filteredCountries = sut.getAllStoredDataInCountry(validCountryCode);
        verify(ipLocatorMock).filterByCountryCode(dummyList, validCountryCode);
        verify(filteredCountries.size() == 1);

        String validCountryCode3 = "USA";
        filteredCountries = sut.getAllStoredDataInCountry(validCountryCode3);
        verify(ipLocatorMock).filterByCountryCode3(dummyList, validCountryCode3);
        verify(filteredCountries.size() == 1);
    }

    @Test
    public void getAllStoredDataInCountryAndCityTest() throws Exception {
        GeoData dummyData = getDummyDataForGoodIp();
        when(ipLocatorMock.getGeoDataAndPersistForIpAddress(goodIpAddress)).thenReturn(dummyData);

        // Test that invalid ip addresses return an error string (Normally this would be a 500 with a better response but for time...
        String badIpResponse = sut.getCountryAndCityFromIpAddress(baddIpAddress);
        verify(ipLocatorMock, never()).getGeoDataAndPersistForIpAddress(anyString());
        assertTrue(badIpResponse.contains("Invalid ip address"));

        // Test that the return is in the right form
        String testResponse = sut.getCountryAndCityFromIpAddress(goodIpAddress);
        verify(ipLocatorMock).getGeoDataAndPersistForIpAddress(goodIpAddress);
        GeoData testReturnData = new Gson().fromJson(testResponse, GeoData.class);
        assertEquals(testReturnData.getCountry(), dummyData.getCountry());
        assertEquals(testReturnData.getCity(), dummyData.getCity());

    }
}