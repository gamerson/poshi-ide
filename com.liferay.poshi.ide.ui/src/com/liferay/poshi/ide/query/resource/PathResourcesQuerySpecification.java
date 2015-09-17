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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.wst.xml.search.core.queryspecifications.container.IMultiResourceProvider;
import org.eclipse.wst.xml.search.core.resource.IResourceRequestor;
import org.eclipse.wst.xml.search.core.resource.IResourceRequestorProvider;
import org.eclipse.wst.xml.search.core.resource.IURIResolver;
import org.eclipse.wst.xml.search.core.resource.IURIResolverProvider;

import com.liferay.poshi.ide.query.PoshiURIResolver;

/**
 * @author Andy Wu
 */
public class PathResourcesQuerySpecification
    implements IMultiResourceProvider, IResourceRequestorProvider, IURIResolverProvider
{

    private String[] folders = new String[] { "/paths", "/tests" };

    public IResourceRequestor getRequestor()
    {
        return PathResourceRequetor.INSTANCE;
    }

    @Override
    public IURIResolver getURIResolver( IFile file, Object selectedNode )
    {
        return PoshiURIResolver.INSTANCE;
    }

    @Override
    public IResource[] getResources( Object selectedNode, IResource resource )
    {
        List<IResource> resources = new ArrayList<IResource>();

        for( String folder : folders )
        {
            resources.add( resource.getProject().getFolder( folder ) );
        }

        return resources.toArray( new IResource[0] );
    }

}
