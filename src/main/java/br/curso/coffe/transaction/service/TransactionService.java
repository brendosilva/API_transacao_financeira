package br.curso.coffe.transaction.service;

import br.curso.coffe.transaction.dto.RequestTransactionDto;
import br.curso.coffe.transaction.dto.TransactionDto;
import br.curso.coffe.transaction.exceptions.UnauthorizedException;
import br.curso.coffe.transaction.redis.TransactionalRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TransactionService {

    private final TransactionalRedisRepository transactionalRedisRepository;


    public TransactionService(TransactionalRedisRepository transactionalRedisRepository){
        this.transactionalRedisRepository = transactionalRedisRepository;
    }

    @Transactional
    public Optional<TransactionDto> save(final RequestTransactionDto requestTransactionDto) {

        requestTransactionDto.setData(LocalDateTime.now());
        return Optional.of(transactionalRedisRepository.save(requestTransactionDto));
    }

    @Retryable(value = QueryTimeoutException.class, maxAttempts = 5,backoff = @Backoff(delay = 100))
    public Optional<TransactionDto> findById(final String id) {
        log.info("Consultando redis");
        return transactionalRedisRepository.findById(id);
    }




}
