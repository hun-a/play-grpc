# Play gRPC
- How to implement gRPC simply using Kotlin

# Implementations

---
## Settings
- [build.gradle.kts](./build.gradle.kts)

### protobuf Block Explanation
The `protobuf` block in the `build.gradle.kts` file is used to configure the Protobuf compiler and related plugins. This block ensures that Protobuf files are correctly compiled into Java and Kotlin classes, including the gRPC classes needed for the server and client implementations.

1. `protoc` Configuration
   ```kotlin
   protoc {
   artifact = "com.google.protobuf:protoc:4.27.2"
   }
   ```
   - `protoc`: This specifies the Protobuf compiler to use.
   - `artifact`: This indicates the Maven artifact for the Protobuf compiler. The Protobuf compiler (protoc) is responsible for compiling `.proto` files into source code in various languages (Java, Kotlin, etc.).
 
2. `plugins` Configuration
This section defines additional plugins to be used by the Protobuf compiler. These plugins generate the necessary gRPC code in Java and Kotlin.

```kotlin
plugins {
    id("grpc") {
        artifact = "io.grpc:protoc-gen-grpc-java:1.65.1"
    }
    id("grpckt") {
        artifact = "io.grpc:protoc-gen-grpc-kotlin:1.4.1:jdk8@jar"
    }
}
```
- `id("grpc")`:
  - `artifact`: This specifies the Maven artifact for the gRPC Java code generator. Here, `io.grpc:protoc-gen-grpc-java:1.65.1` indicates version 1.65.1 of the gRPC Java plugin. This plugin generates the Java classes needed for gRPC, including the service base classes and client stubs.
- `id("grpckt")`:
  - `artifact`: This specifies the Maven artifact for the gRPC Kotlin code generator. Here, `io.grpc:protoc-gen-grpc-kotlin:1.4.1:jdk8@jar` indicates version 1.4.1 of the gRPC Kotlin plugin, compiled for JDK 8 (`jdk8@jar`). This plugin generates the Kotlin classes needed for gRPC.

4. `generateProtoTasks` Configuration
This section configures the tasks that generate source code from `.proto` files. It applies the specified plugins to all Protobuf generation tasks.

```kotlin
generateProtoTasks {
    all().forEach { task ->
        task.plugins {
            id("grpc")
            id("grpckt")
        }
    }
}
```
- `generateProtoTasks`: This block configures the tasks that generate code from Protobuf definitions.
   - `all().forEach { task -> ... }`: This iterates over all Protobuf generation tasks.
   - `task.plugins { ... }`: For each task, this block specifies the plugins to apply.
     - `id("grpc")`: Applies the gRPC Java plugin to generate Java gRPC classes.
     - `id("grpckt")`: Applies the gRPC Kotlin plugin to generate Kotlin gRPC classes.

--- 
## Protobuf
- [helloworld.proto](./src/main/proto/helloworld.proto)

### Naming Conventions for gRPC Generated Classes
The GreeterGrpc class name is automatically generated by the gRPC and Protobuf compiler, following specific naming conventions.

#### Naming Rules
1. `Service Name`: The base name comes from the service name defined in the Protobuf file. In the example, the service is defined as service Greeter.
2. `Suffix Addition`: The suffix Grpc is automatically appended to the service name, resulting in the final class name GreeterGrpc.

Thus, the class name generated for a gRPC service follows this pattern:

- Service Name + Grpc

This naming rule is consistently applied by the gRPC code generator.

#### Example
To better understand, let's look at another example:

Proto File Example:

```proto
syntax = "proto3";

package mypackage;

service MyService {
rpc MyMethod (MyRequest) returns (MyResponse) {}
}

message MyRequest {
string name = 1;
}

message MyResponse {
string message = 1;
}
```
In this example, a service named MyService is defined. When the gRPC code generator compiles this service, it generates the following classes:

- MyServiceGrpc: The class containing the gRPC server and client stubs.
- MyServiceGrpc.MyServiceBlockingStub: The blocking client stub.
- MyServiceGrpc.MyServiceFutureStub: The asynchronous client stub.
- MyServiceGrpc.MyServiceStub: The general client stub.
- Various other inner classes and interfaces.
 
#### Key Components of the Generated Class
The `GreeterGrpc` class includes the following key components:
- `Service Interface`: An abstract class named GreeterImplBase is included, which should be extended when implementing the server-side service.
- `Stub Classes`: The class includes client stubs for blocking, asynchronous, and general use.
- `Metadata`: Metadata such as service name, method names, and method types are included.
---
## Server
- [GreeterService.kt](./src/main/java/io/huna/helloworld/GreeterService.kt)
---
## Client
- [HelloWorldClient.tk](./src/main/java/io/huna/helloworld/HelloWorldClient.kt)
---