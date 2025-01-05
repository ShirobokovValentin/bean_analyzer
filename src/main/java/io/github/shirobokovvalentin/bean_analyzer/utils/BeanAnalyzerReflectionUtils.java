package io.github.shirobokovvalentin.bean_analyzer.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BeanAnalyzerReflectionUtils extends ReflectionUtils
{
	private static final String VALUE = "value";

	// ------------------------------------------------------------


	public static Object invokeMethod(Method method, Object target, Object... args) throws IllegalStateException
	{
		if (method == null)
			throw new IllegalArgumentException("Method can't be null");
		try
		{
			return method.invoke(target, args);
		} catch (IllegalArgumentException e)
		{
			throw new IllegalStateException(e);
		} catch (IllegalAccessException e)
		{
			throw new IllegalStateException(e);
		} catch (InvocationTargetException e)
		{
			throw new IllegalStateException(e);
		}
	}

	public static List<Field> findAllFields(Class<?> clazz)
	{
		Assert.notNull(clazz, "Class must not be null");
		List<Field> allFields = new ArrayList<Field>();
		Class<?> searchType = clazz;
		while (!Object.class.equals(searchType) && searchType != null)
		{
			Field[] fields = searchType.getDeclaredFields();

            allFields.addAll(Arrays.asList(fields));
			searchType = searchType.getSuperclass();
		}
		return allFields;
	}

	public static Method findPublicStaticMethod(Class<?> clazz, String name, Class<?>... paramTypes)
	{
		if (clazz == null)
			return null;

		Method[] methods = clazz.getMethods();
		for (Method method : methods)
		{
			if (Modifier.isStatic(method.getModifiers()))
			{
				if (name.equals(method.getName()))
					if ((paramTypes == null ||
							Arrays.equals(paramTypes, method.getParameterTypes())))
					{
						return method;
					}
			}
		}
		return null;
	}

	// ------------------------------------------------------------

	public static Object getValue(Annotation annotation, String attributeName)
	{
		try
		{
			Method method = annotation.annotationType().getDeclaredMethod(attributeName);
			ReflectionUtils.makeAccessible(method);
			return method.invoke(annotation);
		} catch (Exception ex)
		{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getValue(Annotation annotation)
	{
		return (T) getValue(annotation, VALUE);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getNewInstance(Class<T> typeClass, String factoryMethodName, Class<?>[] paramTypes, Object[] initArgs) throws IllegalStateException
	{
		if (StringUtils.isNotBlank(factoryMethodName))
		{
			Method factoryMethod = ReflectionUtils.findMethod(typeClass, factoryMethodName, paramTypes);
			if (factoryMethod == null)
			{
				throw new IllegalStateException("factoryMethod :" + factoryMethodName + "(?) not found.");
			}
			return (T) BeanAnalyzerReflectionUtils.invokeMethod(factoryMethod, null, initArgs);
		}

		return BeanAnalyzerReflectionUtils.getNewInstance(typeClass, paramTypes, initArgs);
	}


	public static <T> T getNewInstance(Class<T> typeClass, Class<?>[] paramTypes, Object[] initArgs) throws IllegalStateException
	{
		try
		{
			Constructor<T> constructor = typeClass.getDeclaredConstructor(paramTypes);
			constructor.setAccessible(true);
			return constructor.newInstance(initArgs);
		} catch (Exception e)
		{
			throw new IllegalStateException(e);
		}
	}

	public static <T> T getNewInstance(Class<T> typeClass) throws IllegalStateException
	{
		return getNewInstance(typeClass,  new Class<?>[0], new Object[0]);
	}

}
