package com.quickstartdev.librarymanagementsystem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.knf.dev.librarymanagementsystem.Application;
@SpringBootTest(classes=Application.class)
class ApplicationTests {

	@Autowired
	Application app;
	@Test
	void contextLoads() {
		Assertions.assertNotNull(app, "Application context is not loaded successfully.");
	}

}
