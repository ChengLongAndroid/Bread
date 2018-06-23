package com.funcy.g01.base.exception;

public class ErrorCode {
    public static final int SUCCESS = 1000000;
    
    public static final int CMD_PARAM_NUM_ERROR = 1000001;

    public static final int CMD_INDEX_ILLEGAL = 1000002;
    
    public static final int UNKNOWN_ERROR = 1000003;
    
    public static final int SESSIONID_ERROR = 1000004;//sessionid 不合法

    public static final int LOGIN_TIMEOUT = 1000005; //登陆超时
    
    public static final int SERVER_FULL = 1000010; //服务器已满
    
    public static final int WRONG_CODE = 2000001;
    
    public static final int NOT_ENOUGH_ITEM = 2000002;
    
    public static final int NO_ITEM = 2000003;
    
    public static final int WRONG_SHORTSPELL = 2000004;
    
    public static final int NO_TOTEM = 2000005;
    
    public static final int NO_SKILL = 2000006;
    
    public static final int SKILL_IS_NOT_MATCH_TOTEM = 2000007;
    
    public static final int SKILL_STAR_LEVEL_LIMIT = 200008;
    
    public static final int EQUIP_SKILL_LEVEL_LIMIT = 200009;
    
    public static final int TOTEM_IS_NOT_ENABLED = 200010;
    
    public static final int NO_DRESS = 200011;
    
    public static final int BUY_LEVEL_LIMIT = 200012;
    
    public static final int ITEM_CAN_NOT_CELL = 200013;
    
    public static final int ITEM_CAN_NOT_USE = 200014;
    
    public static final int WRONG_CURRENCY_PRICE = 3000001;
    
    public static final int NOT_ENOUGH_CHEESE = 3000002;
    
    public static final int NOT_ENOUGH_GEM = 3000003;
    
    public static final int WRONG_CURRENCY_TYPE = 3000004;

    public static final int no_achieve = 4000001;
    
    
    public static final int DIRTY_WORD = 9000002;
    
    public static final int EMAIL_NO_EXIT = 710001;
	
	public static final int EMAIL_NO_REWARDS = 710002;
	
	public static final int EMAIL_HAD_GOT = 710003;
	
	public static final int EMAIL_IS_NOT_YOUR = 710004;
	
	public static final int npc_not_talk_task = 720001;
	
	public static final int npc_can_not_accept = 720002;
	
	public static final int npc_can_not_complete = 720003;
	
	public static final int npc_can_not_get_present = 720004;
	
	public static final int chest_slot_no_chest = 730001;
	
	public static final int today_had_sign_in = 730002;
	
	public static final int exchange_time_out = 730003;
	
	public static final int exchange_needs_not_enough = 730004;
	
	public static final int out_exchange_num = 730005;
	
	public static final int can_not_exsit_exchange = 730006;
	
	public static final int signin_time_out = 730007;
    
}
