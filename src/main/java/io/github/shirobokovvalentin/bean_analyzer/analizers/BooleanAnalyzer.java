package io.github.shirobokovvalentin.bean_analyzer.analizers;

import io.github.shirobokovvalentin.bean_analyzer.descriptors.Category;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.ObjectDescriptor;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.PrimitiveDescriptor;

public class BooleanAnalyzer extends PrimitiveAnalyzer
{
	@Override
	public boolean isAccept(Class<?> clazz)
	{
		return Boolean.class.isAssignableFrom(clazz);
	}

	@Override
	public PrimitiveDescriptor createDescriptor()
	{
		return new PrimitiveDescriptor(Category.BOOLEAN);
	}

	public boolean isAccept(ObjectDescriptor descriptor)
	{
		return descriptor.getCategory() == Category.BOOLEAN;
	}

}