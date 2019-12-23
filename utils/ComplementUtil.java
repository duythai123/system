package capstone_project.av_service.utils;



import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import capstone_project.av_service.controller.error.BussinessException;

public final class ComplementUtil {

    /** For getter. */
    public static final String PREFIX_GETTER = "get";

    /** For setter. */
    public static final String PREFIX_SETTER = "set";

    //***** Constructor *****
    /**
     * Default constructor.<br>
     */
    private ComplementUtil() {
    }


    public static void complement(final Object source, final Object destination){

        Method[] _dst = destination.getClass().getMethods();
        String _setterName;

        // All methods of destination class.
        for (int i = 0; i < _dst.length; i++) {

            // Pick up setter.
            _setterName = _dst[i].getName();
            if (_setterName.startsWith(PREFIX_SETTER)) {

                // Looking for getter @ source class.
                Method _getter = findMethod(source, PREFIX_GETTER + _setterName.substring(PREFIX_SETTER.length()));
                if (null != _getter) {

                    // Check parameter of setter and returnType of getter.
                    if (1 == _dst[i].getParameterTypes().length
                            && _dst[i].getParameterTypes()[0].getName().equals(_getter.getReturnType().getName())) {

                        // Execute get/set.
                        execSetter(destination, _dst[i], execGetter(source, _getter));
                    }
                }
            }
        }
    }

    public static Object execGetter(final Object target, final Method getter) {

        try {
            // Execute.
            return getter.invoke(target, (Object[]) null);

        } catch (IllegalAccessException e) {
            // In case of failed to invoke.
            throw new BussinessException("Failed to execute " + getter.getName() + "().");
        } catch (InvocationTargetException e) {
            // In case of failed to invoke.
            throw new BussinessException("Failed to execute " + getter.getName() + "().");
        }
    }


    public static void execSetter(final Object target, final Method setter, final Object value) throws BussinessException {

        try {
            // Execute.
            setter.invoke(target, new Object[] {value});

        } catch (IllegalAccessException e) {
            // In case of failed to invoke.
            throw new BussinessException("Failed to execute " + setter.getName() + "(" + value + ").");
        } catch (InvocationTargetException e) {
            // In case of failed to invoke.
            throw new BussinessException("Failed to execute " + setter.getName() + "(" + value + ").");
        }
    }


    public static Method findMethod(final Object source, final String methodName) {

        Method[] _methods = source.getClass().getMethods();

        // Look in every method.
        for (int i = 0; i < _methods.length; i++) {
            // If matches.
            if (_methods[i].getName().equals(methodName)) {
                return _methods[i];
            }
        }

        return null;
    }

}
