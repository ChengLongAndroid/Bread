package com.funcy.g01.base.bo.ranking;

import java.util.HashMap;
import java.util.Map;

import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.RoleRankingInfoProto;
import com.funcy.g01.ranking.bo.RankingBigType;
import com.funcy.g01.ranking.bo.RankingSmallType;

public class RoleRankingInfo {
	private long roleId;

    private String image;

    private boolean sex;

    private String name;
    
    private int roleLevel;

    private String location;

    private int charm;

    private int achievement;

    private int licenseLevel;

    private int recordCharm;

    private int recordAchievement;

    private int recordLicenseLevel;

    private int dayFansNum;

    private int lastDayFansNum;
    
    private transient int newDayFansNum;

    private int weekFansNum;

    private int lastWeekFansNum;
    
    private transient int newWeekFansNum;

    private int allFansNum;

    //注，所有的日榜已修改为月，名字暂不改变
    private int dayChampionNum;

    private int lastDayChampionNum;
    
    private transient int newDayChampionNum;

    private int weekChampionNum;

    private int lastWeekChampionNum;
    
    private transient int newWeekChampionNum;

    private int allChampionNum;

    private int dayRescueNum;

    private int lastDayRescueNum;
    
    private transient int newDayRescueNum;

    private int weekRescueNum;

    private int lastWeekRescueNum;
    
    private transient int newWeekRescueNum;

    private int allRescueNum;
    
    private int photoFrame;
    
    private transient Map<String, Long> rankingMap = new HashMap<String, Long>();
    
    public RoleRankingInfo(long roleId) {
    	this.roleId = roleId;
    	this.image = "";
    	this.name = "";
    	this.location = "";
    }
    
    @SuppressWarnings("unused")
	private RoleRankingInfo() {}

	public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCharm() {
        return charm;
    }

    public void setCharm(int charm) {
        this.charm = charm;
    }

    public int getAchievement() {
        return achievement;
    }

    public void setAchievement(int achievement) {
        this.achievement = achievement;
    }

    public int getLicenseLevel() {
        return licenseLevel;
    }

    public void setLicenseLevel(int licenseLevel) {
        this.licenseLevel = licenseLevel;
    }

    public int getRecordCharm() {
        return recordCharm;
    }

    public void setRecordCharm(int recordCharm) {
        this.recordCharm = recordCharm;
    }

    public int getRecordAchievement() {
        return recordAchievement;
    }

    public void setRecordAchievement(int recordAchievement) {
        this.recordAchievement = recordAchievement;
    }

    public int getRecordLicenseLevel() {
        return recordLicenseLevel;
    }

    public void setRecordLicenseLevel(int recordLicenseLevel) {
        this.recordLicenseLevel = recordLicenseLevel;
    }

    public int getDayFansNum() {
        return dayFansNum;
    }

    public void setDayFansNum(int dayFansNum) {
        this.dayFansNum = dayFansNum;
    }

    public int getLastDayFansNum() {
        return lastDayFansNum;
    }

    public void setLastDayFansNum(int lastDayFansNum) {
        this.lastDayFansNum = lastDayFansNum;
    }

    public int getWeekFansNum() {
        return weekFansNum;
    }

    public void setWeekFansNum(int weekFansNum) {
        this.weekFansNum = weekFansNum;
    }

    public int getLastWeekFansNum() {
        return lastWeekFansNum;
    }

    public void setLastWeekFansNum(int lastWeekFansNum) {
        this.lastWeekFansNum = lastWeekFansNum;
    }

    public int getAllFansNum() {
        return allFansNum;
    }

    public void setAllFansNum(int allFansNum) {
        this.allFansNum = allFansNum;
    }

    public int getDayChampionNum() {
        return dayChampionNum;
    }

    public void setDayChampionNum(int dayChampionNum) {
        this.dayChampionNum = dayChampionNum;
    }

    public int getLastDayChampionNum() {
        return lastDayChampionNum;
    }

    public void setLastDayChampionNum(int lastDayChampionNum) {
        this.lastDayChampionNum = lastDayChampionNum;
    }

    public int getWeekChampionNum() {
        return weekChampionNum;
    }

    public void setWeekChampionNum(int weekChampionNum) {
        this.weekChampionNum = weekChampionNum;
    }

    public int getLastWeekChampionNum() {
        return lastWeekChampionNum;
    }

    public void setLastWeekChampionNum(int lastWeekChampionNum) {
        this.lastWeekChampionNum = lastWeekChampionNum;
    }

    public int getAllChampionNum() {
        return allChampionNum;
    }

    public void setAllChampionNum(int allChampionNum) {
        this.allChampionNum = allChampionNum;
    }

    public int getDayRescueNum() {
        return dayRescueNum;
    }

    public void setDayRescueNum(int dayRescueNum) {
        this.dayRescueNum = dayRescueNum;
    }

    public int getLastDayRescueNum() {
        return lastDayRescueNum;
    }

    public void setLastDayRescueNum(int lastDayRescueNum) {
        this.lastDayRescueNum = lastDayRescueNum;
    }

    public int getWeekRescueNum() {
        return weekRescueNum;
    }

    public void setWeekRescueNum(int weekRescueNum) {
        this.weekRescueNum = weekRescueNum;
    }

    public int getLastWeekRescueNum() {
        return lastWeekRescueNum;
    }

    public void setLastWeekRescueNum(int lastWeekRescueNum) {
        this.lastWeekRescueNum = lastWeekRescueNum;
    }

    public int getAllRescueNum() {
        return allRescueNum;
    }

    public void setAllRescueNum(int allRescueNum) {
        this.allRescueNum = allRescueNum;
    }
    
	public String getKey(RankingBigType rankingBigType, RankingSmallType rankingSmallType, String cityType) {
		if(rankingSmallType == RankingSmallType.city) {
			return rankingBigType.name() + "_" + rankingSmallType.name() + "_" + cityType;
		} else {
			return rankingBigType.name() + "_" + rankingSmallType.name();
		}
	}
    
    public RoleRankingInfoProto.Builder buildProto(RankingBigType rankingBigType, RankingSmallType rankingSmallType, String cityType) {
    	RoleRankingInfoProto.Builder builder = RoleRankingInfoProto.newBuilder();
    	builder.setRoleId(this.roleId);
    	Long ranking = this.rankingMap.get(this.getKey(rankingBigType, rankingSmallType, cityType));
    	if(ranking == null) {
    		ranking = 0l;
    	}
    	builder.setRanking(ranking);
    	builder.setImage(this.image);
    	builder.setSex(this.sex);
    	builder.setName(this.name);
    	builder.setLocation(this.location);
    	builder.setPhotoFrame(photoFrame);
    	builder.setLicenseLevel(this.licenseLevel);
    	builder.setRoleLevel(this.roleLevel);
    	switch(rankingBigType) {
//    	case charm:
//    		builder.setCharm(this.recordCharm);
//    		break;
//    	case achievement:
//    		builder.setAchievement(this.recordAchievement);
//    		break;
//    	case licenseLevel:
//    		builder.setLicenseLevel(this.licenseLevel);
//    		break;
    	case champion:
    		switch (rankingSmallType) {
			case month:
				builder.setChampionNum(this.newDayChampionNum);
//				builder.setNewChampionNum(this.dayChampionNum);
				break;
			case week:
				builder.setChampionNum(this.newWeekChampionNum);
//				builder.setNewChampionNum(this.weekChampionNum);
				break;
			case all:
				builder.setChampionNum(this.allChampionNum);
				break;
			default:
				throw new RuntimeException();
			}
    		break;
//    	case fans:
//    		switch (rankingSmallType) {
//			case day:
//				builder.setFansNum(this.lastDayFansNum);
//				builder.setNewFansNum(this.dayFansNum);
//				break;
//			case week:
//				builder.setFansNum(this.lastWeekFansNum);
//				builder.setNewFansNum(this.weekFansNum);
//				break;
//			case all:
//				builder.setFansNum(this.allFansNum);
//				break;
//			default:
//				throw new RuntimeException();
//			}
//    		break;
    	case rescue:
//    		switch (rankingSmallType) {
//			case day:
//				builder.setRescueNum(this.lastDayRescueNum);
//				builder.setNewRescueNum(this.dayRescueNum);
//				break;
//			case week:
//				builder.setRescueNum(this.lastWeekRescueNum);
//				builder.setNewRescueNum(this.weekRescueNum);
//				break;
//			case all:
				builder.setRescueNum(this.allRescueNum);
//				break;
//			default:
//				throw new RuntimeException();
//			}
    		break;
		default:
			throw new RuntimeException();
    	}
    	return builder;
    }

	public boolean update(Role role) {
		boolean isChange = false;
		if(this.sex != (role.getSex() == 0)) {
			this.sex = (role.getSex() == 0);
			isChange = true;
		}
		if(!this.image.equals("" + role.getPhoto())) {
			isChange = true;
			this.image = "" + role.getPhoto();
		}
		if(this.roleLevel != role.getRoleLevel()) {
			isChange = true;
			this.roleLevel = role.getRoleLevel();
		}
		if(!this.name.equals("" + role.getRoleName())) {
			isChange = true;
			this.name = role.getRoleName();
		}
		if(!this.location.equals("" + role.getCity())) {
			isChange = true;
			this.location = role.getCity();
		}
		if(this.photoFrame != role.getPhotoFrame()) {
			isChange = true;
			this.photoFrame = role.getPhotoFrame();
		}
		if(this.charm != role.getCharm()) {
			isChange = true;
			this.charm = role.getCharm();
		}
		if(this.achievement != role.getAchievePoint()) {
			isChange = true;
			this.achievement = role.getAchievePoint();
		}
		if(this.licenseLevel != role.getShammanLevel()) {
			isChange = true;
			this.licenseLevel = role.getShammanLevel();
		}
		if (this.allFansNum != role.getFansNum()) {
			isChange = true;
			this.allFansNum = role.getFansNum();
		}
		if(this.allRescueNum != role.getRescueNum()) {
			isChange = true;
			this.allRescueNum = role.getRescueNum();
		}
		if(this.allChampionNum != role.getChampionNum()) {
			isChange = true;
			this.allChampionNum = role.getChampionNum();
		}
		initRefresh();
		return isChange;
	}
	
	public void initRefresh() {
//		this.newDayFansNum = role.getFansNum() - this.dayFansNum;
//		this.newWeekFansNum = role.getFansNum() - this.weekFansNum;
		this.newDayChampionNum = this.allChampionNum - this.dayChampionNum;
		this.newWeekChampionNum = this.allChampionNum - this.weekChampionNum;
//		this.newDayRescueNum = role.getRescueNum() - this.dayRescueNum;
//		this.newWeekRescueNum = role.getRescueNum() - this.weekRescueNum;
	}

	public int getNewDayFansNum() {
		return newDayFansNum;
	}

	public int getNewWeekFansNum() {
		return newWeekFansNum;
	}

	public int getNewDayChampionNum() {
		return newDayChampionNum;
	}

	public int getNewWeekChampionNum() {
		return newWeekChampionNum;
	}

	public int getNewDayRescueNum() {
		return newDayRescueNum;
	}

	public int getNewWeekRescueNum() {
		return newWeekRescueNum;
	}

	public Map<String, Long> getRankingMap() {
		return rankingMap;
	}
	
	public int getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
	}

	public void monthRefresh(Role role) {
		this.lastDayChampionNum = role.getChampionNum() - this.dayChampionNum;
		this.dayChampionNum = role.getChampionNum();
		this.newDayChampionNum = 0;
		
//		this.lastDayFansNum = role.getFansNum() - this.dayFansNum;
//		this.dayFansNum = role.getFansNum();
//		this.newDayFansNum = 0;
		
//		this.lastDayRescueNum = role.getRescueNum() - this.dayRescueNum;
//		this.dayRescueNum = role.getRescueNum();
//		this.newDayRescueNum = 0;
	}
	
	public void weekRefresh(Role role) {
		this.lastWeekChampionNum = role.getChampionNum() - this.weekChampionNum;
		this.weekChampionNum = role.getChampionNum();
		this.newWeekChampionNum = 0;
		
//		this.lastWeekFansNum = role.getFansNum() - this.weekFansNum;
//		this.weekFansNum = role.getFansNum();
//		this.newWeekFansNum = 0;
		
//		this.lastWeekRescueNum = role.getRescueNum() - this.weekRescueNum;
//		this.weekRescueNum = role.getRescueNum();
//		this.newWeekRescueNum = 0;
	}

	public int getPhotoFrame() {
		return photoFrame;
	}

	public void setPhotoFrame(int photoFrame) {
		this.photoFrame = photoFrame;
	}

}