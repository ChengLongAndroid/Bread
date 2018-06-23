package com.funcy.g01.base.bo.serverconfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServerInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ServerInfoExample() {
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

        public Criteria andServerTypeIsNull() {
            addCriterion("serverType is null");
            return (Criteria) this;
        }

        public Criteria andServerTypeIsNotNull() {
            addCriterion("serverType is not null");
            return (Criteria) this;
        }

        public Criteria andServerTypeEqualTo(String value) {
            addCriterion("serverType =", value, "serverType");
            return (Criteria) this;
        }

        public Criteria andServerTypeNotEqualTo(String value) {
            addCriterion("serverType <>", value, "serverType");
            return (Criteria) this;
        }

        public Criteria andServerTypeGreaterThan(String value) {
            addCriterion("serverType >", value, "serverType");
            return (Criteria) this;
        }

        public Criteria andServerTypeGreaterThanOrEqualTo(String value) {
            addCriterion("serverType >=", value, "serverType");
            return (Criteria) this;
        }

        public Criteria andServerTypeLessThan(String value) {
            addCriterion("serverType <", value, "serverType");
            return (Criteria) this;
        }

        public Criteria andServerTypeLessThanOrEqualTo(String value) {
            addCriterion("serverType <=", value, "serverType");
            return (Criteria) this;
        }

        public Criteria andServerTypeLike(String value) {
            addCriterion("serverType like", value, "serverType");
            return (Criteria) this;
        }

        public Criteria andServerTypeNotLike(String value) {
            addCriterion("serverType not like", value, "serverType");
            return (Criteria) this;
        }

        public Criteria andServerTypeIn(List<String> values) {
            addCriterion("serverType in", values, "serverType");
            return (Criteria) this;
        }

        public Criteria andServerTypeNotIn(List<String> values) {
            addCriterion("serverType not in", values, "serverType");
            return (Criteria) this;
        }

        public Criteria andServerTypeBetween(String value1, String value2) {
            addCriterion("serverType between", value1, value2, "serverType");
            return (Criteria) this;
        }

        public Criteria andServerTypeNotBetween(String value1, String value2) {
            addCriterion("serverType not between", value1, value2, "serverType");
            return (Criteria) this;
        }

        public Criteria andNetIpIsNull() {
            addCriterion("netIp is null");
            return (Criteria) this;
        }

        public Criteria andNetIpIsNotNull() {
            addCriterion("netIp is not null");
            return (Criteria) this;
        }

        public Criteria andNetIpEqualTo(String value) {
            addCriterion("netIp =", value, "netIp");
            return (Criteria) this;
        }

        public Criteria andNetIpNotEqualTo(String value) {
            addCriterion("netIp <>", value, "netIp");
            return (Criteria) this;
        }

        public Criteria andNetIpGreaterThan(String value) {
            addCriterion("netIp >", value, "netIp");
            return (Criteria) this;
        }

        public Criteria andNetIpGreaterThanOrEqualTo(String value) {
            addCriterion("netIp >=", value, "netIp");
            return (Criteria) this;
        }

        public Criteria andNetIpLessThan(String value) {
            addCriterion("netIp <", value, "netIp");
            return (Criteria) this;
        }

        public Criteria andNetIpLessThanOrEqualTo(String value) {
            addCriterion("netIp <=", value, "netIp");
            return (Criteria) this;
        }

        public Criteria andNetIpLike(String value) {
            addCriterion("netIp like", value, "netIp");
            return (Criteria) this;
        }

        public Criteria andNetIpNotLike(String value) {
            addCriterion("netIp not like", value, "netIp");
            return (Criteria) this;
        }

        public Criteria andNetIpIn(List<String> values) {
            addCriterion("netIp in", values, "netIp");
            return (Criteria) this;
        }

        public Criteria andNetIpNotIn(List<String> values) {
            addCriterion("netIp not in", values, "netIp");
            return (Criteria) this;
        }

        public Criteria andNetIpBetween(String value1, String value2) {
            addCriterion("netIp between", value1, value2, "netIp");
            return (Criteria) this;
        }

        public Criteria andNetIpNotBetween(String value1, String value2) {
            addCriterion("netIp not between", value1, value2, "netIp");
            return (Criteria) this;
        }

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("ip like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("ip not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("ip not between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andHttpPortIsNull() {
            addCriterion("httpPort is null");
            return (Criteria) this;
        }

        public Criteria andHttpPortIsNotNull() {
            addCriterion("httpPort is not null");
            return (Criteria) this;
        }

        public Criteria andHttpPortEqualTo(int value) {
            addCriterion("httpPort =", value, "httpPort");
            return (Criteria) this;
        }

        public Criteria andHttpPortNotEqualTo(int value) {
            addCriterion("httpPort <>", value, "httpPort");
            return (Criteria) this;
        }

        public Criteria andHttpPortGreaterThan(int value) {
            addCriterion("httpPort >", value, "httpPort");
            return (Criteria) this;
        }

        public Criteria andHttpPortGreaterThanOrEqualTo(int value) {
            addCriterion("httpPort >=", value, "httpPort");
            return (Criteria) this;
        }

        public Criteria andHttpPortLessThan(int value) {
            addCriterion("httpPort <", value, "httpPort");
            return (Criteria) this;
        }

        public Criteria andHttpPortLessThanOrEqualTo(int value) {
            addCriterion("httpPort <=", value, "httpPort");
            return (Criteria) this;
        }

        public Criteria andHttpPortIn(List<Integer> values) {
            addCriterion("httpPort in", values, "httpPort");
            return (Criteria) this;
        }

        public Criteria andHttpPortNotIn(List<Integer> values) {
            addCriterion("httpPort not in", values, "httpPort");
            return (Criteria) this;
        }

        public Criteria andHttpPortBetween(int value1, int value2) {
            addCriterion("httpPort between", value1, value2, "httpPort");
            return (Criteria) this;
        }

        public Criteria andHttpPortNotBetween(int value1, int value2) {
            addCriterion("httpPort not between", value1, value2, "httpPort");
            return (Criteria) this;
        }

        public Criteria andRoomServerPortIsNull() {
            addCriterion("roomServerPort is null");
            return (Criteria) this;
        }

        public Criteria andRoomServerPortIsNotNull() {
            addCriterion("roomServerPort is not null");
            return (Criteria) this;
        }

        public Criteria andRoomServerPortEqualTo(int value) {
            addCriterion("roomServerPort =", value, "roomServerPort");
            return (Criteria) this;
        }

        public Criteria andRoomServerPortNotEqualTo(int value) {
            addCriterion("roomServerPort <>", value, "roomServerPort");
            return (Criteria) this;
        }

        public Criteria andRoomServerPortGreaterThan(int value) {
            addCriterion("roomServerPort >", value, "roomServerPort");
            return (Criteria) this;
        }

        public Criteria andRoomServerPortGreaterThanOrEqualTo(int value) {
            addCriterion("roomServerPort >=", value, "roomServerPort");
            return (Criteria) this;
        }

        public Criteria andRoomServerPortLessThan(int value) {
            addCriterion("roomServerPort <", value, "roomServerPort");
            return (Criteria) this;
        }

        public Criteria andRoomServerPortLessThanOrEqualTo(int value) {
            addCriterion("roomServerPort <=", value, "roomServerPort");
            return (Criteria) this;
        }

        public Criteria andRoomServerPortIn(List<Integer> values) {
            addCriterion("roomServerPort in", values, "roomServerPort");
            return (Criteria) this;
        }

        public Criteria andRoomServerPortNotIn(List<Integer> values) {
            addCriterion("roomServerPort not in", values, "roomServerPort");
            return (Criteria) this;
        }

        public Criteria andRoomServerPortBetween(int value1, int value2) {
            addCriterion("roomServerPort between", value1, value2, "roomServerPort");
            return (Criteria) this;
        }

        public Criteria andRoomServerPortNotBetween(int value1, int value2) {
            addCriterion("roomServerPort not between", value1, value2, "roomServerPort");
            return (Criteria) this;
        }

        public Criteria andHallServerPortIsNull() {
            addCriterion("hallServerPort is null");
            return (Criteria) this;
        }

        public Criteria andHallServerPortIsNotNull() {
            addCriterion("hallServerPort is not null");
            return (Criteria) this;
        }

        public Criteria andHallServerPortEqualTo(int value) {
            addCriterion("hallServerPort =", value, "hallServerPort");
            return (Criteria) this;
        }

        public Criteria andHallServerPortNotEqualTo(int value) {
            addCriterion("hallServerPort <>", value, "hallServerPort");
            return (Criteria) this;
        }

        public Criteria andHallServerPortGreaterThan(int value) {
            addCriterion("hallServerPort >", value, "hallServerPort");
            return (Criteria) this;
        }

        public Criteria andHallServerPortGreaterThanOrEqualTo(int value) {
            addCriterion("hallServerPort >=", value, "hallServerPort");
            return (Criteria) this;
        }

        public Criteria andHallServerPortLessThan(int value) {
            addCriterion("hallServerPort <", value, "hallServerPort");
            return (Criteria) this;
        }

        public Criteria andHallServerPortLessThanOrEqualTo(int value) {
            addCriterion("hallServerPort <=", value, "hallServerPort");
            return (Criteria) this;
        }

        public Criteria andHallServerPortIn(List<Integer> values) {
            addCriterion("hallServerPort in", values, "hallServerPort");
            return (Criteria) this;
        }

        public Criteria andHallServerPortNotIn(List<Integer> values) {
            addCriterion("hallServerPort not in", values, "hallServerPort");
            return (Criteria) this;
        }

        public Criteria andHallServerPortBetween(int value1, int value2) {
            addCriterion("hallServerPort between", value1, value2, "hallServerPort");
            return (Criteria) this;
        }

        public Criteria andHallServerPortNotBetween(int value1, int value2) {
            addCriterion("hallServerPort not between", value1, value2, "hallServerPort");
            return (Criteria) this;
        }

        public Criteria andSecretKeyIsNull() {
            addCriterion("secretKey is null");
            return (Criteria) this;
        }

        public Criteria andSecretKeyIsNotNull() {
            addCriterion("secretKey is not null");
            return (Criteria) this;
        }

        public Criteria andSecretKeyEqualTo(String value) {
            addCriterion("secretKey =", value, "secretKey");
            return (Criteria) this;
        }

        public Criteria andSecretKeyNotEqualTo(String value) {
            addCriterion("secretKey <>", value, "secretKey");
            return (Criteria) this;
        }

        public Criteria andSecretKeyGreaterThan(String value) {
            addCriterion("secretKey >", value, "secretKey");
            return (Criteria) this;
        }

        public Criteria andSecretKeyGreaterThanOrEqualTo(String value) {
            addCriterion("secretKey >=", value, "secretKey");
            return (Criteria) this;
        }

        public Criteria andSecretKeyLessThan(String value) {
            addCriterion("secretKey <", value, "secretKey");
            return (Criteria) this;
        }

        public Criteria andSecretKeyLessThanOrEqualTo(String value) {
            addCriterion("secretKey <=", value, "secretKey");
            return (Criteria) this;
        }

        public Criteria andSecretKeyLike(String value) {
            addCriterion("secretKey like", value, "secretKey");
            return (Criteria) this;
        }

        public Criteria andSecretKeyNotLike(String value) {
            addCriterion("secretKey not like", value, "secretKey");
            return (Criteria) this;
        }

        public Criteria andSecretKeyIn(List<String> values) {
            addCriterion("secretKey in", values, "secretKey");
            return (Criteria) this;
        }

        public Criteria andSecretKeyNotIn(List<String> values) {
            addCriterion("secretKey not in", values, "secretKey");
            return (Criteria) this;
        }

        public Criteria andSecretKeyBetween(String value1, String value2) {
            addCriterion("secretKey between", value1, value2, "secretKey");
            return (Criteria) this;
        }

        public Criteria andSecretKeyNotBetween(String value1, String value2) {
            addCriterion("secretKey not between", value1, value2, "secretKey");
            return (Criteria) this;
        }

        public Criteria andServerStateIsNull() {
            addCriterion("serverState is null");
            return (Criteria) this;
        }

        public Criteria andServerStateIsNotNull() {
            addCriterion("serverState is not null");
            return (Criteria) this;
        }

        public Criteria andServerStateEqualTo(int value) {
            addCriterion("serverState =", value, "serverState");
            return (Criteria) this;
        }

        public Criteria andServerStateNotEqualTo(int value) {
            addCriterion("serverState <>", value, "serverState");
            return (Criteria) this;
        }

        public Criteria andServerStateGreaterThan(int value) {
            addCriterion("serverState >", value, "serverState");
            return (Criteria) this;
        }

        public Criteria andServerStateGreaterThanOrEqualTo(int value) {
            addCriterion("serverState >=", value, "serverState");
            return (Criteria) this;
        }

        public Criteria andServerStateLessThan(int value) {
            addCriterion("serverState <", value, "serverState");
            return (Criteria) this;
        }

        public Criteria andServerStateLessThanOrEqualTo(int value) {
            addCriterion("serverState <=", value, "serverState");
            return (Criteria) this;
        }

        public Criteria andServerStateIn(List<Integer> values) {
            addCriterion("serverState in", values, "serverState");
            return (Criteria) this;
        }

        public Criteria andServerStateNotIn(List<Integer> values) {
            addCriterion("serverState not in", values, "serverState");
            return (Criteria) this;
        }

        public Criteria andServerStateBetween(int value1, int value2) {
            addCriterion("serverState between", value1, value2, "serverState");
            return (Criteria) this;
        }

        public Criteria andServerStateNotBetween(int value1, int value2) {
            addCriterion("serverState not between", value1, value2, "serverState");
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

        public Criteria andOpenTimeIsNull() {
            addCriterion("openTime is null");
            return (Criteria) this;
        }

        public Criteria andOpenTimeIsNotNull() {
            addCriterion("openTime is not null");
            return (Criteria) this;
        }

        public Criteria andOpenTimeEqualTo(Date value) {
            addCriterion("openTime =", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeNotEqualTo(Date value) {
            addCriterion("openTime <>", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeGreaterThan(Date value) {
            addCriterion("openTime >", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("openTime >=", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeLessThan(Date value) {
            addCriterion("openTime <", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeLessThanOrEqualTo(Date value) {
            addCriterion("openTime <=", value, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeIn(List<Date> values) {
            addCriterion("openTime in", values, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeNotIn(List<Date> values) {
            addCriterion("openTime not in", values, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeBetween(Date value1, Date value2) {
            addCriterion("openTime between", value1, value2, "openTime");
            return (Criteria) this;
        }

        public Criteria andOpenTimeNotBetween(Date value1, Date value2) {
            addCriterion("openTime not between", value1, value2, "openTime");
            return (Criteria) this;
        }

        public Criteria andDispatcherServerPortIsNull() {
            addCriterion("dispatcherServerPort is null");
            return (Criteria) this;
        }

        public Criteria andDispatcherServerPortIsNotNull() {
            addCriterion("dispatcherServerPort is not null");
            return (Criteria) this;
        }

        public Criteria andDispatcherServerPortEqualTo(int value) {
            addCriterion("dispatcherServerPort =", value, "dispatcherServerPort");
            return (Criteria) this;
        }

        public Criteria andDispatcherServerPortNotEqualTo(int value) {
            addCriterion("dispatcherServerPort <>", value, "dispatcherServerPort");
            return (Criteria) this;
        }

        public Criteria andDispatcherServerPortGreaterThan(int value) {
            addCriterion("dispatcherServerPort >", value, "dispatcherServerPort");
            return (Criteria) this;
        }

        public Criteria andDispatcherServerPortGreaterThanOrEqualTo(int value) {
            addCriterion("dispatcherServerPort >=", value, "dispatcherServerPort");
            return (Criteria) this;
        }

        public Criteria andDispatcherServerPortLessThan(int value) {
            addCriterion("dispatcherServerPort <", value, "dispatcherServerPort");
            return (Criteria) this;
        }

        public Criteria andDispatcherServerPortLessThanOrEqualTo(int value) {
            addCriterion("dispatcherServerPort <=", value, "dispatcherServerPort");
            return (Criteria) this;
        }

        public Criteria andDispatcherServerPortIn(List<Integer> values) {
            addCriterion("dispatcherServerPort in", values, "dispatcherServerPort");
            return (Criteria) this;
        }

        public Criteria andDispatcherServerPortNotIn(List<Integer> values) {
            addCriterion("dispatcherServerPort not in", values, "dispatcherServerPort");
            return (Criteria) this;
        }

        public Criteria andDispatcherServerPortBetween(int value1, int value2) {
            addCriterion("dispatcherServerPort between", value1, value2, "dispatcherServerPort");
            return (Criteria) this;
        }

        public Criteria andDispatcherServerPortNotBetween(int value1, int value2) {
            addCriterion("dispatcherServerPort not between", value1, value2, "dispatcherServerPort");
            return (Criteria) this;
        }

        public Criteria andH5RoomServerPortIsNull() {
            addCriterion("h5RoomServerPort is null");
            return (Criteria) this;
        }

        public Criteria andH5RoomServerPortIsNotNull() {
            addCriterion("h5RoomServerPort is not null");
            return (Criteria) this;
        }

        public Criteria andH5RoomServerPortEqualTo(int value) {
            addCriterion("h5RoomServerPort =", value, "h5RoomServerPort");
            return (Criteria) this;
        }

        public Criteria andH5RoomServerPortNotEqualTo(int value) {
            addCriterion("h5RoomServerPort <>", value, "h5RoomServerPort");
            return (Criteria) this;
        }

        public Criteria andH5RoomServerPortGreaterThan(int value) {
            addCriterion("h5RoomServerPort >", value, "h5RoomServerPort");
            return (Criteria) this;
        }

        public Criteria andH5RoomServerPortGreaterThanOrEqualTo(int value) {
            addCriterion("h5RoomServerPort >=", value, "h5RoomServerPort");
            return (Criteria) this;
        }

        public Criteria andH5RoomServerPortLessThan(int value) {
            addCriterion("h5RoomServerPort <", value, "h5RoomServerPort");
            return (Criteria) this;
        }

        public Criteria andH5RoomServerPortLessThanOrEqualTo(int value) {
            addCriterion("h5RoomServerPort <=", value, "h5RoomServerPort");
            return (Criteria) this;
        }

        public Criteria andH5RoomServerPortIn(List<Integer> values) {
            addCriterion("h5RoomServerPort in", values, "h5RoomServerPort");
            return (Criteria) this;
        }

        public Criteria andH5RoomServerPortNotIn(List<Integer> values) {
            addCriterion("h5RoomServerPort not in", values, "h5RoomServerPort");
            return (Criteria) this;
        }

        public Criteria andH5RoomServerPortBetween(int value1, int value2) {
            addCriterion("h5RoomServerPort between", value1, value2, "h5RoomServerPort");
            return (Criteria) this;
        }

        public Criteria andH5RoomServerPortNotBetween(int value1, int value2) {
            addCriterion("h5RoomServerPort not between", value1, value2, "h5RoomServerPort");
            return (Criteria) this;
        }

        public Criteria andH5HallServerPortIsNull() {
            addCriterion("h5HallServerPort is null");
            return (Criteria) this;
        }

        public Criteria andH5HallServerPortIsNotNull() {
            addCriterion("h5HallServerPort is not null");
            return (Criteria) this;
        }

        public Criteria andH5HallServerPortEqualTo(int value) {
            addCriterion("h5HallServerPort =", value, "h5HallServerPort");
            return (Criteria) this;
        }

        public Criteria andH5HallServerPortNotEqualTo(int value) {
            addCriterion("h5HallServerPort <>", value, "h5HallServerPort");
            return (Criteria) this;
        }

        public Criteria andH5HallServerPortGreaterThan(int value) {
            addCriterion("h5HallServerPort >", value, "h5HallServerPort");
            return (Criteria) this;
        }

        public Criteria andH5HallServerPortGreaterThanOrEqualTo(int value) {
            addCriterion("h5HallServerPort >=", value, "h5HallServerPort");
            return (Criteria) this;
        }

        public Criteria andH5HallServerPortLessThan(int value) {
            addCriterion("h5HallServerPort <", value, "h5HallServerPort");
            return (Criteria) this;
        }

        public Criteria andH5HallServerPortLessThanOrEqualTo(int value) {
            addCriterion("h5HallServerPort <=", value, "h5HallServerPort");
            return (Criteria) this;
        }

        public Criteria andH5HallServerPortIn(List<Integer> values) {
            addCriterion("h5HallServerPort in", values, "h5HallServerPort");
            return (Criteria) this;
        }

        public Criteria andH5HallServerPortNotIn(List<Integer> values) {
            addCriterion("h5HallServerPort not in", values, "h5HallServerPort");
            return (Criteria) this;
        }

        public Criteria andH5HallServerPortBetween(int value1, int value2) {
            addCriterion("h5HallServerPort between", value1, value2, "h5HallServerPort");
            return (Criteria) this;
        }

        public Criteria andH5HallServerPortNotBetween(int value1, int value2) {
            addCriterion("h5HallServerPort not between", value1, value2, "h5HallServerPort");
            return (Criteria) this;
        }

        public Criteria andRankingServerPortIsNull() {
            addCriterion("rankingServerPort is null");
            return (Criteria) this;
        }

        public Criteria andRankingServerPortIsNotNull() {
            addCriterion("rankingServerPort is not null");
            return (Criteria) this;
        }

        public Criteria andRankingServerPortEqualTo(int value) {
            addCriterion("rankingServerPort =", value, "rankingServerPort");
            return (Criteria) this;
        }

        public Criteria andRankingServerPortNotEqualTo(int value) {
            addCriterion("rankingServerPort <>", value, "rankingServerPort");
            return (Criteria) this;
        }

        public Criteria andRankingServerPortGreaterThan(int value) {
            addCriterion("rankingServerPort >", value, "rankingServerPort");
            return (Criteria) this;
        }

        public Criteria andRankingServerPortGreaterThanOrEqualTo(int value) {
            addCriterion("rankingServerPort >=", value, "rankingServerPort");
            return (Criteria) this;
        }

        public Criteria andRankingServerPortLessThan(int value) {
            addCriterion("rankingServerPort <", value, "rankingServerPort");
            return (Criteria) this;
        }

        public Criteria andRankingServerPortLessThanOrEqualTo(int value) {
            addCriterion("rankingServerPort <=", value, "rankingServerPort");
            return (Criteria) this;
        }

        public Criteria andRankingServerPortIn(List<Integer> values) {
            addCriterion("rankingServerPort in", values, "rankingServerPort");
            return (Criteria) this;
        }

        public Criteria andRankingServerPortNotIn(List<Integer> values) {
            addCriterion("rankingServerPort not in", values, "rankingServerPort");
            return (Criteria) this;
        }

        public Criteria andRankingServerPortBetween(int value1, int value2) {
            addCriterion("rankingServerPort between", value1, value2, "rankingServerPort");
            return (Criteria) this;
        }

        public Criteria andRankingServerPortNotBetween(int value1, int value2) {
            addCriterion("rankingServerPort not between", value1, value2, "rankingServerPort");
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