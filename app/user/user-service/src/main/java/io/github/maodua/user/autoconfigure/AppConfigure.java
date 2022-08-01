package io.github.maodua.user.autoconfigure;

import io.github.maodua.wrench.common.advice.InternExceptionHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses= InternExceptionHandler.class)
public class AppConfigure {

}
