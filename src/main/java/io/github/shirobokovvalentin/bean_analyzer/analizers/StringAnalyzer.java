package io.github.shirobokovvalentin.bean_analyzer.analizers;

import io.github.shirobokovvalentin.bean_analyzer.descriptors.Category;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.ObjectDescriptor;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.PrimitiveDescriptor;

public class StringAnalyzer extends PrimitiveAnalyzer
{
	@Override
	public boolean isAccept(Class<?> clazz)
	{
		if (Character.class.isAssignableFrom(clazz))
			return true;

		if (String.class.isAssignableFrom(clazz))
			return true;

		return false;
	}

	@Override
	public PrimitiveDescriptor createDescriptor()
	{
		return new PrimitiveDescriptor(Category.STRING);
	}

	@Override
	public boolean isAccept(ObjectDescriptor descriptor)
	{
		return descriptor.getCategory() == Category.STRING;
	}

}
