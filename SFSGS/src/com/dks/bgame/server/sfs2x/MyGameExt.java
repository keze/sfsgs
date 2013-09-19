package com.dks.bgame.server.sfs2x;

import java.util.Random;

import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.extensions.SFSExtension;

public class MyGameExt extends SFSExtension {

	@Override
	public void init() {
		
		trace("Gues  game Extension for SFS2X started");
		
			
		randomNumber = new Random().nextInt(1000);
		
		
		this.addRequestHandler("startgame", StartGameHandler.class);
		this.addRequestHandler("guess", GuessHandler.class);
		
		this.addEventHandler(SFSEventType.USER_LOGIN, LoginEventHandler.class);
		//this.addEventHandler(SFSEventType.USER_LEAVE_ROOM, OnUserGoneHandler.class);
		
		trace("Random number is : " + randomNumber);
	}

	
	@Override
	public void destroy() {
		super.destroy();
		trace("Tris game destroyed!");
	}
	
	public int randomNumber;	
}
