package com.experis.worldoffice.productservice.model.specification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GenericSpecification {

    public static class DefaultSpecification<T> implements Specification<T> {

        public DefaultSpecification() {
        }

        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            return cb.isNotNull(root.get("id"));
        }
    }

    public static class Eq<T> implements Specification<T> {

        private String value;
        private String field;

        public Eq(String value, String field) {
            this.value = value;
            this.field = field;
        }

        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            if (Objects.isNull(this.value)) {
                return cb.isNotNull(root.get(this.field));
            }
            return cb.equal(cb.upper(root.get(this.field)),this.value.toUpperCase());
        }
    }

    public static class Like<T> implements Specification<T> {

        private String value;
        private String field;

        public Like(String value, String field) {
            this.value = value;
            this.field = field;
        }

        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            if (Objects.isNull(this.value)) {
                return cb.isNotNull(root.get(this.field));
            }
            return cb.like(cb.upper(root.get(this.field)), "%" + this.value.toUpperCase() + "%");
        }
    }

    public static class GTE<T> implements Specification<T> {

        private BigDecimal minor;
        private String field;

        public GTE(BigDecimal minor, String field) {
            this.minor = minor;
            this.field = field;
        }

        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(this.minor)) {
                predicates.add(cb.greaterThanOrEqualTo(root.get(this.field), this.minor));
            }

            return cb.isNotNull(root.get("id"));
        }
    }

    public static class LTE<T> implements Specification<T> {


        private BigDecimal major;
        private String field;

        public LTE(BigDecimal major, String field) {
            this.major = major;
            this.field = field;
        }

        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(this.major)) {
                predicates.add(cb.lessThanOrEqualTo(root.get(this.field), this.major));
            }

            return cb.isNotNull(root.get("id"));
        }
    }

    public static class JoinEqOperation<T,S> implements Specification<T>{

        private String value;
        private String field;
        private String joinField;

        public JoinEqOperation(String value, String joinField,String field) {
            this.value = value;
            this.field = field;
            this.joinField = joinField;
        }

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            if(Objects.isNull(this.joinField) || Objects.isNull(this.field) || Objects.isNull(this.value)){
                return new DefaultSpecification<T>().toPredicate(root,query,cb);
            }
            Join<T,S> tsJoin=root.join(this.joinField);
            return cb.equal(cb.upper(tsJoin.get(this.field)),this.value.toUpperCase());
        }
    }

    public static class CompileSpecificationList<T> implements Specification<T>{

        private List<Specification<T>> specifications;

        public CompileSpecificationList(List<Specification<T>> specifications) {
            this.specifications = specifications;
        }

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            if(Objects.isNull(specifications) || specifications.isEmpty()){
                return new DefaultSpecification<T>().toPredicate(root,query,cb);
            }
            List<Predicate> predicates = specifications.stream().map(e -> e.toPredicate(root,query,cb)).collect(Collectors.toList());

            return cb.and(predicates.toArray(new Predicate[0]));
        }
    }


}
