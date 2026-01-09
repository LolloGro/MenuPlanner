package com.lollo.menuplanner.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @Column(nullable = false)
    private String menuName;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime menuCreatedDate;
    @ElementCollection
    @CollectionTable(name = "menu_meals", joinColumns = @JoinColumn(name = "menu_id"))
    private List<MealOfMenu> menu = new ArrayList<>();

    public Menu(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public LocalDateTime getMenuCreatedDate() {
        return menuCreatedDate;
    }

    public List<MealOfMenu> getMenu() {
        return menu;
    }

    public void setMenu(List<MealOfMenu> menu) {
        this.menu = menu;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Menu menu = (Menu) o;
        return getId() != null && Objects.equals(getId(), menu.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
