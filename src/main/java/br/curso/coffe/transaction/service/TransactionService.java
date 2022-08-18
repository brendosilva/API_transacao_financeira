package br.curso.coffe.transaction.service;

import br.curso.coffe.transaction.dto.RequestTransactionDto;
import br.curso.coffe.transaction.dto.TransactionDto;
import br.curso.coffe.transaction.redis.TransactionalRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class TransactionService {

    private TransactionalRedisRepository transactionalRedisRepository;
    private RetryTemplate retryTemplate;

    private ReactiveKafkaProducerTemplate<String, RequestTransactionDto> reactiveKafkaProducerTemplate;


    @Value("${app.topic}")
    private String topic;

    public TransactionService(TransactionalRedisRepository transactionalRedisRepository,
                              RetryTemplate retryTemplate,
                              ReactiveKafkaProducerTemplate<String, RequestTransactionDto> reactiveKafkaProducerTemplate
    ) {
        this.transactionalRedisRepository = transactionalRedisRepository;
        this.retryTemplate = retryTemplate;
        this.reactiveKafkaProducerTemplate = reactiveKafkaProducerTemplate;
    }


    @Transactional
    @Retryable(value = QueryTimeoutException.class, maxAttempts = 5,backoff = @Backoff(delay = 100))
    public Optional<TransactionDto> save(final RequestTransactionDto requestTransactionDto) {

        requestTransactionDto.setData(LocalDateTime.now());
        reactiveKafkaProducerTemplate.send(topic, requestTransactionDto)
                .doOnSuccess(voidSenderResult -> log.info(voidSenderResult.toString()))
                .subscribe();
        return Optional.of(transactionalRedisRepository.save(requestTransactionDto));
    }


    public Optional<TransactionDto> findById(final String id) {
        return retryTemplate.execute(ret -> {
            log.info("Consultando Redis");
            return transactionalRedisRepository.findById(id);
        });

    }




}
