# Thread
Java allows multi-threads programming.  This project introduces simple examples using threads.

# Packages
## `example0`
This introduces a simplest example of using the `Runnable` interface.
- `Example.java`: An example of classes to be runnable as a thread.
- `ExampleRunnable.java`: Implementing `Runnable` interface to `Example` class.
- `ExampleWithThread.java`:  The `Example` class is instantiated within an anonymous implementation of `Runnable` interface and is executed as a thread.
- `ChekingAlive.java`: This example generates $n$ threads in a `ThreadGroup`.  
After starting threads,  the number of running threads are monitord.
## `example1`
This shows a simple example of asynchronous communication between clients and a server.
- `Server.java`: This server simply records connections from clients.
- `Client.java`: Clients asynchronously connect to the server.
## `example2`
In this example, clients and a server asynchronously exchange tokens.
- `Server.java`: The server manages a limited number of tokens for allowing connections from clients.
- `Client.java`: A client returns a token if it has and gets a new token from the server.
- `Token.java`: A simple token class defined as an example of the `record` class.
## `waitAndNotify`
This  provides a `wait` and `notify` example.
- `Target.java`: A target class running in a thread and **wait** the notification.
- `Main.java`: A main class running ina thread and  **notify** running thread to wake up the target instances.