



异步任务


异步请求实现方式

callable
DeferredResult
WebAsyncTask


能做到实时进度吗


异步调用

https://www.ibm.com/developerworks/cn/java/j-lo-taskschedule/index.html
https://www.cnblogs.com/javanoob/p/springboot_schedule.html


```Java
CompletableFuture<DataResultVO> future = CompletableFuture.supplyAsync(
        new Supplier<DataResultVO>() {
            @Override
            public DataResultVO get() {
                return docFacade.download(model);
            }
        }
        , executor);

DataResultVO vo = future.get();
```