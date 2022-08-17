package br.curso.coffe.transaction.service;

import br.curso.coffe.transaction.dto.RequestTransactionDto;
import br.curso.coffe.transaction.dto.TransactionDto;
import br.curso.coffe.transaction.exceptions.UnauthorizedException;
import br.curso.coffe.transaction.redis.TransactionalRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.RetryConfiguration;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TransactionService {

    private final TransactionalRedisRepository transactionalRedisRepository;
    private final RetryTemplate retryTemplate;

    public TransactionService(TransactionalRedisRepository transactionalRedisRepository, RetryTemplate retryTemplate){
        this.transactionalRedisRepository = transactionalRedisRepository;
        this.retryTemplate = retryTemplate;
    }

    @Transactional
    @Retryable(value = QueryTimeoutException.class, maxAttempts = 5,backoff = @Backoff(delay = 100))
    public Optional<TransactionDto> save(final RequestTransactionDto requestTransactionDto) {

        requestTransactionDto.setData(LocalDateTime.now());
        return Optional.of(transactionalRedisRepository.save(requestTransactionDto));
    }


    public Optional<TransactionDto> findById(final String id) {
        return retryTemplate.execute(ret -> {
            log.info("Consultando Redis");
            return transactionalRedisRepository.findById(id);
        });

    }




}
