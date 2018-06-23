package com.funcy.g01.base.bo.serverconfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServerStateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ServerStateExample() {
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

        public Criteria andServerIdIsNull() {
            addCriterion("serverId is null");
            return (Criteria) this;
        }

        public Criteria andServerIdIsNotNull() {
            addCriterion("serverId is not null");
            return (Criteria) this;
        }

        public Criteria andServerIdEqualTo(int value) {
            addCriterion("serverId =", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdNotEqualTo(int value) {
            addCriterion("serverId <>", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdGreaterThan(int value) {
            addCriterion("serverId >", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdGreaterThanOrEqualTo(int value) {
            addCriterion("serverId >=", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdLessThan(int value) {
            addCriterion("serverId <", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdLessThanOrEqualTo(int value) {
            addCriterion("serverId <=", value, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdIn(List<Integer> values) {
            addCriterion("serverId in", values, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdNotIn(List<Integer> values) {
            addCriterion("serverId not in", values, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdBetween(int value1, int value2) {
            addCriterion("serverId between", value1, value2, "serverId");
            return (Criteria) this;
        }

        public Criteria andServerIdNotBetween(int value1, int value2) {
            addCriterion("serverId not between", value1, value2, "serverId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIsNull() {
            addCriterion("lastUpdateTime is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIsNotNull() {
            addCriterion("lastUpdateTime is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeEqualTo(Date value) {
            addCriterion("lastUpdateTime =", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotEqualTo(Date value) {
            addCriterion("lastUpdateTime <>", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeGreaterThan(Date value) {
            addCriterion("lastUpdateTime >", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("lastUpdateTime >=", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeLessThan(Date value) {
            addCriterion("lastUpdateTime <", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("lastUpdateTime <=", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIn(List<Date> values) {
            addCriterion("lastUpdateTime in", values, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotIn(List<Date> values) {
            addCriterion("lastUpdateTime not in", values, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("lastUpdateTime between", value1, value2, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("lastUpdateTime not between", value1, value2, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andHallRoomNumIsNull() {
            addCriterion("hallRoomNum is null");
            return (Criteria) this;
        }

        public Criteria andHallRoomNumIsNotNull() {
            addCriterion("hallRoomNum is not null");
            return (Criteria) this;
        }

        public Criteria andHallRoomNumEqualTo(int value) {
            addCriterion("hallRoomNum =", value, "hallRoomNum");
            return (Criteria) this;
        }

        public Criteria andHallRoomNumNotEqualTo(int value) {
            addCriterion("hallRoomNum <>", value, "hallRoomNum");
            return (Criteria) this;
        }

        public Criteria andHallRoomNumGreaterThan(int value) {
            addCriterion("hallRoomNum >", value, "hallRoomNum");
            return (Criteria) this;
        }

        public Criteria andHallRoomNumGreaterThanOrEqualTo(int value) {
            addCriterion("hallRoomNum >=", value, "hallRoomNum");
            return (Criteria) this;
        }

        public Criteria andHallRoomNumLessThan(int value) {
            addCriterion("hallRoomNum <", value, "hallRoomNum");
            return (Criteria) this;
        }

        public Criteria andHallRoomNumLessThanOrEqualTo(int value) {
            addCriterion("hallRoomNum <=", value, "hallRoomNum");
            return (Criteria) this;
        }

        public Criteria andHallRoomNumIn(List<Integer> values) {
            addCriterion("hallRoomNum in", values, "hallRoomNum");
            return (Criteria) this;
        }

        public Criteria andHallRoomNumNotIn(List<Integer> values) {
            addCriterion("hallRoomNum not in", values, "hallRoomNum");
            return (Criteria) this;
        }

        public Criteria andHallRoomNumBetween(int value1, int value2) {
            addCriterion("hallRoomNum between", value1, value2, "hallRoomNum");
            return (Criteria) this;
        }

        public Criteria andHallRoomNumNotBetween(int value1, int value2) {
            addCriterion("hallRoomNum not between", value1, value2, "hallRoomNum");
            return (Criteria) this;
        }

        public Criteria andHallUserNumIsNull() {
            addCriterion("hallUserNum is null");
            return (Criteria) this;
        }

        public Criteria andHallUserNumIsNotNull() {
            addCriterion("hallUserNum is not null");
            return (Criteria) this;
        }

        public Criteria andHallUserNumEqualTo(int value) {
            addCriterion("hallUserNum =", value, "hallUserNum");
            return (Criteria) this;
        }

        public Criteria andHallUserNumNotEqualTo(int value) {
            addCriterion("hallUserNum <>", value, "hallUserNum");
            return (Criteria) this;
        }

        public Criteria andHallUserNumGreaterThan(int value) {
            addCriterion("hallUserNum >", value, "hallUserNum");
            return (Criteria) this;
        }

        public Criteria andHallUserNumGreaterThanOrEqualTo(int value) {
            addCriterion("hallUserNum >=", value, "hallUserNum");
            return (Criteria) this;
        }

        public Criteria andHallUserNumLessThan(int value) {
            addCriterion("hallUserNum <", value, "hallUserNum");
            return (Criteria) this;
        }

        public Criteria andHallUserNumLessThanOrEqualTo(int value) {
            addCriterion("hallUserNum <=", value, "hallUserNum");
            return (Criteria) this;
        }

        public Criteria andHallUserNumIn(List<Integer> values) {
            addCriterion("hallUserNum in", values, "hallUserNum");
            return (Criteria) this;
        }

        public Criteria andHallUserNumNotIn(List<Integer> values) {
            addCriterion("hallUserNum not in", values, "hallUserNum");
            return (Criteria) this;
        }

        public Criteria andHallUserNumBetween(int value1, int value2) {
            addCriterion("hallUserNum between", value1, value2, "hallUserNum");
            return (Criteria) this;
        }

        public Criteria andHallUserNumNotBetween(int value1, int value2) {
            addCriterion("hallUserNum not between", value1, value2, "hallUserNum");
            return (Criteria) this;
        }

        public Criteria andFightRoomNumIsNull() {
            addCriterion("fightRoomNum is null");
            return (Criteria) this;
        }

        public Criteria andFightRoomNumIsNotNull() {
            addCriterion("fightRoomNum is not null");
            return (Criteria) this;
        }

        public Criteria andFightRoomNumEqualTo(int value) {
            addCriterion("fightRoomNum =", value, "fightRoomNum");
            return (Criteria) this;
        }

        public Criteria andFightRoomNumNotEqualTo(int value) {
            addCriterion("fightRoomNum <>", value, "fightRoomNum");
            return (Criteria) this;
        }

        public Criteria andFightRoomNumGreaterThan(int value) {
            addCriterion("fightRoomNum >", value, "fightRoomNum");
            return (Criteria) this;
        }

        public Criteria andFightRoomNumGreaterThanOrEqualTo(int value) {
            addCriterion("fightRoomNum >=", value, "fightRoomNum");
            return (Criteria) this;
        }

        public Criteria andFightRoomNumLessThan(int value) {
            addCriterion("fightRoomNum <", value, "fightRoomNum");
            return (Criteria) this;
        }

        public Criteria andFightRoomNumLessThanOrEqualTo(int value) {
            addCriterion("fightRoomNum <=", value, "fightRoomNum");
            return (Criteria) this;
        }

        public Criteria andFightRoomNumIn(List<Integer> values) {
            addCriterion("fightRoomNum in", values, "fightRoomNum");
            return (Criteria) this;
        }

        public Criteria andFightRoomNumNotIn(List<Integer> values) {
            addCriterion("fightRoomNum not in", values, "fightRoomNum");
            return (Criteria) this;
        }

        public Criteria andFightRoomNumBetween(int value1, int value2) {
            addCriterion("fightRoomNum between", value1, value2, "fightRoomNum");
            return (Criteria) this;
        }

        public Criteria andFightRoomNumNotBetween(int value1, int value2) {
            addCriterion("fightRoomNum not between", value1, value2, "fightRoomNum");
            return (Criteria) this;
        }

        public Criteria andFightUserNumIsNull() {
            addCriterion("fightUserNum is null");
            return (Criteria) this;
        }

        public Criteria andFightUserNumIsNotNull() {
            addCriterion("fightUserNum is not null");
            return (Criteria) this;
        }

        public Criteria andFightUserNumEqualTo(int value) {
            addCriterion("fightUserNum =", value, "fightUserNum");
            return (Criteria) this;
        }

        public Criteria andFightUserNumNotEqualTo(int value) {
            addCriterion("fightUserNum <>", value, "fightUserNum");
            return (Criteria) this;
        }

        public Criteria andFightUserNumGreaterThan(int value) {
            addCriterion("fightUserNum >", value, "fightUserNum");
            return (Criteria) this;
        }

        public Criteria andFightUserNumGreaterThanOrEqualTo(int value) {
            addCriterion("fightUserNum >=", value, "fightUserNum");
            return (Criteria) this;
        }

        public Criteria andFightUserNumLessThan(int value) {
            addCriterion("fightUserNum <", value, "fightUserNum");
            return (Criteria) this;
        }

        public Criteria andFightUserNumLessThanOrEqualTo(int value) {
            addCriterion("fightUserNum <=", value, "fightUserNum");
            return (Criteria) this;
        }

        public Criteria andFightUserNumIn(List<Integer> values) {
            addCriterion("fightUserNum in", values, "fightUserNum");
            return (Criteria) this;
        }

        public Criteria andFightUserNumNotIn(List<Integer> values) {
            addCriterion("fightUserNum not in", values, "fightUserNum");
            return (Criteria) this;
        }

        public Criteria andFightUserNumBetween(int value1, int value2) {
            addCriterion("fightUserNum between", value1, value2, "fightUserNum");
            return (Criteria) this;
        }

        public Criteria andFightUserNumNotBetween(int value1, int value2) {
            addCriterion("fightUserNum not between", value1, value2, "fightUserNum");
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