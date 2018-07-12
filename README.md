# Vaadin large table example
This is basically the [tutorial that walks you through the first steps of Vaadin](https://vaadin.com/docs/v10/flow/introduction/tutorial-get-started.html). 

I extended it to test how well Vaadin can deal with a large dataset in a Grid component that does lazy loading and no paging.

Over all it works quite well but if you play long enough with the search text field, the app will run into the following exception:

```
[qtp1235908203-16] ERROR com.vaadin.flow.server.DefaultErrorHandler - 
java.lang.IllegalArgumentException: Null values are not allowed for primitive types but a 'null' value was received for parameter 0 which refers to primitive type 'int' in the method 'setRequestedRange' defined in the class 'com.vaadin.flow.component.grid.Grid'
	at com.vaadin.flow.server.communication.rpc.PublishedServerEventHandlerRpcHandler.decodeArg(PublishedServerEventHandlerRpcHandler.java:241)
	at com.vaadin.flow.server.communication.rpc.PublishedServerEventHandlerRpcHandler.decodeArgs(PublishedServerEventHandlerRpcHandler.java:198)
	at com.vaadin.flow.server.communication.rpc.PublishedServerEventHandlerRpcHandler.invokeMethod(PublishedServerEventHandlerRpcHandler.java:152)
	at com.vaadin.flow.server.communication.rpc.PublishedServerEventHandlerRpcHandler.invokeMethod(PublishedServerEventHandlerRpcHandler.java:137)
	at com.vaadin.flow.server.communication.rpc.PublishedServerEventHandlerRpcHandler.handleNode(PublishedServerEventHandlerRpcHandler.java:116)
	at com.vaadin.flow.server.communication.rpc.AbstractRpcInvocationHandler.handle(AbstractRpcInvocationHandler.java:63)
	at com.vaadin.flow.server.communication.ServerRpcHandler.handleInvocationData(ServerRpcHandler.java:377)
	at com.vaadin.flow.server.communication.ServerRpcHandler.lambda$handleInvocations$0(ServerRpcHandler.java:367)
	at java.util.ArrayList.forEach(ArrayList.java:1257)
	at com.vaadin.flow.server.communication.ServerRpcHandler.handleInvocations(ServerRpcHandler.java:367)
	at com.vaadin.flow.server.communication.ServerRpcHandler.handleRpc(ServerRpcHandler.java:309)
	at com.vaadin.flow.server.communication.UidlRequestHandler.synchronizedHandleRequest(UidlRequestHandler.java:89)
	at com.vaadin.flow.server.SynchronizedRequestHandler.handleRequest(SynchronizedRequestHandler.java:40)
	at com.vaadin.flow.server.VaadinService.handleRequest(VaadinService.java:1493)
	at com.vaadin.flow.server.VaadinServlet.service(VaadinServlet.java:300)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:790)
	at org.eclipse.jetty.servlet.ServletHolder.handle(ServletHolder.java:835)
	at org.eclipse.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1685)
	at org.eclipse.jetty.websocket.server.WebSocketUpgradeFilter.doFilter(WebSocketUpgradeFilter.java:225)
	at org.eclipse.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1668)
	at org.eclipse.jetty.servlet.ServletHandler.doHandle(ServletHandler.java:581)
	at org.eclipse.jetty.server.handler.ScopedHandler.handle(ScopedHandler.java:143)
	at org.eclipse.jetty.security.SecurityHandler.handle(SecurityHandler.java:548)
	at org.eclipse.jetty.server.session.SessionHandler.doHandle(SessionHandler.java:226)
	at org.eclipse.jetty.server.handler.ContextHandler.doHandle(ContextHandler.java:1158)
	at org.eclipse.jetty.servlet.ServletHandler.doScope(ServletHandler.java:511)
	at org.eclipse.jetty.server.session.SessionHandler.doScope(SessionHandler.java:185)
	at org.eclipse.jetty.server.handler.ContextHandler.doScope(ContextHandler.java:1090)
	at org.eclipse.jetty.server.handler.ScopedHandler.handle(ScopedHandler.java:141)
	at org.eclipse.jetty.server.handler.ContextHandlerCollection.handle(ContextHandlerCollection.java:213)
	at org.eclipse.jetty.server.handler.HandlerCollection.handle(HandlerCollection.java:109)
	at org.eclipse.jetty.server.handler.HandlerWrapper.handle(HandlerWrapper.java:119)
	at org.eclipse.jetty.server.Server.handle(Server.java:517)
	at org.eclipse.jetty.server.HttpChannel.handle(HttpChannel.java:308)
	at org.eclipse.jetty.server.HttpConnection.onFillable(HttpConnection.java:242)
	at org.eclipse.jetty.io.AbstractConnection$ReadCallback.succeeded(AbstractConnection.java:273)
	at org.eclipse.jetty.io.FillInterest.fillable(FillInterest.java:95)
	at org.eclipse.jetty.io.SelectChannelEndPoint$2.run(SelectChannelEndPoint.java:75)
	at org.eclipse.jetty.util.thread.strategy.ExecuteProduceConsume.produceAndRun(ExecuteProduceConsume.java:213)
	at org.eclipse.jetty.util.thread.strategy.ExecuteProduceConsume.run(ExecuteProduceConsume.java:147)
	at org.eclipse.jetty.util.thread.QueuedThreadPool.runJob(QueuedThreadPool.java:654)
	at org.eclipse.jetty.util.thread.QueuedThreadPool$3.run(QueuedThreadPool.java:572)
	at java.lang.Thread.run(Thread.java:748)
```
