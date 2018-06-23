package com.funcy.g01.base.bo.user;

import java.util.Date;

public class FrontVersion {
    private int id;

    private int versionNum1;

    private int versionNum2;

    private int source1VersionNum1;

    private int source1VersionNum2;

    private String source1PackName;

    private int source1PackSize;

    private int source2VersionNum1;

    private int source2VersionNum2;

    private String source2PackName;

    private int source2PackSize;

    private int source3VersionNum1;

    private int source3VersionNum2;

    private String source3PackName;

    private int source3PackSize;

    private Date time;

    private String submitter;

    private String descr;

    private Boolean forceUpdate;

    private String forceUpdateUrl;

    private Boolean isInTest;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersionNum1() {
        return versionNum1;
    }

    public void setVersionNum1(int versionNum1) {
        this.versionNum1 = versionNum1;
    }

    public int getVersionNum2() {
        return versionNum2;
    }

    public void setVersionNum2(int versionNum2) {
        this.versionNum2 = versionNum2;
    }

    public int getSource1VersionNum1() {
        return source1VersionNum1;
    }

    public void setSource1VersionNum1(int source1VersionNum1) {
        this.source1VersionNum1 = source1VersionNum1;
    }

    public int getSource1VersionNum2() {
        return source1VersionNum2;
    }

    public void setSource1VersionNum2(int source1VersionNum2) {
        this.source1VersionNum2 = source1VersionNum2;
    }

    public String getSource1PackName() {
        return source1PackName;
    }

    public void setSource1PackName(String source1PackName) {
        this.source1PackName = source1PackName;
    }

    public int getSource1PackSize() {
        return source1PackSize;
    }

    public void setSource1PackSize(int source1PackSize) {
        this.source1PackSize = source1PackSize;
    }

    public int getSource2VersionNum1() {
        return source2VersionNum1;
    }

    public void setSource2VersionNum1(int source2VersionNum1) {
        this.source2VersionNum1 = source2VersionNum1;
    }

    public int getSource2VersionNum2() {
        return source2VersionNum2;
    }

    public void setSource2VersionNum2(int source2VersionNum2) {
        this.source2VersionNum2 = source2VersionNum2;
    }

    public String getSource2PackName() {
        return source2PackName;
    }

    public void setSource2PackName(String source2PackName) {
        this.source2PackName = source2PackName;
    }

    public int getSource2PackSize() {
        return source2PackSize;
    }

    public void setSource2PackSize(int source2PackSize) {
        this.source2PackSize = source2PackSize;
    }

    public int getSource3VersionNum1() {
        return source3VersionNum1;
    }

    public void setSource3VersionNum1(int source3VersionNum1) {
        this.source3VersionNum1 = source3VersionNum1;
    }

    public int getSource3VersionNum2() {
        return source3VersionNum2;
    }

    public void setSource3VersionNum2(int source3VersionNum2) {
        this.source3VersionNum2 = source3VersionNum2;
    }

    public String getSource3PackName() {
        return source3PackName;
    }

    public void setSource3PackName(String source3PackName) {
        this.source3PackName = source3PackName;
    }

    public int getSource3PackSize() {
        return source3PackSize;
    }

    public void setSource3PackSize(int source3PackSize) {
        this.source3PackSize = source3PackSize;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Boolean getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(Boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getForceUpdateUrl() {
        return forceUpdateUrl;
    }

    public void setForceUpdateUrl(String forceUpdateUrl) {
        this.forceUpdateUrl = forceUpdateUrl;
    }

    public Boolean getIsInTest() {
        return isInTest;
    }

    public void setIsInTest(Boolean isInTest) {
        this.isInTest = isInTest;
    }
}