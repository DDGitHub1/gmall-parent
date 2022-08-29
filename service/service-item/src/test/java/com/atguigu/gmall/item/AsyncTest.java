package com.atguigu.gmall.item;

import java.util.concurrent.*;

/**
 * @Author : dyh
 * @Date: 2022/8/29
 * @Description : com.atguigu.gmall.item
 * @Version : 1.0
 */
public class AsyncTest {
    static ExecutorService executor = Executors.newFixedThreadPool(4);

    public static void main(String[] args) throws Exception {
        System.out.println(Thread.currentThread().getName()+": 主线程开始");
        /**
         * 、启动异步任务
         * runAsync();
         *    1）、CompletableFuture.runAsync(Runnable) 返回 CompletableFuture<Void>;
         *             使用默认线程池(ForkJoinPool)，启动一个Runnable任务进行执行，没有返回结果
         *    2）、CompletableFuture.runAsync(Runnable,指定线程池) 返回 CompletableFuture<Void>;
         *             使用指定线程池，启动一个Runnable任务进行执行，没有返回结果
         * supplyAsync();
         *    1）、CompletableFuture.supplyAsync(Runnable) 返回 CompletableFuture<Integer>
         *             使用默认线程池(ForkJoinPool)，启动一个Runnable任务进行执行，有返回结果
         *    2）、CompletableFuture.supplyAsync(Runnable,指定线程池)
         *             使用指定线程池，启动一个Runnable任务进行执行，有返回结果
         */
        //启动异步任务
//        CompletableFuture.runAsync(()->{
//            System.out.println(Thread.currentThread().getName()+"哈哈");
//        },executor);
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("haha");
            return 2; //底层callable
        });
        System.out.println("上次异步结果");
        Integer integer = future.get();
        future.get(1, TimeUnit.MINUTES); //现实等待
        System.out.println(integer);
    }
    /**
     * thenXXX编排任务
     *
     * CompletableFuture<Void>
     * CompletableFuture<Integer>
     *
     *     1、thenRun()\thenRunAsync() future.thenXXXX() ： 接下来干什么 CompletableFuture<Void>
     *          thenRun(runnable)：              不用异步跑任务，而是用主线程
     *          thenRunAsync(runnable):          用异步跑任务，使用默认ForkJoin线程池
     *          thenRunAsync(runnable,executor)
     *        接下来干活用不到上一步的结果，自己运行完也不返回任何结果 CompletableFuture<Void>
     *     2、thenAccept()\thenAcceptAsync()： CompletableFuture<Void>
     *          thenAccept(consumer):       拿到上一步结果，不用异步跑任务，而是用主线程
     *          thenAcceptAsync(consumer):  拿到上一步结果，用异步跑任务，使用默认ForkJoin线程池
     *          thenAcceptAsync(consumer,executor)  拿到上一步结果，用异步跑任务，使用指定线程池
     *     3、thenApply()\thenApplyAsync()：  拿到上一步结果，还能自己返回新结果
     *          thenApply(function)：        拿到上一步结果，不用异步跑任务，而是用主线程，并返回自己的计算结果
     *          thenApplyAsync(function)：   拿到上一步结果，用异步跑任务，用默认线程池，并返回自己的计算结果
     *          thenApplyAsync(function,executor)： 拿到上一步结果，用异步跑任务，用指定线程池，并返回自己的计算结果
     *
     *  thenRun： 不接收上一次结果，无返回值
     *  thenAccept：接收上一次结果，无返回值
     *  thenApply： 接收上一次结果，有返回值
     * @param args
     */
    public static void thenXXX(String[] args) throws Exception {
        //1、1+1   2+3   +5
        //    +3
        CompletableFuture.supplyAsync(()->{
            return 2;
        }).thenApplyAsync((t)->{
            return t+3;
        },executor).thenApply((t)->{
            return t*6;
        }).thenAcceptAsync((t)->{
            System.out.println("把"+t+"保存到数据库");
        }).whenComplete((t,u)->{
            if(u!=null){
                //记录日志
            }
            System.out.println("执行结束，记录日志");
        });





        Thread.sleep(1000000L);
    }
}
