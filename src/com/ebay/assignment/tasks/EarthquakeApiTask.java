package com.ebay.assignment.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ebay.assignment.interfaces.IEarthquakeDataReceiver;
import com.ebay.assignment.mock.EarthquakeDataMockService;
import com.ebay.assignment.model.EarthquakeDataModel;
import com.ebay.assignment.parser.EarthquakeJsonObjectParser;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * This class extends an AsyncTask and it implements a background
 * task that retrieves Earthquake data from the API
 * 
 * Note: Typically I would use Google Volley or Square's OkHttp
 * libraries to make web requests, but in this assignment I was asked
 * to not use any 3rd party libraries
 * 
 * @author asaluja
 *
 */
public class EarthquakeApiTask extends AsyncTask<Void, Void, ArrayList<EarthquakeDataModel>> {
	// ProgressDialog
	private ProgressDialog progressDlg;
	
	// Context
	private Context context;
	
	// Earthquake Data Receiver
	private IEarthquakeDataReceiver eqDataReceiver;
	
	// Earthquake Data Web API
	private final static String EARTHQUAKE_API =
			"http://api.geonames.org/earthquakesJSON?formatted=true&north=44.1&south=-9.9&east=-22.4&west=55.2&username=mkoppelman";
	
	// Test With Mock Data
	// I was working on this assignment while on a flight
	// so I thought of adding a mock data service to test my app
	//
	// Setting the value to "yes" would switch this app to test mode
	private final static String USE_OFFLINE_MOCK_SERVICE = "yes";
	
	// Constructor
	public EarthquakeApiTask(Context ctx, IEarthquakeDataReceiver receiver) {
		this.context = ctx;
		this.eqDataReceiver = receiver;
		
		if(context != null) {
			progressDlg = new ProgressDialog(context);
		}
	}
	
	/**
	 * PreExecute is invoked before launching the
	 * background task, and because it is on the UI thread,
	 * we may update the UI
	 * 
	 * In this case, we show a ProgressDialog when this
	 * method is invoked
	 * 
	 * (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
    protected void onPreExecute() {
		// Show Progres Dialog
		if(progressDlg != null) {
			progressDlg.setMessage("Please wait...");
			progressDlg.show();	
		}
    }
	
	/**
	 * doInBackground is invoked on a background thread as the
	 * name suggests, and we can perform asynchronous or time
	 * consuming operations.
	 * 
	 * In this case, we connect to the Rest Web service to get
	 * Earthquake data and parse the JSON response in this background
	 * task
	 * 
	 * (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected ArrayList<EarthquakeDataModel> doInBackground(Void... params) {
		try {
			String response;
		
			// I was working on this assignment while on a flight
			// so I thought of adding a mock data service to test my app
			//
			if(USE_OFFLINE_MOCK_SERVICE.equalsIgnoreCase("yes")) {
				// Sleep for 3 seconds so that we can see the ProgressDialog
				// in Offline/Mock/Test mode
				Thread.sleep(3 * 1000);
				response = EarthquakeDataMockService.getEarthquakeDataResponse();;
			}
			
			// Use online (live) mode
			else {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(EARTHQUAKE_API);
				
				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
			
				// Scanner trick to read response
				// https://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner.htm
				InputStream inputStream = httpEntity.getContent();
						
				// Use Scanner trick here instead of reading each line
				//
				Scanner scanner = new Scanner(inputStream);
				response = scanner.useDelimiter("\\A").next();
				scanner.close();
				inputStream.close();
			}
			
			// Create a JSONObject from response
			JSONObject jResponseObj = new JSONObject(response);
			if(jResponseObj != null) {
				JSONArray jEarthquakeArray = jResponseObj.getJSONArray("earthquakes");
				
				// Read each JSONObject from JSONArray, and create
				// EarthquakeDataModel object 
				//
				// Populate a List of EarthquakeDataModel objects
				if(jEarthquakeArray != null && 
						jEarthquakeArray.length() > 0) {
					// Create a list
					ArrayList<EarthquakeDataModel> eqList = new ArrayList<EarthquakeDataModel> ();
					
					// Create a Parser object
					EarthquakeJsonObjectParser parser = new EarthquakeJsonObjectParser();
					
					for(int count = 0; count < jEarthquakeArray.length() - 1; count++) {
						JSONObject eqObject = jEarthquakeArray.getJSONObject(count);
						
						if(eqObject != null) {
							EarthquakeDataModel eqDataModel = parser.parseEarthquakeJsonObject(eqObject);
							
							// Add to List
							if(eqDataModel != null) {
								eqList.add(eqDataModel);
							}
						}
					}
					
					return eqList;
				}
			}
			 
		} 
		catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * onPostExecute is invoked on the UI thread and is used to update
	 * the UI with the results from an asynchronous operation.
	 * 
	 * In this case, we first dismiss the ProgressDialog, and then call
	 * the listener/receiver with the results
	 * 
	 * (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
    @Override
    protected void onPostExecute(final ArrayList<EarthquakeDataModel> listEarthquakes) {
        if(progressDlg != null &&
        		progressDlg.isShowing()) {
        	progressDlg.dismiss();
        }
        
        if(eqDataReceiver != null) {
        	eqDataReceiver.onEarthquakeDataReceived(listEarthquakes);
        }
    }
}
