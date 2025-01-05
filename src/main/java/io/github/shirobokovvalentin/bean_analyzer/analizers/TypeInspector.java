package io.github.shirobokovvalentin.bean_analyzer.analizers;

import java.lang.reflect.Field;

import io.github.shirobokovvalentin.bean_analyzer.descriptors.ObjectDescriptor;

public interface TypeInspector
{
	public boolean isAccept(Object obj, Field field);

	public ObjectDescriptor inspect(Object obj, Field field);
}
