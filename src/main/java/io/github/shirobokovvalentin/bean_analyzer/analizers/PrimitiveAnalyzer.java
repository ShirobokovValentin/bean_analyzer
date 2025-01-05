package io.github.shirobokovvalentin.bean_analyzer.analizers;

import io.github.shirobokovvalentin.bean_analyzer.descriptors.ObjectDescriptor;

public abstract class PrimitiveAnalyzer extends BaseAnalyzer<ObjectDescriptor> implements TypeAnalyzer
{
	@Override
	protected void fillDescriptorFromValue(ObjectDescriptor descriptor, Object value)
	{
		super.fillDescriptorFromValue(descriptor, value);
		descriptor.setValue(value);
	}

	@Override
	public Object restore(ObjectDescriptor descriptor)
	{
		return descriptor.getValue();
	}
}
