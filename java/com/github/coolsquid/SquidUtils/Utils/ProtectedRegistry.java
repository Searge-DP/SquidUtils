package com.github.coolsquid.SquidUtils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

/**
 * 
 * @author CoolSquid
 * All rights reserved.
 *
 */

public class ProtectedRegistry extends ArrayList<Object> {
	private static final long serialVersionUID = 8173664693420638448L;
	
	@Override
	public Object remove(int i) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean removeIf(Predicate<? super Object> filter) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void removeRange(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Object set(int i, Object o) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Iterator<Object> iterator() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}
}