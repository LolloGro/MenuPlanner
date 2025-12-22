package com.lollo.menuplanner;

import org.springframework.boot.SpringApplication;

public class TestMenuPlannerApplication {

    public static void main(String[] args) {
        SpringApplication.from(MenuPlannerApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
