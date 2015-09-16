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

package com.liferay.poshi.ide.ui.contentoutline;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.wst.xml.ui.internal.actions.NodeAction;
import org.w3c.dom.Node;

import com.liferay.poshi.ide.ui.util.LaunchUtil;

/**
 * @author Terry Jia
 */
@SuppressWarnings( "restriction" )
public class BaseRunAsAction extends NodeAction
{

    private String title;
    private String buildXmlFile = "";
    private String target = "";
    private File testcaseFile = null;
    private Node node = null;

    public BaseRunAsAction(
        String title, String buildXmlFile, String target, File testcaseFile, Node node)
    {
        this.title = title;
        this.testcaseFile = testcaseFile;
        this.node = node;
        this.buildXmlFile = buildXmlFile;
        this.target = target;

        setText( title );
    }

    public String getUndoDescription()
    {
        return title;
    }

    public void run()
    {
        String fileName = testcaseFile.getName();

        fileName = fileName.substring( 0, fileName.indexOf( "." ) );

        String commandName = LaunchUtil.getCommandName( node );
        StringBuffer sb = new StringBuffer();

        sb.append( "-Dtest.class=" );
        sb.append( fileName );

        if( !commandName.equals( "" ) )
        {
            sb.append( "#" );
            sb.append( commandName );
        }

        IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile( new Path( testcaseFile.getPath() ) );

        LaunchUtil.launchFile( file, buildXmlFile, target, sb.toString() );
    }

}
