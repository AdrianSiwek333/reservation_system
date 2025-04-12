package com.example.reservation_system.specification;

import com.example.reservation_system.entity.Property;
import com.example.reservation_system.entity.Reservation;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class PropertySpecification {

    public static Specification<Property> hasQuery(String queryParam) {
        return (root, query, criteriaBuilder) -> {
            if (queryParam == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("propertyName")), "%" + queryParam.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("city")), "%" + queryParam.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("country").get("countryName")), "%" + queryParam.toLowerCase() + "%")
            );
        };
    }


    public static Specification<Property> hasPriceRange(Integer minPrice, Integer maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice != null && maxPrice != null) {
                return criteriaBuilder.between(root.get("dayPrice"), minPrice, maxPrice);
            } else if (minPrice != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("dayPrice"), minPrice);
            } else if (maxPrice != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("dayPrice"), maxPrice);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    public static Specification<Property> hasDateRange(LocalDate userStartDate, LocalDate userEndDate) {
        return (root, query, cb) -> {
            if (userStartDate == null || userEndDate == null) return cb.conjunction();

            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Reservation> reservationRoot = subquery.from(Reservation.class);
            subquery.select(reservationRoot.get("propertyId").get("id"));

            Predicate overlaps = cb.and(
                    cb.lessThanOrEqualTo(reservationRoot.get("reservationStartDate"), userEndDate),
                    cb.greaterThanOrEqualTo(reservationRoot.get("reservationEndDate"), userStartDate)
            );

            subquery.where(overlaps);

            return cb.not(root.get("id").in(subquery));
        };
    }
}
