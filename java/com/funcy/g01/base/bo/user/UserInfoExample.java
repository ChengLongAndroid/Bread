package com.funcy.g01.base.bo.user;

import java.util.ArrayList;
import java.util.List;

public class UserInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserInfoExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(int value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(int value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(int value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(int value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(int value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(int value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(int value1, int value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(int value1, int value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUDIDIsNull() {
            addCriterion("UDID is null");
            return (Criteria) this;
        }

        public Criteria andUDIDIsNotNull() {
            addCriterion("UDID is not null");
            return (Criteria) this;
        }

        public Criteria andUDIDEqualTo(String value) {
            addCriterion("UDID =", value, "UDID");
            return (Criteria) this;
        }

        public Criteria andUDIDNotEqualTo(String value) {
            addCriterion("UDID <>", value, "UDID");
            return (Criteria) this;
        }

        public Criteria andUDIDGreaterThan(String value) {
            addCriterion("UDID >", value, "UDID");
            return (Criteria) this;
        }

        public Criteria andUDIDGreaterThanOrEqualTo(String value) {
            addCriterion("UDID >=", value, "UDID");
            return (Criteria) this;
        }

        public Criteria andUDIDLessThan(String value) {
            addCriterion("UDID <", value, "UDID");
            return (Criteria) this;
        }

        public Criteria andUDIDLessThanOrEqualTo(String value) {
            addCriterion("UDID <=", value, "UDID");
            return (Criteria) this;
        }

        public Criteria andUDIDLike(String value) {
            addCriterion("UDID like", value, "UDID");
            return (Criteria) this;
        }

        public Criteria andUDIDNotLike(String value) {
            addCriterion("UDID not like", value, "UDID");
            return (Criteria) this;
        }

        public Criteria andUDIDIn(List<String> values) {
            addCriterion("UDID in", values, "UDID");
            return (Criteria) this;
        }

        public Criteria andUDIDNotIn(List<String> values) {
            addCriterion("UDID not in", values, "UDID");
            return (Criteria) this;
        }

        public Criteria andUDIDBetween(String value1, String value2) {
            addCriterion("UDID between", value1, value2, "UDID");
            return (Criteria) this;
        }

        public Criteria andUDIDNotBetween(String value1, String value2) {
            addCriterion("UDID not between", value1, value2, "UDID");
            return (Criteria) this;
        }

        public Criteria andGameCenterIdIsNull() {
            addCriterion("gameCenterId is null");
            return (Criteria) this;
        }

        public Criteria andGameCenterIdIsNotNull() {
            addCriterion("gameCenterId is not null");
            return (Criteria) this;
        }

        public Criteria andGameCenterIdEqualTo(String value) {
            addCriterion("gameCenterId =", value, "gameCenterId");
            return (Criteria) this;
        }

        public Criteria andGameCenterIdNotEqualTo(String value) {
            addCriterion("gameCenterId <>", value, "gameCenterId");
            return (Criteria) this;
        }

        public Criteria andGameCenterIdGreaterThan(String value) {
            addCriterion("gameCenterId >", value, "gameCenterId");
            return (Criteria) this;
        }

        public Criteria andGameCenterIdGreaterThanOrEqualTo(String value) {
            addCriterion("gameCenterId >=", value, "gameCenterId");
            return (Criteria) this;
        }

        public Criteria andGameCenterIdLessThan(String value) {
            addCriterion("gameCenterId <", value, "gameCenterId");
            return (Criteria) this;
        }

        public Criteria andGameCenterIdLessThanOrEqualTo(String value) {
            addCriterion("gameCenterId <=", value, "gameCenterId");
            return (Criteria) this;
        }

        public Criteria andGameCenterIdLike(String value) {
            addCriterion("gameCenterId like", value, "gameCenterId");
            return (Criteria) this;
        }

        public Criteria andGameCenterIdNotLike(String value) {
            addCriterion("gameCenterId not like", value, "gameCenterId");
            return (Criteria) this;
        }

        public Criteria andGameCenterIdIn(List<String> values) {
            addCriterion("gameCenterId in", values, "gameCenterId");
            return (Criteria) this;
        }

        public Criteria andGameCenterIdNotIn(List<String> values) {
            addCriterion("gameCenterId not in", values, "gameCenterId");
            return (Criteria) this;
        }

        public Criteria andGameCenterIdBetween(String value1, String value2) {
            addCriterion("gameCenterId between", value1, value2, "gameCenterId");
            return (Criteria) this;
        }

        public Criteria andGameCenterIdNotBetween(String value1, String value2) {
            addCriterion("gameCenterId not between", value1, value2, "gameCenterId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdIsNull() {
            addCriterion("platformId is null");
            return (Criteria) this;
        }

        public Criteria andPlatformIdIsNotNull() {
            addCriterion("platformId is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformIdEqualTo(String value) {
            addCriterion("platformId =", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdNotEqualTo(String value) {
            addCriterion("platformId <>", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdGreaterThan(String value) {
            addCriterion("platformId >", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdGreaterThanOrEqualTo(String value) {
            addCriterion("platformId >=", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdLessThan(String value) {
            addCriterion("platformId <", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdLessThanOrEqualTo(String value) {
            addCriterion("platformId <=", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdLike(String value) {
            addCriterion("platformId like", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdNotLike(String value) {
            addCriterion("platformId not like", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdIn(List<String> values) {
            addCriterion("platformId in", values, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdNotIn(List<String> values) {
            addCriterion("platformId not in", values, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdBetween(String value1, String value2) {
            addCriterion("platformId between", value1, value2, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdNotBetween(String value1, String value2) {
            addCriterion("platformId not between", value1, value2, "platformId");
            return (Criteria) this;
        }

        public Criteria andRewardIsNull() {
            addCriterion("reward is null");
            return (Criteria) this;
        }

        public Criteria andRewardIsNotNull() {
            addCriterion("reward is not null");
            return (Criteria) this;
        }

        public Criteria andRewardEqualTo(int value) {
            addCriterion("reward =", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardNotEqualTo(int value) {
            addCriterion("reward <>", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardGreaterThan(int value) {
            addCriterion("reward >", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardGreaterThanOrEqualTo(int value) {
            addCriterion("reward >=", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardLessThan(int value) {
            addCriterion("reward <", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardLessThanOrEqualTo(int value) {
            addCriterion("reward <=", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardIn(List<Integer> values) {
            addCriterion("reward in", values, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardNotIn(List<Integer> values) {
            addCriterion("reward not in", values, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardBetween(int value1, int value2) {
            addCriterion("reward between", value1, value2, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardNotBetween(int value1, int value2) {
            addCriterion("reward not between", value1, value2, "reward");
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