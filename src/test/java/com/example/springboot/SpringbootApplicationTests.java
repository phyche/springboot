package com.example.springboot;

import com.example.springboot.module.User;
import com.example.springboot.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

	@Autowired
	UserService userService;

	@Test
	public void contextLoads() {
		List<User> list = userService.selectUserList();

	}

}
