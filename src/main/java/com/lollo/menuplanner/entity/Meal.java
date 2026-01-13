package com.lollo.menuplanner.entity;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Objects;

import static com.lollo.menuplanner.util.Capitalize.capitalizeFirstLetter;

@Entity
@Table(name = "meal")
@EntityListeners(AuditingEntityListener.class)
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;
    @Column(nullable = false, unique = true)
    private String mealName;
    @Column(nullable = false)
    private String mainIngredient;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MealType mealType;
    @Column(nullable = false)
    private int time;
    @OneToOne(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    private Recipe recipe;

    public Meal() {}

    public Meal(String mealName, String mainIngredient, MealType mealType, int time) {
        this.mealName = capitalizeFirstLetter(mealName);
        this.mainIngredient = capitalizeFirstLetter(mainIngredient);
        this.mealType = mealType;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = capitalizeFirstLetter(mealName);
    }

    public String getMainIngredient() {
        return mainIngredient;
    }

    public void setMainIngredient(String mainIngredient) {
        this.mainIngredient = capitalizeFirstLetter(mainIngredient);
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Meal meal = (Meal) o;
        return getId() != null && Objects.equals(getId(), meal.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
