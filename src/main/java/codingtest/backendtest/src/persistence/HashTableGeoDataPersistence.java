package codingtest.backendtest.src.persistence;


import codingtest.backendtest.src.domain.dto.GeoData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

/**
 * Implements the GeoDataCache Persistence backed by a single hashTable in memory. This will resize by double whenver the limit
 * is reached and will work well probably up to a few million of records. the only big issue will be if resizing is constant.
 *
 * Last thing to mention is hash tables are thread safe so as we scale this to being able to have multi threaded actions
 * this layer will provide thread safety.
 *
 */
@Component
public class HashTableGeoDataPersistence implements GeoDataCachePersistence{

    // Maps Ip Addresses to Geo Data
    Hashtable<String, GeoData> geoDataCache = new Hashtable();

    @Override
    public boolean containsGeoData(String data) {
        return geoDataCache.contains(data);
    }

    @Override
    public void addGeoData(GeoData data) throws Exception {
        if(containsGeoData(data.getIp())){
            throw new Exception("Tried to add Geo Data that corresponds to an ip address that has already been added");
        }
        geoDataCache.put(data.getIp(), data);
    }

    @Override
    public void removeGeoData(GeoData data) throws Exception {
        if(!containsGeoData(data.getIp())){
            throw new Exception("Tried to remove Geo Data that corresponds to an ip address that has never been added");
        }
        geoDataCache.remove(data.getIp());
    }

    @Override
    public GeoData getGeoData(String ipAddress) {
        return geoDataCache.get(ipAddress);
    }

    @Override
    public Collection<GeoData> getAllGeoData() {
        return geoDataCache.values();
    }
}
