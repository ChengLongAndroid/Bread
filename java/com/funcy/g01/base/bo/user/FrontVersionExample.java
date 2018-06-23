package com.funcy.g01.base.bo.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FrontVersionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FrontVersionExample() {
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

        public Criteria andVersionNum1IsNull() {
            addCriterion("versionNum1 is null");
            return (Criteria) this;
        }

        public Criteria andVersionNum1IsNotNull() {
            addCriterion("versionNum1 is not null");
            return (Criteria) this;
        }

        public Criteria andVersionNum1EqualTo(Integer value) {
            addCriterion("versionNum1 =", value, "versionNum1");
            return (Criteria) this;
        }

        public Criteria andVersionNum1NotEqualTo(Integer value) {
            addCriterion("versionNum1 <>", value, "versionNum1");
            return (Criteria) this;
        }

        public Criteria andVersionNum1GreaterThan(Integer value) {
            addCriterion("versionNum1 >", value, "versionNum1");
            return (Criteria) this;
        }

        public Criteria andVersionNum1GreaterThanOrEqualTo(Integer value) {
            addCriterion("versionNum1 >=", value, "versionNum1");
            return (Criteria) this;
        }

        public Criteria andVersionNum1LessThan(Integer value) {
            addCriterion("versionNum1 <", value, "versionNum1");
            return (Criteria) this;
        }

        public Criteria andVersionNum1LessThanOrEqualTo(Integer value) {
            addCriterion("versionNum1 <=", value, "versionNum1");
            return (Criteria) this;
        }

        public Criteria andVersionNum1In(List<Integer> values) {
            addCriterion("versionNum1 in", values, "versionNum1");
            return (Criteria) this;
        }

        public Criteria andVersionNum1NotIn(List<Integer> values) {
            addCriterion("versionNum1 not in", values, "versionNum1");
            return (Criteria) this;
        }

        public Criteria andVersionNum1Between(Integer value1, Integer value2) {
            addCriterion("versionNum1 between", value1, value2, "versionNum1");
            return (Criteria) this;
        }

        public Criteria andVersionNum1NotBetween(Integer value1, Integer value2) {
            addCriterion("versionNum1 not between", value1, value2, "versionNum1");
            return (Criteria) this;
        }

        public Criteria andVersionNum2IsNull() {
            addCriterion("versionNum2 is null");
            return (Criteria) this;
        }

        public Criteria andVersionNum2IsNotNull() {
            addCriterion("versionNum2 is not null");
            return (Criteria) this;
        }

        public Criteria andVersionNum2EqualTo(Integer value) {
            addCriterion("versionNum2 =", value, "versionNum2");
            return (Criteria) this;
        }

        public Criteria andVersionNum2NotEqualTo(Integer value) {
            addCriterion("versionNum2 <>", value, "versionNum2");
            return (Criteria) this;
        }

        public Criteria andVersionNum2GreaterThan(Integer value) {
            addCriterion("versionNum2 >", value, "versionNum2");
            return (Criteria) this;
        }

        public Criteria andVersionNum2GreaterThanOrEqualTo(Integer value) {
            addCriterion("versionNum2 >=", value, "versionNum2");
            return (Criteria) this;
        }

        public Criteria andVersionNum2LessThan(Integer value) {
            addCriterion("versionNum2 <", value, "versionNum2");
            return (Criteria) this;
        }

        public Criteria andVersionNum2LessThanOrEqualTo(Integer value) {
            addCriterion("versionNum2 <=", value, "versionNum2");
            return (Criteria) this;
        }

        public Criteria andVersionNum2In(List<Integer> values) {
            addCriterion("versionNum2 in", values, "versionNum2");
            return (Criteria) this;
        }

        public Criteria andVersionNum2NotIn(List<Integer> values) {
            addCriterion("versionNum2 not in", values, "versionNum2");
            return (Criteria) this;
        }

        public Criteria andVersionNum2Between(Integer value1, Integer value2) {
            addCriterion("versionNum2 between", value1, value2, "versionNum2");
            return (Criteria) this;
        }

        public Criteria andVersionNum2NotBetween(Integer value1, Integer value2) {
            addCriterion("versionNum2 not between", value1, value2, "versionNum2");
            return (Criteria) this;
        }

        public Criteria andSource1VersionNum1IsNull() {
            addCriterion("source1VersionNum1 is null");
            return (Criteria) this;
        }

        public Criteria andSource1VersionNum1IsNotNull() {
            addCriterion("source1VersionNum1 is not null");
            return (Criteria) this;
        }

        public Criteria andSource1VersionNum1EqualTo(Integer value) {
            addCriterion("source1VersionNum1 =", value, "source1VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource1VersionNum1NotEqualTo(Integer value) {
            addCriterion("source1VersionNum1 <>", value, "source1VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource1VersionNum1GreaterThan(Integer value) {
            addCriterion("source1VersionNum1 >", value, "source1VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource1VersionNum1GreaterThanOrEqualTo(Integer value) {
            addCriterion("source1VersionNum1 >=", value, "source1VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource1VersionNum1LessThan(Integer value) {
            addCriterion("source1VersionNum1 <", value, "source1VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource1VersionNum1LessThanOrEqualTo(Integer value) {
            addCriterion("source1VersionNum1 <=", value, "source1VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource1VersionNum1In(List<Integer> values) {
            addCriterion("source1VersionNum1 in", values, "source1VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource1VersionNum1NotIn(List<Integer> values) {
            addCriterion("source1VersionNum1 not in", values, "source1VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource1VersionNum1Between(Integer value1, Integer value2) {
            addCriterion("source1VersionNum1 between", value1, value2, "source1VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource1VersionNum1NotBetween(Integer value1, Integer value2) {
            addCriterion("source1VersionNum1 not between", value1, value2, "source1VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource1VersionNum2IsNull() {
            addCriterion("source1VersionNum2 is null");
            return (Criteria) this;
        }

        public Criteria andSource1VersionNum2IsNotNull() {
            addCriterion("source1VersionNum2 is not null");
            return (Criteria) this;
        }

        public Criteria andSource1VersionNum2EqualTo(Integer value) {
            addCriterion("source1VersionNum2 =", value, "source1VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource1VersionNum2NotEqualTo(Integer value) {
            addCriterion("source1VersionNum2 <>", value, "source1VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource1VersionNum2GreaterThan(Integer value) {
            addCriterion("source1VersionNum2 >", value, "source1VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource1VersionNum2GreaterThanOrEqualTo(Integer value) {
            addCriterion("source1VersionNum2 >=", value, "source1VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource1VersionNum2LessThan(Integer value) {
            addCriterion("source1VersionNum2 <", value, "source1VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource1VersionNum2LessThanOrEqualTo(Integer value) {
            addCriterion("source1VersionNum2 <=", value, "source1VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource1VersionNum2In(List<Integer> values) {
            addCriterion("source1VersionNum2 in", values, "source1VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource1VersionNum2NotIn(List<Integer> values) {
            addCriterion("source1VersionNum2 not in", values, "source1VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource1VersionNum2Between(Integer value1, Integer value2) {
            addCriterion("source1VersionNum2 between", value1, value2, "source1VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource1VersionNum2NotBetween(Integer value1, Integer value2) {
            addCriterion("source1VersionNum2 not between", value1, value2, "source1VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource1PackNameIsNull() {
            addCriterion("source1PackName is null");
            return (Criteria) this;
        }

        public Criteria andSource1PackNameIsNotNull() {
            addCriterion("source1PackName is not null");
            return (Criteria) this;
        }

        public Criteria andSource1PackNameEqualTo(String value) {
            addCriterion("source1PackName =", value, "source1PackName");
            return (Criteria) this;
        }

        public Criteria andSource1PackNameNotEqualTo(String value) {
            addCriterion("source1PackName <>", value, "source1PackName");
            return (Criteria) this;
        }

        public Criteria andSource1PackNameGreaterThan(String value) {
            addCriterion("source1PackName >", value, "source1PackName");
            return (Criteria) this;
        }

        public Criteria andSource1PackNameGreaterThanOrEqualTo(String value) {
            addCriterion("source1PackName >=", value, "source1PackName");
            return (Criteria) this;
        }

        public Criteria andSource1PackNameLessThan(String value) {
            addCriterion("source1PackName <", value, "source1PackName");
            return (Criteria) this;
        }

        public Criteria andSource1PackNameLessThanOrEqualTo(String value) {
            addCriterion("source1PackName <=", value, "source1PackName");
            return (Criteria) this;
        }

        public Criteria andSource1PackNameLike(String value) {
            addCriterion("source1PackName like", value, "source1PackName");
            return (Criteria) this;
        }

        public Criteria andSource1PackNameNotLike(String value) {
            addCriterion("source1PackName not like", value, "source1PackName");
            return (Criteria) this;
        }

        public Criteria andSource1PackNameIn(List<String> values) {
            addCriterion("source1PackName in", values, "source1PackName");
            return (Criteria) this;
        }

        public Criteria andSource1PackNameNotIn(List<String> values) {
            addCriterion("source1PackName not in", values, "source1PackName");
            return (Criteria) this;
        }

        public Criteria andSource1PackNameBetween(String value1, String value2) {
            addCriterion("source1PackName between", value1, value2, "source1PackName");
            return (Criteria) this;
        }

        public Criteria andSource1PackNameNotBetween(String value1, String value2) {
            addCriterion("source1PackName not between", value1, value2, "source1PackName");
            return (Criteria) this;
        }

        public Criteria andSource1PackSizeIsNull() {
            addCriterion("source1PackSize is null");
            return (Criteria) this;
        }

        public Criteria andSource1PackSizeIsNotNull() {
            addCriterion("source1PackSize is not null");
            return (Criteria) this;
        }

        public Criteria andSource1PackSizeEqualTo(Integer value) {
            addCriterion("source1PackSize =", value, "source1PackSize");
            return (Criteria) this;
        }

        public Criteria andSource1PackSizeNotEqualTo(Integer value) {
            addCriterion("source1PackSize <>", value, "source1PackSize");
            return (Criteria) this;
        }

        public Criteria andSource1PackSizeGreaterThan(Integer value) {
            addCriterion("source1PackSize >", value, "source1PackSize");
            return (Criteria) this;
        }

        public Criteria andSource1PackSizeGreaterThanOrEqualTo(Integer value) {
            addCriterion("source1PackSize >=", value, "source1PackSize");
            return (Criteria) this;
        }

        public Criteria andSource1PackSizeLessThan(Integer value) {
            addCriterion("source1PackSize <", value, "source1PackSize");
            return (Criteria) this;
        }

        public Criteria andSource1PackSizeLessThanOrEqualTo(Integer value) {
            addCriterion("source1PackSize <=", value, "source1PackSize");
            return (Criteria) this;
        }

        public Criteria andSource1PackSizeIn(List<Integer> values) {
            addCriterion("source1PackSize in", values, "source1PackSize");
            return (Criteria) this;
        }

        public Criteria andSource1PackSizeNotIn(List<Integer> values) {
            addCriterion("source1PackSize not in", values, "source1PackSize");
            return (Criteria) this;
        }

        public Criteria andSource1PackSizeBetween(Integer value1, Integer value2) {
            addCriterion("source1PackSize between", value1, value2, "source1PackSize");
            return (Criteria) this;
        }

        public Criteria andSource1PackSizeNotBetween(Integer value1, Integer value2) {
            addCriterion("source1PackSize not between", value1, value2, "source1PackSize");
            return (Criteria) this;
        }

        public Criteria andSource2VersionNum1IsNull() {
            addCriterion("source2VersionNum1 is null");
            return (Criteria) this;
        }

        public Criteria andSource2VersionNum1IsNotNull() {
            addCriterion("source2VersionNum1 is not null");
            return (Criteria) this;
        }

        public Criteria andSource2VersionNum1EqualTo(Integer value) {
            addCriterion("source2VersionNum1 =", value, "source2VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource2VersionNum1NotEqualTo(Integer value) {
            addCriterion("source2VersionNum1 <>", value, "source2VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource2VersionNum1GreaterThan(Integer value) {
            addCriterion("source2VersionNum1 >", value, "source2VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource2VersionNum1GreaterThanOrEqualTo(Integer value) {
            addCriterion("source2VersionNum1 >=", value, "source2VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource2VersionNum1LessThan(Integer value) {
            addCriterion("source2VersionNum1 <", value, "source2VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource2VersionNum1LessThanOrEqualTo(Integer value) {
            addCriterion("source2VersionNum1 <=", value, "source2VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource2VersionNum1In(List<Integer> values) {
            addCriterion("source2VersionNum1 in", values, "source2VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource2VersionNum1NotIn(List<Integer> values) {
            addCriterion("source2VersionNum1 not in", values, "source2VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource2VersionNum1Between(Integer value1, Integer value2) {
            addCriterion("source2VersionNum1 between", value1, value2, "source2VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource2VersionNum1NotBetween(Integer value1, Integer value2) {
            addCriterion("source2VersionNum1 not between", value1, value2, "source2VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource2VersionNum2IsNull() {
            addCriterion("source2VersionNum2 is null");
            return (Criteria) this;
        }

        public Criteria andSource2VersionNum2IsNotNull() {
            addCriterion("source2VersionNum2 is not null");
            return (Criteria) this;
        }

        public Criteria andSource2VersionNum2EqualTo(Integer value) {
            addCriterion("source2VersionNum2 =", value, "source2VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource2VersionNum2NotEqualTo(Integer value) {
            addCriterion("source2VersionNum2 <>", value, "source2VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource2VersionNum2GreaterThan(Integer value) {
            addCriterion("source2VersionNum2 >", value, "source2VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource2VersionNum2GreaterThanOrEqualTo(Integer value) {
            addCriterion("source2VersionNum2 >=", value, "source2VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource2VersionNum2LessThan(Integer value) {
            addCriterion("source2VersionNum2 <", value, "source2VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource2VersionNum2LessThanOrEqualTo(Integer value) {
            addCriterion("source2VersionNum2 <=", value, "source2VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource2VersionNum2In(List<Integer> values) {
            addCriterion("source2VersionNum2 in", values, "source2VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource2VersionNum2NotIn(List<Integer> values) {
            addCriterion("source2VersionNum2 not in", values, "source2VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource2VersionNum2Between(Integer value1, Integer value2) {
            addCriterion("source2VersionNum2 between", value1, value2, "source2VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource2VersionNum2NotBetween(Integer value1, Integer value2) {
            addCriterion("source2VersionNum2 not between", value1, value2, "source2VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource2PackNameIsNull() {
            addCriterion("source2PackName is null");
            return (Criteria) this;
        }

        public Criteria andSource2PackNameIsNotNull() {
            addCriterion("source2PackName is not null");
            return (Criteria) this;
        }

        public Criteria andSource2PackNameEqualTo(String value) {
            addCriterion("source2PackName =", value, "source2PackName");
            return (Criteria) this;
        }

        public Criteria andSource2PackNameNotEqualTo(String value) {
            addCriterion("source2PackName <>", value, "source2PackName");
            return (Criteria) this;
        }

        public Criteria andSource2PackNameGreaterThan(String value) {
            addCriterion("source2PackName >", value, "source2PackName");
            return (Criteria) this;
        }

        public Criteria andSource2PackNameGreaterThanOrEqualTo(String value) {
            addCriterion("source2PackName >=", value, "source2PackName");
            return (Criteria) this;
        }

        public Criteria andSource2PackNameLessThan(String value) {
            addCriterion("source2PackName <", value, "source2PackName");
            return (Criteria) this;
        }

        public Criteria andSource2PackNameLessThanOrEqualTo(String value) {
            addCriterion("source2PackName <=", value, "source2PackName");
            return (Criteria) this;
        }

        public Criteria andSource2PackNameLike(String value) {
            addCriterion("source2PackName like", value, "source2PackName");
            return (Criteria) this;
        }

        public Criteria andSource2PackNameNotLike(String value) {
            addCriterion("source2PackName not like", value, "source2PackName");
            return (Criteria) this;
        }

        public Criteria andSource2PackNameIn(List<String> values) {
            addCriterion("source2PackName in", values, "source2PackName");
            return (Criteria) this;
        }

        public Criteria andSource2PackNameNotIn(List<String> values) {
            addCriterion("source2PackName not in", values, "source2PackName");
            return (Criteria) this;
        }

        public Criteria andSource2PackNameBetween(String value1, String value2) {
            addCriterion("source2PackName between", value1, value2, "source2PackName");
            return (Criteria) this;
        }

        public Criteria andSource2PackNameNotBetween(String value1, String value2) {
            addCriterion("source2PackName not between", value1, value2, "source2PackName");
            return (Criteria) this;
        }

        public Criteria andSource2PackSizeIsNull() {
            addCriterion("source2PackSize is null");
            return (Criteria) this;
        }

        public Criteria andSource2PackSizeIsNotNull() {
            addCriterion("source2PackSize is not null");
            return (Criteria) this;
        }

        public Criteria andSource2PackSizeEqualTo(Integer value) {
            addCriterion("source2PackSize =", value, "source2PackSize");
            return (Criteria) this;
        }

        public Criteria andSource2PackSizeNotEqualTo(Integer value) {
            addCriterion("source2PackSize <>", value, "source2PackSize");
            return (Criteria) this;
        }

        public Criteria andSource2PackSizeGreaterThan(Integer value) {
            addCriterion("source2PackSize >", value, "source2PackSize");
            return (Criteria) this;
        }

        public Criteria andSource2PackSizeGreaterThanOrEqualTo(Integer value) {
            addCriterion("source2PackSize >=", value, "source2PackSize");
            return (Criteria) this;
        }

        public Criteria andSource2PackSizeLessThan(Integer value) {
            addCriterion("source2PackSize <", value, "source2PackSize");
            return (Criteria) this;
        }

        public Criteria andSource2PackSizeLessThanOrEqualTo(Integer value) {
            addCriterion("source2PackSize <=", value, "source2PackSize");
            return (Criteria) this;
        }

        public Criteria andSource2PackSizeIn(List<Integer> values) {
            addCriterion("source2PackSize in", values, "source2PackSize");
            return (Criteria) this;
        }

        public Criteria andSource2PackSizeNotIn(List<Integer> values) {
            addCriterion("source2PackSize not in", values, "source2PackSize");
            return (Criteria) this;
        }

        public Criteria andSource2PackSizeBetween(Integer value1, Integer value2) {
            addCriterion("source2PackSize between", value1, value2, "source2PackSize");
            return (Criteria) this;
        }

        public Criteria andSource2PackSizeNotBetween(Integer value1, Integer value2) {
            addCriterion("source2PackSize not between", value1, value2, "source2PackSize");
            return (Criteria) this;
        }

        public Criteria andSource3VersionNum1IsNull() {
            addCriterion("source3VersionNum1 is null");
            return (Criteria) this;
        }

        public Criteria andSource3VersionNum1IsNotNull() {
            addCriterion("source3VersionNum1 is not null");
            return (Criteria) this;
        }

        public Criteria andSource3VersionNum1EqualTo(Integer value) {
            addCriterion("source3VersionNum1 =", value, "source3VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource3VersionNum1NotEqualTo(Integer value) {
            addCriterion("source3VersionNum1 <>", value, "source3VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource3VersionNum1GreaterThan(Integer value) {
            addCriterion("source3VersionNum1 >", value, "source3VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource3VersionNum1GreaterThanOrEqualTo(Integer value) {
            addCriterion("source3VersionNum1 >=", value, "source3VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource3VersionNum1LessThan(Integer value) {
            addCriterion("source3VersionNum1 <", value, "source3VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource3VersionNum1LessThanOrEqualTo(Integer value) {
            addCriterion("source3VersionNum1 <=", value, "source3VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource3VersionNum1In(List<Integer> values) {
            addCriterion("source3VersionNum1 in", values, "source3VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource3VersionNum1NotIn(List<Integer> values) {
            addCriterion("source3VersionNum1 not in", values, "source3VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource3VersionNum1Between(Integer value1, Integer value2) {
            addCriterion("source3VersionNum1 between", value1, value2, "source3VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource3VersionNum1NotBetween(Integer value1, Integer value2) {
            addCriterion("source3VersionNum1 not between", value1, value2, "source3VersionNum1");
            return (Criteria) this;
        }

        public Criteria andSource3VersionNum2IsNull() {
            addCriterion("source3VersionNum2 is null");
            return (Criteria) this;
        }

        public Criteria andSource3VersionNum2IsNotNull() {
            addCriterion("source3VersionNum2 is not null");
            return (Criteria) this;
        }

        public Criteria andSource3VersionNum2EqualTo(Integer value) {
            addCriterion("source3VersionNum2 =", value, "source3VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource3VersionNum2NotEqualTo(Integer value) {
            addCriterion("source3VersionNum2 <>", value, "source3VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource3VersionNum2GreaterThan(Integer value) {
            addCriterion("source3VersionNum2 >", value, "source3VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource3VersionNum2GreaterThanOrEqualTo(Integer value) {
            addCriterion("source3VersionNum2 >=", value, "source3VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource3VersionNum2LessThan(Integer value) {
            addCriterion("source3VersionNum2 <", value, "source3VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource3VersionNum2LessThanOrEqualTo(Integer value) {
            addCriterion("source3VersionNum2 <=", value, "source3VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource3VersionNum2In(List<Integer> values) {
            addCriterion("source3VersionNum2 in", values, "source3VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource3VersionNum2NotIn(List<Integer> values) {
            addCriterion("source3VersionNum2 not in", values, "source3VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource3VersionNum2Between(Integer value1, Integer value2) {
            addCriterion("source3VersionNum2 between", value1, value2, "source3VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource3VersionNum2NotBetween(Integer value1, Integer value2) {
            addCriterion("source3VersionNum2 not between", value1, value2, "source3VersionNum2");
            return (Criteria) this;
        }

        public Criteria andSource3PackNameIsNull() {
            addCriterion("source3PackName is null");
            return (Criteria) this;
        }

        public Criteria andSource3PackNameIsNotNull() {
            addCriterion("source3PackName is not null");
            return (Criteria) this;
        }

        public Criteria andSource3PackNameEqualTo(String value) {
            addCriterion("source3PackName =", value, "source3PackName");
            return (Criteria) this;
        }

        public Criteria andSource3PackNameNotEqualTo(String value) {
            addCriterion("source3PackName <>", value, "source3PackName");
            return (Criteria) this;
        }

        public Criteria andSource3PackNameGreaterThan(String value) {
            addCriterion("source3PackName >", value, "source3PackName");
            return (Criteria) this;
        }

        public Criteria andSource3PackNameGreaterThanOrEqualTo(String value) {
            addCriterion("source3PackName >=", value, "source3PackName");
            return (Criteria) this;
        }

        public Criteria andSource3PackNameLessThan(String value) {
            addCriterion("source3PackName <", value, "source3PackName");
            return (Criteria) this;
        }

        public Criteria andSource3PackNameLessThanOrEqualTo(String value) {
            addCriterion("source3PackName <=", value, "source3PackName");
            return (Criteria) this;
        }

        public Criteria andSource3PackNameLike(String value) {
            addCriterion("source3PackName like", value, "source3PackName");
            return (Criteria) this;
        }

        public Criteria andSource3PackNameNotLike(String value) {
            addCriterion("source3PackName not like", value, "source3PackName");
            return (Criteria) this;
        }

        public Criteria andSource3PackNameIn(List<String> values) {
            addCriterion("source3PackName in", values, "source3PackName");
            return (Criteria) this;
        }

        public Criteria andSource3PackNameNotIn(List<String> values) {
            addCriterion("source3PackName not in", values, "source3PackName");
            return (Criteria) this;
        }

        public Criteria andSource3PackNameBetween(String value1, String value2) {
            addCriterion("source3PackName between", value1, value2, "source3PackName");
            return (Criteria) this;
        }

        public Criteria andSource3PackNameNotBetween(String value1, String value2) {
            addCriterion("source3PackName not between", value1, value2, "source3PackName");
            return (Criteria) this;
        }

        public Criteria andSource3PackSizeIsNull() {
            addCriterion("source3PackSize is null");
            return (Criteria) this;
        }

        public Criteria andSource3PackSizeIsNotNull() {
            addCriterion("source3PackSize is not null");
            return (Criteria) this;
        }

        public Criteria andSource3PackSizeEqualTo(Integer value) {
            addCriterion("source3PackSize =", value, "source3PackSize");
            return (Criteria) this;
        }

        public Criteria andSource3PackSizeNotEqualTo(Integer value) {
            addCriterion("source3PackSize <>", value, "source3PackSize");
            return (Criteria) this;
        }

        public Criteria andSource3PackSizeGreaterThan(Integer value) {
            addCriterion("source3PackSize >", value, "source3PackSize");
            return (Criteria) this;
        }

        public Criteria andSource3PackSizeGreaterThanOrEqualTo(Integer value) {
            addCriterion("source3PackSize >=", value, "source3PackSize");
            return (Criteria) this;
        }

        public Criteria andSource3PackSizeLessThan(Integer value) {
            addCriterion("source3PackSize <", value, "source3PackSize");
            return (Criteria) this;
        }

        public Criteria andSource3PackSizeLessThanOrEqualTo(Integer value) {
            addCriterion("source3PackSize <=", value, "source3PackSize");
            return (Criteria) this;
        }

        public Criteria andSource3PackSizeIn(List<Integer> values) {
            addCriterion("source3PackSize in", values, "source3PackSize");
            return (Criteria) this;
        }

        public Criteria andSource3PackSizeNotIn(List<Integer> values) {
            addCriterion("source3PackSize not in", values, "source3PackSize");
            return (Criteria) this;
        }

        public Criteria andSource3PackSizeBetween(Integer value1, Integer value2) {
            addCriterion("source3PackSize between", value1, value2, "source3PackSize");
            return (Criteria) this;
        }

        public Criteria andSource3PackSizeNotBetween(Integer value1, Integer value2) {
            addCriterion("source3PackSize not between", value1, value2, "source3PackSize");
            return (Criteria) this;
        }

        public Criteria andTimeIsNull() {
            addCriterion("time is null");
            return (Criteria) this;
        }

        public Criteria andTimeIsNotNull() {
            addCriterion("time is not null");
            return (Criteria) this;
        }

        public Criteria andTimeEqualTo(Date value) {
            addCriterion("time =", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotEqualTo(Date value) {
            addCriterion("time <>", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThan(Date value) {
            addCriterion("time >", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("time >=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThan(Date value) {
            addCriterion("time <", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThanOrEqualTo(Date value) {
            addCriterion("time <=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeIn(List<Date> values) {
            addCriterion("time in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotIn(List<Date> values) {
            addCriterion("time not in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeBetween(Date value1, Date value2) {
            addCriterion("time between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotBetween(Date value1, Date value2) {
            addCriterion("time not between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andSubmitterIsNull() {
            addCriterion("submitter is null");
            return (Criteria) this;
        }

        public Criteria andSubmitterIsNotNull() {
            addCriterion("submitter is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitterEqualTo(String value) {
            addCriterion("submitter =", value, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterNotEqualTo(String value) {
            addCriterion("submitter <>", value, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterGreaterThan(String value) {
            addCriterion("submitter >", value, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterGreaterThanOrEqualTo(String value) {
            addCriterion("submitter >=", value, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterLessThan(String value) {
            addCriterion("submitter <", value, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterLessThanOrEqualTo(String value) {
            addCriterion("submitter <=", value, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterLike(String value) {
            addCriterion("submitter like", value, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterNotLike(String value) {
            addCriterion("submitter not like", value, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterIn(List<String> values) {
            addCriterion("submitter in", values, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterNotIn(List<String> values) {
            addCriterion("submitter not in", values, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterBetween(String value1, String value2) {
            addCriterion("submitter between", value1, value2, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterNotBetween(String value1, String value2) {
            addCriterion("submitter not between", value1, value2, "submitter");
            return (Criteria) this;
        }

        public Criteria andDescrIsNull() {
            addCriterion("descr is null");
            return (Criteria) this;
        }

        public Criteria andDescrIsNotNull() {
            addCriterion("descr is not null");
            return (Criteria) this;
        }

        public Criteria andDescrEqualTo(String value) {
            addCriterion("descr =", value, "descr");
            return (Criteria) this;
        }

        public Criteria andDescrNotEqualTo(String value) {
            addCriterion("descr <>", value, "descr");
            return (Criteria) this;
        }

        public Criteria andDescrGreaterThan(String value) {
            addCriterion("descr >", value, "descr");
            return (Criteria) this;
        }

        public Criteria andDescrGreaterThanOrEqualTo(String value) {
            addCriterion("descr >=", value, "descr");
            return (Criteria) this;
        }

        public Criteria andDescrLessThan(String value) {
            addCriterion("descr <", value, "descr");
            return (Criteria) this;
        }

        public Criteria andDescrLessThanOrEqualTo(String value) {
            addCriterion("descr <=", value, "descr");
            return (Criteria) this;
        }

        public Criteria andDescrLike(String value) {
            addCriterion("descr like", value, "descr");
            return (Criteria) this;
        }

        public Criteria andDescrNotLike(String value) {
            addCriterion("descr not like", value, "descr");
            return (Criteria) this;
        }

        public Criteria andDescrIn(List<String> values) {
            addCriterion("descr in", values, "descr");
            return (Criteria) this;
        }

        public Criteria andDescrNotIn(List<String> values) {
            addCriterion("descr not in", values, "descr");
            return (Criteria) this;
        }

        public Criteria andDescrBetween(String value1, String value2) {
            addCriterion("descr between", value1, value2, "descr");
            return (Criteria) this;
        }

        public Criteria andDescrNotBetween(String value1, String value2) {
            addCriterion("descr not between", value1, value2, "descr");
            return (Criteria) this;
        }

        public Criteria andForceUpdateIsNull() {
            addCriterion("forceUpdate is null");
            return (Criteria) this;
        }

        public Criteria andForceUpdateIsNotNull() {
            addCriterion("forceUpdate is not null");
            return (Criteria) this;
        }

        public Criteria andForceUpdateEqualTo(Boolean value) {
            addCriterion("forceUpdate =", value, "forceUpdate");
            return (Criteria) this;
        }

        public Criteria andForceUpdateNotEqualTo(Boolean value) {
            addCriterion("forceUpdate <>", value, "forceUpdate");
            return (Criteria) this;
        }

        public Criteria andForceUpdateGreaterThan(Boolean value) {
            addCriterion("forceUpdate >", value, "forceUpdate");
            return (Criteria) this;
        }

        public Criteria andForceUpdateGreaterThanOrEqualTo(Boolean value) {
            addCriterion("forceUpdate >=", value, "forceUpdate");
            return (Criteria) this;
        }

        public Criteria andForceUpdateLessThan(Boolean value) {
            addCriterion("forceUpdate <", value, "forceUpdate");
            return (Criteria) this;
        }

        public Criteria andForceUpdateLessThanOrEqualTo(Boolean value) {
            addCriterion("forceUpdate <=", value, "forceUpdate");
            return (Criteria) this;
        }

        public Criteria andForceUpdateIn(List<Boolean> values) {
            addCriterion("forceUpdate in", values, "forceUpdate");
            return (Criteria) this;
        }

        public Criteria andForceUpdateNotIn(List<Boolean> values) {
            addCriterion("forceUpdate not in", values, "forceUpdate");
            return (Criteria) this;
        }

        public Criteria andForceUpdateBetween(Boolean value1, Boolean value2) {
            addCriterion("forceUpdate between", value1, value2, "forceUpdate");
            return (Criteria) this;
        }

        public Criteria andForceUpdateNotBetween(Boolean value1, Boolean value2) {
            addCriterion("forceUpdate not between", value1, value2, "forceUpdate");
            return (Criteria) this;
        }

        public Criteria andForceUpdateUrlIsNull() {
            addCriterion("forceUpdateUrl is null");
            return (Criteria) this;
        }

        public Criteria andForceUpdateUrlIsNotNull() {
            addCriterion("forceUpdateUrl is not null");
            return (Criteria) this;
        }

        public Criteria andForceUpdateUrlEqualTo(String value) {
            addCriterion("forceUpdateUrl =", value, "forceUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andForceUpdateUrlNotEqualTo(String value) {
            addCriterion("forceUpdateUrl <>", value, "forceUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andForceUpdateUrlGreaterThan(String value) {
            addCriterion("forceUpdateUrl >", value, "forceUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andForceUpdateUrlGreaterThanOrEqualTo(String value) {
            addCriterion("forceUpdateUrl >=", value, "forceUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andForceUpdateUrlLessThan(String value) {
            addCriterion("forceUpdateUrl <", value, "forceUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andForceUpdateUrlLessThanOrEqualTo(String value) {
            addCriterion("forceUpdateUrl <=", value, "forceUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andForceUpdateUrlLike(String value) {
            addCriterion("forceUpdateUrl like", value, "forceUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andForceUpdateUrlNotLike(String value) {
            addCriterion("forceUpdateUrl not like", value, "forceUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andForceUpdateUrlIn(List<String> values) {
            addCriterion("forceUpdateUrl in", values, "forceUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andForceUpdateUrlNotIn(List<String> values) {
            addCriterion("forceUpdateUrl not in", values, "forceUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andForceUpdateUrlBetween(String value1, String value2) {
            addCriterion("forceUpdateUrl between", value1, value2, "forceUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andForceUpdateUrlNotBetween(String value1, String value2) {
            addCriterion("forceUpdateUrl not between", value1, value2, "forceUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andIsInTestIsNull() {
            addCriterion("isInTest is null");
            return (Criteria) this;
        }

        public Criteria andIsInTestIsNotNull() {
            addCriterion("isInTest is not null");
            return (Criteria) this;
        }

        public Criteria andIsInTestEqualTo(Boolean value) {
            addCriterion("isInTest =", value, "isInTest");
            return (Criteria) this;
        }

        public Criteria andIsInTestNotEqualTo(Boolean value) {
            addCriterion("isInTest <>", value, "isInTest");
            return (Criteria) this;
        }

        public Criteria andIsInTestGreaterThan(Boolean value) {
            addCriterion("isInTest >", value, "isInTest");
            return (Criteria) this;
        }

        public Criteria andIsInTestGreaterThanOrEqualTo(Boolean value) {
            addCriterion("isInTest >=", value, "isInTest");
            return (Criteria) this;
        }

        public Criteria andIsInTestLessThan(Boolean value) {
            addCriterion("isInTest <", value, "isInTest");
            return (Criteria) this;
        }

        public Criteria andIsInTestLessThanOrEqualTo(Boolean value) {
            addCriterion("isInTest <=", value, "isInTest");
            return (Criteria) this;
        }

        public Criteria andIsInTestIn(List<Boolean> values) {
            addCriterion("isInTest in", values, "isInTest");
            return (Criteria) this;
        }

        public Criteria andIsInTestNotIn(List<Boolean> values) {
            addCriterion("isInTest not in", values, "isInTest");
            return (Criteria) this;
        }

        public Criteria andIsInTestBetween(Boolean value1, Boolean value2) {
            addCriterion("isInTest between", value1, value2, "isInTest");
            return (Criteria) this;
        }

        public Criteria andIsInTestNotBetween(Boolean value1, Boolean value2) {
            addCriterion("isInTest not between", value1, value2, "isInTest");
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