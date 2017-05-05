package ch.simonsky.partysystem.enums;

public enum QuitReason {
	
	NORMAL_QUIT(0),
	KICK_QUIT(1),
	CLOSE_QUIT(2);
	
	int id;
	
	QuitReason(int id){
		this.id = id;
	}
	
	public int getReasonID(){
		return id;
	}

}
