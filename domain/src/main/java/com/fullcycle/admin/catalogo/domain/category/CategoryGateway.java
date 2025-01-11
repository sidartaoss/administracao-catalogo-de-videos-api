package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {

    Category create(Category aCategory);

    void deleteById(CategoryID aCategoryID);

    Optional<Category> findById(CategoryID aCategoryID);

    Category update(Category aCategory);

    Pagination<Category> findAll(CategorySearchQuery aQuery);
}
