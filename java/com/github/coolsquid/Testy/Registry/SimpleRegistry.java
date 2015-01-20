package com.github.coolsquid.Testy.Registry;

import java.util.ArrayList;
import java.util.List;

import com.github.coolsquid.Testy.Utils.Exception.TestyRuntimeException;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class SimpleRegistry {
	
	protected static List<Object> l = new ArrayList<Object>();
	
	public int maxSize = Integer.MAX_VALUE;
	
	private static int a = 0;
	
	public SimpleRegistry(int i) {
		maxSize = i;
	}
	
	public SimpleRegistry() {}
	
	/**
	 * Registers an object to the registry, as long as the registry is smaller than the max size.
	 * @param object
	 */
	
	public void register(Object object) {
		if (a < maxSize) {
			l.add(object);
			a++;
		}
		else {
			throw new RegistryException("The maximum size was reached!");
		}
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

	public boolean contains(Object object) {
		return l.contains(object);
	}
	
	public boolean isEmpty() {
		return l.isEmpty();
	}
	
	@Override
	public final int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	public String toString() {
		int a = 0;
		String s = "";
		while (a < l.size()) {
			s = s + l.get(a);
			a++;
		}
		return s;
	}
}