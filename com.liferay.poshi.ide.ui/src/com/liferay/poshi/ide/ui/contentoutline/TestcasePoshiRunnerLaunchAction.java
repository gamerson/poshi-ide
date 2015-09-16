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

import org.w3c.dom.Node;

/**
 * @author Terry Jia
 */
public class TestcasePoshiRunnerLaunchAction extends BaseRunAsAction
{

    public TestcasePoshiRunnerLaunchAction( File testcaseFile, Node node, String argument )
    {
        super( "Testcase Poshi Runner Launch", testcaseFile, node, argument );

        this.buildXmlFile = "build-test.xml";
        this.target = "run-poshi-runner";
    }

}
