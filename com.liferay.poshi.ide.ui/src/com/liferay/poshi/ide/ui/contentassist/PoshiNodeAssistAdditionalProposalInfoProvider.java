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

import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.eclipse.wst.xml.search.core.util.DOMUtils;
import org.eclipse.wst.xml.search.editor.contentassist.DefaultDOMContentAssistAdditionalProposalInfoProvider;
import org.w3c.dom.Node;

/**
 * @author Terry Jia
 */
@SuppressWarnings( "restriction" )
public class PoshiNodeAssistAdditionalProposalInfoProvider
    extends DefaultDOMContentAssistAdditionalProposalInfoProvider
{

    @Override
    public String getDisplayText( String displayText, Node node )
    {
        String fileName = DOMUtils.getFileName( (IDOMNode) node );

        fileName = fileName.substring( 0, fileName.indexOf( "." ) );

        return fileName + "#" + displayText;
    }

}
