package io.github.shirobokovvalentin.bean_analyzer.analizers;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import io.github.shirobokovvalentin.bean_analyzer.descriptors.CreationMethod;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.CollectionDescription;

public abstract class CollectionAnalyzer extends BaseAnalyzer<CollectionDescription>
{
	@Override
	protected void fillDescriptorFromField(CollectionDescription descriptor, Field field)
	{
		super.fillDescriptorFromField(descriptor, field);

		findAndProcessXmlElements(descriptor, field);
		findAndProcessXmlElement(descriptor, field);
		findAndProcessGenericType(descriptor, field);
	}

	private void findAndProcessXmlElements(CollectionDescription descriptor, Field field)
	{
		XmlElements annotation = field.getAnnotation(XmlElements.class);
		if (annotation == null)
		{
			return;
		}
		processXmlElements(descriptor, annotation);
	}

	private void processXmlElements(CollectionDescription descriptor, XmlElements annotation)
	{
		XmlElement[] xmlElements = annotation.value();
		if (xmlElements == null)
		{
			return;
		}
		for (XmlElement e : xmlElements)
		{
			processXmlElement(descriptor, e);
		}
	}

	private void processXmlElement(CollectionDescription descriptor, XmlElement annotation)
	{
		Class<?> clazz = annotation.type();
		if (clazz == XmlElement.DEFAULT.class)
		{
			return;
		}
		descriptor.addVariant(new CreationMethod().type(clazz));
	}

	private void findAndProcessXmlElement(CollectionDescription descriptor, Field field)
	{
		XmlElement annotation = field.getAnnotation(XmlElement.class);
		if (annotation == null)
		{
			return;
		}
		processXmlElement(descriptor, annotation);
	}

	private void findAndProcessGenericType(CollectionDescription descriptor, Field field)
	{
		Type type = field.getGenericType();
		if (type instanceof ParameterizedType)
		{
			processGenericType(descriptor, type);
		}
	}

	private void processGenericType(CollectionDescription descriptor, Type type)
	{
		ParameterizedType parameterizedType = (ParameterizedType) type;
		Type[] args = parameterizedType.getActualTypeArguments();
		Type first = args[0];

		if (first instanceof Class)
		{
			Class<?> clazz = (Class<?>) first;
			if (clazz.isInterface())
				return;

			if (Modifier.isAbstract(clazz.getModifiers()))
				return;

			CreationMethod creation = new CreationMethod().type(clazz);
			descriptor.addVariant(creation);
		}
	}

}
