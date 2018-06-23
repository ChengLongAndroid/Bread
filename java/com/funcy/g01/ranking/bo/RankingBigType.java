package com.funcy.g01.ranking.bo;

import java.util.Comparator;

import com.funcy.g01.base.bo.ranking.RoleRankingInfo;

public enum RankingBigType {
//	charm(0) {
//		public Comparator<RoleRankingInfo> getComparator(RankingSmallType rankingSmallType) {
//			return new Comparator<RoleRankingInfo>() {
//				@Override
//				public int compare(RoleRankingInfo o1, RoleRankingInfo o2) {
//					if(o1.getRecordCharm() > o2.getRecordCharm()) {
//						return -1;
//					} else if(o1.getRecordCharm() < o2.getRecordCharm()) {
//						return 1;
//					}
//					return 0;
//				}
//			};
//		}
//	}, 
//	achievement(0, 1) {
//		public Comparator<RoleRankingInfo> getComparator(RankingSmallType rankingSmallType) {
//			return new Comparator<RoleRankingInfo>() {
//				@Override
//				public int compare(RoleRankingInfo o1, RoleRankingInfo o2) {
//					if(o1.getRecordAchievement() > o2.getRecordAchievement()) {
//						return -1;
//					} else if(o1.getRecordAchievement() < o2.getRecordAchievement()) {
//						return 1;
//					}
//					return 0;
//				}
//			};
//		}
//	}, 
//	licenseLevel(0) {
//		public Comparator<RoleRankingInfo> getComparator(RankingSmallType rankingSmallType) {
//			return new Comparator<RoleRankingInfo>() {
//				@Override
//				public int compare(RoleRankingInfo o1, RoleRankingInfo o2) {
//					if(o1.getRecordLicenseLevel() > o2.getRecordLicenseLevel()) {
//						return -1;
//					} else if(o1.getRecordLicenseLevel() < o2.getRecordLicenseLevel()) {
//						return 1;
//					}
//					return 0;
//				}
//			};
//		}
//	}, 
//	fans(1) {
//		public Comparator<RoleRankingInfo> getComparator(RankingSmallType rankingSmallType) {
//			if(rankingSmallType ==  RankingSmallType.day) {
//				return new Comparator<RoleRankingInfo>() {
//					@Override
//					public int compare(RoleRankingInfo o1, RoleRankingInfo o2) {
//						if(o1.getLastDayFansNum() > o2.getLastDayFansNum()) {
//							return -1;
//						} else if(o1.getLastDayFansNum() < o2.getLastDayFansNum()) {
//							return 1;
//						}
//						return 0;
//					}
//				};
//			} else if(rankingSmallType == RankingSmallType.week) {
//				return new Comparator<RoleRankingInfo>() {
//					@Override
//					public int compare(RoleRankingInfo o1, RoleRankingInfo o2) {
//						if(o1.getLastWeekFansNum() > o2.getLastWeekFansNum()) {
//							return -1;
//						} else if(o1.getLastWeekFansNum() < o2.getLastWeekFansNum()) {
//							return 1;
//						}
//						return 0;
//					}
//				};
//			} else {
//				return new Comparator<RoleRankingInfo>() {
//					@Override
//					public int compare(RoleRankingInfo o1, RoleRankingInfo o2) {
//						if(o1.getAllFansNum() > o2.getAllFansNum()) {
//							return -1;
//						} else if(o1.getAllFansNum() < o2.getAllFansNum()) {
//							return 1;
//						}
//						return 0;
//					}
//				};
//			}
//		}
//	}, 
	champion(1, 4) {
		public Comparator<RoleRankingInfo> getComparator(RankingSmallType rankingSmallType) {
			if(rankingSmallType ==  RankingSmallType.month) {
				return new Comparator<RoleRankingInfo>() {
					@Override
					public int compare(RoleRankingInfo o1, RoleRankingInfo o2) {
						if(o1.getNewDayChampionNum() > o2.getNewDayChampionNum()) {
							return -1;
						} else if(o1.getNewDayChampionNum() < o2.getNewDayChampionNum()) {
							return 1;
						}
						return 0;
					}
				};
			} else if(rankingSmallType == RankingSmallType.week) {
				return new Comparator<RoleRankingInfo>() {
					@Override
					public int compare(RoleRankingInfo o1, RoleRankingInfo o2) {
						if(o1.getNewWeekChampionNum() > o2.getNewWeekChampionNum()) {
							return -1;
						} else if(o1.getNewWeekChampionNum() < o2.getNewWeekChampionNum()) {
							return 1;
						}
						return 0;
					}
				};
			} else {
				return new Comparator<RoleRankingInfo>() {
					@Override
					public int compare(RoleRankingInfo o1, RoleRankingInfo o2) {
						if(o1.getAllChampionNum() > o2.getAllChampionNum()) {
							return -1;
						} else if(o1.getAllChampionNum() < o2.getAllChampionNum()) {
							return 1;
						}
						return 0;
					}
				};
			}
		}
	}, 
	rescue(0, 5) {
		public Comparator<RoleRankingInfo> getComparator(RankingSmallType rankingSmallType) {
			return new Comparator<RoleRankingInfo>() {
				@Override
				public int compare(RoleRankingInfo o1, RoleRankingInfo o2) {
					if(o1.getAllRescueNum() > o2.getAllRescueNum()) {
						return -1;
					} else if(o1.getAllRescueNum() < o2.getAllRescueNum()) {
						return 1;
					} 
					return 0;
				}
			};
		}
	};
	
	private final int rankingType; //0按区域 1按时间
	
	private final int code;
	
	private RankingBigType(int rankingType, int code) {
		this.rankingType = rankingType;
		this.code = code;
	}

	public int getRankingType() {
		return rankingType;
	}
	
	public static RankingBigType valueOf(int code) {
		for(RankingBigType rankingBigType : values()) {
			if(rankingBigType.code == code) {
				return rankingBigType;
			}
		}
		throw new RuntimeException("no code:" + code);
	}
	
	abstract Comparator<RoleRankingInfo> getComparator(RankingSmallType rankingSmallType);
}
