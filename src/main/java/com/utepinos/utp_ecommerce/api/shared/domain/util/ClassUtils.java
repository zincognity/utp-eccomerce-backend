package com.utepinos.utp_ecommerce.api.shared.domain.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * * Utilidades de clases.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClassUtils {

  /**
   * * Verifica si el tipo es primitivo.
   * 
   * @param type {@link Class} The type.
   * @return {@link Boolean} True si el tipo es primitive, false si no lo es.
   */
  public static Boolean isPrimitive(Class<?> type) {
    return type.equals(String.class) ||
        type.equals(Integer.class) ||
        type.equals(Long.class) ||
        type.equals(Double.class) ||
        type.equals(Float.class) ||
        type.equals(Boolean.class) ||
        type.equals(BigDecimal.class) ||
        type.equals(LocalDate.class) ||
        type.equals(LocalDateTime.class) ||
        type.equals(Byte.class) ||
        type.equals(Short.class) ||
        type.equals(Character.class) ||
        type.isPrimitive() ||
        type.isEnum();
  }

  /**
   * * Obtiene todos los parámetros de una clase.
   *
   * @param clazz {@link Class} La clase.
   * @return {@link Array} Los parámetros.
   */
  public static Field[] getAllFields(Class<?> clazz) {
    List<Class<?>> classes = new LinkedList<>();

    while (clazz != null && clazz != Object.class) {
      classes.add(clazz);
      clazz = clazz.getSuperclass();
    }

    return classes.stream()
        .flatMap(c -> List.of(c.getDeclaredFields()).stream())
        .toArray(Field[]::new);
  }

  /**
   * * Obtiene un parámetro de una clase.
   *
   * @param clazz {@link Class} La clase.
   * @return {@link Field} El id del parámetro. `null` si no lo encuentra.
   */
  public static Field getIdField(Class<?> clazz) {
    Field idField = null;

    while (clazz != null && clazz != Object.class) {
      try {
        idField = clazz.getDeclaredField("id");
        break;
      } catch (NoSuchFieldException e) {
        clazz = clazz.getSuperclass();
      }
    }

    return idField;
  }

  /**
   * * Verifica si una colección contiene elementos de distintas clases.
   *
   * @param collection {@link Collection} La colección a evaluar.
   * @return {@code true} si la colección contiene elementos de diferentes clases;
   *         {@code false} en caso contrario o si está vacía.
   */
  public static boolean hasDifferentClasses(Collection<?> collection) {
    if (collection.isEmpty())
      return false;

    Class<?> clazz = collection.iterator().next().getClass();

    for (Object item : collection)
      if (!item.getClass().equals(clazz))
        return true;

    return false;
  }

  /**
   * * Obtiene la última clase padre (superclase) de una clase antes de
   * * {@link Object}.
   *
   * @param clazz {@link Class} La clase de la cual se desea obtener la última
   *              superclase.
   * @return La última clase padre distinta de {@link Object}.
   */
  public static Class<?> getLastParentClass(Class<?> clazz) {
    while (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class)
      clazz = clazz.getSuperclass();

    return clazz;
  }
}
