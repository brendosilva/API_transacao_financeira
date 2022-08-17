package br.curso.coffe.transaction.api.controller;


import br.curso.coffe.transaction.dto.LimiteDiarioDto;
import br.curso.coffe.transaction.feign.LimiteClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/limites")
public class LimiteController {

    private final LimiteClient limiteClient;

    public LimiteController(LimiteClient limiteClient) {
        this.limiteClient = limiteClient;
    }

    @GetMapping(value = "/{agencia}/{conta}")
    public LimiteDiarioDto buscarLimiteDiario(@PathVariable("agencia") final Long agencia, @PathVariable("conta") final Long conta){
        return limiteClient.buscarLimiteDiario(agencia, conta);
    }
}
