package io.github.shirobokovvalentin.bean_analyzer.analizers;

import io.github.shirobokovvalentin.bean_analyzer.descriptors.Category;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.ObjectDescriptor;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.PrimitiveDescriptor;

public class FloatAnalyzer extends PrimitiveAnalyzer
{
	@Override
	public boolean isAccept(Class<?> clazz)
	{
		if (Float.class.isAssignableFrom(clazz))
			return true;

		if (Double.class.isAssignableFrom(clazz))
			return true;

		return false;
	}

	@Override
	public PrimitiveDescriptor createDescriptor()
	{
		return new PrimitiveDescriptor(Category.FLOAT);
	}

	@Override
	public boolean isAccept(ObjectDescriptor descriptor)
	{
		return descriptor.getCategory() == Category.FLOAT;
	}

}
