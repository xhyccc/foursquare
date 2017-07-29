package xiong.foursquare;

public class Loader {
	
	public static void main(String[] args){
		System.out.println("City Loading");
		
		City.load("C:\\Users\\xiongha\\Desktop\\foursquare\\dataset_TIST2015\\dataset_TIST2015_Cities.txt", "US");
		
		System.out.println("POI Loading");

		POI.load("C:\\Users\\xiongha\\Desktop\\foursquare\\dataset_TIST2015\\dataset_TIST2015_POIs.txt");
		
		System.out.println("Checkin Loading");

		Checkin.load("C:\\Users\\xiongha\\Desktop\\foursquare\\dataset_TIST2015\\dataset_TIST2015_Checkins.txt");
		
		System.out.println("User Loading");

		User.finInit();
	}

}
