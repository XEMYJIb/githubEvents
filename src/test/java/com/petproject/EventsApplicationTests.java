package com.petproject;

import com.petproject.model.github.Hook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestOperations;

import java.util.Collections;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EventsApplicationTests {

    @Configuration
    @ComponentScan("com.petproject")
    public static class SpringConfig {

        @Bean
        @Primary
        public RestOperations restOperations() {
            Hook.Config config = new Hook().new Config();
            config.setUrl("/web_hook/github");
            Hook hook = new Hook();
            hook.setName("web");
            hook.setActive(true);
            hook.setConfig(config);
            hook.setEvents(Collections.singletonList("issue_comment"));
            final Hook[] entry = {hook};
            final ResponseEntity entity = mock(ResponseEntity.class);
            doReturn(entry)
                .when(entity)
                .getBody();
            final RestOperations restOperations = mock(RestOperations.class);
            doReturn(entity)
                .when(restOperations)
                .exchange(contains("hooks"), any(HttpMethod.class), any(HttpEntity.class), any(Class.class));
            return restOperations;
        }
    }

    @Test
    public void contextLoads() {
    }

}
