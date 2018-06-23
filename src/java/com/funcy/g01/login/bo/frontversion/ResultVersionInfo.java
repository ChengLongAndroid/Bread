package com.funcy.g01.login.bo.frontversion;

import java.util.ArrayList;
import java.util.List;

public class ResultVersionInfo {

	private boolean isForceUpdate;
	
	private int resultBigVersion;
	
	private int resultSmallVersion;
	
	private List<String> packs;
	
	private int totalSize = 0;
	
	public ResultVersionInfo() {
		this.isForceUpdate = false;
		this.packs = new ArrayList<String>();
	}
	
	public void addPacks(String pack, int bigVersion, int smallVersion, int size) {
		this.packs.add(pack);
		this.resultBigVersion = bigVersion;
		this.resultSmallVersion = smallVersion;
		this.totalSize += size;
	}
	
	public int getTotalSize() {
		return this.totalSize;
	}

	public boolean isForceUpdate() {
		return isForceUpdate;
	}

	public void setForceUpdate(boolean isForceUpdate) {
		this.isForceUpdate = isForceUpdate;
	}

	public int getResultBigVersion() {
		return resultBigVersion;
	}

	public int getResultSmallVersion() {
		return resultSmallVersion;
	}

	public List<String> getPacks() {
		return packs;
	}
	
}
