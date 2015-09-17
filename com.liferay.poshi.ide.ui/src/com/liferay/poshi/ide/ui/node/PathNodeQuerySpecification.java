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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.wst.xml.core.internal.document.AttrImpl;
import org.eclipse.wst.xml.search.core.queryspecifications.container.IMultiResourceProvider;
import org.eclipse.wst.xml.search.core.queryspecifications.requestor.IXMLSearchRequestor;
import org.eclipse.wst.xml.search.core.queryspecifications.requestor.IXMLSearchRequestorProvider;

import com.liferay.poshi.ide.ui.PoshiUI;

/**
 * @author Andy Wu
 */
@SuppressWarnings( "restriction" )
public class PathNodeQuerySpecification implements IXMLSearchRequestorProvider, IMultiResourceProvider
{

    private String[] folders = new String[] { "/paths", "/tests" };
    private IResource[] targetResources;

    private class PathFileFinder implements IResourceVisitor
    {

        private String fileName;

        public PathFileFinder( String fileName )
        {
            this.fileName = fileName;
        }

        @Override
        public boolean visit( IResource resource ) throws CoreException
        {
            if( resource.getName().equals( fileName + ".path" ) )
            {
                List<IResource> resources = new ArrayList<IResource>();

                resources.add( resource );

                setTargetResources( resources.toArray( new IResource[0] ) );

                return false;
            }

            return true;
        }

    }

    public IXMLSearchRequestor getRequestor()
    {
        return PathNodeSearchRequestor.INSTANCE;
    }

    @Override
    public IResource[] getResources( Object selectedNode, IResource resource )
    {
        String nodeFileName = "";
        IProject project = resource.getProject();

        if( selectedNode instanceof AttrImpl )
        {
            AttrImpl node = (AttrImpl) selectedNode;

            String nodeValue = node.getValue();

            if( nodeValue.contains( "#" ) && ( nodeValue.indexOf( "#" ) != 0 ) )
            {
                nodeFileName = nodeValue.substring( 0, nodeValue.indexOf( "#" ) );
            }
        }

        if( nodeFileName.equals( "" ) )
        {
            List<IResource> resources = new ArrayList<IResource>();

            for( String folder : folders )
            {
                resources.add( project.getFolder( folder ) );
            }

            return resources.toArray( new IResource[0] );
        }
        else
        {
            final String fileName = nodeFileName;

            // clear last state
            setTargetResources( null );

            try
            {
                IResourceVisitor pathFileFinder = new PathFileFinder( fileName );

                for( String folder : folders )
                {
                    project.getFolder( folder ).accept( pathFileFinder );
                }
            }
            catch( CoreException e )
            {
                PoshiUI.logError( e );
            }

            return targetResources;
        }
    }

    private void setTargetResources( IResource[] targetResources )
    {
        this.targetResources = targetResources;
    }

}
