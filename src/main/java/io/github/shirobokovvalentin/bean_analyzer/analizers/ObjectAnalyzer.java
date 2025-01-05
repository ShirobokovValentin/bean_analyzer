package io.github.shirobokovvalentin.bean_analyzer.analizers;

import io.github.shirobokovvalentin.bean_analyzer.BeanAnalyzer;
import io.github.shirobokovvalentin.bean_analyzer.annotations.ViewId;
import io.github.shirobokovvalentin.bean_analyzer.annotations.ViewIgnore;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.ObjectDescriptor;
import io.github.shirobokovvalentin.bean_analyzer.exceptions.BeanAnalyzerException;
import io.github.shirobokovvalentin.rest.entity.Entity;
import io.github.shirobokovvalentin.bean_analyzer.utils.BeanAnalyzerReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static java.lang.reflect.Modifier.isStatic;

public class ObjectAnalyzer extends BaseAnalyzer<ObjectDescriptor> implements TypeAnalyzer
{
	// ------------------------------------------------------------

	@Override
	public boolean isAccept(Class<?> clazz)
	{
		return true;
	}

	@Override
	protected ObjectDescriptor createDescriptor()
	{
		return new ObjectDescriptor();
	}

	@Override
	protected void fillDescriptorFromValue(ObjectDescriptor descriptor, Object value)
	{
		super.fillDescriptorFromValue(descriptor, value);
		List<Field> fields = BeanAnalyzerReflectionUtils.findAllFields(value.getClass());

		for (Field field : fields)
		{
			fillIdFromAnnotation(descriptor, field, value);

			if (isIgnored(field))
				continue;

			fillProperty(descriptor, value, field);
		}

		if (descriptor.getId() == null)
			fillIdFromEntity(descriptor, value);

	}

	private boolean isIgnored(Field field)
	{
		if (isStatic(field.getModifiers()))
			return true;

		ViewIgnore annotation = field.getAnnotation(ViewIgnore.class);
		if (annotation != null)
			return true;

		return false;
	}

	private void fillProperty(ObjectDescriptor descriptor, Object obj, Field field)
	{
		String key = field.getName();
		try
		{
			field.setAccessible(true);
			Object fieldValue = field.get(obj);
			ObjectDescriptor fieldValueDescriptor = BeanAnalyzer.getInstance().inspect(fieldValue, field);
			descriptor.addField(key, fieldValueDescriptor);
		} catch (Exception e)
		{
			throw new BeanAnalyzerException(e);
		}
	}

	private void fillIdFromAnnotation(ObjectDescriptor descriptor, Field field, Object obj)
	{
		Annotation a = field.getAnnotation(ViewId.class);
		if (a == null)
			return;
		try
		{
			if (descriptor.getId() != null)
				throw new BeanAnalyzerException("not unique ID");

			field.setAccessible(true);
			String id = (String) field.get(obj);
			descriptor.setId(id);
		} catch (Exception e)
		{
			throw new BeanAnalyzerException(e);
		}
	}

	private void fillIdFromEntity(ObjectDescriptor descriptor, Object obj)
	{
		if (obj instanceof Entity)
		{
			Entity e = (Entity) obj;
			descriptor.setId((String) e.getId());
		}

	}

	@Override
	public boolean isAccept(ObjectDescriptor descriptor)
	{
		return true;
	}

	@Override
	public Object restore(ObjectDescriptor descriptor)
	{
		try
		{
			return tryRestore(descriptor);
		} catch (Exception e)
		{
			throw new BeanAnalyzerException(e);
		}
	}

	private Object tryRestore(ObjectDescriptor descriptor)
	{
		Class<?> type = descriptor.getType();
		if (type == null)
		{
			return null;
		}
		Object target = BeanAnalyzerReflectionUtils.getNewInstance(type);
		Map<String, ObjectDescriptor> fields = descriptor.getFields();
		for (Entry<String, ObjectDescriptor> entry : fields.entrySet())
		{
			ObjectDescriptor valueDescriptor = entry.getValue();
			Object value = BeanAnalyzer.getInstance().restore(valueDescriptor);

			Field field = BeanAnalyzerReflectionUtils.findField(target.getClass(), entry.getKey());
			BeanAnalyzerReflectionUtils.makeAccessible(field);
			BeanAnalyzerReflectionUtils.setField(field, target, value);
		}
		return target;
	}

}
