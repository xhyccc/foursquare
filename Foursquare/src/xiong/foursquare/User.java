package xiong.foursquare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class User {
	private static HashMap<String, User> users = new HashMap<String, User>();

	public static User queryByUser(String uid) {
		if(users.containsKey(uid))
			return users.get(uid);
		else
			return new User(uid);
	}

	private String userID;
	private City homeCity;
	private List<Checkin> userCheckins = new ArrayList<Checkin>();

	public User(String userID) {
	//	synchronized (users) {
			users.put(userID, this);
	//	}
		this.userID = userID;
		System.out.println(this.userID+"\t built");
	}

	public void addCheckin(Checkin ck) {
		if (!this.userCheckins.contains(ck)) {
			this.userCheckins.add(ck);
		}
	}
	
	public static void finInit(){
		for(User u:users.values()){
			u.searchHomeCity();
		}
	}

	public void searchHomeCity() {
		HashMap<City, Integer> ccounters = new HashMap<City, Integer>();
		for (Checkin ck : this.userCheckins) {
			if (!ccounters.containsKey(ck.getCity()))
				ccounters.put(ck.getCity(), 1);
			else
				ccounters.put(ck.getCity(), ccounters.get(ck.getCity()) + 1);
		}
		List<Integer> cons = new ArrayList<Integer>(ccounters.values());
		Collections.sort(cons);
		int maxCount = cons.get(cons.size() - 1);
		for (City c : ccounters.keySet()) {
			if (ccounters.get(c) == maxCount) {
				this.homeCity = c;
				return;
			}
		}
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public City getHomeCity() {
		return homeCity;
	}

	public void setHomeCity(City homeCity) {
		this.homeCity = homeCity;
	}

	public List<Checkin> getUserCheckins() {
		return userCheckins;
	}

	public void setUserCheckins(List<Checkin> userCheckins) {
		this.userCheckins = userCheckins;
	}

}
