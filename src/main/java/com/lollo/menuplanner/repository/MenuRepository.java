package com.lollo.menuplanner.repository;

import com.lollo.menuplanner.entity.Menu;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends ListCrudRepository<Menu, Integer> {
}
