package com.yp.pm.kp.api.regression.geoLocation;

import com.yp.enums.api.KeplerDomainTypeEnum;
import com.yp.enums.api.MethodEnum;
import com.yp.pm.kp.BaseAPITest;
import com.yp.pm.kp.api.dto.ResponseDTO;
import com.yp.pm.kp.api.dto.ResultsDTO;
import com.yp.pm.kp.api.dto.domain.GeoLocationDTO;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GetGeoLibTest extends BaseAPITest {

    private Map<String, String> headers = getRequestHeader();

    @Test
    public void testSearchByCityAndState() throws Exception {
        String payload = "{\"locations\":[{\"name\":\"Burbank\",\"status\":1,\"type\":\"CITY\",\"state\":\"CA\"}]}";
        headers.put("accountId", "12345");

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.GEO_LOCATION, getAPIResponse(MethodEnum.POST, "locations/search", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(result.getIndex(), 0);

            List<GeoLocationDTO> geoLocationDTOList = (List<GeoLocationDTO>) result.getObject();
            for (GeoLocationDTO item : geoLocationDTOList) {
                assertObjectNotNull(item.getGeoLibId());
                assertConditionTrue(item.isStatus());
                assertConditionTrue(item.getDescription().toLowerCase().contains("burbank"));
                assertConditionTrue(item.getName().equalsIgnoreCase("burbank"));
                assertConditionTrue(item.getState().equalsIgnoreCase("ca"));
                assertConditionTrue(item.getType().equalsIgnoreCase("city"));
            }
        }
    }

    @Test
    public void testSearchByStateExactMatch() throws Exception {
        String payload = "{\"locations\":[{\"name\":\"California\",\"status\":1,\"type\":\"STATE\"}]}";
        headers.put("accountId", "12345");

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.GEO_LOCATION, getAPIResponse(MethodEnum.POST, "locations/search", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(result.getIndex(), 0);

            List<GeoLocationDTO> geoLocationDTOList = (List<GeoLocationDTO>) result.getObject();
            for (GeoLocationDTO item : geoLocationDTOList) {
                assertObjectNotNull(item.getGeoLibId());
                assertConditionTrue(item.isStatus());
                assertObjectNotNull(item.getDescription());
                assertObjectEqual(item.getName(), "California");
                assertObjectEqual(item.getType(), "STATE");
            }
        }
    }

    @Test
    public void testSearchByStateSubstringMatch() throws Exception {
        String payload = "{\"locations\":[{\"name\":\"Ca\",\"status\":1,\"type\":\"STATE\"}]}";
        headers.put("accountId", "12345");

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.GEO_LOCATION, getAPIResponse(MethodEnum.POST, "locations/search", headers, payload));

        assertResponseError(responseDTO);
        assertConditionTrue(responseDTO.getTotalCount() == 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(result.getIndex(), 0);

            List<GeoLocationDTO> geoLocationDTOList = (List<GeoLocationDTO>) result.getObject();
            for (GeoLocationDTO item : geoLocationDTOList) {
                assertObjectNotNull(item.getGeoLibId());
                assertConditionTrue(item.isStatus());
                assertConditionTrue(item.getDescription().toLowerCase().contains("ca"));
                assertConditionTrue(item.getName().toLowerCase().contains("ca"));
                assertObjectEqual(item.getType(), "STATE");
            }
        }
    }

    @Test
    public void testSearchByCityExactMatch() throws Exception {
        String payload = "{\"locations\":[{\"name\":\"Burbank\",\"status\":1,\"type\":\"CITY\"}]}";
        headers.put("accountId", "12345");

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.GEO_LOCATION, getAPIResponse(MethodEnum.POST, "locations/search", headers, payload));

        assertResponseError(responseDTO);
        assertConditionTrue(responseDTO.getTotalCount() == 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(result.getIndex(), 0);

            List<GeoLocationDTO> geoLocationDTOList = (List<GeoLocationDTO>) result.getObject();
            for (GeoLocationDTO item : geoLocationDTOList) {
                assertObjectNotNull(item.getGeoLibId());
                assertConditionTrue(item.isStatus());
                assertConditionTrue(item.getDescription().toLowerCase().contains("burbank"));
                assertConditionTrue(item.getName().toLowerCase().equalsIgnoreCase("burbank"));
                assertObjectEqual(item.getType(), "CITY");
                assertObjectNotNull(item.getState());
            }
        }
    }

    @Test
    public void testSearchByCitySubstringMatch() throws Exception {
        String payload = "{\"locations\":[{\"name\":\"Bur\",\"status\":1,\"type\":\"CITY\"}]}";
        headers.put("accountId", "12345");

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.GEO_LOCATION, getAPIResponse(MethodEnum.POST, "locations/search", headers, payload));

        assertResponseError(responseDTO);
        assertConditionTrue(responseDTO.getTotalCount() == 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(result.getIndex(), 0);

            List<GeoLocationDTO> geoLocationDTOList = (List<GeoLocationDTO>) result.getObject();
            for (GeoLocationDTO item : geoLocationDTOList) {
                assertObjectNotNull(item.getGeoLibId());
                assertConditionTrue(item.isStatus());
                assertConditionTrue(item.getDescription().toLowerCase().contains("bur"));
                assertConditionTrue(item.getName().toLowerCase().contains("bur"));
                assertObjectEqual(item.getType(), "CITY");
                assertObjectNotNull(item.getState());
            }
        }
    }

    @Test
    public void testSearchByZip() throws Exception {
        String payload = "{\"locations\":[{\"name\":\"91203\",\"status\":1,\"type\":\"ZIP\"}]}";
        headers.put("accountId", "12345");

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.GEO_LOCATION, getAPIResponse(MethodEnum.POST, "locations/search", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(result.getIndex(), 0);

            List<GeoLocationDTO> geoLocationDTOList = (List<GeoLocationDTO>) result.getObject();
            for (GeoLocationDTO item : geoLocationDTOList) {
                assertObjectNotNull(item.getGeoLibId());
                assertConditionTrue(item.isStatus());
                assertConditionTrue(item.getDescription().contains("91203"));
                assertConditionTrue(item.getName().contains("91203"));
                assertObjectEqual(item.getType(), "ZIP");
                assertConditionTrue(item.getState().equalsIgnoreCase("CA"));
            }
        }
    }

    @Test
    public void testGetGeoByDMA() throws Exception {
        String payload = "{\"locations\":[{\"name\":\"Los Angeles\",\"status\":1,\"type\":\"DMA\"}]}";
        headers.put("accountId", "12345");

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.GEO_LOCATION, getAPIResponse(MethodEnum.POST, "locations/search", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(result.getIndex(), 0);

            List<GeoLocationDTO> geoLocationDTOList = (List<GeoLocationDTO>) result.getObject();
            for (GeoLocationDTO item : geoLocationDTOList) {
                assertObjectNotNull(item.getGeoLibId());
                assertConditionTrue(item.isStatus());
                assertConditionTrue(item.getDescription().toLowerCase().contains("los angeles"));
                assertConditionTrue(item.getName().toLowerCase().contains("los angeles"));
                assertObjectEqual(item.getType(), "DMA");
            }
        }
    }

    @Test
    public void testMissingType() throws Exception {
        String payload = "{\"locations\":[{\"name\":\"Los Angeles\",\"status\":1}]}";
        headers.put("accountId", "12345");

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.GEO_LOCATION, getAPIResponse(MethodEnum.POST, "locations/search", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(result.getIndex(), 0);

            List<GeoLocationDTO> geoLocationDTOList = (List<GeoLocationDTO>) result.getObject();
            for (GeoLocationDTO item : geoLocationDTOList) {
                assertObjectNotNull(item.getGeoLibId());
                assertConditionTrue(item.isStatus());
                assertConditionTrue(item.getDescription().toLowerCase().contains("los angeles"));
                assertConditionTrue(item.getName().toLowerCase().contains("los angeles"));
            }
        }
    }

    @Test
    public void testMissingName() throws Exception {
        String payload = "{\"locations\":[{\"status\":1,\"type\":\"DMA\"}]}";
        headers.put("accountId", "12345");

        ResponseDTO responseDTO = getResponseDTO(KeplerDomainTypeEnum.GEO_LOCATION, getAPIResponse(MethodEnum.POST, "locations/search", headers, payload));

        assertResponseError(responseDTO);
        assertObjectEqual(responseDTO.getTotalCount(), 1);

        for (ResultsDTO result : responseDTO.getResults()) {
            assertObjectEqual(result.getIndex(), 0);

            List<GeoLocationDTO> geoLocationDTOList = (List<GeoLocationDTO>) result.getObject();
            for (GeoLocationDTO item : geoLocationDTOList) {
                assertObjectNotNull(item.getGeoLibId());
                assertConditionTrue(item.isStatus());
                assertObjectNotNull(item.getDescription());
                assertObjectNotNull(item.getName());
                assertObjectEqual(item.getType(), "DMA");
            }
        }
    }
}