package io.github.shirobokovvalentin.bean_analyzer.descriptors;

import io.github.shirobokovvalentin.bean_analyzer.exceptions.BeanAnalyzerException;

public class CollectionDescription extends ObjectDescriptor
{
	public CollectionDescription(Category category)
	{
		super(category);
	}

	@Override
	public ObjectDescriptor delete(String key)
	{
		return getFields().remove(key);
	}

	@Override
	public ObjectDescriptor post(ObjectDescriptor entity)
	{
		return addElement(entity);
	}

	@Override
	public ObjectDescriptor put(ObjectDescriptor entity)
	{
		throw new BeanAnalyzerException("Method @PUT not supported on collections");
	}

}
