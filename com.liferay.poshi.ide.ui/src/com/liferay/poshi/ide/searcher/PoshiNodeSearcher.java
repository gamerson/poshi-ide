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

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.xml.search.editor.contentassist.IContentAssistProposalRecorder;
import org.eclipse.wst.xml.search.editor.references.IXMLReferenceTo;
import org.eclipse.wst.xml.search.editor.references.IXMLReferenceToXML;
import org.eclipse.wst.xml.search.editor.searchers.xml.HyperlinkCollectorForXML;
import org.eclipse.wst.xml.search.editor.searchers.xml.XMLSearcherForXML;
import org.eclipse.wst.xml.search.editor.util.XMLSearcherForXMLUtils;

/**
 * @author Terry Jia
 */
public class PoshiNodeSearcher extends XMLSearcherForXML
{

    public void searchForCompletion(
        Object selectedNode, String mathingString, String forceBeforeText, String forceEndText, IFile file,
        IXMLReferenceTo referenceTo, IContentAssistProposalRecorder recorder )
    {

        PoshiNodeAssisitCollectorForXML collector =
            new PoshiNodeAssisitCollectorForXML(
                forceBeforeText, forceEndText, (IXMLReferenceToXML) referenceTo, recorder );

        if( mathingString.contains( "#" ) )
        {
            mathingString = mathingString.substring( mathingString.indexOf( "#" ) + 1, mathingString.length() );
        }

        XMLSearcherForXMLUtils.search( selectedNode, mathingString, file, referenceTo, collector, true );
    }

    public void searchForHyperlink(
        Object selectedNode, int offset, String mathingString, int startOffset, int endOffset, IFile file,
        IXMLReferenceTo referenceTo, IRegion hyperlinkRegion, List<IHyperlink> hyperLinks, ITextEditor textEditor )
    {
        HyperlinkCollectorForXML collector =
            new HyperlinkCollectorForXML( hyperlinkRegion, hyperLinks, startOffset, endOffset );

        if( mathingString.contains( "#" ) )
        {
            mathingString = mathingString.substring( mathingString.indexOf( "#" ) + 1, mathingString.length() );
        }

        XMLSearcherForXMLUtils.search( selectedNode, mathingString, file, referenceTo, collector, false );
    }

}
