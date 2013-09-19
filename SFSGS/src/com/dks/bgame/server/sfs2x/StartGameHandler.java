package com.dks.bgame.server.sfs2x;

import java.util.Random;

import com.smartfoxserver.v2.annotations.Instantiation;
import com.smartfoxserver.v2.annotations.Instantiation.InstantiationMode;
import com.smartfoxserver.v2.api.CreateRoomSettings;
import com.smartfoxserver.v2.api.CreateRoomSettings.RoomExtensionSettings;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
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
	
	
	String roomName = GetRandomRoomName();
	
	CreateRoomSettings settings = new CreateRoomSettings();
	settings.setName(roomName);
	settings.setGame(true);
	settings.setMaxUsers(2);
	settings.setMaxSpectators(0);
	settings.setExtension(new RoomExtensionSettings("MyGameExtension", "com.dks.bgame.server.sfs2x.MyGameExt"));
	
	try	{
		
	Room room = parentExt.getParentZone().createRoom(settings, player);	
	parentExt.trace("Room created:" + roomName);
	parentExt.trace("Room members count:" + room.getUserList().size());
	
	room.addUser(player);
	
	parentExt.trace("Room members count:" + room.getUserList().size());
	}
	catch (SFSCreateRoomException e) {			
		// TODO: handle exception
		
		trace(ExtensionLogLevel.ERROR, e.getMessage());
	}
	catch (SFSJoinRoomException e) {
		// TODO: handle exception
		
		trace(ExtensionLogLevel.ERROR, e.getMessage());
	}
}

private String GetRandomRoomName(){
	return "Game#" + new Random().nextInt(1000);
}

}