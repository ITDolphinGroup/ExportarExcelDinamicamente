package pe.com.win.conexion;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@MapperScan(basePackages = "pe.com.db2.mapper", sqlSessionTemplateRef = "SqlSessionTemplate_DATABASE2")
public class DataSource_db2_Config {

	@Bean(name = "DataSource_DATABASE2")
	public DataSource dataSource_DATABASE2() {
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
	    
	    //Authentication parameters URL FOR sql server
	    dataSource.setUrl("jdbc:sqlserver://10.1.2.106:1433;DatabaseName=PE_OPTICAL_CRM;");
	    dataSource.setUsername("PE_OPTICAL_ERP");
	    dataSource.setPassword("Optical123+");
	    return dataSource;
	}
 
	@Bean(name = "SqlSessionFactory_DATABASE2")
	public SqlSessionFactory SqlSessionFactory_DATABASE2(@Qualifier("DataSource_DATABASE2") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		return bean.getObject();
	}
 
	@Bean(name = "TransactionManager_DATABASE2")
	public DataSourceTransactionManager TransactionManager_DATABASE2(@Qualifier("DataSource_DATABASE2") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
 
	@Bean(name = "SqlSessionTemplate_DATABASE2")
	public SqlSessionTemplate SqlSessionTemplate_DATABASE2(@Qualifier("SqlSessionFactory_DATABASE2") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
