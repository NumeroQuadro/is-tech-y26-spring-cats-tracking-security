package com.catstrackingrestapi.CatsTrackingRestAPI.src;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import src.Models.Cat;
import src.Models.CatColor;
import src.Models.Role;
import src.Services.CatService;
import src.Services.OwnerService;
import src.Services.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@SpringBootApplication
@RestController
@EnableJpaRepositories(basePackages = {"src.Repositories"})
@ComponentScan(basePackages = {"src.Services", "source", "Security"})
@EntityScan(basePackages={"src.Models"})
public class CatsTrackingRestApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(CatsTrackingRestApiApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(OwnerService ownerService, CatService catService, UserService userService) {
		return args -> {
			userService.addAdminIfNotExists("dimonlimon", "fuckfuck");
			var result = catService.getCatsByColorRelatedToOwner(CatColor.black, "dimonlimon");
//			ownerService.addOwnerWithoutCats("dimonlimon", LocalDate.of(1, 1, 1));
		};
	}

}