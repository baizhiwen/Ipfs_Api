package io.ktrade.ipfs;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@SpringBootApplication
//@MapperScan("io.ktrade.ipfs.dao")
public class IpfsApplication {

	public static void main(String[] args) {
		SpringApplication.run(IpfsApplication.class, args);
	}

//	@Autowired
//	private DataSource dataSource;
//	@Bean
//	public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
//		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//		sqlSessionFactoryBean.setDataSource(dataSource);
////		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis-mapping/*Mapper.xml"));
//		return sqlSessionFactoryBean.getObject();
//	}
}
