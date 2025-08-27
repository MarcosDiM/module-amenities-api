package com.inncome;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class ModuloAmenitiesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModuloAmenitiesApplication.class, args);
	}

	// la aplicacion usa la zona horaria sin importar la del servidor
	@PostConstruct
	public void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("America/Argentina/Buenos_Aires"));
	}
}
