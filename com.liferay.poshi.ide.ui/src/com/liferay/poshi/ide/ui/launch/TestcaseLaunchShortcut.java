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

package com.liferay.poshi.ide.ui.launch;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.liferay.poshi.ide.core.PoshiLaunchHelper;
import com.liferay.poshi.ide.ui.PoshiUI;

/**
 * @author Terry Jia
 */
public class TestcaseLaunchShortcut implements ILaunchShortcut
{

    private String getCommandName( Object o )
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

    @Override
    public void launch( ISelection selection, String mode )
    {
        Object selectObj = ( (IStructuredSelection) selection ).getFirstElement();

        if( selectObj instanceof IFile )
        {
            IFile file = (IFile) selectObj;

            String fileName = file.getName();

            fileName = fileName.substring( 0, fileName.indexOf( "." ) );

            launchFile( file, fileName );
        }
    }

    @Override
    public void launch( IEditorPart editor, String mode )
    {
        IStructuredSelection selection =
            (IStructuredSelection) editor.getEditorSite().getSelectionProvider().getSelection();

        FileEditorInput editorInput = (FileEditorInput) editor.getEditorInput();

        IFile file = editorInput.getFile();

        String fileName = file.getName();

        fileName = fileName.substring( 0, fileName.indexOf( "." ) );

        Object e = selection.getFirstElement();

        String commandName = getCommandName( e );

        String argument = fileName;

        if( !commandName.equals( "" ) )
        {
            argument = fileName + "#" + commandName;
        }

        launchFile( file, argument );
    }

    private void launchFile( IFile file, String argument )
    {
        IPath portalPath = file.getProject().getLocation().removeLastSegments( 6 );

        IPath runXmlPath = portalPath.append( "run.xml" );

        if( runXmlPath.toFile().exists() )
        {
            PoshiLaunchHelper helper = new PoshiLaunchHelper();

            helper.setVMArgs( new String[] { "" } );

            try
            {
                helper.runTarget( runXmlPath, "run", argument, true, portalPath.toPortableString() );
            }
            catch( CoreException e )
            {
                PoshiUI.logError( e );
            }
        }
    }

}
