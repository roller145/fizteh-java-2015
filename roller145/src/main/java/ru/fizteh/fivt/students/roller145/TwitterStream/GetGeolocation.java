package ru.fizteh.fivt.students.roller145.TwitterStream;

import com.beust.jcommander.internal.Maps;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.sun.deploy.net.URLEncoder;
import javafx.util.Pair;
import org.json.JSONException;
import org.json.JSONObject;
import twitter4j.GeoLocation;


import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;


import static java.lang.Math.*;

/**
 * This class is working with geocoding for query
 * using Google geocoding API
 * Created by riv on 27.09.15.
 */
public class GetGeolocation {
    private static final String baseUrl = "http://maps.googleapis.com/maps/api/geocode/json";// путь к Geocoding API по HTTP
    private static final String GeoIPUrl  = "http://ipinfo.io/json";
    private static final String near = "nearbly";
    private static final double EARTH_RADIUS = 6371;
    

    public Pair<GeoLocation, Double> getGeolocation(String were) throws IOException {
        if (were.equals(near)) {
            final JSONObject response = new JsonReader().read(GeoIPUrl);
            were = response.getString("city");
        }
        final Map<String, String> params = Maps.newHashMap();
        params.put("sensor", "false");// исходит ли запрос на геокодирование от устройства с датчиком местоположения
        params.put("address", were);// адрес, который нужно геокодировать
        final String url = baseUrl + '?' + encodeParams(params);
        final JSONObject response = new JsonReader().read(url);// делаем запрос к вебсервису и получаем от него ответ

        JSONObject location = response.getJSONArray("results").getJSONObject(0);
        location = location.getJSONObject("geometry");
        location = location.getJSONObject("location");
        final double lng = location.getDouble("lng");// долгота
        final double lat = location.getDouble("lat");// широта

        JSONObject south = response.getJSONArray("results").getJSONObject(0);
        south = south.getJSONObject("geometry");
        south = south.getJSONObject("bounds");
        south = south.getJSONObject("southwest");
        final double southLng = south.getDouble("lng");// долгота
        final double southLat = south.getDouble("lat");// широта

        JSONObject north = response.getJSONArray("results").getJSONObject(0);
        north = north.getJSONObject("geometry");
        north = north.getJSONObject("bounds");
        north = north.getJSONObject("northeast");
        final double northLng = north.getDouble("lng");// долгота
        final double northLat = north.getDouble("lat");// широта

        GeoLocation northLoc = new GeoLocation(northLat,northLng);
        GeoLocation southLoc = new GeoLocation(southLat,southLng);

        GeoLocation result = new GeoLocation(lat,lng);

        final double resultRadius = getDistance(northLoc, southLoc)/2;

        return new Pair<>(result, resultRadius);

    }

    @SuppressWarnings("TryFinallyCanBeTryWithResources")
    public class JsonReader {

        private String readAll(final Reader rd) throws IOException {
            final StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            return sb.toString();
        }

        public JSONObject read(final String url) throws IOException, JSONException {
            final InputStream is = new URL(url).openStream();
            try {
                final BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                final String jsonText = readAll(rd);
                return new JSONObject(jsonText);
            } finally {
                is.close();
            }
        }
    }
    private String encodeParams(final Map<String, String> params) {
        return Joiner.on('&').join(// получаем значение вида key1=value1&key2=value2...
                Iterables.transform(params.entrySet(), input -> {
                    try {
                        return input.getKey() + '=' + URLEncoder.encode(input.getValue(), "utf-8");
                    } catch (final UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                }));
    }

    private double getDistance(GeoLocation A, GeoLocation B){

        return   EARTH_RADIUS
                * 2
                * asin(sqrtt(sqr(sin(Math.toRadians((A.getLatitude() - B.getLatitude())/2))
                    + cos(Math.toRadians(A.getLatitude()))
                    * cos(Math.toRadians(B.getLatitude()))
                    * sqr(sin(Math.toRadians((A.getLongitude() - B.getLongitude())/2))))));
    }

    private double sqrtt(double v) {
        return pow(v,0.5);
    }

    private double sqr(double v) {
        return v*v;
    }
}

  /*private static String getCurrentIP() {
        String result = null;
        try {
            BufferedReader reader = null;
            try {
                URL url = new URL("http://myip.by/");
                InputStream inputStream = null;
                inputStream = url.openStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder allText = new StringBuilder();
                char[] buff = new char[1024];

                int count = 0;
                while ((count = reader.read(buff)) != -1) {
                    allText.append(buff, 0, count);
                }
                Integer indStart = allText.indexOf("\">whois ");
                Integer indEnd = allText.indexOf("</a>", indStart);

                String ipAddress = new String(allText.substring(indStart + 8, indEnd));
                if (ipAddress.split("\\.").length == 4) { // минимальная (неполная)
                    //проверка что выбранный текст является ip адресом.
                    result = ipAddress;
                }
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
*/