package br.curso.coffe.transaction.feign;

import br.curso.coffe.transaction.dto.LimiteDiarioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.awt.*;

@FeignClient(value = "limites", url = "${limites.url}")
public interface LimiteClient {

    @RequestMapping(path = "/limite-diario/{agencia}/{conta}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    LimiteDiarioDto buscarLimiteDiario(@PathVariable("agencia") final Long agencia,@PathVariable("conta") final Long conta);
}
