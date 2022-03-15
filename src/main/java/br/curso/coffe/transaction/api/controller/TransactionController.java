package br.curso.coffe.transaction.api.controller;

import br.curso.coffe.transaction.api.dto.RequestTransactionDto;
import br.curso.coffe.transaction.api.dto.TransactionDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    public Mono<TransactionDto> sendTransaction(final RequestTransactionDto requestTransactionDto){

    }

}
