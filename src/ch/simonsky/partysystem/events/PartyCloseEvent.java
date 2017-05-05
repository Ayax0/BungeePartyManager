package ch.simonsky.partysystem.events;

import ch.simonsky.partysystem.manager.ProxyParty;
import net.md_5.bungee.api.plugin.Event;

public class PartyCloseEvent extends Event{
	
	final ProxyParty party;
	
	public PartyCloseEvent(ProxyParty party){
		this.party = party;	
	}
	
	public ProxyParty getParty(){
		return party;
	}

}
