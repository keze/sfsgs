package com.dks.bgame.server.sfs2x;

import java.awt.font.NumericShaper;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.dks.bgame.server.games.Game;
import com.dks.bgame.server.games.NumberGame;
import com.smartfoxserver.v2.annotations.Instantiation;
import com.smartfoxserver.v2.annotations.Instantiation.InstantiationMode;
import com.smartfoxserver.v2.api.CreateRoomSettings;
import com.smartfoxserver.v2.api.CreateRoomSettings.RoomExtensionSettings;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.entities.match.BoolMatch;
import com.smartfoxserver.v2.entities.match.MatchExpression;
import com.smartfoxserver.v2.entities.match.RoomProperties;
import com.smartfoxserver.v2.exceptions.SFSCreateRoomException;
import com.smartfoxserver.v2.exceptions.SFSJoinRoomException;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;

@Instantiation(InstantiationMode.NEW_INSTANCE)
public class StartGameHandler extends BaseClientRequestHandler {
		
	@Override
	public void handleClientRequest(User player, ISFSObject params) {

		MyGameExt parentExt = (MyGameExt) getParentExtension();

		parentExt.trace("Start game handler requested");

		// TODO: Matchmaking	
		parentExt.trace("Matchmaking");
		// Prepare match expression
		MatchExpression exp = new MatchExpression(RoomProperties.IS_GAME,
				BoolMatch.EQUALS, true).and(
				RoomProperties.HAS_FREE_PLAYER_SLOTS, BoolMatch.EQUALS, true);
		

		List<Room> availableRooms = parentExt.getApi().findRooms(
				parentExt.getParentZone().getRoomList(), exp, 0);

		if (availableRooms.size() > 0) {

			Room room = (Room) availableRooms.get(0);
			
			parentExt.trace("Room found:" + room.getName());
			try {
				room.addUser(player);
				
				parentExt.trace("Room members count after join:"
						+ room.getUserList().size());
				
			} catch (SFSJoinRoomException e) {
				// TODO: handle exception

				trace(ExtensionLogLevel.ERROR, e.getMessage());
			}

			parentExt.trace("Room user list size:" + room.getUserList().size());
			parentExt.trace("Max users:" + room.getMaxUsers());
			
			for (User user : room.getUserList()) {
				parentExt.trace(user.getName());
			}
			
			if (room.getUserList().size() == room.getMaxUsers()) 
			{
				parentExt.CurrentGame = CreateGame(room.getUserList());
				
				NumberGame game = ((NumberGame)parentExt.CurrentGame);
				
				ISFSObject rtn = new SFSObject();
				
				
				rtn.putInt("target", game.TargetNumber);
				rtn.putInt("n0", game.availableNumbers[0]);
				rtn.putInt("n1", game.availableNumbers[1]);
				rtn.putInt("n2", game.availableNumbers[2]);
				rtn.putInt("n3", game.availableNumbers[3]);
				rtn.putInt("n4", game.availableNumbers[4]);
				rtn.putInt("n5", game.availableNumbers[5]);
				
				//rtn.putIntArray("numbers", new ArrayList(Arrays.asList(game.availableNumbers)));;
			
				parentExt.trace("Sending response...");
				send("startgame", rtn, room.getUserList());
				parentExt.trace("Sent");
			}
		} else {

			parentExt.trace("Room not found");
			
			String roomName = GetRandomRoomName();

			CreateRoomSettings settings = new CreateRoomSettings();
			settings.setName(roomName);
			settings.setGame(true);
			settings.setMaxUsers(2);
			settings.setMaxSpectators(0);
			settings.setExtension(new RoomExtensionSettings("MyGameExtension",
					"com.dks.bgame.server.sfs2x.MyGameExt"));

			try {

				Room room = parentExt.getParentZone().createRoom(settings, player);
				parentExt.trace("Room created:" + roomName);
				parentExt.trace("Room members count before join:" + room.getUserList().size());

				room.addUser(player);

				parentExt.trace("Room members count after join:" + room.getUserList().size());
			} catch (SFSCreateRoomException e) {
				// TODO: handle exception

				trace(ExtensionLogLevel.ERROR, e.getMessage());
			} catch (SFSJoinRoomException e) {
				// TODO: handle exception

				trace(ExtensionLogLevel.ERROR, e.getMessage());
			}
		}
	}

	private String GetRandomRoomName() {
		return "Game#" + new Random().nextInt(1000);
	}

	
	private Game CreateGame(List<User> players) {
		
		Game game = new NumberGame();
		game.start();
		
		return game;
	}
}