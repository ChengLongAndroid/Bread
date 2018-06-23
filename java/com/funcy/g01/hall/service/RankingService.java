package com.funcy.g01.hall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funcy.g01.base.global.ServerContext;
import com.funcy.g01.base.net.GamePlayer;
import com.funcy.g01.base.net.RankingServerClient;
import com.funcy.g01.base.proto.ranking.RankingRespCmdProtoBuffer.BytesRespProto;
import com.funcy.g01.base.proto.service.ReqCmdProto.IntIntStringIntIntBoolLongProto;
import com.google.protobuf.Message.Builder;

@Service
public class RankingService {
	
	@Autowired
	private ServerContext serverContext;

	public Builder getRankingList(int bigType, int smallType, String cityType, int fromRanking, int num, boolean needSelf, GamePlayer gamePlayer) {
		RankingServerClient rankingServerClient = serverContext.borrowRankingServerClient();
		try {
			IntIntStringIntIntBoolLongProto.Builder builder = IntIntStringIntIntBoolLongProto.newBuilder();
			builder.setParams1(bigType);
			builder.setParams2(smallType);
			builder.setParams3(cityType);
			builder.setParams4(fromRanking);
			builder.setParams5(num);
			builder.setParams6(needSelf);
			builder.setParams7(gamePlayer.getRoleId());
			BytesRespProto resp = (BytesRespProto)rankingServerClient.sendAndWaitResp("rankingService4Server.getRankingList", builder);
			return resp.toBuilder();
		} finally {
			serverContext.addRankingServerClient4Send(rankingServerClient);
		}
	}
	
}
