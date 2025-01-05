package io.github.shirobokovvalentin.bean_analyzer.analizers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.github.shirobokovvalentin.bean_analyzer.BeanAnalyzer;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.Category;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.CollectionDescription;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.ObjectDescriptor;

public class ListAnalyzer extends BaseCollectionAnalyzer
{
	@Override
	protected CollectionDescription createDescriptor()
	{
		return new CollectionDescription(Category.LIST);
	}

	@Override
	public boolean isAccept(Class<?> clazz)
	{
		return List.class.isAssignableFrom(clazz);
	}

	@Override
	public boolean isAccept(ObjectDescriptor descriptor)
	{
		return descriptor.getCategory() == Category.LIST;
	}

	@Override
	public List<Object> restore(ObjectDescriptor descriptor)
	{
		final Map<String, ObjectDescriptor> fields = descriptor.getFields();

		if (fields == null)
			return Collections.emptyList();

		List<Object> result = new ArrayList<Object>();

		for (Entry<String, ObjectDescriptor> entry : fields.entrySet())
		{
			final ObjectDescriptor valueDescriptor = entry.getValue();
			final Object value = BeanAnalyzer.getInstance().restore(valueDescriptor);
			result.add(value);
		}

		return result;
	}

}
