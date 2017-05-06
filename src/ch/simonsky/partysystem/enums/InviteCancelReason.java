package ch.simonsky.partysystem.enums;

public enum InviteCancelReason {
	
	TIMEOUT(0),
	DECLINE(1),
	ACCEPT(2),
	DISSABLED(3),
	CANCEL(4);
	
	int id;
	
	InviteCancelReason(int id){
		this.id = id;
	}
	
	public int getReasonID(){
		return id;
	}

}
