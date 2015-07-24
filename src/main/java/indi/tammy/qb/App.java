package indi.tammy.qb;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

/**
 * 主程序入口
 *
 */
@ImportResource(value = {"classpath:/ApplicationContextBase.xml"})
@SpringBootApplication
public class App extends SpringBootServletInitializer{
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder 	application) {
   	 	return application.sources(App.class);
    }
	
	public static void main(String[] args){
		SpringApplication app = new SpringApplication(App.class);
		app.run(args);
	}
	
}
