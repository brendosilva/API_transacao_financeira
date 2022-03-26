package br.curso.coffe.transaction.redis;

import br.curso.coffe.transaction.dto.TransactionDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionalRedisRepository extends CrudRepository<TransactionDto, String> {

}
