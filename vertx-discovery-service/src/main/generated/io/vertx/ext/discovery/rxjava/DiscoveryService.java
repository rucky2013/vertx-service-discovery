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

package io.vertx.ext.discovery.rxjava;

import java.util.Map;
import rx.Observable;
import io.vertx.ext.discovery.rxjava.spi.DiscoveryBridge;
import io.vertx.rxjava.core.Vertx;
import io.vertx.ext.discovery.Record;
import java.util.Set;
import java.util.function.Function;
import java.util.List;
import io.vertx.ext.discovery.DiscoveryOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Discovery service main entry point.
 * <p>
 * The discovery service is an infrastructure that let you publish and find `services`. A `service` is a discoverable
 * functionality. It can be qualified by its type, metadata, and location. So a `service` can be a database, a
 * service proxy, a HTTP endpoint. It does not have to be a vert.x entity, but can be anything. Each service is
 * described by a {@link io.vertx.ext.discovery.Record}.
 * <p>
 * The discovery service implements the interactions defined in the service-oriented computing. And to some extend,
 * also provides the dynamic service-oriented computing interaction. So, application can react to arrival and
 * departure of services.
 * <p>
 * A service provider can:
 * <p>
 * * publish a service record
 * * un-publish a published record
 * * update the status of a published service (down, out of service...)
 * <p>
 * A service consumer can:
 * <p>
 * * lookup for services
 * * bind to a selected service (it gets a {@link io.vertx.ext.discovery.rxjava.ServiceReference}) and use it
 * * release the service once the consumer is done with it
 * * listen for arrival, departure and modification of services.
 * <p>
 * Consumer would 1) lookup for service record matching their need, 2) retrieve the {@link io.vertx.ext.discovery.rxjava.ServiceReference} that give access
 * to the service, 3) get a service object to access the service, 4) release the service object once done.
 * <p>
 * A state above, the central piece of information shared by the providers and consumers are {@link io.vertx.ext.discovery.Record}.
 * <p>
 * Providers and consumers must create their own {@link io.vertx.ext.discovery.rxjava.DiscoveryService} instance. These instances are collaborating
 * in background (distributed structure) to keep the set of services in sync.
 *
 * <p/>
 * NOTE: This class has been automatically generated from the {@link io.vertx.ext.discovery.DiscoveryService original} non RX-ified interface using Vert.x codegen.
 */

public class DiscoveryService {

  final io.vertx.ext.discovery.DiscoveryService delegate;

  public DiscoveryService(io.vertx.ext.discovery.DiscoveryService delegate) {
    this.delegate = delegate;
  }

  public Object getDelegate() {
    return delegate;
  }

  /**
   * Creates an instance of {@link io.vertx.ext.discovery.rxjava.DiscoveryService}.
   * @param vertx the vert.x instance
   * @param options the discovery options
   * @return the create discovery service.
   */
  public static DiscoveryService create(Vertx vertx, DiscoveryOptions options) { 
    DiscoveryService ret = DiscoveryService.newInstance(io.vertx.ext.discovery.DiscoveryService.create((io.vertx.core.Vertx)vertx.getDelegate(), options));
    return ret;
  }

  /**
   * Creates a new instance of {@link io.vertx.ext.discovery.rxjava.DiscoveryService} using the default configuration.
   * @param vertx the vert.x instance
   * @return the created instance
   */
  public static DiscoveryService create(Vertx vertx) { 
    DiscoveryService ret = DiscoveryService.newInstance(io.vertx.ext.discovery.DiscoveryService.create((io.vertx.core.Vertx)vertx.getDelegate()));
    return ret;
  }

  /**
   * Gets a service reference from the given record.
   * @param record the chosen record
   * @return the service reference, that allows retrieving the service object. Once called the service reference is cached, and need to be released.
   */
  public ServiceReference getReference(Record record) { 
    ServiceReference ret = ServiceReference.newInstance(delegate.getReference(record));
    return ret;
  }

  /**
   * Gets a service reference from the given record, the reference is configured with the given json object.
   * @param record the chosen record
   * @param configuration the configuration
   * @return the service reference, that allows retrieving the service object. Once called the service reference is cached, and need to be released.
   */
  public ServiceReference getReferenceWithConfiguration(Record record, JsonObject configuration) { 
    ServiceReference ret = ServiceReference.newInstance(delegate.getReferenceWithConfiguration(record, configuration));
    return ret;
  }

  /**
   * Releases the service reference.
   * @param reference the reference to release, must not be <code>null</code>
   * @return whether or not the reference has been released.
   */
  public boolean release(ServiceReference reference) { 
    boolean ret = delegate.release((io.vertx.ext.discovery.ServiceReference)reference.getDelegate());
    return ret;
  }

  /**
   * Registers a discovery bridge. Bridges let you integrate other discovery technologies in this discovery service.
   * @param bridge the bridge
   * @param configuration the optional configuration
   * @return the current {@link io.vertx.ext.discovery.rxjava.DiscoveryService}
   */
  public DiscoveryService registerDiscoveryBridge(DiscoveryBridge bridge, JsonObject configuration) { 
    DiscoveryService ret = DiscoveryService.newInstance(delegate.registerDiscoveryBridge((io.vertx.ext.discovery.spi.DiscoveryBridge)bridge.getDelegate(), configuration));
    return ret;
  }

  /**
   * Closes the discovery service
   */
  public void close() { 
    delegate.close();
  }

  /**
   * Publishes a record.
   * @param record the record
   * @param resultHandler handler called when the operation has completed (successfully or not). In case of success, the passed record has a registration id required to modify and un-register the service.
   */
  public void publish(Record record, Handler<AsyncResult<Record>> resultHandler) { 
    delegate.publish(record, resultHandler);
  }

  /**
   * Publishes a record.
   * @param record the record
   * @return 
   */
  public Observable<Record> publishObservable(Record record) { 
    io.vertx.rx.java.ObservableFuture<Record> resultHandler = io.vertx.rx.java.RxHelper.observableFuture();
    publish(record, resultHandler.toHandler());
    return resultHandler;
  }

  /**
   * Un-publishes a record.
   * @param id the registration id
   * @param resultHandler handler called when the operation has completed (successfully or not).
   */
  public void unpublish(String id, Handler<AsyncResult<Void>> resultHandler) { 
    delegate.unpublish(id, new Handler<AsyncResult<java.lang.Void>>() {
      public void handle(AsyncResult<java.lang.Void> ar) {
        if (ar.succeeded()) {
          resultHandler.handle(io.vertx.core.Future.succeededFuture(ar.result()));
        } else {
          resultHandler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    });
  }

  /**
   * Un-publishes a record.
   * @param id the registration id
   * @return 
   */
  public Observable<Void> unpublishObservable(String id) { 
    io.vertx.rx.java.ObservableFuture<Void> resultHandler = io.vertx.rx.java.RxHelper.observableFuture();
    unpublish(id, resultHandler.toHandler());
    return resultHandler;
  }

  /**
   * Lookups for a single record.
   * <p>
   * Filters are expressed using a Json object. Each entry of the given filter will be checked against the record.
   * All entry must match exactly the record. The entry can use the special "*" value to denotes a requirement on the
   * key, but not on the value.
   * <p>
   * Let's take some example:
   * <pre>
   *   { "name" = "a" } => matches records with name set fo "a"
   *   { "color" = "*" } => matches records with "color" set
   *   { "color" = "red" } => only matches records with "color" set to "red"
   *   { "color" = "red", "name" = "a"} => only matches records with name set to "a", and color set to "red"
   * </pre>
   * <p>
   * If the filter is not set (<code>null</code> or empty), it accepts all records.
   * <p>
   * This method returns the first matching record.
   * @param filter the filter.
   * @param resultHandler handler called when the lookup has been completed. When there are no matching record, the operation succeed, but the async result has no result.
   */
  public void getRecord(JsonObject filter, Handler<AsyncResult<Record>> resultHandler) { 
    delegate.getRecord(filter, resultHandler);
  }

  /**
   * Lookups for a single record.
   * <p>
   * Filters are expressed using a Json object. Each entry of the given filter will be checked against the record.
   * All entry must match exactly the record. The entry can use the special "*" value to denotes a requirement on the
   * key, but not on the value.
   * <p>
   * Let's take some example:
   * <pre>
   *   { "name" = "a" } => matches records with name set fo "a"
   *   { "color" = "*" } => matches records with "color" set
   *   { "color" = "red" } => only matches records with "color" set to "red"
   *   { "color" = "red", "name" = "a"} => only matches records with name set to "a", and color set to "red"
   * </pre>
   * <p>
   * If the filter is not set (<code>null</code> or empty), it accepts all records.
   * <p>
   * This method returns the first matching record.
   * @param filter the filter.
   * @return 
   */
  public Observable<Record> getRecordObservable(JsonObject filter) { 
    io.vertx.rx.java.ObservableFuture<Record> resultHandler = io.vertx.rx.java.RxHelper.observableFuture();
    getRecord(filter, resultHandler.toHandler());
    return resultHandler;
  }

  /**
   * Lookups for a single record.
   * <p>
   * The filter is a  taking a {@link io.vertx.ext.discovery.Record} as argument and returning a boolean. You should see it
   * as an <code>accept</code> method of a filter. This method return a record passing the filter.
   * <p>
   * This method only looks for records with a <code>UP</code> status.
   * @param filter the filter, must not be <code>null</code>. To return all records, use a function accepting all records
   * @param resultHandler the result handler called when the lookup has been completed. When there are no matching record, the operation succeed, but the async result has no result.
   */
  public void getRecord(Function<Record,Boolean> filter, Handler<AsyncResult<Record>> resultHandler) { 
    delegate.getRecord(new java.util.function.Function<io.vertx.ext.discovery.Record,java.lang.Boolean>() {
      public java.lang.Boolean apply(io.vertx.ext.discovery.Record arg) {
        Boolean ret = filter.apply(arg);
        return ret;
      }
    }, resultHandler);
  }

  /**
   * Lookups for a single record.
   * <p>
   * The filter is a  taking a {@link io.vertx.ext.discovery.Record} as argument and returning a boolean. You should see it
   * as an <code>accept</code> method of a filter. This method return a record passing the filter.
   * <p>
   * This method only looks for records with a <code>UP</code> status.
   * @param filter the filter, must not be <code>null</code>. To return all records, use a function accepting all records
   * @return 
   */
  public Observable<Record> getRecordObservable(Function<Record,Boolean> filter) { 
    io.vertx.rx.java.ObservableFuture<Record> resultHandler = io.vertx.rx.java.RxHelper.observableFuture();
    getRecord(filter, resultHandler.toHandler());
    return resultHandler;
  }

  /**
   * Lookups for a single record.
   * <p>
   * The filter is a  taking a {@link io.vertx.ext.discovery.Record} as argument and returning a boolean. You should see it
   * as an <code>accept</code> method of a filter. This method return a record passing the filter.
   * <p>
   * Unlike {@link io.vertx.ext.discovery.rxjava.DiscoveryService#getRecord}, this method may accept records with a <code>OUT OF SERVICE</code>
   * status, if the <code>includeOutOfService</code> parameter is set to <code>true</code>.
   * @param filter the filter, must not be <code>null</code>. To return all records, use a function accepting all records
   * @param includeOutOfService whether or not the filter accepts <code>OUT OF SERVICE</code> records
   * @param resultHandler the result handler called when the lookup has been completed. When there are no matching record, the operation succeed, but the async result has no result.
   */
  public void getRecord(Function<Record,Boolean> filter, boolean includeOutOfService, Handler<AsyncResult<Record>> resultHandler) { 
    delegate.getRecord(new java.util.function.Function<io.vertx.ext.discovery.Record,java.lang.Boolean>() {
      public java.lang.Boolean apply(io.vertx.ext.discovery.Record arg) {
        Boolean ret = filter.apply(arg);
        return ret;
      }
    }, includeOutOfService, resultHandler);
  }

  /**
   * Lookups for a single record.
   * <p>
   * The filter is a  taking a {@link io.vertx.ext.discovery.Record} as argument and returning a boolean. You should see it
   * as an <code>accept</code> method of a filter. This method return a record passing the filter.
   * <p>
   * Unlike {@link io.vertx.ext.discovery.rxjava.DiscoveryService#getRecord}, this method may accept records with a <code>OUT OF SERVICE</code>
   * status, if the <code>includeOutOfService</code> parameter is set to <code>true</code>.
   * @param filter the filter, must not be <code>null</code>. To return all records, use a function accepting all records
   * @param includeOutOfService whether or not the filter accepts <code>OUT OF SERVICE</code> records
   * @return 
   */
  public Observable<Record> getRecordObservable(Function<Record,Boolean> filter, boolean includeOutOfService) { 
    io.vertx.rx.java.ObservableFuture<Record> resultHandler = io.vertx.rx.java.RxHelper.observableFuture();
    getRecord(filter, includeOutOfService, resultHandler.toHandler());
    return resultHandler;
  }

  /**
   * Lookups for a set of records. Unlike {@link io.vertx.ext.discovery.rxjava.DiscoveryService#getRecord}, this method returns all matching
   * records.
   * @param filter the filter - see {@link io.vertx.ext.discovery.rxjava.DiscoveryService#getRecord}
   * @param resultHandler handler called when the lookup has been completed. When there are no matching record, the operation succeed, but the async result has an empty list as result.
   */
  public void getRecords(JsonObject filter, Handler<AsyncResult<List<Record>>> resultHandler) { 
    delegate.getRecords(filter, resultHandler);
  }

  /**
   * Lookups for a set of records. Unlike {@link io.vertx.ext.discovery.rxjava.DiscoveryService#getRecord}, this method returns all matching
   * records.
   * @param filter the filter - see {@link io.vertx.ext.discovery.rxjava.DiscoveryService#getRecord}
   * @return 
   */
  public Observable<List<Record>> getRecordsObservable(JsonObject filter) { 
    io.vertx.rx.java.ObservableFuture<List<Record>> resultHandler = io.vertx.rx.java.RxHelper.observableFuture();
    getRecords(filter, resultHandler.toHandler());
    return resultHandler;
  }

  /**
   * Lookups for a set of records. Unlike {@link io.vertx.ext.discovery.rxjava.DiscoveryService#getRecord}, this method returns all matching
   * records.
   * <p>
   * The filter is a  taking a {@link io.vertx.ext.discovery.Record} as argument and returning a boolean. You should see it
   * as an <code>accept</code> method of a filter. This method return a record passing the filter.
   * <p>
   * This method only looks for records with a <code>UP</code> status.
   * @param filter the filter, must not be <code>null</code>. To return all records, use a function accepting all records
   * @param resultHandler handler called when the lookup has been completed. When there are no matching record, the operation succeed, but the async result has an empty list as result.
   */
  public void getRecords(Function<Record,Boolean> filter, Handler<AsyncResult<List<Record>>> resultHandler) { 
    delegate.getRecords(new java.util.function.Function<io.vertx.ext.discovery.Record,java.lang.Boolean>() {
      public java.lang.Boolean apply(io.vertx.ext.discovery.Record arg) {
        Boolean ret = filter.apply(arg);
        return ret;
      }
    }, resultHandler);
  }

  /**
   * Lookups for a set of records. Unlike {@link io.vertx.ext.discovery.rxjava.DiscoveryService#getRecord}, this method returns all matching
   * records.
   * <p>
   * The filter is a  taking a {@link io.vertx.ext.discovery.Record} as argument and returning a boolean. You should see it
   * as an <code>accept</code> method of a filter. This method return a record passing the filter.
   * <p>
   * This method only looks for records with a <code>UP</code> status.
   * @param filter the filter, must not be <code>null</code>. To return all records, use a function accepting all records
   * @return 
   */
  public Observable<List<Record>> getRecordsObservable(Function<Record,Boolean> filter) { 
    io.vertx.rx.java.ObservableFuture<List<Record>> resultHandler = io.vertx.rx.java.RxHelper.observableFuture();
    getRecords(filter, resultHandler.toHandler());
    return resultHandler;
  }

  /**
   * Lookups for a set of records. Unlike {@link io.vertx.ext.discovery.rxjava.DiscoveryService#getRecord}, this method returns all matching
   * records.
   * <p>
   * The filter is a  taking a {@link io.vertx.ext.discovery.Record} as argument and returning a boolean. You should see it
   * as an <code>accept</code> method of a filter. This method return a record passing the filter.
   * <p>
   * Unlike {@link io.vertx.ext.discovery.rxjava.DiscoveryService#getRecords}, this method may accept records with a <code>OUT OF SERVICE</code>
   * status, if the <code>includeOutOfService</code> parameter is set to <code>true</code>.
   * @param filter the filter, must not be <code>null</code>. To return all records, use a function accepting all records
   * @param includeOutOfService whether or not the filter accepts <code>OUT OF SERVICE</code> records
   * @param resultHandler handler called when the lookup has been completed. When there are no matching record, the operation succeed, but the async result has an empty list as result.
   */
  public void getRecords(Function<Record,Boolean> filter, boolean includeOutOfService, Handler<AsyncResult<List<Record>>> resultHandler) { 
    delegate.getRecords(new java.util.function.Function<io.vertx.ext.discovery.Record,java.lang.Boolean>() {
      public java.lang.Boolean apply(io.vertx.ext.discovery.Record arg) {
        Boolean ret = filter.apply(arg);
        return ret;
      }
    }, includeOutOfService, resultHandler);
  }

  /**
   * Lookups for a set of records. Unlike {@link io.vertx.ext.discovery.rxjava.DiscoveryService#getRecord}, this method returns all matching
   * records.
   * <p>
   * The filter is a  taking a {@link io.vertx.ext.discovery.Record} as argument and returning a boolean. You should see it
   * as an <code>accept</code> method of a filter. This method return a record passing the filter.
   * <p>
   * Unlike {@link io.vertx.ext.discovery.rxjava.DiscoveryService#getRecords}, this method may accept records with a <code>OUT OF SERVICE</code>
   * status, if the <code>includeOutOfService</code> parameter is set to <code>true</code>.
   * @param filter the filter, must not be <code>null</code>. To return all records, use a function accepting all records
   * @param includeOutOfService whether or not the filter accepts <code>OUT OF SERVICE</code> records
   * @return 
   */
  public Observable<List<Record>> getRecordsObservable(Function<Record,Boolean> filter, boolean includeOutOfService) { 
    io.vertx.rx.java.ObservableFuture<List<Record>> resultHandler = io.vertx.rx.java.RxHelper.observableFuture();
    getRecords(filter, includeOutOfService, resultHandler.toHandler());
    return resultHandler;
  }

  /**
   * Updates the given record. The record must has been published, and has it's registration id set.
   * @param record the updated record
   * @param resultHandler handler called when the lookup has been completed.
   */
  public void update(Record record, Handler<AsyncResult<Record>> resultHandler) { 
    delegate.update(record, resultHandler);
  }

  /**
   * Updates the given record. The record must has been published, and has it's registration id set.
   * @param record the updated record
   * @return 
   */
  public Observable<Record> updateObservable(Record record) { 
    io.vertx.rx.java.ObservableFuture<Record> resultHandler = io.vertx.rx.java.RxHelper.observableFuture();
    update(record, resultHandler.toHandler());
    return resultHandler;
  }

  /**
   * @return the set of service references retrieved by this discovery service.
   * @return 
   */
  public Set<ServiceReference> bindings() { 
    Set<ServiceReference> ret = delegate.bindings().stream().map(elt -> ServiceReference.newInstance(elt)).collect(java.util.stream.Collectors.toSet());
    return ret;
  }

  /**
   * Release the service object retrieved using <code>get</code> methods from the service type interface.
   * It searches for the reference associated with the given object and release it.
   * @param discovery the discovery service
   * @param svcObject the service object
   */
  public static void releaseServiceObject(DiscoveryService discovery, Object svcObject) { 
    io.vertx.ext.discovery.DiscoveryService.releaseServiceObject((io.vertx.ext.discovery.DiscoveryService)discovery.getDelegate(), svcObject);
  }


  public static DiscoveryService newInstance(io.vertx.ext.discovery.DiscoveryService arg) {
    return arg != null ? new DiscoveryService(arg) : null;
  }
}
