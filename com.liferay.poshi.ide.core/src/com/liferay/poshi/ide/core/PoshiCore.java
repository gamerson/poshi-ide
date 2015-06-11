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

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

/**
 * @author Terry Jia
 */
public class PoshiCore extends Plugin
{

    public static final IPath GLOBAL_SETTINGS_PATH = new Path( System.getProperty( "user.home", "" ) +
        "/.liferay-poshi-ide" );

    private static PoshiCore plugin;

    public static final String PLUGIN_ID = "com.liferay.poshi.ide.core";

    public static IStatus createErrorStatus( Exception e )
    {
        return createErrorStatus( PLUGIN_ID, e );
    }

    public static IStatus createErrorStatus( String msg )
    {
        return createErrorStatus( PLUGIN_ID, msg );
    }

    public static IStatus createErrorStatus( String pluginId, String msg )
    {
        return new Status( IStatus.ERROR, pluginId, msg );
    }

    public static IStatus createErrorStatus( String pluginId, String msg, Throwable e )
    {
        return new Status( IStatus.ERROR, pluginId, msg, e );
    }

    public static IStatus createErrorStatus( String pluginId, Throwable t )
    {
        return new Status( IStatus.ERROR, pluginId, t.getMessage(), t );
    }

    public static IStatus createInfoStatus( String msg )
    {
        return createInfoStatus( PLUGIN_ID, msg );
    }

    public static IStatus createInfoStatus( String pluginId, String msg )
    {
        return new Status( IStatus.INFO, pluginId, msg );
    }

    public static IStatus createWarningStatus( String message )
    {
        return new Status( IStatus.WARNING, PLUGIN_ID, message );
    }

    public static IStatus createWarningStatus( String message, String id )
    {
        return new Status( IStatus.WARNING, id, message );
    }

    public static IStatus createWarningStatus( String message, String id, Exception e )
    {
        return new Status( IStatus.WARNING, id, message, e );
    }

    /**
     * Returns the shared instance
     *
     * @return the shared instance
     */
    public static PoshiCore getDefault()
    {
        return plugin;
    }

    public static void logError( IStatus status )
    {
        getDefault().getLog().log( status );
    }

    public static void logError( String msg )
    {
        logError( createErrorStatus( msg ) );
    }

    public static void logError( String msg, Throwable t )
    {
        getDefault().getLog().log( createErrorStatus( PLUGIN_ID, msg, t ) );
    }

    public static void logError( Throwable t )
    {
        getDefault().getLog().log( new Status( IStatus.ERROR, PLUGIN_ID, t.getMessage(), t ) );
    }

    public static void logInfo( String msg )
    {
        logError( createInfoStatus( msg ) );
    }

    public static void logWarning( Throwable t )
    {
        getDefault().getLog().log( new Status( IStatus.WARNING, PLUGIN_ID, t.getMessage(), t ) );
    }

    public PoshiCore()
    {
    }

    public void start( BundleContext context ) throws Exception
    {
        super.start( context );
        plugin = this;

    }

    public void stop( BundleContext context ) throws Exception
    {
        plugin = null;
        super.stop( context );
    }

}
