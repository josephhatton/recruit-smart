package com.recruitsmart.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.recruitsmart.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.recruitsmart.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.Applicant.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.Company.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.Book.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.JobOrder.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.HiringContact.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.Address.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.Activity.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.ActivityAction.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.ApplicantComment.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.ApplicantInternalComment.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.ApplicantStatus.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.CompanyComment.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.CompanyInternalComment.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.HiringContactComment.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.HiringContactInternalComment.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.HotList.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.JobOrderComment.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.JobOrderInternalComment.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.Skill.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.SkillCategory.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.WorkHistory.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.WorkStatus.class.getName(), jcacheConfiguration);
            cm.createCache(com.recruitsmart.domain.HotListBucket.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
