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

package com.liferay.poshi.ide.searcher;

import org.eclipse.swt.graphics.Image;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.eclipse.wst.xml.search.core.IXMLSearchDOMNodeCollector;
import org.eclipse.wst.xml.search.core.util.DOMUtils;
import org.eclipse.wst.xml.search.core.util.StringUtils;
import org.eclipse.wst.xml.search.editor.contentassist.IContentAssistAdditionalProposalInfoProvider;
import org.eclipse.wst.xml.search.editor.contentassist.IContentAssistProposalRecorder;
import org.eclipse.wst.xml.search.editor.references.IXMLReferenceToXML;
import org.eclipse.wst.xml.search.editor.searchers.AbstractContentAssisitCollector;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * @author Terry Jia
 */
@SuppressWarnings( "restriction" )
public class PoshiNodeAssisitCollectorForXML extends AbstractContentAssisitCollector<IXMLReferenceToXML>
    implements IXMLSearchDOMNodeCollector
{

    public PoshiNodeAssisitCollectorForXML(
        String forceBeforeText, String forceEndText, IXMLReferenceToXML referencePath,
        IContentAssistProposalRecorder recorder )
    {
        super( forceBeforeText, forceEndText, referencePath, recorder );
    }

    public boolean add( IDOMNode node )
    {
        collect( recorder, node, referencePath );

        return true;
    }

    private void collect( final IContentAssistProposalRecorder recorder, IDOMNode node, IXMLReferenceToXML referencePath )
    {
        String value = null;

        int nodeType = node.getNodeType();

        switch( nodeType )
        {
        case Node.ATTRIBUTE_NODE:
            value = ( (Attr) node ).getValue();
            break;
        case Node.TEXT_NODE:
            value = DOMUtils.getTextContent( (Text) node );
            break;
        case Node.ELEMENT_NODE:
            String targetNode = referencePath.getTargetNodes()[0];
            if( targetNode.startsWith( "@" ) )
            {
                String attrName = targetNode.substring( 1, targetNode.length() );
                String attrValue = ( (Element) node ).getAttribute( attrName );
                value = attrValue;
            }
            else
            {
                // text node
                Element element = ( (Element) node );
                Node firstChild = element.getFirstChild();
                if( firstChild != null && firstChild.getNodeType() == Node.TEXT_NODE )
                {
                    value = DOMUtils.getTextContent( (Text) firstChild );
                }
            }
            break;
        }

        if( value == null )
        {
            return;
        }

        String fileName = DOMUtils.getFileName( (IDOMNode) node );

        fileName = fileName.substring( 0, fileName.indexOf( "." ) );

        Image image = null;
        int relevance = 1000;
        String displayText = value;
        String replaceText = fileName + "#" + value;

        Object proposedObject = null;

        IContentAssistAdditionalProposalInfoProvider provider = referencePath.getAdditionalProposalInfoProvider();

        if( provider != null )
        {
            String newDisplayText = provider.getDisplayText( displayText, node );

            if( !StringUtils.isEmpty( newDisplayText ) )
            {
                displayText = newDisplayText;
            }

            image = provider.getImage( node );

            proposedObject = provider.getTextInfo( node );
        }

        recorder.recordProposal( image, relevance, displayText, replaceText, proposedObject );
    }

}