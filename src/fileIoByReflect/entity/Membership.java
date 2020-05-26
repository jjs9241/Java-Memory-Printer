package fileIoByReflect.entity;

import java.util.Date;

public class Membership {
	//
	private String clubId; 
	private String memberEmail; 
	private Role role;
	private String joinDate;

	public Membership() {
		//
		this.role = Role.Member;
	}
	
	public Membership(Club club, Member member) {
		//
		this();
		this.clubId = club.getId(); 
		this.memberEmail = member.getEmail(); 

		this.role = Role.Member;
		this.joinDate = (new Date()).toString();
	}
	
	public Membership(String clubId, String memberEmail) {
		//
		this();
		this.clubId = clubId; 
		this.memberEmail = memberEmail;
		
		this.role = Role.Member;
		this.joinDate = (new Date()).toString();
	}

	@Override
	public String toString() {
		// 
		StringBuilder builder = new StringBuilder(); 
		
		builder.append("club Id:").append(clubId); 
		builder.append(", member email:").append(memberEmail); 
		builder.append(", role:").append(role.name());
		builder.append(", join date:").append(joinDate); 
		
		return builder.toString(); 
	}
	
	public static Membership getSample(Club club, Member member) {
		// 
		Membership membership = new Membership(club, member); 
		membership.setRole(Role.Member);
		
		return membership; 
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getClubId() {
		return clubId;
	}

	public String getMemberEmail() {
		return memberEmail;
	}
}
