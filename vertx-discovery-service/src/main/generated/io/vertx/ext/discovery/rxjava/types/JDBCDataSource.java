/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.ext.discovery.rxjava.types;

import java.util.Map;
import rx.Observable;
import io.vertx.ext.discovery.types.DataSource;
import io.vertx.rxjava.ext.jdbc.JDBCClient;
import io.vertx.ext.discovery.rxjava.DiscoveryService;
import io.vertx.ext.discovery.Record;
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link io.vertx.ext.discovery.types.JDBCDataSource original} non RX-ified interface using Vert.x codegen.
 */

public class JDBCDataSource {

  final io.vertx.ext.discovery.types.JDBCDataSource delegate;

  public JDBCDataSource(io.vertx.ext.discovery.types.JDBCDataSource delegate) {
    this.delegate = delegate;
  }

  public Object getDelegate() {
    return delegate;
  }

  public static Record createRecord(String name, JsonObject location, JsonObject metadata) { 
    Record ret = io.vertx.ext.discovery.types.JDBCDataSource.createRecord(name, location, metadata);
    return ret;
  }

  /**
   * Convenient method that looks for a JDBC datasource source and provides the configured {@link io.vertx.rxjava.ext.jdbc.JDBCClient}. The
   * async result is marked as failed is there are no matching services, or if the lookup fails.
   * @param discovery The discovery service
   * @param filter The filter, optional
   * @param resultHandler the result handler
   */
  public static void getJDBCClient(DiscoveryService discovery, JsonObject filter, Handler<AsyncResult<JDBCClient>> resultHandler) { 
    io.vertx.ext.discovery.types.JDBCDataSource.getJDBCClient((io.vertx.ext.discovery.DiscoveryService)discovery.getDelegate(), filter, new Handler<AsyncResult<io.vertx.ext.jdbc.JDBCClient>>() {
      public void handle(AsyncResult<io.vertx.ext.jdbc.JDBCClient> ar) {
        if (ar.succeeded()) {
          resultHandler.handle(io.vertx.core.Future.succeededFuture(JDBCClient.newInstance(ar.result())));
        } else {
          resultHandler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
  }

  /**
   * Convenient method that looks for a JDBC datasource source and provides the configured {@link io.vertx.rxjava.ext.jdbc.JDBCClient}. The
   * async result is marked as failed is there are no matching services, or if the lookup fails.
   * @param discovery The discovery service
   * @param filter The filter, optional
   * @return 
   */
  public static Observable<JDBCClient> getJDBCClientObservable(DiscoveryService discovery, JsonObject filter) { 
    io.vertx.rx.java.ObservableFuture<JDBCClient> resultHandler = io.vertx.rx.java.RxHelper.observableFuture();
    getJDBCClient(discovery, filter, resultHandler.toHandler());
    return resultHandler;
  }

  /**
   * Convenient method that looks for a JDBC datasource source and provides the configured {@link io.vertx.rxjava.ext.jdbc.JDBCClient}. The
   * async result is marked as failed is there are no matching services, or if the lookup fails.
   * @param discovery The discovery service
   * @param filter The filter, optional
   * @param consumerConfiguration the consumer configuration
   * @param resultHandler the result handler
   */
  public static void getJDBCClient(DiscoveryService discovery, JsonObject filter, JsonObject consumerConfiguration, Handler<AsyncResult<JDBCClient>> resultHandler) { 
    io.vertx.ext.discovery.types.JDBCDataSource.getJDBCClient((io.vertx.ext.discovery.DiscoveryService)discovery.getDelegate(), filter, consumerConfiguration, new Handler<AsyncResult<io.vertx.ext.jdbc.JDBCClient>>() {
      public void handle(AsyncResult<io.vertx.ext.jdbc.JDBCClient> ar) {
        if (ar.succeeded()) {
          resultHandler.handle(io.vertx.core.Future.succeededFuture(JDBCClient.newInstance(ar.result())));
        } else {
          resultHandler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
  }

  /**
   * Convenient method that looks for a JDBC datasource source and provides the configured {@link io.vertx.rxjava.ext.jdbc.JDBCClient}. The
   * async result is marked as failed is there are no matching services, or if the lookup fails.
   * @param discovery The discovery service
   * @param filter The filter, optional
   * @param consumerConfiguration the consumer configuration
   * @return 
   */
  public static Observable<JDBCClient> getJDBCClientObservable(DiscoveryService discovery, JsonObject filter, JsonObject consumerConfiguration) { 
    io.vertx.rx.java.ObservableFuture<JDBCClient> resultHandler = io.vertx.rx.java.RxHelper.observableFuture();
    getJDBCClient(discovery, filter, consumerConfiguration, resultHandler.toHandler());
    return resultHandler;
  }


  public static JDBCDataSource newInstance(io.vertx.ext.discovery.types.JDBCDataSource arg) {
    return arg != null ? new JDBCDataSource(arg) : null;
  }
}
