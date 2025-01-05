package io.github.shirobokovvalentin.bean_analyzer;

import io.github.shirobokovvalentin.bean_analyzer.analizers.*;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.Argument;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.CreationMethod;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.ObjectDescriptor;
import io.github.shirobokovvalentin.bean_analyzer.exceptions.BeanAnalyzerException;
import io.github.shirobokovvalentin.bean_analyzer.utils.BeanAnalyzerReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BeanAnalyzer
{
	private static final List<TypeAnalyzer> analyzers = new ArrayList<TypeAnalyzer>();

	static
	{
		analyzers.add(new NullAnalyzer());
		analyzers.add(new BooleanAnalyzer());
		analyzers.add(new IntegerAnalyzer());
		analyzers.add(new FloatAnalyzer());
		analyzers.add(new StringAnalyzer());
		analyzers.add(new EnumAnalyzer());
		analyzers.add(new ListAnalyzer());
		analyzers.add(new SetAnalyzer());
		analyzers.add(new MapAnalyzer());
		analyzers.add(new ObjectAnalyzer());
	}

	// ------------------------------------------------------------	

	private static final BeanAnalyzer instance = new BeanAnalyzer();

	public static BeanAnalyzer getInstance()
	{
		return instance;
	}

	private BeanAnalyzer()
	{
		super();
	}

	// ------------------------------------------------------------	

	public ObjectDescriptor inspect(Object bean)
	{
		return inspect(bean, null);
	}

	public Object restore(ObjectDescriptor bean)
	{
		for (TypeRestorer restorer : analyzers)
		{
			if (restorer.isAccept(bean))
			{
				return restorer.restore(bean);
			}
		}
		return null;
	}

	public ObjectDescriptor create(CreationMethod creationMethod)
	{
		try
		{
			Class<?> type = creationMethod.getType();
			String factoryMethodName = creationMethod.getFactoryMethod();
			List<Argument<?>> arguments = creationMethod.getArguments();

			Class<?>[] paramTypes = extractParamTypes(arguments);
			Object[] objects = extractParamValue(arguments);

			Object newEntity = BeanAnalyzerReflectionUtils.getNewInstance(type, factoryMethodName, paramTypes, objects);

			return inspect(newEntity);

		} catch (Exception e)
		{
			throw new BeanAnalyzerException(e);
		}
	}

	private Class<?>[] extractParamTypes(List<Argument<?>> arguments){
		Class<?>[] paramTypes;
		if (arguments != null)
		{
			paramTypes = new Class<?>[arguments.size()];
			for (int i = 0; i < paramTypes.length; i++)
			{
				paramTypes[i] = arguments.get(i).getType();
			}
		}
		else
		{
			paramTypes = new Class<?>[0];
		}

		return paramTypes;
	}

	private Object[] extractParamValue(List<Argument<?>> arguments)
	{
		Object[] paramValue;
		if (arguments != null)
		{
			paramValue = new Object[arguments.size()];

			for (int i = 0; i < arguments.size(); i++)
			{
				paramValue[i] = arguments.get(i).getValue();
			}
		}
		else
		{
			paramValue = new Object[0];
		}

		return paramValue;
	}

	public ObjectDescriptor inspect(Object bean, Field field)
	{
		for (TypeAnalyzer inspector : analyzers)
		{
			if (inspector.isAccept(bean, field))
			{
				return inspector.inspect(bean, field);
			}
		}
		return null;
	}

}
