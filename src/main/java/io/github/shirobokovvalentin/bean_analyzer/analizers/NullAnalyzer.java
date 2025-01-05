package io.github.shirobokovvalentin.bean_analyzer.analizers;

import java.lang.reflect.Field;

import io.github.shirobokovvalentin.bean_analyzer.descriptors.Category;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.ObjectDescriptor;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.PrimitiveDescriptor;

public class NullAnalyzer extends BaseAnalyzer<ObjectDescriptor> implements TypeAnalyzer
{
	@Override
	public boolean isAccept(Class<?> clazz)
	{
		return clazz == null;
	}

	@Override
	protected ObjectDescriptor createDescriptor()
	{
		return new PrimitiveDescriptor(Category.NULL);
	}

	@Override
	protected void fillDescriptor(ObjectDescriptor descriptor, Object value, Field field)
	{
		descriptor.setType(Object.class);
	}

	@Override
	public boolean isAccept(ObjectDescriptor descriptor)
	{
		return descriptor.getCategory() == Category.NULL;
	}

	@Override
	public Object restore(ObjectDescriptor descriptor)
	{
		return null;
	}

}
