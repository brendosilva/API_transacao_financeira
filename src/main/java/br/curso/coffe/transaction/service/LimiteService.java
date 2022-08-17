package br.curso.coffe.transaction.service;

import br.curso.coffe.transaction.dto.LimiteDiarioDto;
import br.curso.coffe.transaction.feign.LimiteClient;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.decorators.Decorators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.Supplier;

@Service
public class LimiteService {

    @Autowired
    private CircuitBreaker countCircuitBreaker;

    private final LimiteClient limiteClient;

    public LimiteService(LimiteClient limiteClient) {
        this.limiteClient = limiteClient;
    }

    public LimiteDiarioDto buscarLimiteDiario(final Long agencia, final Long conta) {
        var limiteDiarioSup = fallback(agencia, conta);
        return limiteDiarioSup.get();
    }

    private Supplier<LimiteDiarioDto> fallback(final Long agencia, final Long conta) {
        var limiteDiarioSupplier = countCircuitBreaker.decorateSupplier(() -> limiteClient.buscarLimiteDiario(agencia, conta));
        return Decorators
                .ofSupplier(limiteDiarioSupplier)
                .withCircuitBreaker(countCircuitBreaker)
                .withFallback(Arrays.asList(CallNotPermittedException.class), e -> this.getStaticLimit())
                .decorate();
    }

    private LimiteDiarioDto getStaticLimit() {
        LimiteDiarioDto limiteDiarioDto = new LimiteDiarioDto();
        limiteDiarioDto.setValor(BigDecimal.ZERO);

        return limiteDiarioDto;
    }
}
