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

import java.io.FileNotFoundException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.liferay.poshi.ide.core.PoshiLaunchHelper;
import com.liferay.poshi.ide.ui.PoshiUI;

/**
 * @author Terry Jia
 */
public abstract class BuildCommandAction extends AbstractObjectAction
{

    public BuildCommandAction()
    {
        super();
    }

    protected abstract String getBuildCommand();

    protected abstract String getBuildFilePath();

    public void run( IAction action )
    {
        if( fSelection instanceof IStructuredSelection )
        {
            Object[] elems = ( (IStructuredSelection) fSelection ).toArray();

            IProject project = null;

            Object elem = elems[0];

            if( elem instanceof IProject )
            {
                project = (IProject) elem;
            }
            else {
                project = ( (IResource) elem ).getProject();
            }

            IPath portalPath = project.getLocation().removeLastSegments( 6 );

            IPath runXmlPath = portalPath.append( getBuildFilePath() );

            if( runXmlPath.toFile().exists() )
            {
                final IProject p = project;
                final IPath path = runXmlPath.removeLastSegments( 1 );
                final IPath buildFile = runXmlPath;

                new Job( p.getName() + " : " + getBuildCommand() ) //$NON-NLS-1$
                {

                    @Override
                    protected IStatus run( IProgressMonitor monitor )
                    {
                        try
                        {
                            PoshiLaunchHelper helper = new PoshiLaunchHelper();

                            helper.setVMArgs( new String[] { "-XX:MaxPermSize=256m", "-Xms256m" } );

                            try
                            {
                                helper.runTarget( buildFile, getBuildCommand(), "", true, path.toPortableString() );
                            }
                            catch( CoreException e )
                            {
                                PoshiUI.logError( e );
                            }
                        }
                        catch( Exception e )
                        {
                            return PoshiUI.createErrorStatus( "Error running build command " + getBuildCommand(), e ); //$NON-NLS-1$
                        }

                        return Status.OK_STATUS;
                    }
                }.schedule();

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

}
