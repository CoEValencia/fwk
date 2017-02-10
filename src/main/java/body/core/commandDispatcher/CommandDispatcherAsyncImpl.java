package body.core.commandDispatcher;

import java.util.ArrayList;
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

    @Override
    public CommandDispatcherAsyncImpl<T> add(String command, List<CompletableFuture<T>> fun) {
        fun.stream()
            .filter(it -> it != null)
            .forEach(it -> {
                  add(command, it);
              });

        return this;
    }

    public CommandDispatcherAsyncImpl add(String command, CompletableFuture<T> fun) {
        final List<CompletableFuture<T>> listFun = map.get(command);
        if (null != listFun) {
            if (!listFun.contains(fun))
                listFun.add(fun);
        }
        else
            map.put(command, new ArrayList() {{add(fun);}} );

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

    public CommandDispatcherAsyncImpl remove(String command) {
        map.remove(command);
        return this;
    }

    public CommandDispatcherAsyncImpl<T> dispatch(String command) {
        final List<CompletableFuture<T>> listFun = map.get(command);

        if (null != listFun){
            for(int i = 0; i< listFun.size(); i++) {
                final int[] res = {i};
                executeSupply(listFun.get(res[0]));
            }
        }

        return this;
    }

    public int size() { return this.map.size(); }

}