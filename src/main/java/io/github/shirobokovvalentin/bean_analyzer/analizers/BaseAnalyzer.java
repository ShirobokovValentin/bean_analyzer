package io.github.shirobokovvalentin.bean_analyzer.analizers;

import static java.lang.reflect.Modifier.isAbstract;
import static java.lang.reflect.Modifier.isInterface;

import java.lang.reflect.Field;

import io.github.shirobokovvalentin.bean_analyzer.annotations.Required;
import io.github.shirobokovvalentin.bean_analyzer.annotations.ViewDescription;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.CreationMethod;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.ObjectDescriptor;

public abstract class BaseAnalyzer<T extends ObjectDescriptor> implements TypeInspector
{

	public T inspect(Object value, Field field)
	{
		T descriptor = createDescriptor();

		fillDescriptor(descriptor, value, field);

		return descriptor;
	}

	protected void fillDescriptor(T descriptor, Object value, Field field)
	{
		if (value != null)
			fillDescriptorFromValue(descriptor, value);

		if (field != null)
			fillDescriptorFromField(descriptor, field);
	}

	protected void fillDescriptorFromValue(T descriptor, Object value)
	{
		Class<?> valueClass = value.getClass();
		descriptor.setType(valueClass);
		descriptor.addCreation(new CreationMethod().type(valueClass));
	}

	protected void fillDescriptorFromField(T descriptor, Field field)
	{
		ViewDescription description = field.getAnnotation(ViewDescription.class);
		if (description != null)
			descriptor.setDescription(description.value());

		Required required = field.getAnnotation(Required.class);
		if (required != null)
			descriptor.setRequired(true);

		Class<?> fieldType = field.getType();
		int modifiers = fieldType.getModifiers();

		if (isAbstract(modifiers))
			return;

		if (isInterface(modifiers))
			return;

		if (descriptor.getType() == null)
			descriptor.setType(fieldType);

		if (descriptor.getType() == null)
			descriptor.setType(Object.class);

		descriptor.addCreation(new CreationMethod().type(fieldType));
	}

	@Override
	public boolean isAccept(Object obj, Field field)
	{
		Class<?> clazz = null;

		if (obj != null)
		{
			clazz = obj.getClass();
		}
		else if (field != null)
		{
			clazz = field.getType();
		}

		return isAccept(clazz);

	}

	protected abstract boolean isAccept(Class<?> clazz);

	protected abstract T createDescriptor();

}
