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

package com.liferay.poshi.ide.query;

import org.eclipse.core.resources.IResource;
import org.eclipse.wst.xml.search.core.resource.IURIResolver;
import org.eclipse.wst.xml.search.core.resource.ResourceBaseURIResolver;

/**
 * @author Terry Jia
 */
public class PoshiURIResolver extends ResourceBaseURIResolver
{

    public static final IURIResolver INSTANCE = new PoshiURIResolver();

    public String resolve( Object selectedNode, IResource rootContainer, IResource file )
    {
        String s = super.resolve( selectedNode, rootContainer, file );

        if( !s.equals( "" ) && s.contains( "." ) )
        {
            s = s.substring( 0, s.indexOf( "." ) );
        }

        if( s.contains( "/" ) )
        {
            s = s.substring( s.indexOf( "/" ) + 1 );
        }

        return s;
    }
}
