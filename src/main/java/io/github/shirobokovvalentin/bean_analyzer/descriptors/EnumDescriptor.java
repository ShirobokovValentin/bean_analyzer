package io.github.shirobokovvalentin.bean_analyzer.descriptors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class EnumDescriptor extends ObjectDescriptor
{
	public EnumDescriptor()
	{
		super(Category.ENUM);
	}

	@XmlElement(name = "value", nillable = true)
	public Object getValue()
	{
		return super.getValue();
	}

}
