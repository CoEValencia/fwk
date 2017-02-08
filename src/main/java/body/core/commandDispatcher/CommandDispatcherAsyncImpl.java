package body.core.commandDispatcher;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vicboma on 08/02/17.
 */
public class CommandDispatcherAsyncImpl<T> implements CommandDispatcherAsync<T> {

    private Map<String, List<CompletableFuture<T>>> map = new ConcurrentHashMap();

    public static <J> CommandDispatcherAsync<J> create() {
        return new CommandDispatcherAsyncImpl<J>();
    }

    CommandDispatcherAsyncImpl() {

    }

    public CommandDispatcherAsyncImpl add(String command, CompletableFuture<T> fun) {
        final List<CompletableFuture<T>> listFun = map.get(command);
        if (null != listFun)
            if (listFun.indexOf(fun) == -1)
                listFun.add(fun);
            else
                map.put(command, Arrays.asList(fun));

        return this;
    }

    public Boolean contains(String command) {
        return map.containsKey(command);
    }

    public CommandDispatcherAsyncImpl remove(String command, CompletableFuture<T> fun) {
        final List<CompletableFuture<T>> listFun = map.get(command);
        final int index = (null != listFun) ? listFun.indexOf(fun) : -1;
        if (index != -1) {

            final CompletableFuture<T> completableFuture = listFun.remove(index);
            completableFuture.cancel(true);

            if (listFun.size() == 0)
                listFun.clear();
        }

        return this;
    }

    public CompletableFuture dispatch(String command, CompletableFuture<T> fun) {
        CompletableFuture res = CompletableFuture.completedFuture(true);
        final List<CompletableFuture<T>> listFun = map.get(command);

        if (null != listFun)
            res = CompletableFuture.allOf((CompletableFuture<?>[]) listFun.toArray());

        return res;
    }

}