package io.github.shirobokovvalentin.bean_analyzer.descriptors;

import java.util.List;

public interface Crud
{
	public ObjectDescriptor get(List<String> path);
	
	public ObjectDescriptor put(ObjectDescriptor newValue);
	
	public ObjectDescriptor post(ObjectDescriptor newValue);
	
	public ObjectDescriptor delete(String key);

}
