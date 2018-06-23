package com.funcy.g01.ranking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funcy.g01.base.bo.ranking.RoleRankingInfo;
import com.funcy.g01.base.net.RankingServerClient;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleRankingInfoProto;
import com.funcy.g01.base.proto.ranking.RankingRespCmdProtoBuffer.BytesRespProto;
import com.funcy.g01.base.proto.ranking.RankingRespCmdProtoBuffer.GetRankingListRespProto;
import com.funcy.g01.ranking.bo.RankingBigType;
import com.funcy.g01.ranking.bo.RankingClient;
import com.funcy.g01.ranking.bo.RankingInfoManager;
import com.funcy.g01.ranking.bo.RankingSmallType;
import com.google.protobuf.Message.Builder;

@Service
public class RankingService4Server {

	@Autowired
	private RankingInfoManager rankingInfoManager;
	
	public Builder getRankingList(int bigType, int smallType, String cityType, int fromRanking, int num, boolean needSelf, long roleId, RankingClient rankingClient) {
		RankingBigType rankingBigType = RankingBigType.valueOf(bigType);
		RankingSmallType rankingSmallType = RankingSmallType.values()[smallType];
		GetRankingListRespProto.Builder builder = GetRankingListRespProto.newBuilder();
		List<RoleRankingInfo> roleRankingList = rankingInfoManager.getRankingList(rankingBigType, rankingSmallType, cityType, fromRanking, num);
		for(RoleRankingInfo rankingInfo : roleRankingList) {
			RoleRankingInfoProto.Builder proto = rankingInfo.buildProto(rankingBigType, rankingSmallType, cityType);
			builder.addRankingList(proto);
//			System.out.println(proto.toString());
		}
		if(needSelf) {
			RoleRankingInfo rankingInfo = rankingInfoManager.getRoleRankingInfo(roleId);
			if(rankingInfo != null) {
				builder.setSelf(rankingInfo.buildProto(rankingBigType, rankingSmallType, cityType));
			}
		}
		BytesRespProto.Builder bytesBuilder = BytesRespProto.newBuilder();
		bytesBuilder.addByteArray(builder.build().toByteString());
		return bytesBuilder;
	}
	
}
