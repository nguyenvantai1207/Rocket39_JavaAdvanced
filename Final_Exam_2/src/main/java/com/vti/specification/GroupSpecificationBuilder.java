package com.vti.specification;

import com.vti.entity.Group;
import com.vti.filter.GroupFilter;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

@Data
public class GroupSpecificationBuilder {
    private GroupFilter filter;
    private String search;

    public GroupSpecificationBuilder(GroupFilter filter, String search) {
        this.filter = filter;
        this.search = search;
    }

    public Specification<Group> build() {
        SearchCriteria searchCriteria = new SearchCriteria("groupName", "LIKE", search);

        SearchCriteria minQuantityCriteria = new SearchCriteria("quantity", ">=", filter.getMinQuantity());

        SearchCriteria maxQuantityCriteria = new SearchCriteria("quantity", "<=", filter.getMaxQuantity());

        SearchCriteria equalQuantityCriteria = new SearchCriteria("quantity", "=", filter.getQuantity());

        Specification<Group> where = null;

        if (!StringUtils.isEmpty(search)) {
            where = new GroupSpecification(searchCriteria);
        }

        //filter equal quantity
        if (filter.getQuantity() != 0) {
            if (where != null) {
                where = where.and(new GroupSpecification(equalQuantityCriteria));
            } else {
                where = new GroupSpecification(equalQuantityCriteria);
            }
        }

        //filter by rangeQuantity
        if (filter.getMinQuantity() != 0 && filter.getMaxQuantity() != 0) {
            if (where != null) {
                where = where.and(new GroupSpecification(minQuantityCriteria)).and(new GroupSpecification(maxQuantityCriteria));
            } else {
                where = new GroupSpecification(minQuantityCriteria).and(new GroupSpecification(maxQuantityCriteria));
            }
        } else if (filter.getMinQuantity() != 0) {
            if (where != null) {
                where = where.and(new GroupSpecification(minQuantityCriteria));
            } else {
                where = new GroupSpecification(minQuantityCriteria);
            }
        } else if (filter.getMaxQuantity() != 0) {
            if (where != null) {
                where = where.and(new GroupSpecification(maxQuantityCriteria));
            } else {
                where = new GroupSpecification(maxQuantityCriteria);
            }
        }

        return where;
    }
}
