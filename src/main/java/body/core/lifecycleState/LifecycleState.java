package body.core.lifecycleState;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public interface LifecycleState {

    public static final String NO_STATE = "no_state";

    public static final String INITIALIZE = "initialize";

    public static final String DESTROY = "destroy";

    public static final String RESUME = "resume";

    public static final String CONSTRUCTOR = "constructor" ;
}
