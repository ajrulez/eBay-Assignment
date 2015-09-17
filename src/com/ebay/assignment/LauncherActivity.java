package com.ebay.assignment;

import java.util.ArrayList;
import java.util.List;

import com.ebay.assignment.adapters.EarthquakeListAdapter;
import com.ebay.assignment.interfaces.IEarthquakeDataReceiver;
import com.ebay.assignment.model.EarthquakeDataModel;
import com.ebay.assignment.tasks.EarthquakeApiTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * This is the launcher or the main activity.
 * 
 * This activity supports rotation or config changes by
 * saving the list of earthquakes, and only requests new
 * set of earthquake data in cases where there's no saved
 * earthquake data
 * 
 * @author asaluja
 *
 */
public class LauncherActivity extends Activity
							  implements IEarthquakeDataReceiver {
	
	// List View for showing Earthquake Data
	private ListView lvEarthquakes;
	
	// Adapter for Earthquake ListView
	private EarthquakeListAdapter eqAdapter;
	
	// List of Earthquakes
	private ArrayList<EarthquakeDataModel> eqDataModelList = null;
	
	// Key for saved earthquake list
	private static final String KEY_SAVED_EARTHQUAKE_LIST = "SAVED_EARTHQUAKE_LIST";
	
	// Create the view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Set the layout
        setContentView(R.layout.activity_launcher);
        
        // Set the ListView
        lvEarthquakes = (ListView) findViewById(R.id.lvEarthquakes);
        
        // Bind the Adapter
		if(eqAdapter == null) {
			eqAdapter = new EarthquakeListAdapter(LauncherActivity.this, 0, LauncherActivity.this);
			lvEarthquakes.setAdapter(eqAdapter);
		}
		
		// Check the Bundle, and restore the earthquake list
		if(savedInstanceState != null) {
			eqDataModelList = savedInstanceState.getParcelableArrayList(KEY_SAVED_EARTHQUAKE_LIST);
		}
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    	
    	// Check if we already have a list of Earthquakes
    	if(eqDataModelList == null || 
    			eqDataModelList.size() == 0) {
    		// Start AsyncTask
    		EarthquakeApiTask eqTask = new EarthquakeApiTask(LauncherActivity.this, 
    															LauncherActivity.this);
    		eqTask.execute(new Void[] {});
    	}
    	
    	// Show the existing list
    	else {
    		showList(eqDataModelList);
    	}
    }
    
    @Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		// SaveEarthquake List
		outState.putParcelableArrayList(KEY_SAVED_EARTHQUAKE_LIST, eqDataModelList);
	}
    
    /**
     * Callback that is invoked when data is retrieved from the AsyncTask
     * 
     */
	@Override
	public void onEarthquakeDataReceived(ArrayList<EarthquakeDataModel> listEarthquakes) {
		if(listEarthquakes != null && 
				listEarthquakes.size() > 0) {
			eqDataModelList = listEarthquakes;
			showList(listEarthquakes);
		}
		
		else {
			// Hide the ListView
			lvEarthquakes.setVisibility(View.INVISIBLE);
			
			// Show Error
			showAlert(getResources().getString(R.string.error_title),
								getResources().getString(R.string.error_no_earthquake_data));
		}
	}
	
	/**
	 * Method to the List of earthquakes
	 * @param listEarthquakes
	 */
	protected void showList(List<EarthquakeDataModel> listEarthquakes) {
		if(listEarthquakes != null && 
				listEarthquakes.size() > 0) {
			// Show the ListView
			lvEarthquakes.setVisibility(View.VISIBLE);
			
			// Update the data
			eqAdapter.updateData(listEarthquakes);
		}
	}
	
	// Method to show an Alert dialog
	protected void showAlert(final String title, final String message) {
		runOnUiThread(new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				AlertDialog alertDialog1 = new AlertDialog.Builder(LauncherActivity.this).create();

				// Setting Dialog Title
		        alertDialog1.setTitle(title);

		        // Setting Dialog Message
		        alertDialog1.setMessage(message);

		        // Setting Icon to Dialog
		        alertDialog1.setIcon(R.drawable.ic_launcher);

		        // Setting OK Button
		        alertDialog1.setButton(getResources().getString(R.string.close), new DialogInterface.OnClickListener() {

		            public void onClick(DialogInterface dialog, int which) {
		            	// Nothing to do
		            }
		        });

		        // Showing Alert Message
		        alertDialog1.show();
			}
		});
	}
}
