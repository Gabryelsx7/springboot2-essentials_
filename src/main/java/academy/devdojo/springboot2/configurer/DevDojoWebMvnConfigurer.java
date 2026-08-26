package academy.devdojo.springboot2.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.ReactivePageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class DevDojoWebMvnConfigurer implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        ReactivePageableHandlerMethodArgumentResolver pageHandler = new ReactivePageableHandlerMethodArgumentResolver ();
        pageHandler.setFallbackPageable(PageRequest.of(0,5));
    }
}
