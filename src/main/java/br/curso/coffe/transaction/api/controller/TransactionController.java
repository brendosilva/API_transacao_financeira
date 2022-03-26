package br.curso.coffe.transaction.api.controller;

import br.curso.coffe.transaction.dto.RequestTransactionDto;
import br.curso.coffe.transaction.dto.TransactionDto;
import br.curso.coffe.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/transactions")
@Tag(name = "/transaction", description = "Recursos API's para manipulação de transações financeiras")
public class TransactionController {
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @Operation(description = "API de criação de transação financeira")
    @ResponseBody
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Retorno ok com transação criada."),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação da API."),
        @ApiResponse(responseCode = "403", description = "Erro de autorização da API."),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado")})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TransactionDto> sendTransaction(@RequestBody final RequestTransactionDto requestTransactionDto){
        final Optional<TransactionDto> dto = transactionService.save(requestTransactionDto);
        if (dto.isPresent()){
            return Mono.just(dto.get());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");


    }

    @Operation(description = "Recurso da API para buscar  as transações persistidas por id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorno ok com transação criada."),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação da API."),
            @ApiResponse(responseCode = "403", description = "Erro de autorização da API."),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")})
    @Parameters(value = {@Parameter(name = "id", in = ParameterIn.PATH)})
    @GetMapping(value = "/{id}")
    public Mono<TransactionDto> returnTranscation(@PathVariable final String id) {
        final Optional<TransactionDto> dto = transactionService.findById(id);
        if(dto.isPresent()) return Mono.just(dto.get());
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "unable to find resource");
    }

    @Operation(description = "Recurso da API para remover uma transação persistidas por id")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Retorno ok com transação criada."),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação da API."),
            @ApiResponse(responseCode = "403", description = "Erro de autorização da API."),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")})
    @Parameters(value = {@Parameter(name = "id", in = ParameterIn.PATH)})
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TransactionDto> deleteTranscation(@PathVariable("id") final String uuid) {
        return Mono.empty();
    }

    @Operation(description = "Recurso da API para autorizar a transação financeira")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorno ok com transação criada."),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação da API."),
            @ApiResponse(responseCode = "403", description = "Erro de autorização da API."),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")})
    @Parameters(value = {@Parameter(name = "id", in = ParameterIn.PATH)})
    @PatchMapping(value = "/{id}/confirmar")
    public Mono<TransactionDto> confirmTransaction(@PathVariable("id") final String uuid){
        return Mono.empty();
    }

}
