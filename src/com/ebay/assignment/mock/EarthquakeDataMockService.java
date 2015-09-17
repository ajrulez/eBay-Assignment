package com.ebay.assignment.mock;

/**
 * This class provides a mock response for Earthquake data
 * and is used when we are offline or in test mode. This way
 * we can test our parser and our UI without depending on the
 * the network or the live service.
 * 
 * We just have to make sure that the format of response returned
 * by this class is same as the format of real data.
 * 
 * @author asaluja
 *
 */
public class EarthquakeDataMockService {
	// Offline Response String
	private final static String mockResponse = 
			"{\"earthquakes\": [{\"eqid\": \"c0001xgp\",\"magnitude\": 8.8,\"lng\": 142.369,\"src\": \"us\",\"datetime\": \"2011-03-11 04:46:23\",\"depth\": 24.4,\"lat\": 38.322},{\"eqid\": \"c000905e\",\"magnitude\": 8.6,\"lng\": 93.0632,\"src\": \"us\",\"datetime\": \"2012-04-11 06:38:37\",\"depth\": 22.9,\"lat\": 2.311},{\"eqid\": \"2007hear\",\"magnitude\": 8.4,\"lng\": 101.3815,\"src\": \"us\",\"datetime\": \"2007-09-12 09:10:26\",\"depth\": 30,\"lat\": -4.5172},{\"eqid\": \"c00090da\",\"magnitude\": 8.2,\"lng\": 92.4522,\"src\": \"us\",\"datetime\": \"2012-04-11 08:43:09\",\"depth\": 16.4,\"lat\": 0.7731},{\"eqid\": \"2007aqbk\",\"magnitude\": 8,\"lng\": 156.9567,\"src\": \"us\",\"datetime\": \"2007-04-01 18:39:56\",\"depth\": 10,\"lat\": -8.4528},{\"eqid\": \"2007hec6\",\"magnitude\": 7.8,\"lng\": 100.9638,\"src\": \"us\",\"datetime\": \"2007-09-12 21:49:01\",\"depth\": 10,\"lat\": -2.5265},{\"eqid\": \"b000g7x7\",\"magnitude\": 7.8,\"lng\": 62.0532,\"src\": \"us\",\"datetime\": \"2013-04-16 08:44:20\",\"depth\": 82,\"lat\": 28.1069},{\"eqid\": \"a00043nx\",\"magnitude\": 7.7,\"lng\": 100.1139,\"src\": \"us\",\"datetime\": \"2010-10-25 12:42:22\",\"depth\": 20.6,\"lat\": -3.4841},{\"eqid\": \"2010utc5\",\"magnitude\": 7.7,\"lng\": 97.1315,\"src\": \"us\",\"datetime\": \"2010-04-06 20:15:02\",\"depth\": 31,\"lat\": 2.3602},{\"eqid\": \"2009mebz\",\"magnitude\": 7.6,\"lng\": 99.9606,\"src\": \"us\",\"datetime\": \"2009-09-30 08:16:09\",\"depth\": 80,\"lat\": -0.7889}]}";
	
	// Method to return the mock response
	public static String getEarthquakeDataResponse() {
		return mockResponse;
	}
}
