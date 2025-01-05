package io.github.shirobokovvalentin.bean_analyzer.analizers;

import java.util.Collection;

import io.github.shirobokovvalentin.bean_analyzer.BeanAnalyzer;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.CollectionDescription;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.ObjectDescriptor;

public abstract class BaseCollectionAnalyzer extends CollectionAnalyzer implements TypeAnalyzer
{
	@Override
	protected void fillDescriptorFromValue(CollectionDescription descriptor, Object value)
	{
		super.fillDescriptorFromValue(descriptor, value);
		Collection<?> list = (Collection<?>) value;

		for (Object element : list)
		{
			ObjectDescriptor elementDescriptor = BeanAnalyzer.getInstance().inspect(element);
			descriptor.addElement(elementDescriptor);
		}

	}

}
