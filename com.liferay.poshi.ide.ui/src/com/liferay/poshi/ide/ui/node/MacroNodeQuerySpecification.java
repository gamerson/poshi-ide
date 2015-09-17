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
import org.eclipse.wst.xml.core.internal.document.AttrImpl;
import org.eclipse.wst.xml.search.core.queryspecifications.container.IResourceProvider;
import org.eclipse.wst.xml.search.core.queryspecifications.requestor.IXMLSearchRequestor;
import org.eclipse.wst.xml.search.core.queryspecifications.requestor.IXMLSearchRequestorProvider;

/**
 * @author Terry Jia
 */
@SuppressWarnings( "restriction" )
public class MacroNodeQuerySpecification implements IXMLSearchRequestorProvider, IResourceProvider
{

    public IXMLSearchRequestor getRequestor()
    {
        return MacroNodeSearchRequestor.INSTANCE;
    }

    public IResource getResource( Object selectedNode, IResource resource )
    {
        String nodeFileName = "";

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
            return resource.getProject().getFolder( "/macros" );
        }
        else
        {
            StringBuffer sb = new StringBuffer();
            sb.append( "/macros/" );
            if( nodeFileName.startsWith( "SO" ) )
            {
                sb.append( "so/" );
            }
            else if( nodeFileName.startsWith( "Kaleo" ) )
            {
                sb.append( "ee/" );
            }
            else if( nodeFileName.startsWith( "Mobile" ) && !nodeFileName.equals( "MobileDevice" ) )
            {
                sb.append( "mobile/" );
            }

            sb.append( nodeFileName );
            sb.append( ".macro" );

            return resource.getProject().getFile( sb.toString() );
        }
    }

}
