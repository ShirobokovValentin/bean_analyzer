package io.github.shirobokovvalentin.bean_analyzer.descriptors;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@XmlType(propOrder = { "category", "class", "options", "variants", "creations" })
@XmlAccessorType(XmlAccessType.NONE)
public class TypeDescriptor
{
	@XmlElement(name = "category", nillable = true)
	private Category category;

	@XmlElement(name = "class", nillable = true)
	private Class<?> type;

	@XmlElement(name = "options")
	private Set<String> options;

	@XmlElement(name = "variants")
	private Set<CreationMethod> variants;

//	@XmlElement(name = "creations")
	private Set<CreationMethod> creations;

	// ------------------------------------------------------------

	public Category getCategory()
	{
		return this.category;
	}

	public void setCategory(Category category)
	{
		this.category = category;
	}

	public TypeDescriptor category(Category category)
	{
		setCategory(category);
		return this;
	}

	public Class<?> getType()
	{
		return this.type;
	}

	public void setType(Class<?> type)
	{
		this.type = type;
	}

	public TypeDescriptor type(Class<?> type)
	{
		setType(type);
		return this;
	}

	public Set<String> getOptions()
	{
		if (this.options == null)
			this.options = new LinkedHashSet<String>();

		return this.options;
	}

	public Set<CreationMethod> getCreations()
	{
		if (this.creations == null)
			this.creations = new LinkedHashSet<CreationMethod>();
		return this.creations;
	}

	public void setOptions(Set<String> options)
	{
		this.options = options;
	}

	public void addOptions(String option)
	{
		getOptions().add(option);
	}

	public Set<CreationMethod> getVariants()
	{
		if (this.variants == null)
			this.variants = new LinkedHashSet<CreationMethod>();
		return this.variants;
	}

	public void setVariants(Set<CreationMethod> variants)
	{
		this.variants = variants;
	}

	public void addVariant(CreationMethod variant)
	{
		getVariants().add(variant);
	}

	// ------------------------------------------------------------

	@Override
	public String toString()
	{
		return ReflectionToStringBuilder.toString(this);
	}
}
