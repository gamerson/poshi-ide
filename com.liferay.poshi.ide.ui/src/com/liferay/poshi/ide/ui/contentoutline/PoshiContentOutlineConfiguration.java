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

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.ui.internal.IReleasable;
import org.eclipse.wst.xml.ui.views.contentoutline.XMLContentOutlineConfiguration;

/**
 * @author Terry Jia
 */
@SuppressWarnings( "restriction" )
public class PoshiContentOutlineConfiguration extends XMLContentOutlineConfiguration
{

    private ActionManagerMenuListener fContextMenuFiller = null;

    private class ActionManagerMenuListener implements IMenuListener, IReleasable
    {

        private PoshiNodeActionManager fActionManager;
        private TreeViewer fTreeViewer;

        public ActionManagerMenuListener( TreeViewer viewer )
        {
            fTreeViewer = viewer;
        }

        public void menuAboutToShow( IMenuManager manager )
        {
            if( fActionManager == null )
            {
                fActionManager = new PoshiNodeActionManager( (IStructuredModel) fTreeViewer.getInput(), fTreeViewer );
            }

            if( fActionManager != null )
            {
                fActionManager.fillContextMenu( manager, fTreeViewer.getSelection() );
            }
        }

        public void release()
        {
            fTreeViewer = null;

            if( fActionManager != null )
            {
                fActionManager.setModel( null );
            }
        }
    }

    public IMenuListener getMenuListener( TreeViewer viewer )
    {
        if( fContextMenuFiller == null )
        {
            fContextMenuFiller = new ActionManagerMenuListener( viewer );
        }

        return fContextMenuFiller;
    }

    public void unconfigure( TreeViewer viewer )
    {
        super.unconfigure( viewer );

        if( fContextMenuFiller != null )
        {
            fContextMenuFiller.release();
            fContextMenuFiller = null;
        }
    }

}
