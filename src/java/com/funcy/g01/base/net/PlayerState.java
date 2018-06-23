package com.funcy.g01.base.net;

public enum PlayerState {
	connected, logon, disconnected;

    public static PlayerState convertToPlayerState(int intValue) {
        return values()[intValue];
    }
}
