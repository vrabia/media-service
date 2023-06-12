package app.vrabia.mediaservice.config;

import app.vrabia.vrcommon.models.security.FiltersToAdd;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import app.vrabia.vrcommon.models.security.PublicEndpoints;
import org.springframework.context.annotation.Primary;

@Configuration
public class MediaServiceSecurityBeans {

    @Bean
    @Primary
    public PublicEndpoints authServicePublicEndpoints() {
        PublicEndpoints publicEndpoints = new PublicEndpoints();
        publicEndpoints.getEndpoints().add("/image/communities/**");
        publicEndpoints.getEndpoints().add("/image/store-html/**");
        publicEndpoints.getEndpoints().add("/image/html/**");
        return publicEndpoints;
    }

    @Bean
    @Primary
    public FiltersToAdd mediaServiceFiltersToAdd(ApplicationContext applicationContext) {
        FiltersToAdd filtersToAdd = new FiltersToAdd();
        filtersToAdd.getFilters().add(applicationContext.getBean(ClientAuthorizationFilter.class));
        return filtersToAdd;
    }
}
