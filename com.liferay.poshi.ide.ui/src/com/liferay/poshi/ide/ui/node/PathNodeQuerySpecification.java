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

package com.liferay.poshi.ide.ui.node;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.wst.xml.core.internal.document.AttrImpl;
import org.eclipse.wst.xml.search.core.queryspecifications.container.IResourceProvider;
import org.eclipse.wst.xml.search.core.queryspecifications.requestor.IXMLSearchRequestor;
import org.eclipse.wst.xml.search.core.queryspecifications.requestor.IXMLSearchRequestorProvider;

@SuppressWarnings( "restriction" )
public class PathNodeQuerySpecification implements IXMLSearchRequestorProvider, IResourceProvider
{

    public IXMLSearchRequestor getRequestor()
    {
        return PathNodeSearchRequestor.INSTANCE;
    }

    public IResource getResource( Object selectedNode, IResource resource )
    {

        String nodeFileName = "";

        if( selectedNode instanceof AttrImpl )
        {
            AttrImpl node = (AttrImpl) selectedNode;
            node.getModel().getBaseLocation();
            String nodeValue = node.getValue();

            if( nodeValue.contains( "#" ) && ( nodeValue.indexOf( "#" ) != 0 ) )
            {
                nodeFileName = nodeValue.substring( 0, nodeValue.indexOf( "#" ) );
            }
        }

        if( nodeFileName.equals( "" ) )
        {
            return resource.getProject().getFolder( "/paths" );
        }
        else
        {
            final String fileName = nodeFileName;
            // clear last state
            setTargetResource( null );

            try
            {
                resource.getProject().getFolder( "paths" ).accept( new IResourceVisitor()
                {

                    @Override
                    public boolean visit( IResource resource ) throws CoreException
                    {
                        if( resource.getName().equals( fileName + ".path" ) )
                        {
                            setTargetResource( resource );

                            return false;
                        }

                        return true;
                    }
                } );
            }
            catch( CoreException e )
            {
                e.printStackTrace();
            }

            return targetResource;
        }
    }

    private void setTargetResource( IResource targetResource )
    {
        this.targetResource = targetResource;
    }

    private IResource targetResource;

}
