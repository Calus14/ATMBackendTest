package codingtest.backendtest.src.persistence;

import codingtest.backendtest.src.domain.dto.GeoData;

import java.util.Collection;
import java.util.List;

/**
 * The abstract interface for persiting data in our cache. this will allow users to define different beans with implementations
 * such that retrival can be faster at the cost of higher in cache memory or slower call on lower in cache memory etc.
 */
public interface GeoDataCachePersistence {

    boolean containsGeoData(String ipAddress);

    /**
     * Throws an exception if the data already exists
     * @param data Data to add
     * @throws Exception thrown if the data already exists as service should be aware that there is a duplicate
     */
    void addGeoData(GeoData data) throws Exception;

    /**
     * Attempst to remove data if it exists
     * @param data data to remove
     * @throws Exception thrown if the data does not exist in the cache.
     */
    void removeGeoData(GeoData data) throws Exception;

    GeoData getGeoData(String ipAddress);
    Collection<GeoData> getAllGeoData();
}
