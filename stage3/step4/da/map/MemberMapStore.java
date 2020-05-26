package javastory.club.stage3.step4.da.map;

import java.util.*;
import java.util.stream.Collectors;

import javastory.club.stage3.step1.entity.club.ClubMembership;
import javastory.club.stage3.step1.entity.club.CommunityMember;
import javastory.club.stage3.step4.da.map.io.MemoryMap;
import javastory.club.stage3.step4.store.MemberStore;
import javastory.club.stage3.step4.util.MemberDuplicationException;

public class MemberMapStore implements MemberStore {
	//
	private Map<String,CommunityMember> memberMap;

	public MemberMapStore() {
		//
		this.memberMap = MemoryMap.getInstance().getMemberMap();
	}
	
	@Override
	public String create(CommunityMember member) {
		// 
//		if (memberMap.get(member.getId()) != null) {
//			throw new MemberDuplicationException("Member already exists with email: " + member.getId()); 
//		}
//		
//		memberMap.put(member.getId(), member);
		Optional.ofNullable(memberMap.get(member.getId())).ifPresent(targetMember->{throw new MemberDuplicationException("Member already exists with email: " + member.getId());} );
		memberMap.put(member.getId(), member);
		return member.getId();
	}

	@Override
	public CommunityMember retrieve(String memberId) {
		// 
		return memberMap.get(memberId); 
	}
	
	@Override
	public List<CommunityMember> retrieveByName(String name) {
		//
//		List<CommunityMember> members = new ArrayList<>(); 
//		Iterator<CommunityMember> memberIter = memberMap.values().iterator(); 
//		while(memberIter.hasNext()) {
//			CommunityMember member = memberIter.next(); 
//			if (member.getName().equals(name)) {
//				members.add(member); 
//			}
//		}
//		
//		return members;
		return memberMap.values().stream().filter(member->member.getName().equals(name)).collect(Collectors.toList());
	}

	@Override
	public void update(CommunityMember member) {
		// 
//		if (memberMap.get(member.getId()) == null) {
//			throw new NoSuchElementException("No such a member with email: " + member.getId()); 
//		}
//		
//		memberMap.put(member.getId(), member);
		
//		Optional.ofNullable(memberMap.get(member.getId())).orElseThrow(()->new NoSuchElementException("No such a member with email: " + member.getId()));
		memberMap.put(member.getId(), member);
	}

	@Override
	public void delete(String memberId) {
		//
		memberMap.remove(memberId);
	}

	@Override
	public boolean exists(String memberId) {
		//
//		if(memberMap.get(memberId) != null) {
//			return true; 
//		};
//		
//		return false;
		return Optional.ofNullable(memberMap.get(memberId)).isPresent();
	}
}