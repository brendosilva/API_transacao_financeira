package br.curso.coffe.transaction.api.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(of = "uuid")
public class TransactionDto {

    private UUID uuid;

}
