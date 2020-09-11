package br.com.gaudium.entrega.model;

import com.google.android.gms.maps.model.LatLng;

public class DebugLocationRetriever {
	private double lat, lng;

	public LatLng getLatLng(){
		return new LatLng(lat, lng);
	}

	public void setLatLng(double lat, double lng){
		this.lat = lat;
		this.lng = lng;
	}

	public double distanceToInMeters(LatLng to) {
		double lat_a = lat;
		double lng_a = lng;

		double lat_b = to.latitude;
		double lng_b = to.longitude;

		double earthRadius = 3958.75;
		double latDiff = Math.toRadians(lat_b-lat_a);
		double lngDiff = Math.toRadians(lng_b-lng_a);
		double a = Math.sin(latDiff /2) * Math.sin(latDiff /2) +
				Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
						Math.sin(lngDiff /2) * Math.sin(lngDiff /2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double distance = earthRadius * c;

		int meterConversion = 1609;

		return distance * meterConversion;
	}
}
