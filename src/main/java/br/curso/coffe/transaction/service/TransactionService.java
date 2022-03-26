package br.curso.coffe.transaction.service;

import br.curso.coffe.transaction.dto.RequestTransactionDto;
import br.curso.coffe.transaction.dto.TransactionDto;
import br.curso.coffe.transaction.redis.TransactionalRedisRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionService {

    private TransactionalRedisRepository transactionalRedisRepository;


    public TransactionService(TransactionalRedisRepository transactionalRedisRepository){
        this.transactionalRedisRepository = transactionalRedisRepository;


    }

    @Transactional
    public Optional<TransactionDto> save(final RequestTransactionDto requestTransactionDto) {

        requestTransactionDto.setData(LocalDateTime.now());
        return Optional.of(transactionalRedisRepository.save(requestTransactionDto));
    }


    public Optional<TransactionDto> findById(final String id) {
        return transactionalRedisRepository.findById(id);
    }


}
