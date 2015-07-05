package com.paymee.config;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.ServerConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@PropertySource("classpath:/env/paymee.properties")
//@PropertySource({"classpath:/env/paymee.properties", "classpath:/env/paymee-${app.env}.properties"})
@EnableAsync
@EnableScheduling
public class ApplicationConfig {

	@Inject
	private Environment environment;

	@Bean
	public DataSource dataSource() {
		try {
			final PGPoolingDataSource dataSource = new PGPoolingDataSource();
			dataSource.setUrl(environment.getProperty("jdbc.url"));
			dataSource.setUser(getCrypto().decrypt(environment.getProperty("jdbc.user")));
			dataSource.setPassword(getCrypto().decrypt(environment.getProperty("jdbc.password")));
			dataSource.setInitialConnections(0);
			dataSource.setMaxConnections(10);
			return dataSource;
		} catch (SQLException e) {
			throw new RuntimeException("Exception when configuring data source.", e);
		}
	}

	@Bean
	public EbeanServer builcEbeanServer() {
		final ServerConfig serverConfig = new ServerConfig();
		serverConfig.setName(environment.getProperty("ebean.name"));
		serverConfig.setDataSource(dataSource());
		serverConfig.setDdlGenerate(environment.getProperty("ebean.generateDdl", Boolean.class));
		serverConfig.setDdlRun(environment.getProperty("ebean.runDdl", Boolean.class));
		serverConfig.setDefaultServer(true);
		serverConfig.setRegister(false);
		serverConfig.addPackage("com.paymee.domain");
		serverConfig.setAutoCommitMode(true);
		serverConfig.setDatabasePlatformName(environment.getProperty("ebean.db"));
		final EbeanServer ebeanServer = EbeanServerFactory.create(serverConfig);

		return ebeanServer;
	}

	@Bean
	public ObjectMapper jacksonObjectMapper() {
		return new ObjectMapper();
	}

	private StandardPBEStringEncryptor getCrypto() {
		final StandardPBEStringEncryptor crypto = new StandardPBEStringEncryptor();
		crypto.setPassword(environment.getProperty("crypto.password"));
		return crypto;
	}
}
