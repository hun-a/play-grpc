package io.huna.helloworld


import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder

fun main(args: Array<String>) {
    val channel: ManagedChannel = ManagedChannelBuilder.forAddress("localhost", 50051)
        .usePlaintext()
        .build()

    val stub = GreeterGrpc.newBlockingStub(channel)

    val request = HelloRequest.newBuilder().setName("World").build()
    val response = stub.sayHello(request)

    println("Received: ${response.message}")

    channel.shutdown()
}
