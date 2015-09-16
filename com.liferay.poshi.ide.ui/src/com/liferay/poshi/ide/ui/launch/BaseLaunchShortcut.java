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
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.FileEditorInput;

import com.liferay.poshi.ide.ui.util.LaunchUtil;

/**
 * @author Terry Jia
 */
public class BaseLaunchShortcut implements ILaunchShortcut
{

    protected String buildXmlFile = "";
    protected String target = "";

    @Override
    public void launch( ISelection selection, String mode )
    {
        Object selectObj = ( (IStructuredSelection) selection ).getFirstElement();

        if( selectObj instanceof IFile )
        {
            IFile file = (IFile) selectObj;

            String fileName = file.getName();

            fileName = fileName.substring( 0, fileName.indexOf( "." ) );

            StringBuffer sb = new StringBuffer();

            sb.append( "-Dtest.class=" );
            sb.append( fileName );

            LaunchUtil.launchFile( file, buildXmlFile, target, sb.toString() );
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

        String commandName = LaunchUtil.getCommandName( e );

        StringBuffer sb = new StringBuffer();

        sb.append( "-Dtest.class=" );
        sb.append( fileName );

        if( !commandName.equals( "" ) )
        {
            sb.append( "#" );
            sb.append( commandName );
        }

        LaunchUtil.launchFile( file, buildXmlFile, target, sb.toString() );
    }

}
