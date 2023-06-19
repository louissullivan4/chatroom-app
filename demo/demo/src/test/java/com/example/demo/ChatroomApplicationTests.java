package com.example.demo;

import com.example.demo.controller.AccountController;
import com.example.demo.controller.RoomController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class ChatroomApplicationTests {
	@Autowired
	private AccountController accountController;

	@Autowired
	private RoomController roomController;
	@Test
	void contextLoads() throws Exception {
		assertThat(accountController).isNotNull();
		assertThat(roomController).isNotNull();
	}

}
