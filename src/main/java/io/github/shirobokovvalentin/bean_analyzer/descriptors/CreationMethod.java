package io.github.shirobokovvalentin.bean_analyzer.descriptors;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import io.github.shirobokovvalentin.bean_analyzer.exceptions.BeanAnalyzerException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@XmlAccessorType(XmlAccessType.NONE)
public class CreationMethod
{
	@XmlElement(name = "type", nillable = true)
	private Class<?> type;

	@XmlElement(name = "factoryMethod")
	private String factoryMethod;

	@XmlElement(name = "arguments")
	private List<Argument<?>> arguments;

	// ------------------------------------------------------------

	public List<Argument<?>> getArguments()
	{
		return this.arguments;
	}

	public void setArguments(List<Argument<?>> parameters)
	{
		this.arguments = parameters;
	}

	public void setParameters(List<String> sParameters)
	{
		for (String parameter : sParameters)
		{
			addArgument(new Argument<String>(String.class, parameter));
		}
	}

	public CreationMethod addArgument(Argument<?> parameter)
	{
		if (getArguments() == null)
			setArguments(new ArrayList<Argument<?>>());
		getArguments().add(parameter);
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

	public void setType(String sType)
	{
		try
		{
			this.type = Class.forName(sType);
		} catch (ClassNotFoundException e)
		{
			throw new BeanAnalyzerException(e);
		}
	}

	public CreationMethod type(Class<?> type)
	{
		setType(type);
		return this;
	}

	public String getFactoryMethod()
	{
		return this.factoryMethod;
	}

	public void setFactoryMethod(String factoryMethod)
	{
		if (StringUtils.isBlank(factoryMethod))
			this.factoryMethod = null;
		else
			this.factoryMethod = factoryMethod;
	}

	public CreationMethod factoryMethod(String factory)
	{
		setFactoryMethod(factory);
		return this;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.arguments == null) ? 0 : this.arguments.hashCode());
		result = prime * result + ((this.factoryMethod == null) ? 0 : this.factoryMethod.hashCode());
		result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreationMethod other = (CreationMethod) obj;
		if (this.arguments == null)
		{
			if (other.arguments != null)
				return false;
		}
		else if (!this.arguments.equals(other.arguments))
			return false;
		if (this.factoryMethod == null)
		{
			if (other.factoryMethod != null)
				return false;
		}
		else if (!this.factoryMethod.equals(other.factoryMethod))
			return false;
		if (this.type == null)
		{
			if (other.type != null)
				return false;
		}
		else if (!this.type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return ReflectionToStringBuilder.toString(this);
	}

}
