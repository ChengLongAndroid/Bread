package com.funcy.g01.base.bo.user;

import java.util.ArrayList;
import java.util.List;

public class AdminChannelInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AdminChannelInfoExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
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

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andChannelIdIsNull() {
            addCriterion("channelId is null");
            return (Criteria) this;
        }

        public Criteria andChannelIdIsNotNull() {
            addCriterion("channelId is not null");
            return (Criteria) this;
        }

        public Criteria andChannelIdEqualTo(Integer value) {
            addCriterion("channelId =", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotEqualTo(Integer value) {
            addCriterion("channelId <>", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdGreaterThan(Integer value) {
            addCriterion("channelId >", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("channelId >=", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLessThan(Integer value) {
            addCriterion("channelId <", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLessThanOrEqualTo(Integer value) {
            addCriterion("channelId <=", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdIn(List<Integer> values) {
            addCriterion("channelId in", values, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotIn(List<Integer> values) {
            addCriterion("channelId not in", values, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdBetween(Integer value1, Integer value2) {
            addCriterion("channelId between", value1, value2, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotBetween(Integer value1, Integer value2) {
            addCriterion("channelId not between", value1, value2, "channelId");
            return (Criteria) this;
        }

        public Criteria andUpdateUrlIsNull() {
            addCriterion("updateUrl is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUrlIsNotNull() {
            addCriterion("updateUrl is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUrlEqualTo(String value) {
            addCriterion("updateUrl =", value, "updateUrl");
            return (Criteria) this;
        }

        public Criteria andUpdateUrlNotEqualTo(String value) {
            addCriterion("updateUrl <>", value, "updateUrl");
            return (Criteria) this;
        }

        public Criteria andUpdateUrlGreaterThan(String value) {
            addCriterion("updateUrl >", value, "updateUrl");
            return (Criteria) this;
        }

        public Criteria andUpdateUrlGreaterThanOrEqualTo(String value) {
            addCriterion("updateUrl >=", value, "updateUrl");
            return (Criteria) this;
        }

        public Criteria andUpdateUrlLessThan(String value) {
            addCriterion("updateUrl <", value, "updateUrl");
            return (Criteria) this;
        }

        public Criteria andUpdateUrlLessThanOrEqualTo(String value) {
            addCriterion("updateUrl <=", value, "updateUrl");
            return (Criteria) this;
        }

        public Criteria andUpdateUrlLike(String value) {
            addCriterion("updateUrl like", value, "updateUrl");
            return (Criteria) this;
        }

        public Criteria andUpdateUrlNotLike(String value) {
            addCriterion("updateUrl not like", value, "updateUrl");
            return (Criteria) this;
        }

        public Criteria andUpdateUrlIn(List<String> values) {
            addCriterion("updateUrl in", values, "updateUrl");
            return (Criteria) this;
        }

        public Criteria andUpdateUrlNotIn(List<String> values) {
            addCriterion("updateUrl not in", values, "updateUrl");
            return (Criteria) this;
        }

        public Criteria andUpdateUrlBetween(String value1, String value2) {
            addCriterion("updateUrl between", value1, value2, "updateUrl");
            return (Criteria) this;
        }

        public Criteria andUpdateUrlNotBetween(String value1, String value2) {
            addCriterion("updateUrl not between", value1, value2, "updateUrl");
            return (Criteria) this;
        }

        public Criteria andUpdatePortIsNull() {
            addCriterion("updatePort is null");
            return (Criteria) this;
        }

        public Criteria andUpdatePortIsNotNull() {
            addCriterion("updatePort is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatePortEqualTo(Integer value) {
            addCriterion("updatePort =", value, "updatePort");
            return (Criteria) this;
        }

        public Criteria andUpdatePortNotEqualTo(Integer value) {
            addCriterion("updatePort <>", value, "updatePort");
            return (Criteria) this;
        }

        public Criteria andUpdatePortGreaterThan(Integer value) {
            addCriterion("updatePort >", value, "updatePort");
            return (Criteria) this;
        }

        public Criteria andUpdatePortGreaterThanOrEqualTo(Integer value) {
            addCriterion("updatePort >=", value, "updatePort");
            return (Criteria) this;
        }

        public Criteria andUpdatePortLessThan(Integer value) {
            addCriterion("updatePort <", value, "updatePort");
            return (Criteria) this;
        }

        public Criteria andUpdatePortLessThanOrEqualTo(Integer value) {
            addCriterion("updatePort <=", value, "updatePort");
            return (Criteria) this;
        }

        public Criteria andUpdatePortIn(List<Integer> values) {
            addCriterion("updatePort in", values, "updatePort");
            return (Criteria) this;
        }

        public Criteria andUpdatePortNotIn(List<Integer> values) {
            addCriterion("updatePort not in", values, "updatePort");
            return (Criteria) this;
        }

        public Criteria andUpdatePortBetween(Integer value1, Integer value2) {
            addCriterion("updatePort between", value1, value2, "updatePort");
            return (Criteria) this;
        }

        public Criteria andUpdatePortNotBetween(Integer value1, Integer value2) {
            addCriterion("updatePort not between", value1, value2, "updatePort");
            return (Criteria) this;
        }

        public Criteria andUpdateContextIsNull() {
            addCriterion("updateContext is null");
            return (Criteria) this;
        }

        public Criteria andUpdateContextIsNotNull() {
            addCriterion("updateContext is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateContextEqualTo(String value) {
            addCriterion("updateContext =", value, "updateContext");
            return (Criteria) this;
        }

        public Criteria andUpdateContextNotEqualTo(String value) {
            addCriterion("updateContext <>", value, "updateContext");
            return (Criteria) this;
        }

        public Criteria andUpdateContextGreaterThan(String value) {
            addCriterion("updateContext >", value, "updateContext");
            return (Criteria) this;
        }

        public Criteria andUpdateContextGreaterThanOrEqualTo(String value) {
            addCriterion("updateContext >=", value, "updateContext");
            return (Criteria) this;
        }

        public Criteria andUpdateContextLessThan(String value) {
            addCriterion("updateContext <", value, "updateContext");
            return (Criteria) this;
        }

        public Criteria andUpdateContextLessThanOrEqualTo(String value) {
            addCriterion("updateContext <=", value, "updateContext");
            return (Criteria) this;
        }

        public Criteria andUpdateContextLike(String value) {
            addCriterion("updateContext like", value, "updateContext");
            return (Criteria) this;
        }

        public Criteria andUpdateContextNotLike(String value) {
            addCriterion("updateContext not like", value, "updateContext");
            return (Criteria) this;
        }

        public Criteria andUpdateContextIn(List<String> values) {
            addCriterion("updateContext in", values, "updateContext");
            return (Criteria) this;
        }

        public Criteria andUpdateContextNotIn(List<String> values) {
            addCriterion("updateContext not in", values, "updateContext");
            return (Criteria) this;
        }

        public Criteria andUpdateContextBetween(String value1, String value2) {
            addCriterion("updateContext between", value1, value2, "updateContext");
            return (Criteria) this;
        }

        public Criteria andUpdateContextNotBetween(String value1, String value2) {
            addCriterion("updateContext not between", value1, value2, "updateContext");
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