package com.tickets.api;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes = {ApiApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RestAssuredBaseTestClass {


    @LocalServerPort
    protected int serverPort;

    protected static String baseURI = RestAssured.baseURI;
    protected static String basePath = RestAssured.basePath;

    protected int port = RestAssured.port;

    @BeforeAll
    public static void setup() {
    }

    @BeforeEach
    public void before() throws InterruptedException {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        log.info("*******************************");
        log.info("****     STARTING TEST     ****");
        log.info("*******************************");

        RestAssured.port = serverPort;
        port = serverPort;

        log.info("********************************************************************");
        log.info("****   Started on URI/PATH:PORT => {}:{}{}", baseURI, port, basePath);
        log.info("********************************************************************");
    }
}
