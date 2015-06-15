/*******************************************************************************
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 *******************************************************************************/

package com.liferay.poshi.ide.query.resource;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.wst.xml.search.core.resource.DefaultResourceRequestor;
import org.eclipse.wst.xml.search.core.resource.IResourceRequestor;
import org.eclipse.wst.xml.search.core.resource.IURIResolver;

/**
 * @author Terry Jia
 */
public class FunctionResourcesRequestor extends DefaultResourceRequestor
{

    public static final IResourceRequestor INSTANCE = new FunctionResourcesRequestor();

    @Override
    public boolean accept(
        Object selectedNode, IResource rootContainer, IFolder folder, IURIResolver resolver, String matching,
        boolean fullMatch )
    {
        return super.accept( selectedNode, rootContainer, folder, resolver, matching, fullMatch );
    }

}
