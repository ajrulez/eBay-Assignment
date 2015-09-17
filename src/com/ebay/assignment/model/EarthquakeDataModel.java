package com.ebay.assignment.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class represents an Earthquake data model object, and
 * it implements a Parcelable because we may want to save a List
 * of objects of this class, or pass along an object of this class
 * in an intent.
 * 
 * Note that not all fields may be required, and that this
 * data model is a total match of the JSON response. See a
 * sample of JSON response below:
 * 
 * {
 *   "eqid": "c0001xgp",
 *   "magnitude": 8.8,
 *   "lng": 142.369,
 *   "src": "us",
 *   "datetime": "2011-03-11 04:46:23",
 *   "depth": 24.4,
 *   "lat": 38.322
 * },
 * 
 * @author asaluja
 *
 */

public class EarthquakeDataModel implements Parcelable {
	// Earthquake ID
	private String earthQuakeId;
	
	// Magnitude
	private double magnitude;
	
	// Depth
	private double depth;
	
	// Latitude
	private double latitude;
	
	// Longitude
	private double longitude;
	
	// Timestamp as String
	private String timestamp;
	
	// Source
	private String source;
	
	/**
	 * Creator method for Parcelable - Required for
	 * implementing Parcelable
	 * 
	 */
	public static final Parcelable.Creator<EarthquakeDataModel> CREATOR = new Parcelable.Creator<EarthquakeDataModel>() {
		public EarthquakeDataModel createFromParcel(Parcel in) {
			return new EarthquakeDataModel(in);
		}

		public EarthquakeDataModel[] newArray(int size) {
			return new EarthquakeDataModel[size];
		}
	};
		
	/**
	 * Default Constructor - Required for implementing Parcelable
	 * 
	 */
	public EarthquakeDataModel() {
		earthQuakeId = "";
		magnitude = 0.0f;
		depth = 0.0f;
		latitude = 0.0f;
		longitude = 0.0f;
		timestamp = "";
		source = "";
	}
	
	/**
	 * Contructor with a Parcel as input - Required for
	 * implementing Parcelable
	 * 
	 * @param Parcel - Input Parcel to construct an object from
	 * 
	 */
	public EarthquakeDataModel(Parcel in) {
		// Must be done in same order as 
		// writeToParcel method
		//
		earthQuakeId = in.readString();
		magnitude = in.readDouble();
		depth = in.readDouble();
		latitude = in.readDouble();
		longitude = in.readDouble();
		timestamp = in.readString();
		source = in.readString();
    }
	
	/**
	 * Method to write EarthquakeDataModel object to a Parcel
	 * 
	 * (non-Javadoc)
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// Must be written in the same order
		// it is read (see constructor)
		dest.writeString(earthQuakeId);
		dest.writeDouble(magnitude);
		dest.writeDouble(depth);
		dest.writeDouble(latitude);
		dest.writeDouble(longitude);
		dest.writeString(timestamp);
		dest.writeString(source);
	}
	
	/**
	 * (non-Javadoc)
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		return 0;
	}

	
	// Accessors and Mutators
	//
	//
	public String getEarthquakeId() {
		return earthQuakeId;
	}
	
	public void setEarthquakeId(String id) {
		this.earthQuakeId = id;
	}
	
	public double getMagnitude() {
		return magnitude;
	}
	
	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}
	
	public double getDepth() {
		return depth;
	}
	
	public void setDepth(double depth) {
		this.depth = depth;
	}

	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double lat) {
		this.latitude = lat;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double lng) {
		this.longitude = lng;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String ts) {
		this.timestamp = ts;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
}
