package io.github.shirobokovvalentin.bean_analyzer.analizers;

import java.lang.reflect.Field;

import io.github.shirobokovvalentin.bean_analyzer.descriptors.Category;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.EnumDescriptor;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.ObjectDescriptor;

public class EnumAnalyzer extends BaseAnalyzer<EnumDescriptor> implements TypeAnalyzer
{
	@Override
	public boolean isAccept(Class<?> clazz)
	{
		return clazz.isEnum();
	}

	@Override
	protected EnumDescriptor createDescriptor()
	{
		return new EnumDescriptor();
	}

	@Override
	protected void fillDescriptorFromValue(EnumDescriptor descriptor, Object value)
	{
		super.fillDescriptorFromValue(descriptor, value);
		fillOptions(descriptor);

		descriptor.value(value);
	}

	@Override
	protected void fillDescriptorFromField(EnumDescriptor descriptor, Field field)
	{
		super.fillDescriptorFromField(descriptor, field);
		fillOptions(descriptor);
	}

	private void fillOptions(EnumDescriptor descriptor)
	{
		final Class<?> type = descriptor.getType();
		for (Object obj : type.getEnumConstants())
		{
			descriptor.addOptions(obj.toString());
		}
	}

	public boolean isAccept(ObjectDescriptor descriptor)
	{
		return descriptor.getCategory() == Category.ENUM;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object restore(ObjectDescriptor descriptor)
	{
		Object rawValue = descriptor.getValue();
		if (rawValue == null)
			return null;

		String stringValue = rawValue.toString();
		Class<Enum> enumType = (Class<Enum>) descriptor.getType();

		Enum<?> value = Enum.valueOf(enumType, stringValue);
		return value;
	}

}
