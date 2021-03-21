package pe.com.win.conexion;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@MapperScan(basePackages = "pe.com.win.mapper", sqlSessionTemplateRef = "SqlSessionTemplate_DATABASE1")
public class DataSource_db1_Config {

	@Primary
	@Bean(name = "DataSource_DATABASE1")
	public DataSource dataSource_DATABASE1() {
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
	    
	    //Authentication parameters URL FOR sql server
	    dataSource.setUrl("jdbc:sqlserver://10.1.2.106:1433;DatabaseName=PE_OPTICAL_CRM;");
	    dataSource.setUsername("PE_OPTICAL_ERP");
	    dataSource.setPassword("Optical123+");
	    return dataSource;
	}
 
	@Bean(name = "SqlSessionFactory_DATABASE1")
	public SqlSessionFactory SqlSessionFactory_DATABASE1(@Qualifier("DataSource_DATABASE1") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		return bean.getObject();
	}
 
	@Bean(name = "TransactionManager_DATABASE1")
	public DataSourceTransactionManager TransactionManager_DATABASE1(@Qualifier("DataSource_DATABASE1") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
 
	@Bean(name = "SqlSessionTemplate_DATABASE1")
	public SqlSessionTemplate SqlSessionTemplate_DATABASE1(@Qualifier("SqlSessionFactory_DATABASE1") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
