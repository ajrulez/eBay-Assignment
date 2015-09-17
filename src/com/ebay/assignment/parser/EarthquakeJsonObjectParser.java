package com.ebay.assignment.parser;

import org.json.JSONObject;

import com.ebay.assignment.model.EarthquakeDataModel;

/**
 * This class is used to parse a JSON-object
 * that represents an earthquake to a EarthquakeDataModel
 * object.
 * 
 * Note; I am using native JSON parsing instead of 3rd
 * party library like GSON which easily converts a JSON
 * object. I am doing this because there is a restriction
 * on using 3rd party libraries for this assignment
 * 
 * @author asaluja
 *
 */
public class EarthquakeJsonObjectParser {
	/**
	 * Method to parse the JSON object representing each
	 * earthquake into a EarthquakeDataModel object
	 * 
	 * @param jsonEarthquake
	 * @return
	 */
	public EarthquakeDataModel parseEarthquakeJsonObject(JSONObject jsonEarthquake) {
		EarthquakeDataModel eqModel = null;
		
		if(jsonEarthquake != null) {
			eqModel = new EarthquakeDataModel();
			
			// Earthquake Id
			eqModel.setEarthquakeId(jsonEarthquake.optString("eqid", ""));
			
			// Magnitude
			eqModel.setMagnitude(jsonEarthquake.optDouble("magnitude", 0.0f));
			
			// Depth
			eqModel.setDepth(jsonEarthquake.optDouble("depth", 0.0f));
			
			// Latitude
			eqModel.setLatitude(jsonEarthquake.optDouble("lat", 0.0f));
			
			// Longitude
			eqModel.setLongitude(jsonEarthquake.optDouble("lng", 0.0f));
			
			// Source
			eqModel.setSource(jsonEarthquake.optString("src", ""));
			
			// Timestamp
			eqModel.setTimestamp(jsonEarthquake.optString("datetime", ""));
		}
		
		return eqModel;
	}
}
