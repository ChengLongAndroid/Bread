package com.funcy.g01.base.bo.npc;

public enum NpcTaskType {
	//1:对话任务 2:送信任务 3：参与一定数量的比赛 4：当一定数量的萨满 5：获得一定数量的前三名 6：需求物品 7：比赛中说话n次，8：比赛中发n次表情
	talk(1), mail(2), fight(3), saman(4), top3(5), item(6), chat(7), chatWithExpression(8);
	
	private final int code;
	
	private NpcTaskType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
	public static NpcTaskType getByCode(int code) {
		for(NpcTaskType npcTaskType : values()) {
			if(code == npcTaskType.getCode()) {
				return npcTaskType;
			}
		}
		return null;
	}
	
}
