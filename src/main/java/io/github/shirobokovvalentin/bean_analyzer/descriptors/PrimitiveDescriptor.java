package io.github.shirobokovvalentin.bean_analyzer.descriptors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class PrimitiveDescriptor extends ObjectDescriptor
{
	public PrimitiveDescriptor(Category category)
	{
		super(category);
	}

	@Override
	@XmlElement(name = "value", nillable = true)
	public Object getValue()
	{
		return super.getValue();
	}

}
