package com.ibm.app.util;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Supports only JDK 8 and up.
 */
public class AnnotationUtil {
    private static final String ANNOTATIONS = "annotations";
    public static final String ANNOTATION_DATA = "annotationData";

    public static void alterAnnotationOn(Class clazzToLookFor, Class<? extends Annotation> annotationToAlter, Annotation annotationValue) {
        try {
            //In JDK8 Class has a private method called annotationData().
            //We first need to invoke it to obtain a reference to AnnotationData class which is a private class
            Method method = Class.class.getDeclaredMethod(ANNOTATION_DATA, new Class[0]);
            method.setAccessible(true);
            //Since AnnotationData is a private class we cannot create a direct reference to it. We will have to
            //manage with just Object
            Object annotationData = method.invoke(clazzToLookFor);
            //We now look for the map called "annotations" within AnnotationData object.
            Field annotations = annotationData.getClass().getDeclaredField(ANNOTATIONS);
            annotations.setAccessible(true);
            Map<Class<? extends Annotation>, Annotation> map =
                    (Map<Class<? extends Annotation>, Annotation>) annotations.get(annotationData);
            map.put(annotationToAlter, annotationValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
