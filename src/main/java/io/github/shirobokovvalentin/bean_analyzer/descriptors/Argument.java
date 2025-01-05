package io.github.shirobokovvalentin.bean_analyzer.descriptors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType
public class Argument<T>
{
	@XmlElement(name = "type", nillable = true)
	private Class<T> type;

	@XmlElement(name = "value", nillable = true)
	private T value;

	// --------------------------------------------------

	public Argument()
	{
		super();
	}

	public Argument(Class<T> type)
	{
		this.type = type;
	}

	public Argument(Class<T> type, T value)
	{
		this(type);
		this.value = value;
	}

	// --------------------------------------------------

	public Class<T> getType()
	{
		return this.type;
	}

	public void setType(Class<T> type)
	{
		this.type = type;
	}

	public T getValue()
	{
		return this.value;
	}

	public void setValue(T value)
	{
		this.value = value;
	}

}
