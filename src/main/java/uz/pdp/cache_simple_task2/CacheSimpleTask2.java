package uz.pdp.cache_simple_task2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

@SpringBootApplication
@EnableCaching
public class CacheSimpleTask2 {

    public static void main(String[] args) {
        SpringApplication.run(CacheSimpleTask2.class, args);
    }

    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager concurrentMapCacheManager = new ConcurrentMapCacheManager();
        concurrentMapCacheManager.setCacheNames(Collections.singletonList("students"));
        return concurrentMapCacheManager;
    }

}
// juda ko'p select qilinadigan malumotlarni keshlash kerak
// ko'p o'zgarmaydigan malumotlarda keshni ishlatish kerak
