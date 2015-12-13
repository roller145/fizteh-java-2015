package ru.fizteh.fivt.students.roller145.moduletests.library;

import javafx.util.Pair;
import junit.framework.TestCase;
import org.powermock.modules.junit4.PowerMockRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import ru.fizteh.fivt.students.roller145.TwitterStream.GetGeolocation;
import twitter4j.GeoLocation;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by riv on 12.10.15.
 */


@RunWith(PowerMockRunner.class)
@PrepareForTest({URL.class, GetGeolocation.class})
public class GetGeolocationTest extends TestCase {
    private URL GeoIPUrl;
    private URL baseURL;
    static final String CITY  = "Dolgoprudnyy";
    static final String MOSCOW = "Moscow";
    private final String URLAdress ="http://ipinfo.io/json";
    static final double LAT = 55.755826;
    static final double LNG = 37.6173;
    static final double RADIUS = 28.95595392698468;

    @Before
    public void loadData() throws Exception {
        GeoIPUrl = PowerMockito.mock(URL.class);
        PowerMockito.whenNew(URL.class).withArguments("http://maps.googleapis.com/maps/api/geocode/json?address=" + MOSCOW +"&sensor=false").thenReturn(GeoIPUrl);

        baseURL = PowerMockito.mock(URL.class);
        PowerMockito.whenNew(URL.class).withArguments(URLAdress).thenReturn(baseURL);

    }
    @Test
    public void getLocationTest() throws Exception {
        String resultDolgoprudnyy = "{\n" +
                "  \"ip\": \"93.175.2.59\",\n" +
                "  \"hostname\": \"No Hostname\",\n" +
                "  \"city\": \"Dolgoprudnyy\",\n" +
                "  \"region\": \"Moscow Oblast\",\n" +
                "  \"country\": \"RU\",\n" +
                "  \"loc\": \"55.9041,37.5606\",\n" +
                "  \"org\": \"AS5467 Non state educational institution Educational Scientific and Experimental Center of Moscow Institute of Physics and Technology\",\n" +
                "  \"postal\": \"141700\"\n" +
                "}";
        InputStream instream = new ByteArrayInputStream(resultDolgoprudnyy.getBytes(StandardCharsets.UTF_8));
        PowerMockito.when(baseURL.openStream()).thenReturn(instream);
        String location = new GetGeolocation().getLocation();
        assertEquals(location, CITY);
    }

    @Test
    public void getGeoLocationTest() throws IOException {
        String result = "{\n" +
                "   \"results\" : [\n" +
                "      {\n" +
                "         \"address_components\" : [\n" +
                "            {\n" +
                "               \"long_name\" : \"Moscow\",\n" +
                "               \"short_name\" : \"Moscow\",\n" +
                "               \"types\" : [ \"locality\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"gorod Moskva\",\n" +
                "               \"short_name\" : \"g. Moskva\",\n" +
                "               \"types\" : [ \"administrative_area_level_2\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Moscow\",\n" +
                "               \"short_name\" : \"Moscow\",\n" +
                "               \"types\" : [ \"administrative_area_level_1\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Russia\",\n" +
                "               \"short_name\" : \"RU\",\n" +
                "               \"types\" : [ \"country\", \"political\" ]\n" +
                "            }\n" +
                "         ],\n" +
                "         \"formatted_address\" : \"Moscow, Moscow, Russia\",\n" +
                "         \"geometry\" : {\n" +
                "            \"bounds\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 56.009657,\n" +
                "                  \"lng\" : 37.9456611\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 55.48992699999999,\n" +
                "                  \"lng\" : 37.3193288\n" +
                "               }\n" +
                "            },\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 55.755826,\n" +
                "               \"lng\" : 37.6173\n" +
                "            },\n" +
                "            \"location_type\" : \"APPROXIMATE\",\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 56.009657,\n" +
                "                  \"lng\" : 37.9456611\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 55.48992699999999,\n" +
                "                  \"lng\" : 37.3193288\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"place_id\" : \"ChIJybDUc_xKtUYRTM9XV8zWRD0\",\n" +
                "         \"types\" : [ \"locality\", \"political\" ]\n" +
                "      },\n" +
                "      {\n" +
                "         \"address_components\" : [\n" +
                "            {\n" +
                "               \"long_name\" : \"Moscow\",\n" +
                "               \"short_name\" : \"Moscow\",\n" +
                "               \"types\" : [ \"locality\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Latah County\",\n" +
                "               \"short_name\" : \"Latah County\",\n" +
                "               \"types\" : [ \"administrative_area_level_2\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"Idaho\",\n" +
                "               \"short_name\" : \"ID\",\n" +
                "               \"types\" : [ \"administrative_area_level_1\", \"political\" ]\n" +
                "            },\n" +
                "            {\n" +
                "               \"long_name\" : \"United States\",\n" +
                "               \"short_name\" : \"US\",\n" +
                "               \"types\" : [ \"country\", \"political\" ]\n" +
                "            }\n" +
                "         ],\n" +
                "         \"formatted_address\" : \"Moscow, ID, USA\",\n" +
                "         \"geometry\" : {\n" +
                "            \"bounds\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 46.758882,\n" +
                "                  \"lng\" : -116.962068\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 46.71091209999999,\n" +
                "                  \"lng\" : -117.0396979\n" +
                "               }\n" +
                "            },\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : 46.73238749999999,\n" +
                "               \"lng\" : -117.0001651\n" +
                "            },\n" +
                "            \"location_type\" : \"APPROXIMATE\",\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : 46.758882,\n" +
                "                  \"lng\" : -116.962068\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : 46.71091209999999,\n" +
                "                  \"lng\" : -117.0396979\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"place_id\" : \"ChIJ0WHAIi0hoFQRbK3q5g0V_T4\",\n" +
                "         \"types\" : [ \"locality\", \"political\" ]\n" +
                "      }\n" +
                "   ],\n" +
                "   \"status\" : \"OK\"\n" +
                "}\n";

        InputStream instream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
        PowerMockito.when(GeoIPUrl.openStream()).thenReturn(instream);
        Pair<GeoLocation, Double> location = new GetGeolocation().getGeolocation(MOSCOW);
        GeoLocation coordinates = new GeoLocation(LAT,LNG);
        Pair<GeoLocation, Double> rightLocation = new Pair<> (coordinates,RADIUS);
        assertEquals(location,rightLocation);
    }
}