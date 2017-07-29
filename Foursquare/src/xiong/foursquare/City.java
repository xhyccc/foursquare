package xiong.foursquare;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class City {
	private static HashMap<Integer, City> cities = new HashMap<Integer, City>();
	private static Integer counter = 0;

	private String cityName;
	private String countryName;
	private String cityType;
	private Set<POI> cityPOIs = new HashSet<POI>();
	private double lon;
	private double lat;
	private String co;

	public static void load(String file, String co) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String ln = reader.readLine();
			while (ln != null) {
				String[] lns = ln.split("\t");
				if (lns[3].equals(co))
					new City(lns[0], lns[4], lns[3], lns[5], Double.parseDouble(lns[1]), Double.parseDouble(lns[2]));
				ln = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public City(String cName, String countryName, String co, String cType, double lon, double lat) {
		// synchronized (counter) {
		cities.put(counter++, this);
		// }
		this.cityName = cName;
		this.countryName = countryName;
		this.cityType = cType;
		this.lat = lat;
		this.lon = lon;
		this.co = co;
	}

	public static City nearestCity(double lon, double lat, String co) {
		double minDis = Double.MAX_VALUE;
		City nearest = null;
		for (City city : cities.values()) {
			// if (city.co.equals(co)) {
			double dis = distance(city.getLat(), city.lon, lat, lon, "K");
			if (dis < minDis) {
				minDis = dis;
				nearest = city;
			}
			// }
		}
		return nearest;
	}

	public void addPOI(POI ck) {
		this.cityPOIs.add(ck);
	}

	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

	private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == "K") {
			dist = dist * 1.609344;
		} else if (unit == "N") {
			dist = dist * 0.8684;
		}

		return (dist);
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCityType() {
		return cityType;
	}

	public void setCityType(String cityType) {
		this.cityType = cityType;
	}

	public Set<POI> getCityPOIs() {
		return cityPOIs;
	}

	public void setCityCheckins(Set<POI> cityPOIs) {
		this.cityPOIs = cityPOIs;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

}
