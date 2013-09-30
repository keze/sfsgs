package com.dks.bgame.server.games;

import java.util.Random;

import com.smartfoxserver.v2.entities.User;


public class NumberGame extends Game {

	public int TargetNumber;
	public int[] availableNumbers = new int[6];
	
	public String player1Response;
	public String player2Repsonse;

	public int player1ResponseValue;
	public int player2RepsonseValue; 
	
	@Override
	public void start() {
		
		Random randGen = new Random();
		
		TargetNumber = randGen.nextInt(1000);
		
		availableNumbers[0] = randGen.nextInt(10);
		availableNumbers[1] = randGen.nextInt(10);
		availableNumbers[2] = randGen.nextInt(10);
		availableNumbers[3] = randGen.nextInt(10);
		availableNumbers[4] = (randGen.nextInt(5)+1)*5;		
		availableNumbers[5] = (randGen.nextInt(4)+1)*25;			
	}

	@Override
	public boolean isFinished() {
		
		return (player1Response != "" && player2Repsonse != "");
	}
	
	public void Response(User player, String response) {
		
		if (player == player1){
			player1Response = response;
			
			// TODO evaluate response
		}
		else if (player == player2) {
			
			player2Repsonse = response;
			// TODO evaluate response			
		}
		else {
			// TODO throw exception
		}
	}
	
	

	
	
}
