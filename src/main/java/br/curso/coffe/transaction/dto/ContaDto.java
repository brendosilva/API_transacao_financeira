package br.curso.coffe.transaction.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class ContaDto implements Serializable {
    private static final long serialVersionUID = 2806412403585360625L;

    @Schema(description = "Codigo da Agencia")
    @NotNull(message = "informa o codigo da agencia")
    private Long codigoAgencia;

    @Schema(description = "Codigo da conta")
    @NotNull(message = "informa o codigo da conta")
    private Long codigoConta;

}
