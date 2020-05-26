package javastory.club.stage3.step4.da.map;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javastory.club.stage3.step1.entity.AutoIdEntity;
import javastory.club.stage3.step1.entity.club.TravelClub;
import javastory.club.stage3.step4.da.map.io.MemoryMap;
import javastory.club.stage3.step4.store.ClubStore;
import javastory.club.stage3.step4.util.ClubDuplicationException;

public class ClubMapStore implements ClubStore {
	//
	private Map<String,TravelClub> clubMap; 
	private Map<String,Integer> autoIdMap; 

	public ClubMapStore() {
		//  
		this.clubMap = MemoryMap.getInstance().getClubMap(); 
		this.autoIdMap = MemoryMap.getInstance().getAutoIdMap(); 
	}

	@Override
	public String create(TravelClub club) {
		// 
//		if (clubMap.get(club.getId()) != null) {
//			throw new ClubDuplicationException("Club already exists with id: " + club.getId()); 
//		}
		Optional.ofNullable(clubMap.get(club.getId())).ifPresent(targetClub->{throw new ClubDuplicationException("Club already exists with id: " + targetClub.getId()); });
		
		if (club instanceof AutoIdEntity) {
			String className = TravelClub.class.getSimpleName(); 
			if(autoIdMap.get(className) == null) {
				autoIdMap.put(className, 1); 
			}
			int keySequence = autoIdMap.get(className); 
			String autoId = String.format("%05d", keySequence); 
			club.setAutoId(autoId);
			autoIdMap.put(className, ++keySequence);
		}

		clubMap.put(club.getId(),  club); 

		return club.getId();
	}

	@Override
	public TravelClub retrieve(String clubId) {
		// 
		return clubMap.get(clubId); 
	}

	@Override
	public TravelClub retrieveByName(String name) {
		//
		Iterator<TravelClub> clubIter = clubMap.values().iterator(); 
		TravelClub clubFound = null; 
		while(clubIter.hasNext()) {
			TravelClub club = clubIter.next(); 
			if (club.getName().equals(name)) {
				clubFound = club;
				break; 
			}
		}

		return clubFound; 
	}

	@Override
	public void update(TravelClub club) {
		// 
		//		if (clubMap.get(club.getId()) == null) {
		//			throw new NoSuchElementException("No such a element: " + club.getId()); 
		//		}


		//		clubMap.put(club.getId(), club);
//		Optional.ofNullable(clubMap.get(club.getId()))
//		.orElseThrow(()->new NoSuchElementException("No such a element: " + club.getId()));
		
		clubMap.put(club.getId(), club);
	}

	@Override
	public void delete(String clubId) {
		// 
		clubMap.remove(clubId); 
	}

	@Override
	public boolean exists(String clubId) {
		//
		//		if (clubMap.get(clubId) != null) {
		//			return true; 
		//		};
		//		
		//		return false;
		return Optional.ofNullable(clubMap.get(clubId)).isPresent();
	}
}