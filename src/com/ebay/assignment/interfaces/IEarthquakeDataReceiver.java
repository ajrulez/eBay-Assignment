package com.ebay.assignment.interfaces;

import java.util.ArrayList;
import com.ebay.assignment.model.EarthquakeDataModel;

/**
 * This interface declares a method that is called
 * when Earthquake data is received
 * 
 * @author asaluja
 *
 */

public interface IEarthquakeDataReceiver {
	// Method that gets invoked when earthquake data is retrieved
	public void onEarthquakeDataReceived(ArrayList<EarthquakeDataModel> listEarthquakes);
}
