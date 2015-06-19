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

package com.liferay.poshi.ide.core;

import org.eclipse.ant.launching.IAntLaunchConstants;
import org.eclipse.core.externaltools.internal.IExternalToolConstants;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;

/**
 * @author Greg Amerson
 */
@SuppressWarnings( "restriction" )
public class PoshiLaunchHelper extends LaunchHelper
{

    public static final String ANT_CLASSPATH_PROVIDER = "org.eclipse.ant.ui.AntClasspathProvider";

    public static final String ANT_LAUNCH_CONFIG_TYPE_ID = IAntLaunchConstants.ID_ANT_LAUNCH_CONFIGURATION_TYPE;

    protected IPath currentBuildFile;

    protected String currentTargets;

    private String[] additionalVMArgs;

    private IProgressMonitor monitor;

    public PoshiLaunchHelper()
    {
        super( ANT_LAUNCH_CONFIG_TYPE_ID );

        setLaunchSync( false );
        setLaunchInBackground( true );
        setLaunchCaptureInConsole( true );
        setLaunchIsPrivate( true );
    }

    public ILaunchConfiguration createLaunchConfiguration(
        IPath buildFile, String targets, String argument, boolean separateJRE, String workingDir ) throws CoreException
    {
        ILaunchConfigurationWorkingCopy launchConfig = super.createLaunchConfiguration();

        launchConfig.setAttribute(
            IJavaLaunchConfigurationConstants.ATTR_CLASSPATH_PROVIDER, ANT_CLASSPATH_PROVIDER );
        launchConfig.setAttribute(
            IJavaLaunchConfigurationConstants.ATTR_JRE_CONTAINER_PATH,
            "org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/jdk1.7.0_75" );
        launchConfig.setAttribute(
            IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME,
            "org.eclipse.ant.internal.launching.remote.InternalAntRunner" );
        launchConfig.setAttribute(
            IJavaLaunchConfigurationConstants.ATTR_SOURCE_PATH_PROVIDER, ANT_CLASSPATH_PROVIDER );

        launchConfig.setAttribute( IExternalToolConstants.ATTR_LOCATION, buildFile.toOSString() );

        launchConfig.setAttribute( IExternalToolConstants.ATTR_WORKING_DIRECTORY, workingDir );

        launchConfig.setAttribute( IAntLaunchConstants.ATTR_ANT_TARGETS, targets );

        launchConfig.setAttribute( DebugPlugin.ATTR_PROCESS_FACTORY_ID, "org.eclipse.ant.ui.remoteAntProcessFactory" );

        launchConfig.setAttribute( "org.eclipse.ui.externaltools.ATTR_TOOL_ARGUMENTS", "-Dtest.class=" + argument );

        if( separateJRE )
        {
            launchConfig.setAttribute( IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, getVMArgumentsAttr() );
        }

        return launchConfig;
    }

    public String getClasspathProviderAttributeValue()
    {
        return PoshiClasspathProvider.ID;
    }

    public String getNewLaunchConfigurationName()
    {
        StringBuffer buffer = new StringBuffer();

        if( this.currentBuildFile != null )
        {
            buffer.append( this.currentBuildFile.lastSegment() );
        }

        if( this.currentTargets != null )
        {
            buffer.append( " [" ); //$NON-NLS-1$
            buffer.append( this.currentTargets );
            buffer.append( "]" ); //$NON-NLS-1$
        }

        return buffer.toString();
    }

    private String getVMArgumentsAttr()
    {
        StringBuffer args = new StringBuffer( "-Xmx768m" ); //$NON-NLS-1$

        if( additionalVMArgs.length != 0 )
        {
            for( String vmArg : additionalVMArgs )
            {
                args.append( " " + vmArg );
            }
        }

        return args.toString();
    }

    public void runTarget( IPath buildFile, String targets, String argument, boolean separateJRE, String workingDir )
        throws CoreException
    {
        if( isLaunchRunning() )
        {
            throw new IllegalStateException( "Existing launch in progress" ); //$NON-NLS-1$
        }

        this.currentBuildFile = buildFile;

        this.currentTargets = targets;

        ILaunchConfiguration launchConfig =
            createLaunchConfiguration( buildFile, targets, argument, separateJRE, workingDir );

        launch( launchConfig, ILaunchManager.RUN_MODE, monitor );

        this.currentBuildFile = null;

        this.currentTargets = null;
    }

    public void setVMArgs( String[] vmargs )
    {
        this.additionalVMArgs = vmargs;
    }

}