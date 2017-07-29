package xiong.foursquare;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class POI {
	private static HashMap<String, POI> POIS = new HashMap<String, POI>();
	private static long counter = 0;
	public static POI queryByPOI(String pid) {
		return POIS.get(pid);
	}

	private String venueID;
	private String inCity;
	private List<Checkin> poiCheckins = new ArrayList<Checkin>();
	private String venueType;
	private double lon;
	private double lat;
	private City city;
	private String co;

	public static void load(String file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String ln = reader.readLine();
			while (ln != null) {
				String[] lns = ln.split("\t");
				new POI(lns[0], lns[3], lns[4], Double.parseDouble(lns[1]), Double.parseDouble(lns[2]));
				ln = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public POI(String vID, String vType, String co, double lon, double lat) {
	//	synchronized (POIS) {
			POIS.put(vID, this);
	//	}
		this.venueID = vID;
		this.venueType = vType;
		this.lat = lat;
		this.lon = lon;
		this.co = co;
		this.city = City.nearestCity(this.lon, this.lat, this.co);
		// System.out.println(this.venueType+"\t"+this.city.getCityName());
		System.out.println((counter++)+" POI loaded");
	}

	public static List<POI> filterByCity(List<City> cities, Collection<POI> venues) {
		List<POI> vs = new ArrayList<POI>();
		for (POI v : venues) {
			if (cities.contains(v.city) && !vs.contains(v))
				vs.add(v);
		}
		return vs;
	}

	public void addCheckin(Checkin ck) {
		if (!this.poiCheckins.contains(ck)) {
			this.poiCheckins.add(ck);
		}
	}

	public String getVenueID() {
		return venueID;
	}

	public void setVenueID(String venueID) {
		this.venueID = venueID;
	}

	public String getInCity() {
		return inCity;
	}

	public void setInCity(String inCity) {
		this.inCity = inCity;
	}

	public List<Checkin> getPoiCheckins() {
		return poiCheckins;
	}

	public void setPoiCheckins(List<Checkin> poiCheckins) {
		this.poiCheckins = poiCheckins;
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

	public String getVenueType() {
		return venueType;
	}

	public void setVenueType(String venueType) {
		this.venueType = venueType;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

}
