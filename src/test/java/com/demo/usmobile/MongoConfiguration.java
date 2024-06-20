package com.demo.usmobile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;

import com.demo.usmobile.repository.UserRepository;
import com.mongodb.WriteConcern;

@Configuration
public class MongoConfiguration {
    @Bean
    public MongoDatabaseFactory factory() {
        return new SimpleMongoClientDatabaseFactory("mongodb://localhost:27017/imager200_test");
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory) {
        MongoTemplate template = new MongoTemplate(mongoDbFactory);
        template.setWriteConcern(WriteConcern.ACKNOWLEDGED);
        return template;
    }

    @Bean
    public MongoRepositoryFactoryBean mongoFactoryRepositoryBean(MongoTemplate template) {
        MongoRepositoryFactoryBean mongoDbFactoryBean = new MongoRepositoryFactoryBean(UserRepository.class);
        mongoDbFactoryBean.setMongoOperations(template);

        return mongoDbFactoryBean;
    }
}