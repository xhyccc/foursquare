package xiong.foursquare;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Loader {
	
	public static void main(String[] args){
		System.out.println("City Loading");
		
		City.load("C:\\Users\\xiongha\\Desktop\\foursquare\\dataset_TIST2015\\dataset_TIST2015_Cities.txt", "US");
		
		System.out.println("POI Loading");

		POI.load("C:\\Users\\xiongha\\Desktop\\foursquare\\dataset_TIST2015\\dataset_TIST2015_POIs.txt", "US");
		
		System.out.println("Checkin Loading");

		Checkin.load("C:\\Users\\xiongha\\Desktop\\foursquare\\dataset_TIST2015\\dataset_TIST2015_Checkins.txt");
		
		System.out.println("User Loading");

		User.finInit();
		
		
		try {
			PrintStream ps =new PrintStream("C:\\Users\\xiongha\\Desktop\\foursquare\\dataset_TIST2015\\POIcounts.txt");
			ps.print("CityName\tLat\tLon\tUsers");
			for(String vtype:POI.POIbyTypes.keySet()){
				ps.print("\t"+vtype);
			}
			ps.println();
			for(City c:POI.POIbyCityTypes.keySet()){
				ps.print(c.getCityName()+"\t"+c.getLat()+"\t"+c.getLon()+"\t"+c.getCityUsers().size());
				for(String vtype:POI.POIbyTypes.keySet()){
					if(POI.POIbyCityTypes.get(c).containsKey(vtype)){
						ps.print("\t"+POI.POIbyCityTypes.get(c).get(vtype).size());
					}else{
						ps.print("\t0");
					}
				}
				ps.println();
			}
			
			ps.close();
			
			
			ps =new PrintStream("C:\\Users\\xiongha\\Desktop\\foursquare\\dataset_TIST2015\\HCKcounts.txt");
			ps.print("CityName\tLat\tLon\tUsers");
			for(String vtype:POI.POIbyTypes.keySet()){
				ps.print("\t"+vtype);
			}
			ps.println();
			for(City c:POI.POIbyCityTypes.keySet()){
				ps.print(c.getCityName()+"\t"+c.getLat()+"\t"+c.getLon()+"\t"+c.getCityUsers().size());
				for(String vtype:POI.POIbyTypes.keySet()){
					if(POI.POIbyCityTypes.get(c).containsKey(vtype)){
						ps.print("\t"+POI.getHomeCheckins(POI.POIbyCityTypes.get(c).get(vtype)).size());
					}else{
						ps.print("\t0");
					}
				}
				ps.println();
			}
			
			ps.close();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
