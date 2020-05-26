package javastory.club.stage3.step4.da.map;

import javastory.club.stage3.step4.store.*;

public class ClubStoreMapLycler implements ClubStoreLycler {
	//
	private static ClubStoreLycler lycler; 
	
	private ClubStoreMapLycler() {
	}; 
	
	public static ClubStoreLycler getInstance() {
		// 
		if (lycler == null) {
			lycler = new ClubStoreMapLycler(); 
		}
		
		return lycler;
	}
	
	@Override
	public MemberStore requestMemberStore() {
		// 
		return new MemberMapStore();
	}

	@Override
	public ClubStore requestClubStore() {
		// 
		return new ClubMapStore();
	}

	@Override
	public BoardStore requestBoardStore() {
		// 
		return new BoardMapStore();
	}

	@Override
	public PostingStore requestPostingStore() {
		// 
		return new PostingMapStore();
	}

	@Override
	public IOStore requestIOStore() {
		//
		return new IOMapStore();
	}

	/*private void bootMap() {
		//
	}

	private void backUp() {
		//
		Map<String, CommunityMember> memberMap = MemoryMap.getInstance().getMemberMap();
		Map<String, TravelClub> clubMap = MemoryMap.getInstance().getClubMap();
		Map<String, SocialBoard> boardMap = MemoryMap.getInstance().getBoardMap();
		Map<String, Posting> postingMap = MemoryMap.getInstance().getPostingMap();
		Map<String, Integer> autoIdMap = MemoryMap.getInstance().getAutoIdMap();

		Set<ClubMembership> membershipSet = new LinkedHashSet<>();
		Set<Address> addressSet = new LinkedHashSet<>();

		List <CommunityMember> memberList = memberMap.values().stream().filter(m->membershipSet.addAll(m.getMembershipList())).filter(m->addressSet.addAll(m.getAddresses())).collect(Collectors.toList());
		List<TravelClub> clubList = clubMap.values().stream().collect(Collectors.toList());
		List<SocialBoard> boardList = boardMap.values().stream().collect(Collectors.toList());
		List<Posting> postingList = postingMap.values().stream().collect(Collectors.toList());

		List<MemberPfo> memberPfos = new ArrayList<>();
		List<ClubPfo> clubPfos = new ArrayList<>();
		List<BoardPfo> boardPfos = new ArrayList<>();
		List<PostingPfo> postingPfos = new ArrayList<>();


		for(CommunityMember member : memberList) {
			MemberPfo memberPfo = new MemberPfo(member);
			memberPfos.add(memberPfo);
		}

		for(TravelClub club : clubList) {
			ClubPfo clubPfo = new ClubPfo(club);
			clubPfos.add(clubPfo);
		}

		for(SocialBoard board : boardList) {
			BoardPfo boardPfo = new BoardPfo(board);
			boardPfos.add(boardPfo);
		}

		for(Posting posting : postingList){
			PostingPfo postingPfo = new PostingPfo(posting);
			postingPfos.add(postingPfo);
		}

		autoIdMap.keySet().stream();
	}*/
}