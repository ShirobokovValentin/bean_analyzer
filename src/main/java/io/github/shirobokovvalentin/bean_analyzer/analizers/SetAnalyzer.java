package io.github.shirobokovvalentin.bean_analyzer.analizers;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import io.github.shirobokovvalentin.bean_analyzer.BeanAnalyzer;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.Category;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.CollectionDescription;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.ObjectDescriptor;

public class SetAnalyzer extends BaseCollectionAnalyzer
{
	@Override
	protected CollectionDescription createDescriptor()
	{
		return new CollectionDescription(Category.SET);
	}

	@Override
	public boolean isAccept(Class<?> clazz)
	{
		return Set.class.isAssignableFrom(clazz);
	}

	public boolean isAccept(ObjectDescriptor descriptor)
	{
		return descriptor.getCategory() == Category.SET;
	}

	public Set<Object> restore(ObjectDescriptor descriptor)
	{
		final Map<String, ObjectDescriptor> fields = descriptor.getFields();

		if (fields == null)
			return Collections.emptySet();

		Set<Object> result = new LinkedHashSet<Object>();

		for (Entry<String, ObjectDescriptor> entry : fields.entrySet())
		{
			final ObjectDescriptor valueDescriptor = entry.getValue();
			final Object value = BeanAnalyzer.getInstance().restore(valueDescriptor);
			result.add(value);
		}

		return result;
	}

}
