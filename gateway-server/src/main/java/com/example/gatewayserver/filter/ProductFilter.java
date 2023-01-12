package com.example.gatewayserver.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ProductFilter extends AbstractGatewayFilterFactory<Config> {
    public ProductFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(final Config config) {
        return (exchange, chain) -> {
            log.info("ProductFilter baseMessage: {}", config.getBaseMessage());

            if (config.isPreLogger()) {
                log.info("ProductFilter Start: {}", exchange.getRequest());
            }

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostLogger()) {
                    log.info("ProductFilter End: {}", exchange.getResponse());
                }
            }));
        };
    }
}
