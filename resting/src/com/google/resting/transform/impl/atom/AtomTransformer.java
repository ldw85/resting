/*
 * Copyright (C) 2011 Google Code.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.resting.transform.impl.atom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import com.google.resting.component.Alias;
import com.google.resting.component.impl.xml.XMLAlias;
import com.google.resting.transform.Transformer;
import com.google.resting.transform.impl.XMLTransformer;

/**
 * A transformer implementation to parse ATOM feeds
 * @author lakshmipriya-p
 *
 */
public class AtomTransformer<T> implements Transformer {
	private boolean addDefaultNamespace = true;
	private static final String NAMESPACE = "http://www.w3.org/2005/Atom";
	private static final String PREFIX = "atom";
	
	
	public AtomTransformer() {}
	
	public AtomTransformer(boolean addDefaultNamespace) {
		this.addDefaultNamespace = addDefaultNamespace;
	}
	
	@Override
	//TODO
	public T createEntity(String singleEntityStream, Class targetType) {
		return null;
	}

	@Override
	//TODO
	public List getEntityList(Object source, Class targetType, Alias alias) {
		return null;
	}

	@Override
	public List getEntityList(String responseString, Class targetType,
			Alias alias) {
		if(addDefaultNamespace && alias instanceof XMLAlias) {
			Map<QName, Class> m = new HashMap<QName, Class>();
			m.put(new QName(NAMESPACE, "feed", PREFIX), AtomFeed.class);
			m.put(new QName(NAMESPACE, "link", PREFIX), AtomLink.class);
			m.put(new QName(NAMESPACE, "author", PREFIX), AtomAuthor.class);
			m.put(new QName(NAMESPACE, "entry", PREFIX), AtomEntry.class);
			((XMLAlias)alias).setQNameMap(m);
			Map<String, Class> implicitCollectionMap = new HashMap<String, Class>();
			implicitCollectionMap.put("links", AtomFeed.class);
			implicitCollectionMap.put("entries", AtomFeed.class);
			implicitCollectionMap.put("links", AtomEntry.class);
			((XMLAlias)alias).setImplicitCollectionMap(implicitCollectionMap);
		}
		List<T> l = new XMLTransformer<T>(true).getEntityList(
				responseString, targetType, alias);
		return l;
	}
}
