package com.booksearch;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;

@Tag("restdocs")
@Retention(RetentionPolicy.RUNTIME)
@ContextConfiguration(classes = {SpringRestDocsTestConfiguration.class})
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
public @interface SpringRestDocsTest {}

@ComponentScan
@EnableAutoConfiguration
class SpringRestDocsTestConfiguration {}