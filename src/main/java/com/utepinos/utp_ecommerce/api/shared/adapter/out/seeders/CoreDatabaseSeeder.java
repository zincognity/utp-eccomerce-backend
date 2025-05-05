package com.utepinos.utp_ecommerce.api.shared.adapter.out.seeders;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import lombok.extern.slf4j.Slf4j;

/**
 * * Clase de configuración para inicializar la base de datos con datos de
 * * seeders.
 */
@Slf4j
@Configuration
public class CoreDatabaseSeeder {
  @Value("classpath:sql/core/seeder.sql")
  private Resource scriptSql;

  @Bean
  DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
    DataSourceInitializer initializer = new DataSourceInitializer();

    initializer.setDataSource(dataSource);
    initializer.setDatabasePopulator(new ResourceDatabasePopulator(scriptSql));

    return initializer;
  }
}
