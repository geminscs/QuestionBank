package indi.tammy.qb;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * 主程序入口
 *
 */
@ImportResource(value = {"ApplicationContextBase.xml"})
@SpringBootApplication
public class App {
	
	public static void main(String[] args){
		SpringApplication app = new SpringApplication(App.class);
		app.run(args);
	}
	
}
