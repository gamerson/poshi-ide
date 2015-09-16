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

package com.liferay.poshi.ide.ui.contentoutline;

import java.io.File;
import java.util.List;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.xml.ui.internal.contentoutline.XMLNodeActionManager;
import org.w3c.dom.Node;

import com.liferay.poshi.ide.ui.util.LaunchUtil;

/**
 * @author Terry Jia
 */
@SuppressWarnings( "restriction" )
public class PoshiNodeActionManager extends XMLNodeActionManager
{

    public PoshiNodeActionManager( IStructuredModel model, Viewer viewer )
    {
        super( model, viewer );
    }

    public void contributeActions( IMenuManager menu, List selection )
    {
        super.contributeActions( menu, selection );

        if( selection.size() == 1 )
        {
            Node node = (Node) selection.get( 0 );

            contributeRunAsActions( menu, node );
        }
    }

    protected void contributeRunAsActions( IMenuManager menu, Node node )
    {
        contributeEditGrammarInformationActions( menu, node );

        File file = new File( this.getModel().getBaseLocation() );

             IMenuManager addRunAsMenu = new MyMenuManager( "Run As" );
        menu.add( addRunAsMenu );

        contributeAction( addRunAsMenu, new TestcasePoshiRunnerLaunchAction( file, node) );
        contributeAction( addRunAsMenu, new TestcaseTomcatLaunchAction( file, node) );
        contributeAction( addRunAsMenu, new TestcaseLaunchAction( file, node) );
        contributeAction( addRunAsMenu, new RunSeleniumTestLaunchAction( file, node) );
        contributeAction( addRunAsMenu, new RunSeleniumTomcatLaunchAction( file, node) );
    }
}
