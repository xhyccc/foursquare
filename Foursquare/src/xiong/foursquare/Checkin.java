package xiong.foursquare;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Checkin {
	public static DateFormat inputFormat = new SimpleDateFormat(
		        "E MMM dd HH:mm:ss z yyyy", Locale.UK);

	private static HashMap<Long,Checkin> checkins = new HashMap<Long, Checkin>();
	private static Long counter = 0l;
	private String userID;
	private String venueID;
	private User user;
	private City city; 
	private POI poi;
	private Date time;
	private int offset;
	private boolean homeCheckin = false;
	
	
	public boolean isHomeCheckin() {
		return homeCheckin;
	}

	public void setHomeCheckin(boolean homeCheckin) {
		this.homeCheckin = homeCheckin;
	}

	public static void load(String file){
		try {
			BufferedReader reader =new BufferedReader(new FileReader(file));
			String ln = reader.readLine();
			int lcon = 0;
			while(ln!=null){
				String[] lns = ln.split("\t");
				if(POI.isExist(lns[1]))
					new Checkin(lns[0],lns[1],lns[2],lns[3]);
				ln = reader.readLine();
				lcon++;
			}
			reader.close();
		} catch (IOException | NumberFormatException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Checkin(String userID, String venueID, String date, String offset) throws NumberFormatException, ParseException{
	//	synchronized(counter){
			checkins.put(counter++, this);
	//	}
		this.userID = userID;
		this.venueID = venueID;
		long ms = inputFormat.parse(date).getTime() + Long.parseLong(offset)*60*1000;
		this.offset = Integer.parseInt(offset);
		this.time = new Date(ms);
		this.poi= POI.queryByPOI(venueID);
		this.poi.addCheckin(this);
		this.user=User.queryByUser(this.userID);
		this.user.addCheckin(this);
		this.city = this.poi.getCity();
	}
	
	public static Set<Checkin> filterByUserCities(Set<City> cities, Collection<Checkin> ckns){
		Set<Checkin> cks = new HashSet<Checkin>();
		for(Checkin ck:ckns){
			if(cities.contains(ck.city)&&!cks.contains(ck))
				cks.add(ck);
		}
		return cks;
	}
	
	public static Set<Checkin> filterByUserIDs(Set<String> uids, Collection<Checkin> ckns){
		Set<Checkin> cks = new HashSet<Checkin>();
		for(Checkin ck:ckns){
			if(uids.contains(ck.userID)&&!cks.contains(ck))
				cks.add(ck);
		}
		return cks;
	}
	
	public static Set<Checkin> filterByVenueIDs(Set<String> vids, Collection<Checkin> ckns){
		Set<Checkin> cks = new HashSet<Checkin>();
		for(Checkin ck:ckns){
			if(vids.contains(ck.venueID)&&!cks.contains(ck))
				cks.add(ck);
		}
		return cks;
	}
	
	public static Set<Checkin> filterByHours(Set<Integer> hours, Collection<Checkin> ckns){
		Set<Checkin> cks = new HashSet<Checkin>();
		for(Checkin ck:ckns){
			if(hours.contains(ck.time.getHours())&&!cks.contains(ck))
				cks.add(ck);
		}
		return cks;
	}
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getVenueID() {
		return venueID;
	}
	public void setVenueID(String venueID) {
		this.venueID = venueID;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
	
}
