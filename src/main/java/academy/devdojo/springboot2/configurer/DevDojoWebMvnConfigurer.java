package academy.devdojo.springboot2.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver; // IMPORTANTE: Versão correta para Spring MVC
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class DevDojoWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver pageHandler = new PageableHandlerMethodArgumentResolver();

        // Define que se o usuário não passar page/size na URL, o padrão será página 0 com 5 elementos
        pageHandler.setFallbackPageable(PageRequest.of(0, 5));

        // ADICIONADO: Obrigatório para o Spring registrar o resolvedor na aplicação
        resolvers.add(pageHandler);
    }
}