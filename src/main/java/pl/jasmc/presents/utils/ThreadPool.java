package pl.jasmc.presents.utils;

import org.bukkit.Bukkit;
import pl.jasmc.presents.Presents;

import java.util.concurrent.ExecutorService;

public class ThreadPool {

    private static ExecutorService pool = new CachedThreadPoolErrorReporting();

    public static void runTaskAsync(Runnable run) {
        pool.submit(run);
    }
}

//ASYNC POBIERANIE:
/*

ThreadPool.runTaskAsync


 */