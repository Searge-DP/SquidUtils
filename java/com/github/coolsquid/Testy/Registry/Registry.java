package com.github.coolsquid.Testy.Registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.coolsquid.Testy.Utils.Exception.TestyRuntimeException;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class Registry {
	
	protected static Map<Object, Integer> m = new HashMap<Object, Integer>();
	protected static List<Object> l = new ArrayList<Object>();
	
	public int maxSize = Integer.MAX_VALUE;
	
	private static int a = 0;
	
	public Registry(int i) {
		maxSize = i;
	}
	
	public Registry() {}
	
	public void register(Object o) {
		if (maxSize < a) {
			l.add(o);
			m.put(o, a);
			a++;
		}
		else {
			throw new RegistryException("The maximum size was reached!");
		}
	}
	
	public int get(Object o) {
		return m.get(o);
	}
	
	public Object get(int i) {
		return l.get(i);
	}
	
	protected class RegistryException extends TestyRuntimeException {
		private static final long serialVersionUID = 1879823L;
		public RegistryException(String comment) {
			super(comment);
		}
	}

	public int size() {
		return l.size();
	}
}