package com.dks.bgame.server.sfs2x;

import com.smartfoxserver.v2.annotations.Instantiation;
import com.smartfoxserver.v2.annotations.Instantiation.InstantiationMode;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

@Instantiation(InstantiationMode.NEW_INSTANCE)
public class GuessHandler extends BaseClientRequestHandler {

	private static final String CMD_RES = "res";
	//private static final String CMD_TIE = "tie";
	//private static final String CMD_MOVE = "move";
	
	
	@Override
	public void handleClientRequest(User player, ISFSObject params) {
		
		MyGameExt parentExt = (MyGameExt) getParentExtension();
				
		parentExt.trace("Guess handler requested");
		
		int n = params.getInt("number");
		
		parentExt.trace("Guess number : " + n);
		
		
		
		
				
		ISFSObject rtn = new SFSObject();
		
		if (parentExt.randomNumber > n)
			rtn.putUtfString("res", "less");
		else if (parentExt.randomNumber < n)
			rtn.putUtfString("res", "greater");
		else
			rtn.putUtfString("res", "equal");
			
		parentExt.send(CMD_RES, rtn, player);		
	}

}
