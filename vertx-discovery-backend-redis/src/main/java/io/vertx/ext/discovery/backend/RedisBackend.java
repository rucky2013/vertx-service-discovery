/*
 * Copyright (c) 2011-2016 The original author or authors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 *      The Eclipse Public License is available at
 *      http://www.eclipse.org/legal/epl-v10.html
 *
 *      The Apache License v2.0 is available at
 *      http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */

package io.vertx.ext.discovery.backend;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.discovery.Record;
import io.vertx.ext.discovery.spi.DiscoveryBackend;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * An implementation of the discovery backend based on Redis.
 *
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class RedisBackend implements DiscoveryBackend {

  private RedisClient redis;
  private String key;

  @Override
  public void init(Vertx vertx, JsonObject configuration) {
    key = configuration.getString("key", "records");
    redis = RedisClient.create(vertx, new RedisOptions(configuration));
  }

  @Override
  public void store(Record record, Handler<AsyncResult<Record>> resultHandler) {
    if (record.getRegistration() != null) {
      resultHandler.handle(Future.failedFuture("The record has already been registered"));
      return;
    }
    String uuid = UUID.randomUUID().toString();
    record.setRegistration(uuid);
    redis.hset(key, uuid, record.toJson().encode(), ar -> {
      if (ar.succeeded()) {
        resultHandler.handle(Future.succeededFuture(record));
      } else {
        resultHandler.handle(Future.failedFuture(ar.cause()));
      }
    });
  }

  @Override
  public void remove(Record record, Handler<AsyncResult<Record>> resultHandler) {
    Objects.requireNonNull(record.getRegistration(), "No registration id in the record");
    remove(record.getRegistration(), resultHandler);
  }

  @Override
  public void remove(String uuid, Handler<AsyncResult<Record>> resultHandler) {
    Objects.requireNonNull(uuid, "No registration id in the record");

    redis.hget(key, uuid, ar -> {
      if (ar.succeeded()) {
        if (ar.result() != null) {
          redis.hdel(key, uuid, deletion -> {
            if (deletion.succeeded()) {
              resultHandler.handle(Future.succeededFuture(
                  new Record(new JsonObject(ar.result()))));
            } else {
              resultHandler.handle(Future.failedFuture(deletion.cause()));
            }
          });
        } else {
          resultHandler.handle(Future.failedFuture("Record '" + uuid + "' not found"));
        }
      } else {
        resultHandler.handle(Future.failedFuture(ar.cause()));
      }
    });
  }

  @Override
  public void update(Record record, Handler<AsyncResult<Void>> resultHandler) {
    Objects.requireNonNull(record.getRegistration(), "No registration id in the record");
    redis.hset(key, record.getRegistration(), record.toJson().encode(), ar -> {
      if (ar.succeeded()) {
        resultHandler.handle(Future.succeededFuture());
      } else {
        resultHandler.handle(Future.failedFuture(ar.cause()));
      }
    });
  }

  @Override
  public void getRecords(Handler<AsyncResult<List<Record>>> resultHandler) {
    redis.hgetall(key, ar -> {
      if (ar.succeeded()) {
        JsonObject entries = ar.result();
        resultHandler.handle(Future.succeededFuture(entries.fieldNames().stream()
            .map(key -> new Record(new JsonObject(entries.getString(key))))
            .collect(Collectors.toList())));
      } else {
        resultHandler.handle(Future.failedFuture(ar.cause()));
      }
    });
  }

  @Override
  public void getRecord(String uuid, Handler<AsyncResult<Record>> resultHandler) {
    redis.hget(key, uuid, ar -> {
      if (ar.succeeded()) {
        if (ar.result() != null) {
          resultHandler.handle(Future.succeededFuture(new Record(new JsonObject(ar.result()))));
        } else {
          resultHandler.handle(Future.succeededFuture(null));
        }
      } else {
        resultHandler.handle(Future.failedFuture(ar.cause()));
      }
    });
  }
}
