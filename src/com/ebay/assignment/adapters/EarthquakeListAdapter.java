package com.ebay.assignment.adapters;

import java.util.ArrayList;
import java.util.List;

import com.ebay.assignment.MapActivity;
import com.ebay.assignment.R;
import com.ebay.assignment.model.EarthquakeDataModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class extends ArrayAdapter and is used to implement
 * the list array adapter to display EarthquakeDataModel object.
 * 
 * We are using Android ViewHolder Pattern to improve the
 * ListView performance
 * 
 * Note: If we had to load different images for each earthquake
 * item in the list, I would have gone for a LazyLoad list. I
 * have implemented my own LazyLoad lists, but there are also
 * 3rd party libraries like UniversalImageLoader, Picaso
 * that support lazy loading.
 * 
 * In this case, the data is all text, and we use static image (location
 * pin) for each item so we just load the static image, and there's no
 * lazy loading needed.
 * 
 * @author asaluja
 *
 */
public class EarthquakeListAdapter extends ArrayAdapter<EarthquakeDataModel> {
	
	/**
	 * ViewHolder Class to implement the pattern
	 * @author asaluja
	 *
	 */
	static class ViewHolder {
		// Earthquake ID
		TextView tvEarthquakeId;
		
		// Magnitude
		TextView tvMagnitude;
		
		// Depth
		TextView tvDepth;
		
		// Location Pin ImageView
		ImageView ivLocationPin;
		
		// Timestamp
		TextView tvTimestamp;
	}
	
	// Data - List<EarthquakeDataModel>
	private List<EarthquakeDataModel> arrEarthquakes = new ArrayList<EarthquakeDataModel> ();
	
	// Activity
	private Activity activity;
	
	// Context
	private Context context;

	// Default Constructor
	private EarthquakeListAdapter(Context context, int resource) {
		super(context, resource);
		this.context = context;
	}
	
	// Constructor
	public EarthquakeListAdapter(Context context, int resource, Activity activity) {
		super(context, resource);
		this.context = context;
		this.activity = activity;
	}
	
	// Update Earthquake Data
	public synchronized void updateData(final List<EarthquakeDataModel> data) {
		try {
			// It is preferred to update the list on UI thread
			if(activity != null) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						arrEarthquakes = data;
						notifyDataSetChanged();
					}
				});
			}
			else {
				arrEarthquakes = data;
				notifyDataSetChanged();
			}
		}
		catch(Exception e) {
		
		}
	}
	
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Use ViewHolder Pattern
		ViewHolder holder = null;
		
		// Convert View is null, we must inflate a View from the List item layout
		if(convertView == null) {
			LayoutInflater inflater = 
					(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.earthquake_list_item, null);
            
            // Creates a ViewHolder and store references to the children views
            // we want to bind data to.
            holder = new ViewHolder();
            
            // Earthquake ID
            holder.tvEarthquakeId = (TextView) convertView.findViewById(R.id.tvEarthQuakeId);
            
            // Magnitude
            holder.tvMagnitude = (TextView) convertView.findViewById(R.id.tvMagnitude);
            
            // Depth
            holder.tvDepth = (TextView) convertView.findViewById(R.id.tvDepth);
            
            // Timestamp
            holder.tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
            
            // Location Pin
            holder.ivLocationPin = (ImageView) convertView.findViewById(R.id.ivLocationPin);
            
            final ViewHolder tempHolder = holder;
            // OnCLickListener for ImageView - when user clicks on
            // an ImageView, we will display the MapView showing location
            // of the Earthquake on the Map
            holder.ivLocationPin.setOnClickListener(new OnClickListener() {
            	// TODO
            	// Show MapView
				@Override
				public void onClick(View v) {
					
					// Get the EarthquakeDataModel object from the Tag, and then
					// retrieve Latitude and Longitude
					EarthquakeDataModel eqDataModel = (EarthquakeDataModel) tempHolder.ivLocationPin.getTag();
					if(eqDataModel != null) {
						String latitude = String.valueOf(eqDataModel.getLatitude());
						String longitude = String.valueOf(eqDataModel.getLongitude());
						
						// Show Map
						if(latitude != null && 
								longitude != null &&
								latitude.length() > 0 &&
								longitude.length() >0 &&
								activity != null) {
							Intent launchMapActivity = new Intent(activity, MapActivity.class);
							launchMapActivity.putExtra(MapActivity.INTENT_LOCATION_LAT_KEY, latitude);
							launchMapActivity.putExtra(MapActivity.INTENT_LOCATION_LNG_KEY, longitude);
							
							activity.startActivity(launchMapActivity);
						}
						
						else {
							Toast.makeText(context, context.getString(R.string.error_no_earthquake_location), Toast.LENGTH_LONG).show();
						}
					}
					
					else {
						Toast.makeText(context, context.getString(R.string.error_no_earthquake_location), Toast.LENGTH_LONG).show();
					}
				}
            });
            
            convertView.setTag(holder);
        } 
		
		// Convert View is not null, reuse the View
		else {
			// Get the ViewHolder back to get fast access to the TextView
			holder = (ViewHolder) convertView.getTag();
		}
		
		// Populate the List Item Data from EarthquakeDataModel
		if(arrEarthquakes != null && 
				arrEarthquakes.size() > 0 &&
				position < arrEarthquakes.size()) {
			// Bind the data efficiently with the holder.
			//
			// Earthquake Id
			holder.tvEarthquakeId.setText(arrEarthquakes.get(position).getEarthquakeId());
			
			// Magnitude
			holder.tvMagnitude.setText(String.valueOf(arrEarthquakes.get(position).getMagnitude()));
			
			// Depth
			holder.tvDepth.setText(String.valueOf(arrEarthquakes.get(position).getDepth()));
			
			// Timestamp
			holder.tvTimestamp.setText(arrEarthquakes.get(position).getTimestamp());
			
			// Set the EarthquakeDataModel object as a Tag for the ImageView
			// so that we can retrieve Latitude and Longitude when the
			// LocationPin is clicked
			holder.ivLocationPin.setTag(arrEarthquakes.get(position));
		
			// Get the magnitude, convert it to a number, and if the
			// Magnitude is greater than 8, then colorify the
			// List item
			double dMagnitude = arrEarthquakes.get(position).getMagnitude();
			if(dMagnitude >= 8.0f) {
				holder.tvEarthquakeId.setTextColor(getColor(R.color.Red));
				holder.tvMagnitude.setTextColor(getColor(R.color.Red));
				holder.tvDepth.setTextColor(getColor(R.color.Red));
				holder.tvTimestamp.setTextColor(getColor(R.color.Red));
			}
			
			else {
				holder.tvEarthquakeId.setTextColor(getColor(R.color.Black));
				holder.tvMagnitude.setTextColor(getColor(R.color.Black));
				holder.tvDepth.setTextColor(getColor(R.color.Black));
				holder.tvTimestamp.setTextColor(getColor(R.color.Black));
			}
		}
		
		// Return the view
       return convertView;  
	}
	
	public int getCount() {
		return arrEarthquakes.size();
	}
	
	protected int getColor(int colorResourceId) {
		return context.getResources().getColor(colorResourceId);
	}
}
