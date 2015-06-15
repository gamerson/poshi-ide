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

package com.liferay.poshi.ide.query.staticvalue;

import org.eclipse.wst.xml.search.core.statics.DefaultStaticValueVisitor;
import org.eclipse.wst.xml.search.core.statics.StaticValueQuerySpecification;

/**
 * @author Terry Jia
 */
public class SeleniumQuerySpecification extends StaticValueQuerySpecification
{

    private static DefaultStaticValueVisitor visitor = new DefaultStaticValueVisitor();

    private final String[] seleniumCommands = { "addSelection", "antCommand", "assertAlertNotPresent", "assertChecked",
        "assertConfirmation", "assertConsoleTextNotPresent", "assertConsoleTextPresent",
        "assertHTMLSourceTextNotPresent", "assertHTMLSourceTextPresent", "assertLiferayErrors", "assertLocation",
        "assertNotChecked", "assertNotLocation", "assertNotPartialText", "assertNotValue", "assertNotVisible",
        "assertJavaScriptErrors", "assertSelectedLabel", "assertTextNotPresent", "assertTextPresent",
        "assertPartialConfirmation", "assertPartialText", "assertText", "assertValue", "assertVisible", "click",
        "clickAt", "clickAtAndWait", "close", "copyText", "doubleClickAt", "dragAndDrop", "dragAndDropToObject",
        "isChecked", "isElementNotPresent", "isElementPresent", "isElementPresentAfterWait", "isHTMLSourceTextPresent",
        "isNotChecked", "isNotText", "isNotValue", "isNotVisible", "isPartialText", "isText", "isValue", "isVisible",
        "keyPress", "makeVisible", "mouseDown", "mouseDownAt", "mouseMoveAt", "mouseOver", "mouseRelease", "mouseUp",
        "mouseUpAt", "open", "openWindow", "paste", "pause", "refresh", "scrollWebElementIntoView", "select",
        "selectAndWait", "selectFrame", "selectPopUp", "selectWindow", "setWindowSize", "sendKeys",
        "sendKeysAceEditor", "sikuliAssertElementNotPresent", "sikuliAssertElementPresent", "sikuliClick",
        "sikuliDragAndDrop", "sikuliLeftMouseDown", "sikuliLeftMouseUp", "sikuliMouseMove", "sikuliRightMouseDown",
        "sikuliRightMouseUp", "sikuliType", "sikuliUploadCommonFile", "sikuliUploadTCatFile", "sikuliUploadTempFile",
        "type", "typeAceEditor", "typeCKEditor", "typeFrame", "typeScreen", "uploadCommonFile", "uploadFile",
        "uploadTempFile", "waitForConfirmation", "waitForElementNotPresent", "waitForElementPresent",
        "waitForNotPartialText", "waitForNotValue", "waitForNotVisible", "waitForPartialText", "waitForPopUp",
        "waitForText", "waitForTextNotPresent", "waitForValue", "waitForVisible" };

    public SeleniumQuerySpecification()
    {
        super( visitor );

        for( String seleniumCommand : seleniumCommands )
        {
            visitor.registerValue( seleniumCommand, "The " + seleniumCommand + " command" );
        }
    }

}
