package com.lollo.menuplanner.repository;

import com.lollo.menuplanner.entity.Menu;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends ListCrudRepository<Menu, Integer> {
    List<Menu> findByCreatedBy(String createdBy);
    Optional<Menu> findByIdAndCreatedBy(int id, String createdBy);
}

