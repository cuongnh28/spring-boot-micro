package com.demo.context;

import com.demo.config.thread.CommonContext;
import com.demo.statics.DeviceType;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.core.NamedThreadLocal;

/**
 * Holder class to expose the web request in the form of a thread-bound {@link CommonContext} object. The request will be
 * inherited by any child threads spawned by the current thread if the {@code inheritable} flag is set to {@code true}.
 *
 * @author Robin Zhou
 */
public abstract class CommonContextHolder {

    private static final ThreadLocal<CommonContext> COMMON_CONTEXT_HOLDER =
            new NamedThreadLocal<>("Common context");

    private static final ThreadLocal<CommonContext> INHERITABLE_COMMON_CONTEXT_HOLDER =
            new NamedInheritableThreadLocal<>("Common context");

    /**
     * Reset the CommonContext for the current thread.
     */
    public static void resetContext() {
        COMMON_CONTEXT_HOLDER.remove();
        INHERITABLE_COMMON_CONTEXT_HOLDER.remove();
    }

    /**
     * Bind the given CommonContext to the current thread,
     * <i>not</i> exposing it as inheritable for child threads.
     *
     * @param context the CommonContext to expose
     */
    public static void setContext(@Nullable CommonContext context) {
        setContext(context, false);
    }

    /**
     * Bind the given CommonContext to the current thread.
     *
     * @param context the CommonContext to expose, or {@code null} to reset the thread-bound context
     * @param inheritable whether to expose the CommonContext as inheritable for child threads (using an
     *     {@link InheritableThreadLocal})
     */
    public static void setContext(@Nullable CommonContext context, boolean inheritable) {
        if (context == null) {
            resetContext();
        } else {
            if (inheritable) {
                INHERITABLE_COMMON_CONTEXT_HOLDER.set(context);
                COMMON_CONTEXT_HOLDER.remove();
            } else {
                COMMON_CONTEXT_HOLDER.set(context);
                INHERITABLE_COMMON_CONTEXT_HOLDER.remove();
            }
        }
    }

    /**
     * Return the CommonContext currently bound to the thread.
     *
     * @return the CommonContext currently bound to the thread, or {@code null} if none bound
     */
    @Nullable
    public static CommonContext getContext() {
        CommonContext context = COMMON_CONTEXT_HOLDER.get();
        if (context == null) {
            context = INHERITABLE_COMMON_CONTEXT_HOLDER.get();
        }
        return context;
    }

    public static String getAccountId() {
        if (ObjectUtils.isEmpty(getContext())) {
//            throw new BaseException(HttpStatus.UNAUTHORIZED, ResponseCode.COMMON_UNAUTHORIZED);
        }
        return getContext().getAccountId();
    }

    public static String getClientId() {
        if (ObjectUtils.isEmpty(getContext())) {
//            throw new Exception(HttpStatus.UNAUTHORIZED, ResponseCode.COMMON_UNAUTHORIZED);
        }
        return getContext().getClientId();
    }

    public static DeviceType getDeviceType() {
        // TODO throw exception if empty
        return getContext().getDeviceType();
    }

    public static String getRequestId() {
        if (ObjectUtils.isEmpty(getContext())) {
            return null;
        }
        return getContext().getRequestId();
    }
}
