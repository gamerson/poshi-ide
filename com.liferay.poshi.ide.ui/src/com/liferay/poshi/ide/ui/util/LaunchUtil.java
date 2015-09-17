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

package com.liferay.poshi.ide.ui.util;

import java.io.FileNotFoundException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.liferay.poshi.ide.core.PoshiLaunchHelper;
import com.liferay.poshi.ide.ui.PoshiUI;

/**
 * @author Terry Jia
 */
public class LaunchUtil
{

    public static String getCommandName( Object o )
    {
        if( o instanceof Attr )
        {
            return getCommandName( ( (Attr) o ).getOwnerElement() );
        }
        else if( o instanceof Element )
        {
            Element e = (Element) o;
            String nodeName = e.getNodeName();

            if( nodeName.equals( "command" ) )
            {
                return e.getAttribute( "name" );
            }
            else if( nodeName.equals( "definition" ) )
            {
                return "";
            }
            else
            {
                return getCommandName( e.getParentNode() );
            }
        }
        else if( o instanceof Text )
        {
            return getCommandName( ( (Text) o ).getParentNode() );
        }
        else
        {
            return "";
        }
    }

    public static void launchFile( IFile file, String buildXmlFile, String target, String argument )
    {
        IPath portalPath = file.getProject().getLocation().removeLastSegments( 6 );

        IPath runXmlPath = portalPath.append( buildXmlFile );

        if( runXmlPath.toFile().exists() )
        {
            PoshiLaunchHelper helper = new PoshiLaunchHelper();

            helper.setVMArgs( new String[] { "-XX:MaxPermSize=256m", "-Xms256m" } );

            try
            {
                helper.runTarget( runXmlPath, target, argument, true, portalPath.toPortableString() );
            }
            catch( CoreException e )
            {
                PoshiUI.logError( e );
            }
        }
        else
        {
            try
            {
                throw new FileNotFoundException();
            }
            catch( FileNotFoundException e )
            {
                PoshiUI.logError( "Can't find build file at:" + runXmlPath.toPortableString(), e );
            }
        }
    }

}
