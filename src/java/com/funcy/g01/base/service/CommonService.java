package com.funcy.g01.base.service;

import org.springframework.stereotype.Service;
import com.funcy.g01.base.net.GamePlayer;

@Service
public class CommonService {

	public void cutoutGame(GamePlayer gamePlayer) {
		gamePlayer.setCutOut(true);
	}
	
	public void comeBackGame(GamePlayer gamePlayer) {
		gamePlayer.setCutOut(false);
		
	}
	
}
