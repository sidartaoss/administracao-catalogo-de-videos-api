package com.fullcycle.admin.catalogo.infrastructure.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ComponentScan("com.fullcycle.admin.catalogo")
public class WebServerConfig {
}
