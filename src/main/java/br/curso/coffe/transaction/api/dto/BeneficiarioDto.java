package br.curso.coffe.transaction.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class BeneficiarioDto {

    @Schema(description = "CPF do beneficiario")
    @NotNull(message = "Informar o cpf")
    private Long cpf;

    @NotNull(message = "informa o codigo do banco de destino")
    @Schema(description = "codigo do banco de destino")
    private Long codigoBanco;

    @NotNull(message = "informa a agencia")
    @Schema(description = "Agencia de destino")
    private String agencia;

    @NotNull(message = "informa a conta")
    @Schema(description = "Conta de destino")
    private String conta;

    @NotNull(message = "Informar o nome do favorecido")
    @Schema(description = "Nome do favorecido")
    private String nomeFavorecido;
}
