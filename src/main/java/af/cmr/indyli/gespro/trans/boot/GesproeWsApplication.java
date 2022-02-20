package af.cmr.indyli.gespro.trans.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;

import af.cmr.indyli.gespro.trans.config.GesproWsConfig;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@Import({ GesproWsConfig.class })
@EnableSwagger2
public class GesproeWsApplication {

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GesproeWsApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(GesproeWsApplication.class, args);
	}

}
