package ar.edu.iua.iwr;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import ar.edu.iua.iwr.integration.cli2.model.business.IProductCli2Business;
import ar.edu.iua.iwr.model.persistence.ProductRepository;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class BackendApplication extends SpringBootServletInitializer implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Value("${spring.profiles.active}")
	private String profile;

	// product cli 2 slim view
	/*@Autowired
	private IProductCli2Business productCli2Business;

	@Autowired
	private ProductRepository productDAO;
*/
	@Override
	public void run(String... args) throws Exception {
		// dev o prod
		log.info("Perfil activo '{}'", profile);
		/*log.info(
				"Default -------------------------------------------------------------------------------------------------------");
		productCli2Business.listExpired(new Date());
		log.info(
				"Customizada ---------------------------------------------------------------------------------------------------");
		productCli2Business.listSlim();

		log.info("Cantidad de productos de la categoría id=3: {}", productDAO.countProductsByCategory(3));
		log.info("Set stock=true producto id que no existe, resultado={}", productDAO.setStock(true, 333));
*/
	}

}
