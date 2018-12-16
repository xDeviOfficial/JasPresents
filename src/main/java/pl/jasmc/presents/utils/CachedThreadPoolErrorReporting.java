package pl.jasmc.presents.utils;

import org.bukkit.Bukkit;
import pl.jasmc.presents.Presents;

import java.util.concurrent.*;

public class CachedThreadPoolErrorReporting extends ThreadPoolExecutor {
    public CachedThreadPoolErrorReporting() {
        super(0, Integer.MAX_VALUE,
                60L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<Runnable>());
    }

    @Override
    protected void afterExecute(Runnable task, Throwable thrown) {
        super.afterExecute(task, thrown);
        if (thrown != null) {
            thrown.printStackTrace();
        }
        if (task instanceof Future<?>) {
            try {
                @SuppressWarnings("unused")
                Object result = ((Future<?>)task).get();
            } catch (CancellationException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

