package io.github.shirobokovvalentin.bean_analyzer.analizers;

import io.github.shirobokovvalentin.bean_analyzer.descriptors.Category;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.ObjectDescriptor;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.PrimitiveDescriptor;

public class IntegerAnalyzer extends PrimitiveAnalyzer
{
	@Override
	public boolean isAccept(Class<?> clazz)
	{
		if (Byte.class.isAssignableFrom(clazz))
			return true;

		if (Short.class.isAssignableFrom(clazz))
			return true;

		if (Integer.class.isAssignableFrom(clazz))
			return true;

		if (Long.class.isAssignableFrom(clazz))
			return true;

		return false;

	}

	@Override
	public PrimitiveDescriptor createDescriptor()
	{
		return new PrimitiveDescriptor(Category.INTEGER);
	}

	@Override
	public boolean isAccept(ObjectDescriptor descriptor)
	{
		return descriptor.getCategory() == Category.INTEGER;
	}

}
