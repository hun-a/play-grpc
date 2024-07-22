package io.huna.helloworld

import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver

class GreeterService : GreeterGrpc.GreeterImplBase() {
    override fun sayHello(request: HelloRequest, responseObserver: StreamObserver<HelloReply>) {
        val reply = HelloReply.newBuilder().setMessage("Hello, ${request.name}").build()
        responseObserver.onNext(reply)
        responseObserver.onCompleted()
    }
}

fun main(args: Array<String>) {
    val server: Server = ServerBuilder
        .forPort(50051)
        .addService(GreeterService())
        .build()
        .start()

    println("Server started, listening on 50051")

    server.awaitTermination()
}
