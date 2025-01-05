package io.github.shirobokovvalentin.bean_analyzer.analizers;

import io.github.shirobokovvalentin.bean_analyzer.descriptors.ObjectDescriptor;

public interface TypeRestorer
{
	public boolean isAccept(ObjectDescriptor descriptor);

	public Object restore(ObjectDescriptor descriptor);
}
