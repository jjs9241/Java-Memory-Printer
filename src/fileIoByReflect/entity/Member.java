package fileIoByReflect.entity;

import java.util.ArrayList;
import java.util.List;

import java.lang.Exception;

//import util.InvalidEmailException;

public class Member implements Entity {
	//
	private String email;		// key 
	private String name; 
	private String nickName; 
	private String phoneNumber; 
	private String birthDay; 

	private List<Address> addresses; 
	private List<Membership> membershipList;
	
	public Member() {
		// 
		this.membershipList = new ArrayList<>(); 
		this.addresses = new ArrayList<>(); 
	}
	
	public Member(String email, String name, String phoneNumber) throws Exception {
		// 
		this(); 
		setEmail(email);
		this.name = name; 
		this.phoneNumber = phoneNumber; 
	}

	@Override
	public String toString() {
		// 
		StringBuilder builder = new StringBuilder(); 
		
		builder.append("Name:").append(name); 
		builder.append(", email:").append(email); 
		builder.append(", nickname:").append(nickName); 
		builder.append(", phone number:").append(phoneNumber); 
		builder.append(", birthDay:").append(birthDay); 

		if (addresses != null) {
			int i = 1; 
			for(Address address : addresses) {
				builder.append(", Address[" + i + "]").append(address.toString()); 
			}
		}
		
		return builder.toString(); 
	}
	
	public static Member getSample() {
		// 
		Member member = null;
		try {
			member = new Member("sample@sample.com", "sample name", "010-0000-0000");
			member.setBirthDay("2000.01.01");
			member.getAddresses().add(Address.getHomeAddressSample());
		} catch (Exception e) {//InvalidEmailException e) {
			System.out.println(e.getMessage());
		}
		return member; 
	}
	
	public void addMembership(Membership membership) {
		if (membership == null) {
			return;
		} else if (membership.getMemberEmail() != this.email) {
			return;
		}
		this.membershipList.add(membership);
	}

	@Override
	public String getId() {
		return email; 
	}

	public List<Membership> getMembershipList() {
		return this.membershipList; 
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws Exception {
		//
		if (!this.isValidEmailAddress(email)) {
			String message = "Email is not valid. --> " + email;
			throw new Exception(message); 
		}
		
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}
	
	public List<Address> getAddresses() {
		return addresses;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

    private boolean isValidEmailAddress(String email) {
    	//
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
