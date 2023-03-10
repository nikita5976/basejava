package webapp;

import webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume();
        Field field = r.getClass().getDeclaredFields()[0];
        System.out.println(field);
        System.out.println(field.getName());
        //System.out.println(field.get(r));      исключение т.к. private
        System.out.println("------------------------------");
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        // TODO : invoke r.toString via reflection
        System.out.println(r);
        System.out.println(field.get(r));
        field.setAccessible(false);
        //System.out.println(field.get(r));  исключение т.к. опять private
        System.out.println("--------------------------------------");

        // кролик
        Resume resumeObject = new Resume("testUUID");
        Class<? extends Resume> resumeClass = resumeObject.getClass();
        System.out.println(resumeClass);

        // private method
        Method toStringMethod = Resume.class.getDeclaredMethod("toString");
        toStringMethod.setAccessible(true);
        String returnToString = (String) toStringMethod.invoke(resumeObject);
        System.out.println("значение, которое возвращает  метод toString  = " + returnToString);

        // public method
        Method method = resumeObject.getClass().getMethod("toString");
        System.out.println(method);
        Object result = method.invoke(resumeObject);
        System.out.println(result);
    }
}
