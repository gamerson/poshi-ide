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

package com.liferay.poshi.ide.ui.contentassist;

import org.eclipse.core.resources.IResource;
import org.eclipse.wst.xml.search.editor.contentassist.ResourceContentAssistAdditionalProposalInfoProvider;

/**
 * @author Terry Jia
 */
public class PoshiContentAssistAdditionalProposalInfoProvider
    extends ResourceContentAssistAdditionalProposalInfoProvider
{

    public String getDisplayText( String displayText, IResource resource )
    {
        String fullName = resource.getName();

        if( !fullName.equals( "" ) && fullName.contains( "." ) )
        {
            displayText = fullName.substring( 0, fullName.indexOf( "." ) );
        }

        return displayText;
    }
}
