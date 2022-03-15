package br.curso.coffe.transaction.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(of = "uuid")
public class TransactionDto {

    @Schema(description = "Codigo de identificação da transação")
    private UUID uuid;

    @Schema(description = "valor da transação")
    @NotNull(message = "informa o valor da transação")
    private BigDecimal valor;

    @Schema(description = "data/hora/minuto e segundo da transação")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime data;

    @Schema(description = "conta de origem da transação")
    @NotNull(message = "informar a conta de origem da transação")
    @Valid
    private ContaDto conta;

    @Schema(description = "beneficiario da transaçaõ")
    @Valid
    private BeneficiarioDto beneficiario;

    @NotNull(message = "informar o tipo da transação")
    @Schema(description = "Tipo da transação")
    private TipoTransacao tipoTransacao;

    @Schema(description = "situação da transação")
    private SituacaoEnum situacao;

}
