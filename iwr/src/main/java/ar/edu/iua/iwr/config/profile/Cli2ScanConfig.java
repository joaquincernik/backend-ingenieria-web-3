package ar.edu.iua.iwr.config.profile;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//esta es una clase de configuracion, spring las carga automaticamente
@Configuration

//poder trabajar el perfil de forma dinamica
// @ConditionalCOnExpreison(

//todo lo que esta dentro de eso usalo
//repositorios
@EnableJpaRepositories(basePackages = "ar.edu.iua.iwr", 
excludeFilters = {
		@ComponentScan.Filter(type = FilterType.REGEX, pattern = "ar\\.edu\\.iua\\.iwr\\.integration\\.cli1\\..*")
		
})


//para que se cargen las entidades
@EntityScan(basePackages = {
		"ar.edu.iua.iwr.model",
		"ar.edu.iua.iwr.auth",
		"ar.edu.iua.iwr.integration.cli2.model"
})

//solamente las que coincidan con el perfil
@Profile("cli2")
public class Cli2ScanConfig {

}
