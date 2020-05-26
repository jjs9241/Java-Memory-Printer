package fileIoByReflect.entity;

import java.util.ArrayList;
import java.util.List;

import java.util.Date;

public class Club implements Entity {
	private String id; 		// auto incremental style
	private String foundationDay; 
	
	private List<Membership> membershipList; 
	
	private Club() {
		this.membershipList = new ArrayList<Membership>(); 
	}
	
	public Club(String id) {
		// 
		this(); 
		this.setId(id); 
		this.foundationDay = (new Date()).toString(); 
	}
	
	@Override
	public String toString() {
		// 
		StringBuilder builder = new StringBuilder(); 
		
		builder.append("Club Id:").append(id); 
		builder.append(", foundation day:").append(foundationDay); 
		
		return builder.toString(); 
	}
	
	public static Club getSample() {
		// 
		String id = "Club Sample"; 
		Club club = new Club(id); 

		return club; 
	}
	
	@Override
	public String getId() {
		return id; 
	}
	
	public void setId(String id) {
		this.id = id; 
	}
	
	public Membership getMembershipBy(String email) {
		//
		if (email == null || email.isEmpty()) {
			return null;
		}
		
		for (Membership membership : this.membershipList) {
			if(email.equals(membership.getMemberEmail())){
				return membership;
			}
		}
		return null;
	}
	
	public List<Membership> getMembershipList() {
		return this.membershipList; 
	}
	
	public void addMembership(Membership membership) {
		if (membership == null) {
			return;
		} else if (membership.getClubId() != this.id) {
			return;
		}
		this.membershipList.add(membership);
	}
	
	public void deleteMembership(String email) {
		//
		if (email == null || email.isEmpty()) {
			return;
		}
		
		for (Membership membership : this.membershipList) {
			if(email.equals(membership.getMemberEmail())){
				this.membershipList.remove(membership);
			}
		}
		return;
	}
	
	public String getFoundationDay() {
		return foundationDay; 
	}
	
	public void setFoundationDay (String foundationDay) {
		//
		this.foundationDay = foundationDay; 
	}
}
