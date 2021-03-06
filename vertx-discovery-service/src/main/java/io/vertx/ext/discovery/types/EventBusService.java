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

package io.vertx.ext.discovery.types;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.discovery.DiscoveryService;
import io.vertx.ext.discovery.Record;
import io.vertx.ext.discovery.ServiceReference;
import io.vertx.ext.discovery.spi.ServiceType;

import java.util.Collection;
import java.util.Objects;

/**
 * {@link ServiceType} for event bus services (service proxies).
 * Consumers receive a service proxy to use the service.
 *
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
@VertxGen
public interface EventBusService extends ServiceType {

  /**
   * Name of the type.
   */
  String TYPE = "eventbus-service-proxy";

  /**
   * Sugar method to creates a record for this type.
   * <p>
   * The java interface is added to the metadata in the `service.interface` key.
   *
   * @param name     the name of the service.
   * @param address  the event bus address on which the service available
   * @param itf      the Java interface
   * @param metadata the metadata
   * @return the created record
   */
  @GenIgnore
  static Record createRecord(String name, String address, Class itf, JsonObject metadata) {
    return createRecord(name, address, itf.getName(), metadata);
  }

  /**
   * Sugar method to creates a record for this type.
   * <p>
   * The java interface is added to the metadata in the `service.interface` key.
   *
   * @param name     the name of the service.
   * @param address  the event bus address on which the service available
   * @param itf      the Java interface (name)
   * @param metadata the metadata
   * @return the created record
   */
  static Record createRecord(String name, String address, String itf, JsonObject metadata) {
    Objects.requireNonNull(name);
    Objects.requireNonNull(itf);
    Objects.requireNonNull(address);

    JsonObject meta;
    if (metadata == null) {
      meta = new JsonObject();
    } else {
      meta = metadata.copy();
    }

    return new Record()
        .setType(TYPE)
        .setName(name)
        .setMetadata(meta.put("service.interface", itf))
        .setLocation(new JsonObject().put(Record.ENDPOINT, address));
  }

  /**
   * Same as {@link #createRecord(String, String, Class, JsonObject)} but without metadata.
   *
   * @param name    the name of the service
   * @param itf     the Java interface
   * @param address the event bus address on which the service available
   * @return the created record
   */
  @GenIgnore
  static Record createRecord(String name, String address, Class itf) {
    return createRecord(name, address, itf, null);
  }

  /**
   * Lookup for a service record and if found, retrieve it and return the service object (used to consume the service).
   * This is a convenient method to avoid explicit lookup and then retrieval of the service.
   *
   * @param discovery     the discovery service
   * @param filter        the filter to select the service
   * @param resultHandler the result handler
   * @param <T>           the service interface
   */
  static <T> void getProxy(DiscoveryService discovery, JsonObject filter, Handler<AsyncResult<T>>
      resultHandler) {
    discovery.getRecord(filter, ar -> {
      if (ar.failed()) {
        resultHandler.handle(Future.failedFuture(ar.cause()));
      } else {
        if (ar.result() == null) {
          resultHandler.handle(Future.failedFuture("Cannot find service matching with " + filter));
        } else {
          ServiceReference service = discovery.getReference(ar.result());
          resultHandler.handle(Future.succeededFuture(service.get()));
        }
      }
    });
  }

  /**
   * Lookup for a service record and if found, retrieve it and return the service object (used to consume the service).
   * This is a convenient method to avoid explicit lookup and then retrieval of the service. A filter based on the
   * request interface is used.
   *
   * @param discovery     the discovery service
   * @param itf           the service interface
   * @param resultHandler the result handler
   * @param <T>           the service interface
   */
  @GenIgnore
  static <T> void getProxy(DiscoveryService discovery, Class<T> itf, Handler<AsyncResult<T>>
      resultHandler) {
    JsonObject filter = new JsonObject().put("service.interface", itf.getName());
    getProxy(discovery, filter, resultHandler);
  }

  static <T> void getProxy(DiscoveryService discovery, String serviceInterface, String proxyInterface,
                           Handler<AsyncResult<T>> resultHandler) {
    JsonObject filter = new JsonObject().put("service.interface", serviceInterface);
    getProxy(discovery, filter, proxyInterface, resultHandler);
  }

  static <T> void getProxy(DiscoveryService discovery, JsonObject filter, String
      proxyClass, Handler<AsyncResult<T>> resultHandler) {
    discovery.getRecord(filter, ar -> {
      if (ar.failed()) {
        resultHandler.handle(Future.failedFuture(ar.cause()));
      } else {
        if (ar.result() == null) {
          resultHandler.handle(Future.failedFuture("Cannot find service matching with " + filter));
        } else {
          ServiceReference service = discovery.getReferenceWithConfiguration(ar.result(),
              new JsonObject().put("client.class", proxyClass));
          resultHandler.handle(Future.succeededFuture(service.get()));
        }
      }
    });
  }

  /**
   * Lookup for a service record and if found, retrieve it and return the service object (used to consume the service).
   * This is a convenient method to avoid explicit lookup and then retrieval of the service. A filter based on the
   * request interface is used.
   *
   * @param discovery     the discovery service
   * @param itf           the service interface
   * @param resultHandler the result handler
   * @param <T>           the service interface
   */
  static <T> void getProxy(DiscoveryService discovery, String itf, Handler<AsyncResult<T>>
      resultHandler) {
    JsonObject filter = new JsonObject().put("service.interface", itf);
    getProxy(discovery, filter, resultHandler);
  }
}
