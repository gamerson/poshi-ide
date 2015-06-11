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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.wst.xml.core.contentmodel.modelquery.IExternalSchemaLocationProvider;

/**
 * @author Terry Jia
 */
public class PoshiExternalSchemaLocationProvider implements IExternalSchemaLocationProvider
{

    @Override
    public Map<String, String> getExternalSchemaLocation( URI fileURI )
    {

        Map<String, String> locations = new HashMap<String, String>();

        if( fileURI.toString().endsWith( "testcase" ) || fileURI.toString().endsWith( "function" ) ||
            fileURI.toString().endsWith( "macro" ) )
        {
            try
            {
                final URL url =
                    FileLocator.toFileURL( PoshiCore.getDefault().getBundle().getEntry(
                        "resources/poshi-base.xsd" ) );

                locations.put( IExternalSchemaLocationProvider.NO_NAMESPACE_SCHEMA_LOCATION, url.toString() );
            }
            catch( Exception e )
            {
                PoshiCore.logError( e );
            }
        }

        return locations;
    }

}
