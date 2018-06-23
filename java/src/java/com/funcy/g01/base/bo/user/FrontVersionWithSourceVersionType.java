package com.funcy.g01.base.bo.user;

public class FrontVersionWithSourceVersionType {

	public FrontVersion frontVersion;
	
	public int sourceVersionType;

	public FrontVersionWithSourceVersionType(FrontVersion frontVersion,
			int sourceVersionType) {
		this.frontVersion = frontVersion;
		this.sourceVersionType = sourceVersionType;
	}

	public String getSourcePackName() {
		if(this.sourceVersionType == 1) {
			return frontVersion.getSource1PackName();
		} else if(sourceVersionType == 2) {
			return frontVersion.getSource2PackName();
		} else if(sourceVersionType == 3) {
			return frontVersion.getSource3PackName();
		}
		return null;
	}

	public int getSourcePackSize() {
		if(this.sourceVersionType == 1) {
			return frontVersion.getSource1PackSize();
		} else if(sourceVersionType == 2) {
			return frontVersion.getSource2PackSize();
		} else if(sourceVersionType == 3) {
			return frontVersion.getSource3PackSize();
		}
		return 0;
	}
	
}
