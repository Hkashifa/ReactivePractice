package com.example.ReactivePractice;

import com.example.ReactivePractice.entities.Order;
import com.example.ReactivePractice.enums.OrderStatus;
import com.example.ReactivePractice.repositories.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.example.ReactivePractice.enums.OrderStatus.PREPARING;

@SpringBootApplication
public class ReactivePracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactivePracticeApplication.class, args);
    }



	@Bean
	public CommandLineRunner runner(OrderRepository repository) {
		return args -> {
			for (int i = 0; i < 3000; i++) {
				repository.save(
                        Order.builder()
                                .orderStatus(OrderStatus.valueOf(String.valueOf(PREPARING)))
                                .item("Test"+i)
                                .price(i)
                                .build()
				).subscribe();
			}
		};
	}
}
