package com.dks.bgame.server.games;

import com.smartfoxserver.v2.entities.User;

public abstract class Game {

	User player1;
	User player2;
	
	public abstract void start();
	
	public abstract boolean isFinished();
}
