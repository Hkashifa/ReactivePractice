package com.example.ReactivePractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReactivePracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactivePracticeApplication.class, args);
    }


    //Remove after successfully testing
//	@Bean
//	public CommandLineRunner runner(OrderRepository repository) {
//		return args -> {
//			for (int i = 0; i < 3000; i++) {
//				repository.save(
//						Student.builder()
//								.firstname("Test" + i)
//								.lastname("test" + i)
//								.age(i)
//								.build()
//				).subscribe();
//			}
//		};
//	}
}
