package com.funcy.g01.base.bo.ranking;

import java.util.ArrayList;
import java.util.List;

public class RoleRankingInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RoleRankingInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andRoleIdIsNull() {
            addCriterion("roleId is null");
            return (Criteria) this;
        }

        public Criteria andRoleIdIsNotNull() {
            addCriterion("roleId is not null");
            return (Criteria) this;
        }

        public Criteria andRoleIdEqualTo(Long value) {
            addCriterion("roleId =", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotEqualTo(Long value) {
            addCriterion("roleId <>", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThan(Long value) {
            addCriterion("roleId >", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThanOrEqualTo(Long value) {
            addCriterion("roleId >=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThan(Long value) {
            addCriterion("roleId <", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThanOrEqualTo(Long value) {
            addCriterion("roleId <=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdIn(List<Long> values) {
            addCriterion("roleId in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotIn(List<Long> values) {
            addCriterion("roleId not in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdBetween(Long value1, Long value2) {
            addCriterion("roleId between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotBetween(Long value1, Long value2) {
            addCriterion("roleId not between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andImageIsNull() {
            addCriterion("image is null");
            return (Criteria) this;
        }

        public Criteria andImageIsNotNull() {
            addCriterion("image is not null");
            return (Criteria) this;
        }

        public Criteria andImageEqualTo(String value) {
            addCriterion("image =", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotEqualTo(String value) {
            addCriterion("image <>", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageGreaterThan(String value) {
            addCriterion("image >", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageGreaterThanOrEqualTo(String value) {
            addCriterion("image >=", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageLessThan(String value) {
            addCriterion("image <", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageLessThanOrEqualTo(String value) {
            addCriterion("image <=", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageLike(String value) {
            addCriterion("image like", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotLike(String value) {
            addCriterion("image not like", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageIn(List<String> values) {
            addCriterion("image in", values, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotIn(List<String> values) {
            addCriterion("image not in", values, "image");
            return (Criteria) this;
        }

        public Criteria andImageBetween(String value1, String value2) {
            addCriterion("image between", value1, value2, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotBetween(String value1, String value2) {
            addCriterion("image not between", value1, value2, "image");
            return (Criteria) this;
        }

        public Criteria andSexIsNull() {
            addCriterion("sex is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("sex is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(Boolean value) {
            addCriterion("sex =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(Boolean value) {
            addCriterion("sex <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(Boolean value) {
            addCriterion("sex >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(Boolean value) {
            addCriterion("sex >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(Boolean value) {
            addCriterion("sex <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(Boolean value) {
            addCriterion("sex <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<Boolean> values) {
            addCriterion("sex in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<Boolean> values) {
            addCriterion("sex not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(Boolean value1, Boolean value2) {
            addCriterion("sex between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(Boolean value1, Boolean value2) {
            addCriterion("sex not between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andLocationIsNull() {
            addCriterion("location is null");
            return (Criteria) this;
        }

        public Criteria andLocationIsNotNull() {
            addCriterion("location is not null");
            return (Criteria) this;
        }

        public Criteria andLocationEqualTo(String value) {
            addCriterion("location =", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationNotEqualTo(String value) {
            addCriterion("location <>", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationGreaterThan(String value) {
            addCriterion("location >", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationGreaterThanOrEqualTo(String value) {
            addCriterion("location >=", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationLessThan(String value) {
            addCriterion("location <", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationLessThanOrEqualTo(String value) {
            addCriterion("location <=", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationLike(String value) {
            addCriterion("location like", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationNotLike(String value) {
            addCriterion("location not like", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationIn(List<String> values) {
            addCriterion("location in", values, "location");
            return (Criteria) this;
        }

        public Criteria andLocationNotIn(List<String> values) {
            addCriterion("location not in", values, "location");
            return (Criteria) this;
        }

        public Criteria andLocationBetween(String value1, String value2) {
            addCriterion("location between", value1, value2, "location");
            return (Criteria) this;
        }

        public Criteria andLocationNotBetween(String value1, String value2) {
            addCriterion("location not between", value1, value2, "location");
            return (Criteria) this;
        }

        public Criteria andCharmIsNull() {
            addCriterion("charm is null");
            return (Criteria) this;
        }

        public Criteria andCharmIsNotNull() {
            addCriterion("charm is not null");
            return (Criteria) this;
        }

        public Criteria andCharmEqualTo(Integer value) {
            addCriterion("charm =", value, "charm");
            return (Criteria) this;
        }

        public Criteria andCharmNotEqualTo(Integer value) {
            addCriterion("charm <>", value, "charm");
            return (Criteria) this;
        }

        public Criteria andCharmGreaterThan(Integer value) {
            addCriterion("charm >", value, "charm");
            return (Criteria) this;
        }

        public Criteria andCharmGreaterThanOrEqualTo(Integer value) {
            addCriterion("charm >=", value, "charm");
            return (Criteria) this;
        }

        public Criteria andCharmLessThan(Integer value) {
            addCriterion("charm <", value, "charm");
            return (Criteria) this;
        }

        public Criteria andCharmLessThanOrEqualTo(Integer value) {
            addCriterion("charm <=", value, "charm");
            return (Criteria) this;
        }

        public Criteria andCharmIn(List<Integer> values) {
            addCriterion("charm in", values, "charm");
            return (Criteria) this;
        }

        public Criteria andCharmNotIn(List<Integer> values) {
            addCriterion("charm not in", values, "charm");
            return (Criteria) this;
        }

        public Criteria andCharmBetween(Integer value1, Integer value2) {
            addCriterion("charm between", value1, value2, "charm");
            return (Criteria) this;
        }

        public Criteria andCharmNotBetween(Integer value1, Integer value2) {
            addCriterion("charm not between", value1, value2, "charm");
            return (Criteria) this;
        }

        public Criteria andAchievementIsNull() {
            addCriterion("achievement is null");
            return (Criteria) this;
        }

        public Criteria andAchievementIsNotNull() {
            addCriterion("achievement is not null");
            return (Criteria) this;
        }

        public Criteria andAchievementEqualTo(Integer value) {
            addCriterion("achievement =", value, "achievement");
            return (Criteria) this;
        }

        public Criteria andAchievementNotEqualTo(Integer value) {
            addCriterion("achievement <>", value, "achievement");
            return (Criteria) this;
        }

        public Criteria andAchievementGreaterThan(Integer value) {
            addCriterion("achievement >", value, "achievement");
            return (Criteria) this;
        }

        public Criteria andAchievementGreaterThanOrEqualTo(Integer value) {
            addCriterion("achievement >=", value, "achievement");
            return (Criteria) this;
        }

        public Criteria andAchievementLessThan(Integer value) {
            addCriterion("achievement <", value, "achievement");
            return (Criteria) this;
        }

        public Criteria andAchievementLessThanOrEqualTo(Integer value) {
            addCriterion("achievement <=", value, "achievement");
            return (Criteria) this;
        }

        public Criteria andAchievementIn(List<Integer> values) {
            addCriterion("achievement in", values, "achievement");
            return (Criteria) this;
        }

        public Criteria andAchievementNotIn(List<Integer> values) {
            addCriterion("achievement not in", values, "achievement");
            return (Criteria) this;
        }

        public Criteria andAchievementBetween(Integer value1, Integer value2) {
            addCriterion("achievement between", value1, value2, "achievement");
            return (Criteria) this;
        }

        public Criteria andAchievementNotBetween(Integer value1, Integer value2) {
            addCriterion("achievement not between", value1, value2, "achievement");
            return (Criteria) this;
        }

        public Criteria andLicenseLevelIsNull() {
            addCriterion("licenseLevel is null");
            return (Criteria) this;
        }

        public Criteria andLicenseLevelIsNotNull() {
            addCriterion("licenseLevel is not null");
            return (Criteria) this;
        }

        public Criteria andLicenseLevelEqualTo(Integer value) {
            addCriterion("licenseLevel =", value, "licenseLevel");
            return (Criteria) this;
        }

        public Criteria andLicenseLevelNotEqualTo(Integer value) {
            addCriterion("licenseLevel <>", value, "licenseLevel");
            return (Criteria) this;
        }

        public Criteria andLicenseLevelGreaterThan(Integer value) {
            addCriterion("licenseLevel >", value, "licenseLevel");
            return (Criteria) this;
        }

        public Criteria andLicenseLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("licenseLevel >=", value, "licenseLevel");
            return (Criteria) this;
        }

        public Criteria andLicenseLevelLessThan(Integer value) {
            addCriterion("licenseLevel <", value, "licenseLevel");
            return (Criteria) this;
        }

        public Criteria andLicenseLevelLessThanOrEqualTo(Integer value) {
            addCriterion("licenseLevel <=", value, "licenseLevel");
            return (Criteria) this;
        }

        public Criteria andLicenseLevelIn(List<Integer> values) {
            addCriterion("licenseLevel in", values, "licenseLevel");
            return (Criteria) this;
        }

        public Criteria andLicenseLevelNotIn(List<Integer> values) {
            addCriterion("licenseLevel not in", values, "licenseLevel");
            return (Criteria) this;
        }

        public Criteria andLicenseLevelBetween(Integer value1, Integer value2) {
            addCriterion("licenseLevel between", value1, value2, "licenseLevel");
            return (Criteria) this;
        }

        public Criteria andLicenseLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("licenseLevel not between", value1, value2, "licenseLevel");
            return (Criteria) this;
        }

        public Criteria andRecordCharmIsNull() {
            addCriterion("recordCharm is null");
            return (Criteria) this;
        }

        public Criteria andRecordCharmIsNotNull() {
            addCriterion("recordCharm is not null");
            return (Criteria) this;
        }

        public Criteria andRecordCharmEqualTo(Integer value) {
            addCriterion("recordCharm =", value, "recordCharm");
            return (Criteria) this;
        }

        public Criteria andRecordCharmNotEqualTo(Integer value) {
            addCriterion("recordCharm <>", value, "recordCharm");
            return (Criteria) this;
        }

        public Criteria andRecordCharmGreaterThan(Integer value) {
            addCriterion("recordCharm >", value, "recordCharm");
            return (Criteria) this;
        }

        public Criteria andRecordCharmGreaterThanOrEqualTo(Integer value) {
            addCriterion("recordCharm >=", value, "recordCharm");
            return (Criteria) this;
        }

        public Criteria andRecordCharmLessThan(Integer value) {
            addCriterion("recordCharm <", value, "recordCharm");
            return (Criteria) this;
        }

        public Criteria andRecordCharmLessThanOrEqualTo(Integer value) {
            addCriterion("recordCharm <=", value, "recordCharm");
            return (Criteria) this;
        }

        public Criteria andRecordCharmIn(List<Integer> values) {
            addCriterion("recordCharm in", values, "recordCharm");
            return (Criteria) this;
        }

        public Criteria andRecordCharmNotIn(List<Integer> values) {
            addCriterion("recordCharm not in", values, "recordCharm");
            return (Criteria) this;
        }

        public Criteria andRecordCharmBetween(Integer value1, Integer value2) {
            addCriterion("recordCharm between", value1, value2, "recordCharm");
            return (Criteria) this;
        }

        public Criteria andRecordCharmNotBetween(Integer value1, Integer value2) {
            addCriterion("recordCharm not between", value1, value2, "recordCharm");
            return (Criteria) this;
        }

        public Criteria andRecordAchievementIsNull() {
            addCriterion("recordAchievement is null");
            return (Criteria) this;
        }

        public Criteria andRecordAchievementIsNotNull() {
            addCriterion("recordAchievement is not null");
            return (Criteria) this;
        }

        public Criteria andRecordAchievementEqualTo(Integer value) {
            addCriterion("recordAchievement =", value, "recordAchievement");
            return (Criteria) this;
        }

        public Criteria andRecordAchievementNotEqualTo(Integer value) {
            addCriterion("recordAchievement <>", value, "recordAchievement");
            return (Criteria) this;
        }

        public Criteria andRecordAchievementGreaterThan(Integer value) {
            addCriterion("recordAchievement >", value, "recordAchievement");
            return (Criteria) this;
        }

        public Criteria andRecordAchievementGreaterThanOrEqualTo(Integer value) {
            addCriterion("recordAchievement >=", value, "recordAchievement");
            return (Criteria) this;
        }

        public Criteria andRecordAchievementLessThan(Integer value) {
            addCriterion("recordAchievement <", value, "recordAchievement");
            return (Criteria) this;
        }

        public Criteria andRecordAchievementLessThanOrEqualTo(Integer value) {
            addCriterion("recordAchievement <=", value, "recordAchievement");
            return (Criteria) this;
        }

        public Criteria andRecordAchievementIn(List<Integer> values) {
            addCriterion("recordAchievement in", values, "recordAchievement");
            return (Criteria) this;
        }

        public Criteria andRecordAchievementNotIn(List<Integer> values) {
            addCriterion("recordAchievement not in", values, "recordAchievement");
            return (Criteria) this;
        }

        public Criteria andRecordAchievementBetween(Integer value1, Integer value2) {
            addCriterion("recordAchievement between", value1, value2, "recordAchievement");
            return (Criteria) this;
        }

        public Criteria andRecordAchievementNotBetween(Integer value1, Integer value2) {
            addCriterion("recordAchievement not between", value1, value2, "recordAchievement");
            return (Criteria) this;
        }

        public Criteria andRecordLicenseLevelIsNull() {
            addCriterion("recordLicenseLevel is null");
            return (Criteria) this;
        }

        public Criteria andRecordLicenseLevelIsNotNull() {
            addCriterion("recordLicenseLevel is not null");
            return (Criteria) this;
        }

        public Criteria andRecordLicenseLevelEqualTo(Integer value) {
            addCriterion("recordLicenseLevel =", value, "recordLicenseLevel");
            return (Criteria) this;
        }

        public Criteria andRecordLicenseLevelNotEqualTo(Integer value) {
            addCriterion("recordLicenseLevel <>", value, "recordLicenseLevel");
            return (Criteria) this;
        }

        public Criteria andRecordLicenseLevelGreaterThan(Integer value) {
            addCriterion("recordLicenseLevel >", value, "recordLicenseLevel");
            return (Criteria) this;
        }

        public Criteria andRecordLicenseLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("recordLicenseLevel >=", value, "recordLicenseLevel");
            return (Criteria) this;
        }

        public Criteria andRecordLicenseLevelLessThan(Integer value) {
            addCriterion("recordLicenseLevel <", value, "recordLicenseLevel");
            return (Criteria) this;
        }

        public Criteria andRecordLicenseLevelLessThanOrEqualTo(Integer value) {
            addCriterion("recordLicenseLevel <=", value, "recordLicenseLevel");
            return (Criteria) this;
        }

        public Criteria andRecordLicenseLevelIn(List<Integer> values) {
            addCriterion("recordLicenseLevel in", values, "recordLicenseLevel");
            return (Criteria) this;
        }

        public Criteria andRecordLicenseLevelNotIn(List<Integer> values) {
            addCriterion("recordLicenseLevel not in", values, "recordLicenseLevel");
            return (Criteria) this;
        }

        public Criteria andRecordLicenseLevelBetween(Integer value1, Integer value2) {
            addCriterion("recordLicenseLevel between", value1, value2, "recordLicenseLevel");
            return (Criteria) this;
        }

        public Criteria andRecordLicenseLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("recordLicenseLevel not between", value1, value2, "recordLicenseLevel");
            return (Criteria) this;
        }

        public Criteria andDayFansNumIsNull() {
            addCriterion("dayFansNum is null");
            return (Criteria) this;
        }

        public Criteria andDayFansNumIsNotNull() {
            addCriterion("dayFansNum is not null");
            return (Criteria) this;
        }

        public Criteria andDayFansNumEqualTo(Integer value) {
            addCriterion("dayFansNum =", value, "dayFansNum");
            return (Criteria) this;
        }

        public Criteria andDayFansNumNotEqualTo(Integer value) {
            addCriterion("dayFansNum <>", value, "dayFansNum");
            return (Criteria) this;
        }

        public Criteria andDayFansNumGreaterThan(Integer value) {
            addCriterion("dayFansNum >", value, "dayFansNum");
            return (Criteria) this;
        }

        public Criteria andDayFansNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("dayFansNum >=", value, "dayFansNum");
            return (Criteria) this;
        }

        public Criteria andDayFansNumLessThan(Integer value) {
            addCriterion("dayFansNum <", value, "dayFansNum");
            return (Criteria) this;
        }

        public Criteria andDayFansNumLessThanOrEqualTo(Integer value) {
            addCriterion("dayFansNum <=", value, "dayFansNum");
            return (Criteria) this;
        }

        public Criteria andDayFansNumIn(List<Integer> values) {
            addCriterion("dayFansNum in", values, "dayFansNum");
            return (Criteria) this;
        }

        public Criteria andDayFansNumNotIn(List<Integer> values) {
            addCriterion("dayFansNum not in", values, "dayFansNum");
            return (Criteria) this;
        }

        public Criteria andDayFansNumBetween(Integer value1, Integer value2) {
            addCriterion("dayFansNum between", value1, value2, "dayFansNum");
            return (Criteria) this;
        }

        public Criteria andDayFansNumNotBetween(Integer value1, Integer value2) {
            addCriterion("dayFansNum not between", value1, value2, "dayFansNum");
            return (Criteria) this;
        }

        public Criteria andLastDayFansNumIsNull() {
            addCriterion("lastDayFansNum is null");
            return (Criteria) this;
        }

        public Criteria andLastDayFansNumIsNotNull() {
            addCriterion("lastDayFansNum is not null");
            return (Criteria) this;
        }

        public Criteria andLastDayFansNumEqualTo(Integer value) {
            addCriterion("lastDayFansNum =", value, "lastDayFansNum");
            return (Criteria) this;
        }

        public Criteria andLastDayFansNumNotEqualTo(Integer value) {
            addCriterion("lastDayFansNum <>", value, "lastDayFansNum");
            return (Criteria) this;
        }

        public Criteria andLastDayFansNumGreaterThan(Integer value) {
            addCriterion("lastDayFansNum >", value, "lastDayFansNum");
            return (Criteria) this;
        }

        public Criteria andLastDayFansNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("lastDayFansNum >=", value, "lastDayFansNum");
            return (Criteria) this;
        }

        public Criteria andLastDayFansNumLessThan(Integer value) {
            addCriterion("lastDayFansNum <", value, "lastDayFansNum");
            return (Criteria) this;
        }

        public Criteria andLastDayFansNumLessThanOrEqualTo(Integer value) {
            addCriterion("lastDayFansNum <=", value, "lastDayFansNum");
            return (Criteria) this;
        }

        public Criteria andLastDayFansNumIn(List<Integer> values) {
            addCriterion("lastDayFansNum in", values, "lastDayFansNum");
            return (Criteria) this;
        }

        public Criteria andLastDayFansNumNotIn(List<Integer> values) {
            addCriterion("lastDayFansNum not in", values, "lastDayFansNum");
            return (Criteria) this;
        }

        public Criteria andLastDayFansNumBetween(Integer value1, Integer value2) {
            addCriterion("lastDayFansNum between", value1, value2, "lastDayFansNum");
            return (Criteria) this;
        }

        public Criteria andLastDayFansNumNotBetween(Integer value1, Integer value2) {
            addCriterion("lastDayFansNum not between", value1, value2, "lastDayFansNum");
            return (Criteria) this;
        }

        public Criteria andWeekFansNumIsNull() {
            addCriterion("weekFansNum is null");
            return (Criteria) this;
        }

        public Criteria andWeekFansNumIsNotNull() {
            addCriterion("weekFansNum is not null");
            return (Criteria) this;
        }

        public Criteria andWeekFansNumEqualTo(Integer value) {
            addCriterion("weekFansNum =", value, "weekFansNum");
            return (Criteria) this;
        }

        public Criteria andWeekFansNumNotEqualTo(Integer value) {
            addCriterion("weekFansNum <>", value, "weekFansNum");
            return (Criteria) this;
        }

        public Criteria andWeekFansNumGreaterThan(Integer value) {
            addCriterion("weekFansNum >", value, "weekFansNum");
            return (Criteria) this;
        }

        public Criteria andWeekFansNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("weekFansNum >=", value, "weekFansNum");
            return (Criteria) this;
        }

        public Criteria andWeekFansNumLessThan(Integer value) {
            addCriterion("weekFansNum <", value, "weekFansNum");
            return (Criteria) this;
        }

        public Criteria andWeekFansNumLessThanOrEqualTo(Integer value) {
            addCriterion("weekFansNum <=", value, "weekFansNum");
            return (Criteria) this;
        }

        public Criteria andWeekFansNumIn(List<Integer> values) {
            addCriterion("weekFansNum in", values, "weekFansNum");
            return (Criteria) this;
        }

        public Criteria andWeekFansNumNotIn(List<Integer> values) {
            addCriterion("weekFansNum not in", values, "weekFansNum");
            return (Criteria) this;
        }

        public Criteria andWeekFansNumBetween(Integer value1, Integer value2) {
            addCriterion("weekFansNum between", value1, value2, "weekFansNum");
            return (Criteria) this;
        }

        public Criteria andWeekFansNumNotBetween(Integer value1, Integer value2) {
            addCriterion("weekFansNum not between", value1, value2, "weekFansNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekFansNumIsNull() {
            addCriterion("lastWeekFansNum is null");
            return (Criteria) this;
        }

        public Criteria andLastWeekFansNumIsNotNull() {
            addCriterion("lastWeekFansNum is not null");
            return (Criteria) this;
        }

        public Criteria andLastWeekFansNumEqualTo(Integer value) {
            addCriterion("lastWeekFansNum =", value, "lastWeekFansNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekFansNumNotEqualTo(Integer value) {
            addCriterion("lastWeekFansNum <>", value, "lastWeekFansNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekFansNumGreaterThan(Integer value) {
            addCriterion("lastWeekFansNum >", value, "lastWeekFansNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekFansNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("lastWeekFansNum >=", value, "lastWeekFansNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekFansNumLessThan(Integer value) {
            addCriterion("lastWeekFansNum <", value, "lastWeekFansNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekFansNumLessThanOrEqualTo(Integer value) {
            addCriterion("lastWeekFansNum <=", value, "lastWeekFansNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekFansNumIn(List<Integer> values) {
            addCriterion("lastWeekFansNum in", values, "lastWeekFansNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekFansNumNotIn(List<Integer> values) {
            addCriterion("lastWeekFansNum not in", values, "lastWeekFansNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekFansNumBetween(Integer value1, Integer value2) {
            addCriterion("lastWeekFansNum between", value1, value2, "lastWeekFansNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekFansNumNotBetween(Integer value1, Integer value2) {
            addCriterion("lastWeekFansNum not between", value1, value2, "lastWeekFansNum");
            return (Criteria) this;
        }

        public Criteria andAllFansNumIsNull() {
            addCriterion("allFansNum is null");
            return (Criteria) this;
        }

        public Criteria andAllFansNumIsNotNull() {
            addCriterion("allFansNum is not null");
            return (Criteria) this;
        }

        public Criteria andAllFansNumEqualTo(Integer value) {
            addCriterion("allFansNum =", value, "allFansNum");
            return (Criteria) this;
        }

        public Criteria andAllFansNumNotEqualTo(Integer value) {
            addCriterion("allFansNum <>", value, "allFansNum");
            return (Criteria) this;
        }

        public Criteria andAllFansNumGreaterThan(Integer value) {
            addCriterion("allFansNum >", value, "allFansNum");
            return (Criteria) this;
        }

        public Criteria andAllFansNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("allFansNum >=", value, "allFansNum");
            return (Criteria) this;
        }

        public Criteria andAllFansNumLessThan(Integer value) {
            addCriterion("allFansNum <", value, "allFansNum");
            return (Criteria) this;
        }

        public Criteria andAllFansNumLessThanOrEqualTo(Integer value) {
            addCriterion("allFansNum <=", value, "allFansNum");
            return (Criteria) this;
        }

        public Criteria andAllFansNumIn(List<Integer> values) {
            addCriterion("allFansNum in", values, "allFansNum");
            return (Criteria) this;
        }

        public Criteria andAllFansNumNotIn(List<Integer> values) {
            addCriterion("allFansNum not in", values, "allFansNum");
            return (Criteria) this;
        }

        public Criteria andAllFansNumBetween(Integer value1, Integer value2) {
            addCriterion("allFansNum between", value1, value2, "allFansNum");
            return (Criteria) this;
        }

        public Criteria andAllFansNumNotBetween(Integer value1, Integer value2) {
            addCriterion("allFansNum not between", value1, value2, "allFansNum");
            return (Criteria) this;
        }

        public Criteria andDayChampionNumIsNull() {
            addCriterion("dayChampionNum is null");
            return (Criteria) this;
        }

        public Criteria andDayChampionNumIsNotNull() {
            addCriterion("dayChampionNum is not null");
            return (Criteria) this;
        }

        public Criteria andDayChampionNumEqualTo(Integer value) {
            addCriterion("dayChampionNum =", value, "dayChampionNum");
            return (Criteria) this;
        }

        public Criteria andDayChampionNumNotEqualTo(Integer value) {
            addCriterion("dayChampionNum <>", value, "dayChampionNum");
            return (Criteria) this;
        }

        public Criteria andDayChampionNumGreaterThan(Integer value) {
            addCriterion("dayChampionNum >", value, "dayChampionNum");
            return (Criteria) this;
        }

        public Criteria andDayChampionNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("dayChampionNum >=", value, "dayChampionNum");
            return (Criteria) this;
        }

        public Criteria andDayChampionNumLessThan(Integer value) {
            addCriterion("dayChampionNum <", value, "dayChampionNum");
            return (Criteria) this;
        }

        public Criteria andDayChampionNumLessThanOrEqualTo(Integer value) {
            addCriterion("dayChampionNum <=", value, "dayChampionNum");
            return (Criteria) this;
        }

        public Criteria andDayChampionNumIn(List<Integer> values) {
            addCriterion("dayChampionNum in", values, "dayChampionNum");
            return (Criteria) this;
        }

        public Criteria andDayChampionNumNotIn(List<Integer> values) {
            addCriterion("dayChampionNum not in", values, "dayChampionNum");
            return (Criteria) this;
        }

        public Criteria andDayChampionNumBetween(Integer value1, Integer value2) {
            addCriterion("dayChampionNum between", value1, value2, "dayChampionNum");
            return (Criteria) this;
        }

        public Criteria andDayChampionNumNotBetween(Integer value1, Integer value2) {
            addCriterion("dayChampionNum not between", value1, value2, "dayChampionNum");
            return (Criteria) this;
        }

        public Criteria andLastDayChampionNumIsNull() {
            addCriterion("lastDayChampionNum is null");
            return (Criteria) this;
        }

        public Criteria andLastDayChampionNumIsNotNull() {
            addCriterion("lastDayChampionNum is not null");
            return (Criteria) this;
        }

        public Criteria andLastDayChampionNumEqualTo(Integer value) {
            addCriterion("lastDayChampionNum =", value, "lastDayChampionNum");
            return (Criteria) this;
        }

        public Criteria andLastDayChampionNumNotEqualTo(Integer value) {
            addCriterion("lastDayChampionNum <>", value, "lastDayChampionNum");
            return (Criteria) this;
        }

        public Criteria andLastDayChampionNumGreaterThan(Integer value) {
            addCriterion("lastDayChampionNum >", value, "lastDayChampionNum");
            return (Criteria) this;
        }

        public Criteria andLastDayChampionNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("lastDayChampionNum >=", value, "lastDayChampionNum");
            return (Criteria) this;
        }

        public Criteria andLastDayChampionNumLessThan(Integer value) {
            addCriterion("lastDayChampionNum <", value, "lastDayChampionNum");
            return (Criteria) this;
        }

        public Criteria andLastDayChampionNumLessThanOrEqualTo(Integer value) {
            addCriterion("lastDayChampionNum <=", value, "lastDayChampionNum");
            return (Criteria) this;
        }

        public Criteria andLastDayChampionNumIn(List<Integer> values) {
            addCriterion("lastDayChampionNum in", values, "lastDayChampionNum");
            return (Criteria) this;
        }

        public Criteria andLastDayChampionNumNotIn(List<Integer> values) {
            addCriterion("lastDayChampionNum not in", values, "lastDayChampionNum");
            return (Criteria) this;
        }

        public Criteria andLastDayChampionNumBetween(Integer value1, Integer value2) {
            addCriterion("lastDayChampionNum between", value1, value2, "lastDayChampionNum");
            return (Criteria) this;
        }

        public Criteria andLastDayChampionNumNotBetween(Integer value1, Integer value2) {
            addCriterion("lastDayChampionNum not between", value1, value2, "lastDayChampionNum");
            return (Criteria) this;
        }

        public Criteria andWeekChampionNumIsNull() {
            addCriterion("weekChampionNum is null");
            return (Criteria) this;
        }

        public Criteria andWeekChampionNumIsNotNull() {
            addCriterion("weekChampionNum is not null");
            return (Criteria) this;
        }

        public Criteria andWeekChampionNumEqualTo(Integer value) {
            addCriterion("weekChampionNum =", value, "weekChampionNum");
            return (Criteria) this;
        }

        public Criteria andWeekChampionNumNotEqualTo(Integer value) {
            addCriterion("weekChampionNum <>", value, "weekChampionNum");
            return (Criteria) this;
        }

        public Criteria andWeekChampionNumGreaterThan(Integer value) {
            addCriterion("weekChampionNum >", value, "weekChampionNum");
            return (Criteria) this;
        }

        public Criteria andWeekChampionNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("weekChampionNum >=", value, "weekChampionNum");
            return (Criteria) this;
        }

        public Criteria andWeekChampionNumLessThan(Integer value) {
            addCriterion("weekChampionNum <", value, "weekChampionNum");
            return (Criteria) this;
        }

        public Criteria andWeekChampionNumLessThanOrEqualTo(Integer value) {
            addCriterion("weekChampionNum <=", value, "weekChampionNum");
            return (Criteria) this;
        }

        public Criteria andWeekChampionNumIn(List<Integer> values) {
            addCriterion("weekChampionNum in", values, "weekChampionNum");
            return (Criteria) this;
        }

        public Criteria andWeekChampionNumNotIn(List<Integer> values) {
            addCriterion("weekChampionNum not in", values, "weekChampionNum");
            return (Criteria) this;
        }

        public Criteria andWeekChampionNumBetween(Integer value1, Integer value2) {
            addCriterion("weekChampionNum between", value1, value2, "weekChampionNum");
            return (Criteria) this;
        }

        public Criteria andWeekChampionNumNotBetween(Integer value1, Integer value2) {
            addCriterion("weekChampionNum not between", value1, value2, "weekChampionNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekChampionNumIsNull() {
            addCriterion("lastWeekChampionNum is null");
            return (Criteria) this;
        }

        public Criteria andLastWeekChampionNumIsNotNull() {
            addCriterion("lastWeekChampionNum is not null");
            return (Criteria) this;
        }

        public Criteria andLastWeekChampionNumEqualTo(Integer value) {
            addCriterion("lastWeekChampionNum =", value, "lastWeekChampionNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekChampionNumNotEqualTo(Integer value) {
            addCriterion("lastWeekChampionNum <>", value, "lastWeekChampionNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekChampionNumGreaterThan(Integer value) {
            addCriterion("lastWeekChampionNum >", value, "lastWeekChampionNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekChampionNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("lastWeekChampionNum >=", value, "lastWeekChampionNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekChampionNumLessThan(Integer value) {
            addCriterion("lastWeekChampionNum <", value, "lastWeekChampionNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekChampionNumLessThanOrEqualTo(Integer value) {
            addCriterion("lastWeekChampionNum <=", value, "lastWeekChampionNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekChampionNumIn(List<Integer> values) {
            addCriterion("lastWeekChampionNum in", values, "lastWeekChampionNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekChampionNumNotIn(List<Integer> values) {
            addCriterion("lastWeekChampionNum not in", values, "lastWeekChampionNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekChampionNumBetween(Integer value1, Integer value2) {
            addCriterion("lastWeekChampionNum between", value1, value2, "lastWeekChampionNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekChampionNumNotBetween(Integer value1, Integer value2) {
            addCriterion("lastWeekChampionNum not between", value1, value2, "lastWeekChampionNum");
            return (Criteria) this;
        }

        public Criteria andAllChampionNumIsNull() {
            addCriterion("allChampionNum is null");
            return (Criteria) this;
        }

        public Criteria andAllChampionNumIsNotNull() {
            addCriterion("allChampionNum is not null");
            return (Criteria) this;
        }

        public Criteria andAllChampionNumEqualTo(Integer value) {
            addCriterion("allChampionNum =", value, "allChampionNum");
            return (Criteria) this;
        }

        public Criteria andAllChampionNumNotEqualTo(Integer value) {
            addCriterion("allChampionNum <>", value, "allChampionNum");
            return (Criteria) this;
        }

        public Criteria andAllChampionNumGreaterThan(Integer value) {
            addCriterion("allChampionNum >", value, "allChampionNum");
            return (Criteria) this;
        }

        public Criteria andAllChampionNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("allChampionNum >=", value, "allChampionNum");
            return (Criteria) this;
        }

        public Criteria andAllChampionNumLessThan(Integer value) {
            addCriterion("allChampionNum <", value, "allChampionNum");
            return (Criteria) this;
        }

        public Criteria andAllChampionNumLessThanOrEqualTo(Integer value) {
            addCriterion("allChampionNum <=", value, "allChampionNum");
            return (Criteria) this;
        }

        public Criteria andAllChampionNumIn(List<Integer> values) {
            addCriterion("allChampionNum in", values, "allChampionNum");
            return (Criteria) this;
        }

        public Criteria andAllChampionNumNotIn(List<Integer> values) {
            addCriterion("allChampionNum not in", values, "allChampionNum");
            return (Criteria) this;
        }

        public Criteria andAllChampionNumBetween(Integer value1, Integer value2) {
            addCriterion("allChampionNum between", value1, value2, "allChampionNum");
            return (Criteria) this;
        }

        public Criteria andAllChampionNumNotBetween(Integer value1, Integer value2) {
            addCriterion("allChampionNum not between", value1, value2, "allChampionNum");
            return (Criteria) this;
        }

        public Criteria andDayRescueNumIsNull() {
            addCriterion("dayRescueNum is null");
            return (Criteria) this;
        }

        public Criteria andDayRescueNumIsNotNull() {
            addCriterion("dayRescueNum is not null");
            return (Criteria) this;
        }

        public Criteria andDayRescueNumEqualTo(Integer value) {
            addCriterion("dayRescueNum =", value, "dayRescueNum");
            return (Criteria) this;
        }

        public Criteria andDayRescueNumNotEqualTo(Integer value) {
            addCriterion("dayRescueNum <>", value, "dayRescueNum");
            return (Criteria) this;
        }

        public Criteria andDayRescueNumGreaterThan(Integer value) {
            addCriterion("dayRescueNum >", value, "dayRescueNum");
            return (Criteria) this;
        }

        public Criteria andDayRescueNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("dayRescueNum >=", value, "dayRescueNum");
            return (Criteria) this;
        }

        public Criteria andDayRescueNumLessThan(Integer value) {
            addCriterion("dayRescueNum <", value, "dayRescueNum");
            return (Criteria) this;
        }

        public Criteria andDayRescueNumLessThanOrEqualTo(Integer value) {
            addCriterion("dayRescueNum <=", value, "dayRescueNum");
            return (Criteria) this;
        }

        public Criteria andDayRescueNumIn(List<Integer> values) {
            addCriterion("dayRescueNum in", values, "dayRescueNum");
            return (Criteria) this;
        }

        public Criteria andDayRescueNumNotIn(List<Integer> values) {
            addCriterion("dayRescueNum not in", values, "dayRescueNum");
            return (Criteria) this;
        }

        public Criteria andDayRescueNumBetween(Integer value1, Integer value2) {
            addCriterion("dayRescueNum between", value1, value2, "dayRescueNum");
            return (Criteria) this;
        }

        public Criteria andDayRescueNumNotBetween(Integer value1, Integer value2) {
            addCriterion("dayRescueNum not between", value1, value2, "dayRescueNum");
            return (Criteria) this;
        }

        public Criteria andLastDayRescueNumIsNull() {
            addCriterion("lastDayRescueNum is null");
            return (Criteria) this;
        }

        public Criteria andLastDayRescueNumIsNotNull() {
            addCriterion("lastDayRescueNum is not null");
            return (Criteria) this;
        }

        public Criteria andLastDayRescueNumEqualTo(Integer value) {
            addCriterion("lastDayRescueNum =", value, "lastDayRescueNum");
            return (Criteria) this;
        }

        public Criteria andLastDayRescueNumNotEqualTo(Integer value) {
            addCriterion("lastDayRescueNum <>", value, "lastDayRescueNum");
            return (Criteria) this;
        }

        public Criteria andLastDayRescueNumGreaterThan(Integer value) {
            addCriterion("lastDayRescueNum >", value, "lastDayRescueNum");
            return (Criteria) this;
        }

        public Criteria andLastDayRescueNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("lastDayRescueNum >=", value, "lastDayRescueNum");
            return (Criteria) this;
        }

        public Criteria andLastDayRescueNumLessThan(Integer value) {
            addCriterion("lastDayRescueNum <", value, "lastDayRescueNum");
            return (Criteria) this;
        }

        public Criteria andLastDayRescueNumLessThanOrEqualTo(Integer value) {
            addCriterion("lastDayRescueNum <=", value, "lastDayRescueNum");
            return (Criteria) this;
        }

        public Criteria andLastDayRescueNumIn(List<Integer> values) {
            addCriterion("lastDayRescueNum in", values, "lastDayRescueNum");
            return (Criteria) this;
        }

        public Criteria andLastDayRescueNumNotIn(List<Integer> values) {
            addCriterion("lastDayRescueNum not in", values, "lastDayRescueNum");
            return (Criteria) this;
        }

        public Criteria andLastDayRescueNumBetween(Integer value1, Integer value2) {
            addCriterion("lastDayRescueNum between", value1, value2, "lastDayRescueNum");
            return (Criteria) this;
        }

        public Criteria andLastDayRescueNumNotBetween(Integer value1, Integer value2) {
            addCriterion("lastDayRescueNum not between", value1, value2, "lastDayRescueNum");
            return (Criteria) this;
        }

        public Criteria andWeekRescueNumIsNull() {
            addCriterion("weekRescueNum is null");
            return (Criteria) this;
        }

        public Criteria andWeekRescueNumIsNotNull() {
            addCriterion("weekRescueNum is not null");
            return (Criteria) this;
        }

        public Criteria andWeekRescueNumEqualTo(Integer value) {
            addCriterion("weekRescueNum =", value, "weekRescueNum");
            return (Criteria) this;
        }

        public Criteria andWeekRescueNumNotEqualTo(Integer value) {
            addCriterion("weekRescueNum <>", value, "weekRescueNum");
            return (Criteria) this;
        }

        public Criteria andWeekRescueNumGreaterThan(Integer value) {
            addCriterion("weekRescueNum >", value, "weekRescueNum");
            return (Criteria) this;
        }

        public Criteria andWeekRescueNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("weekRescueNum >=", value, "weekRescueNum");
            return (Criteria) this;
        }

        public Criteria andWeekRescueNumLessThan(Integer value) {
            addCriterion("weekRescueNum <", value, "weekRescueNum");
            return (Criteria) this;
        }

        public Criteria andWeekRescueNumLessThanOrEqualTo(Integer value) {
            addCriterion("weekRescueNum <=", value, "weekRescueNum");
            return (Criteria) this;
        }

        public Criteria andWeekRescueNumIn(List<Integer> values) {
            addCriterion("weekRescueNum in", values, "weekRescueNum");
            return (Criteria) this;
        }

        public Criteria andWeekRescueNumNotIn(List<Integer> values) {
            addCriterion("weekRescueNum not in", values, "weekRescueNum");
            return (Criteria) this;
        }

        public Criteria andWeekRescueNumBetween(Integer value1, Integer value2) {
            addCriterion("weekRescueNum between", value1, value2, "weekRescueNum");
            return (Criteria) this;
        }

        public Criteria andWeekRescueNumNotBetween(Integer value1, Integer value2) {
            addCriterion("weekRescueNum not between", value1, value2, "weekRescueNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekRescueNumIsNull() {
            addCriterion("lastWeekRescueNum is null");
            return (Criteria) this;
        }

        public Criteria andLastWeekRescueNumIsNotNull() {
            addCriterion("lastWeekRescueNum is not null");
            return (Criteria) this;
        }

        public Criteria andLastWeekRescueNumEqualTo(Integer value) {
            addCriterion("lastWeekRescueNum =", value, "lastWeekRescueNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekRescueNumNotEqualTo(Integer value) {
            addCriterion("lastWeekRescueNum <>", value, "lastWeekRescueNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekRescueNumGreaterThan(Integer value) {
            addCriterion("lastWeekRescueNum >", value, "lastWeekRescueNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekRescueNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("lastWeekRescueNum >=", value, "lastWeekRescueNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekRescueNumLessThan(Integer value) {
            addCriterion("lastWeekRescueNum <", value, "lastWeekRescueNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekRescueNumLessThanOrEqualTo(Integer value) {
            addCriterion("lastWeekRescueNum <=", value, "lastWeekRescueNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekRescueNumIn(List<Integer> values) {
            addCriterion("lastWeekRescueNum in", values, "lastWeekRescueNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekRescueNumNotIn(List<Integer> values) {
            addCriterion("lastWeekRescueNum not in", values, "lastWeekRescueNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekRescueNumBetween(Integer value1, Integer value2) {
            addCriterion("lastWeekRescueNum between", value1, value2, "lastWeekRescueNum");
            return (Criteria) this;
        }

        public Criteria andLastWeekRescueNumNotBetween(Integer value1, Integer value2) {
            addCriterion("lastWeekRescueNum not between", value1, value2, "lastWeekRescueNum");
            return (Criteria) this;
        }

        public Criteria andAllRescueNumIsNull() {
            addCriterion("allRescueNum is null");
            return (Criteria) this;
        }

        public Criteria andAllRescueNumIsNotNull() {
            addCriterion("allRescueNum is not null");
            return (Criteria) this;
        }

        public Criteria andAllRescueNumEqualTo(Integer value) {
            addCriterion("allRescueNum =", value, "allRescueNum");
            return (Criteria) this;
        }

        public Criteria andAllRescueNumNotEqualTo(Integer value) {
            addCriterion("allRescueNum <>", value, "allRescueNum");
            return (Criteria) this;
        }

        public Criteria andAllRescueNumGreaterThan(Integer value) {
            addCriterion("allRescueNum >", value, "allRescueNum");
            return (Criteria) this;
        }

        public Criteria andAllRescueNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("allRescueNum >=", value, "allRescueNum");
            return (Criteria) this;
        }

        public Criteria andAllRescueNumLessThan(Integer value) {
            addCriterion("allRescueNum <", value, "allRescueNum");
            return (Criteria) this;
        }

        public Criteria andAllRescueNumLessThanOrEqualTo(Integer value) {
            addCriterion("allRescueNum <=", value, "allRescueNum");
            return (Criteria) this;
        }

        public Criteria andAllRescueNumIn(List<Integer> values) {
            addCriterion("allRescueNum in", values, "allRescueNum");
            return (Criteria) this;
        }

        public Criteria andAllRescueNumNotIn(List<Integer> values) {
            addCriterion("allRescueNum not in", values, "allRescueNum");
            return (Criteria) this;
        }

        public Criteria andAllRescueNumBetween(Integer value1, Integer value2) {
            addCriterion("allRescueNum between", value1, value2, "allRescueNum");
            return (Criteria) this;
        }

        public Criteria andAllRescueNumNotBetween(Integer value1, Integer value2) {
            addCriterion("allRescueNum not between", value1, value2, "allRescueNum");
            return (Criteria) this;
        }

        public Criteria andPhotoFrameIsNull() {
            addCriterion("photoFrame is null");
            return (Criteria) this;
        }

        public Criteria andPhotoFrameIsNotNull() {
            addCriterion("photoFrame is not null");
            return (Criteria) this;
        }

        public Criteria andPhotoFrameEqualTo(Integer value) {
            addCriterion("photoFrame =", value, "photoFrame");
            return (Criteria) this;
        }

        public Criteria andPhotoFrameNotEqualTo(Integer value) {
            addCriterion("photoFrame <>", value, "photoFrame");
            return (Criteria) this;
        }

        public Criteria andPhotoFrameGreaterThan(Integer value) {
            addCriterion("photoFrame >", value, "photoFrame");
            return (Criteria) this;
        }

        public Criteria andPhotoFrameGreaterThanOrEqualTo(Integer value) {
            addCriterion("photoFrame >=", value, "photoFrame");
            return (Criteria) this;
        }

        public Criteria andPhotoFrameLessThan(Integer value) {
            addCriterion("photoFrame <", value, "photoFrame");
            return (Criteria) this;
        }

        public Criteria andPhotoFrameLessThanOrEqualTo(Integer value) {
            addCriterion("photoFrame <=", value, "photoFrame");
            return (Criteria) this;
        }

        public Criteria andPhotoFrameIn(List<Integer> values) {
            addCriterion("photoFrame in", values, "photoFrame");
            return (Criteria) this;
        }

        public Criteria andPhotoFrameNotIn(List<Integer> values) {
            addCriterion("photoFrame not in", values, "photoFrame");
            return (Criteria) this;
        }

        public Criteria andPhotoFrameBetween(Integer value1, Integer value2) {
            addCriterion("photoFrame between", value1, value2, "photoFrame");
            return (Criteria) this;
        }

        public Criteria andPhotoFrameNotBetween(Integer value1, Integer value2) {
            addCriterion("photoFrame not between", value1, value2, "photoFrame");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}