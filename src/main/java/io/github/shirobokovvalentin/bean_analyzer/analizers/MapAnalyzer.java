package io.github.shirobokovvalentin.bean_analyzer.analizers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import io.github.shirobokovvalentin.bean_analyzer.BeanAnalyzer;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.Category;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.CollectionDescription;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.ObjectDescriptor;

public class MapAnalyzer extends CollectionAnalyzer implements TypeAnalyzer
{
	@Override
	protected CollectionDescription createDescriptor()
	{
		return new CollectionDescription(Category.MAP);
	}

	@Override
	public boolean isAccept(Class<?> cls)
	{
		return Map.class.isAssignableFrom(cls);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void fillDescriptorFromValue(CollectionDescription descriptor, Object value)
	{
		super.fillDescriptorFromValue(descriptor, value);
		Map<String, Object> map = (Map<String, Object>) value;

		for (Map.Entry<String, Object> entry : map.entrySet())
		{
			String key = entry.getKey();
			ObjectDescriptor valueDescriptor = BeanAnalyzer.getInstance().inspect(entry.getValue());
			descriptor.addField(key, valueDescriptor);
		}
	}

	@Override
	public boolean isAccept(ObjectDescriptor descriptor)
	{
		return descriptor.getCategory() == Category.MAP;
	}

	@Override
	public Map<Object, Object> restore(ObjectDescriptor descriptor)
	{
		final Map<String, ObjectDescriptor> fields = descriptor.getFields();

		if (fields == null)
			return Collections.emptyMap();

		Map<Object, Object> result = new HashMap<Object, Object>();

		for (Entry<String, ObjectDescriptor> entry : fields.entrySet())
		{
			final ObjectDescriptor valueDescriptor = entry.getValue();
			final Object value = BeanAnalyzer.getInstance().restore(valueDescriptor);
			result.put(entry.getKey(), value);
		}

		return result;
	}
}
