package models;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public interface IMap {

    default void fromMap(Map<String, String> map) {
        for (Method method : this.getClass().getMethods()) {
            if (isSetter(method)) {
                String propertyName = extractPropertyName(method);
                if(propertyName != null)
                    mapValueToProperty(method, propertyName, map.get(propertyName));
            }
        }
    }

    default Map<String, String> toMap() {
        Map<String, String> result = new LinkedHashMap<>();
        for (Method method : this.getClass().getMethods()) {
            if (isGetter(method)) {
                String propertyName = extractPropertyName(method);
                result.put(propertyName, invokeGetter(method));
            }
        }
        return result;
    }

    private boolean isSetter(Method method) {
        return method.getName().startsWith("set") && method.getParameterCount() == 1;
    }

    private boolean isGetter(Method method) {
        return (method.getName().startsWith("get") || method.getName().startsWith("is"))
                && !method.getName().equalsIgnoreCase("getClass") && method.getParameterCount() == 0;
    }

    private String extractPropertyName(Method method) {
        String nomeProp = null;
        int prefixLength = 0;
        if(isGetter(method)){
            prefixLength = method.getName().startsWith("get") ? 3 : method.getName().startsWith("is") ? 2 : 0;
        }else if(isSetter(method)){
            prefixLength = method.getName().startsWith("set") ? 3 :0;
        }
        if(prefixLength>0){
            nomeProp = method.getName().substring(prefixLength);
            return Character.toLowerCase(nomeProp.charAt(0)) + nomeProp.substring(1);
        }
        return null;
    }

    private void mapValueToProperty(Method method, String propertyName, String value) {
        if (value == null) return;
        try {
            Class<?> paramType = method.getParameterTypes()[0];
            Object parsedValue = parseValue(paramType, value);
            if (parsedValue != null)
                method.invoke(this, parsedValue);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Object parseValue(Class<?>  paramType, String value) {
        try{

            if (paramType.equals(String.class)) 
                return  value;
            else if (paramType == int.class || paramType.equals(Integer.class)) 
                return  Integer.valueOf(value);
            else if (paramType == double.class || paramType.equals(Double.class)) 
                return Double.valueOf(value);
            else if (paramType.equals(LocalDate.class)) 
                return LocalDate.parse(value);
            else if(paramType.equals(Long.class))
                return Long.valueOf(value);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private String invokeGetter(Method method) {
        try {
            Object value = method.invoke(this);
            return value != null ? value.toString() : null;
        } catch (Exception ex) {
           
            return null;
        }
    }
}
