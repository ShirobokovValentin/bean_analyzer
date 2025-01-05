package io.github.shirobokovvalentin.bean_analyzer.descriptors;

import io.github.shirobokovvalentin.bean_analyzer.BeanAnalyzer;
import io.github.shirobokovvalentin.bean_analyzer.exceptions.BeanAnalyzerException;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.*;
import java.util.Map.Entry;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = { "type", "value", "required", "description", "comment", "fields" })
public class ObjectDescriptor implements Crud
{
	private String id;

	@XmlElement(name = "type")
	private TypeDescriptor type = new TypeDescriptor();

	@XmlElement(name = "value")
	private Object value;

	@XmlElement(name = "required")
	private boolean required;

	@XmlElement(name = "description")
	private String description;

	@XmlElement(name = "fields")
	private Map<String, ObjectDescriptor> fields;

	// ------------------------------------------------------------

	public ObjectDescriptor(Category category)
	{
		this.type.setCategory(category);
	}

	public ObjectDescriptor()
	{
		this(Category.OBJECT);
	}

	// ------------------------------------------------------------

	public String getId()
	{
		return this.id;
	}

	public TypeDescriptor getTypeDescriptor()
	{
		return this.type;
	}

	public Object getValue()
	{
		return this.value;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getDescription()
	{
		return this.description;
	}

	public Map<String, ObjectDescriptor> getFields()
	{
		if (this.fields == null)
		{
			this.fields = new LinkedHashMap<String, ObjectDescriptor>();
		}
		return this.fields;
	}

	public void setTypeDescriptor(TypeDescriptor type)
	{
		this.type = type;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}

	public Class<?> getType()
	{
		return this.type.getType();
	}

	public Category getCategory()
	{
		return this.type.getCategory();
	}

	public void setType(Class<?> type)
	{
		this.type.setType(type);
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void addCreation(CreationMethod option)
	{
		this.type.getCreations().add(option);
	}

	public void addOptions(String option)
	{
		this.type.addOptions(option);
	}

	public Set<CreationMethod> getVariants()
	{
		return type.getVariants();
	}

	public void addVariant(CreationMethod variant)
	{
		type.addVariant(variant);
	}

	public void setFields(Map<String, ObjectDescriptor> fields)
	{
		this.fields = fields;
	}

	public ObjectDescriptor addField(String key, ObjectDescriptor field)
	{
		getFields().put(key, field);
		return this;
	}

	public ObjectDescriptor addElement(ObjectDescriptor element)
	{
		String key = element.getId();

		if (key == null)
		{
			key = String.valueOf(getFields().size());
		}

		return addField(key, element);
	}

	public ObjectDescriptor value(Object value)
	{
		setValue(value);
		return this;
	}

	// ------------------------------------------------------------

	public ObjectDescriptor get(List<String> path)
	{
		ObjectDescriptor property = this;
		for (String segment : path)
		{
			property = property.getFields().get(segment);
			if (property == null)
				return null;
		}

		return property;
	}

    public ObjectDescriptor put(String sPath, Object object)
    {
        ObjectDescriptor newDescriptor = BeanAnalyzer.getInstance().inspect(object);
        return put(sPath, newDescriptor);
    }

    public ObjectDescriptor put(String sPath, CreationMethod creationMethod)
    {
        ObjectDescriptor newDescriptor = BeanAnalyzer.getInstance().create(creationMethod);
        return put(sPath, newDescriptor);
    }

    public ObjectDescriptor put(String sPath, ObjectDescriptor newValue)
    {
        List<String> path = Arrays.asList(sPath.split("/"));
        ObjectDescriptor currentObjectDescriptor = get(path);
        return currentObjectDescriptor.put(newValue);
    }

	public ObjectDescriptor put(ObjectDescriptor newValue)
	{
		if (newValue.getFields() != null)
		{
			mergeFields(newValue);
		}

		if (newValue.getValue() != null)
		{
			setValue(newValue.getValue());
		}

		return this;
	}

    public ObjectDescriptor post(String sPath, Object object)
    {
        ObjectDescriptor newDescriptor = BeanAnalyzer.getInstance().inspect(object);
        return post(sPath, newDescriptor);
    }

    public ObjectDescriptor post(String sPath, CreationMethod creationMethod)
    {
        ObjectDescriptor newDescriptor = BeanAnalyzer.getInstance().create(creationMethod);
        return post(sPath, newDescriptor);
    }

    public ObjectDescriptor post(String sPath, ObjectDescriptor newValue)
    {
        List<String> path = Arrays.asList(sPath.split("/"));
        ObjectDescriptor currentObjectDescriptor = get(path);
        return currentObjectDescriptor.post(newValue);
    }

	public ObjectDescriptor post(ObjectDescriptor newValue)
	{
		throw new BeanAnalyzerException("Method @POST not supported on Object");
	}

	public ObjectDescriptor delete(String key)
	{
		ObjectDescriptor element = getFields().get(key);
		if (element == null) {
			throw new BeanAnalyzerException("element not found");
		}
		return element.value(null);
	}

	private void mergeFields(ObjectDescriptor newValue)
	{
		for (Entry<String, ObjectDescriptor> entry : newValue.getFields().entrySet())
		{
			String fieldName = entry.getKey();
			ObjectDescriptor fieldValue = entry.getValue();
			getFields().put(fieldName, fieldValue);
		}
	}

	// ------------------------------------------------------------

	@Override
	public String toString()
	{
		return ReflectionToStringBuilder.toString(this);
	}

}
