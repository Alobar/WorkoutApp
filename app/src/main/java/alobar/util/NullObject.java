package alobar.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory for Null Objects
 */
@SuppressWarnings("unchecked")
public class NullObject {

    private static Map<Class, Object> cache = new HashMap<>();

    /**
     * Returns an instance of a Null Object for the specified type.
     *
     * @param clazz The required type
     * @param <T>   The type of the required type
     * @return The singleton Null Object of the specified type
     */
    public static <T> T get(Class<T> clazz) {
        T result = (T) cache.get(clazz);
        if (result == null) {
            result = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new DeepNullInvocation(clazz));
            cache.put(clazz, result);
        }
        return result;
    }

    private static class DeepNullInvocation implements InvocationHandler {

        private final String name;

        public DeepNullInvocation(Class clazz) {
            this.name = clazz.getSimpleName();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("toString"))
                return String.format("%s { Null }", name);

            if (method.getName().equals("equals"))
                return args[0] == proxy;

            Class returnType = method.getReturnType();
            if (returnType.isPrimitive())
                return DefaultValue.get(returnType);

            if (returnType.isInterface())
                return get(returnType);

            return null;
        }
    }

}
