/*
 * Copyright (C) 2013 salesforce.com, inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.auraframework.integration.test.root.event.locationchange;

import org.auraframework.integration.test.util.WebDriverTestCase;
import org.auraframework.integration.test.util.WebDriverTestCase.ExcludeBrowsers;
import org.auraframework.test.util.WebDriverUtil.BrowserType;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Tests that Verify the mechanics of Browser History Management. Location Change event is an APPLICATION event.
 * {@link aura.def.EventType} Which means that all handler registered to handle this event are invoked. NOTE: Location
 * change event is fired as soon as the page is loaded. Implementation is in AuraHistoryService_Private.js
 */
// History Service is not supported in IE7 or IE8
@ExcludeBrowsers({ BrowserType.IE8 })
public class BrowserHistoryManagementUITest extends WebDriverTestCase {

    /**
     * Verify the functionality of "aura.historyService.back()" and "aura.historyService.forward()". The component has 3
     * buttons. One to start the navigation and the other two to go back and forth using Aura History Service. The
     * locator string used in the aura.historyService.set() has an attribute value which is used to initialize the
     * location change event for this component. test:test_LocChng_Event is the event and it has a 'num' attribute. The
     * history service set() is setting the values of this attribute. The actions registered for Location Change event
     * handlers use this num. The number is extracted from the event.
     */
    @Test
    public void testNavigation() throws Exception {
        open("/test/test_LocChng_Navigation.app");
        int i = 0;
        String locationChangeIndicator = ".complete";
        String displayLocator = ".id";
        String nextLocator = ".Next";
        String backLocator = ".Back";
        // 1
        i = navigateForwardWithClientActionAndVerify(i);
        // 2
        i = navigateForwardWithClientActionAndVerify(i);
        // 3
        i = navigateForwardWithClientActionAndVerify(i);
        // 4
        i = navigateForwardWithClientActionAndVerify(i);

        findByCssSelector(backLocator).click();
        i--;
        // 3
        getAuraUITestingUtil().waitForElementDisplayed(By.cssSelector(locationChangeIndicator),
                "location change indicator not found");
        assertEquals("ButtonClicked", getAuraUITestingUtil().getEval("return window.aura.historyService.get().token"));
        assertEquals(Integer.toString(i), getAuraUITestingUtil().getEval("return window.aura.historyService.get().num"));
        assertEquals(Integer.toString(i), findByCssSelector(displayLocator).getText());
        findByCssSelector(backLocator).click();
        i--;
        // 2
        getAuraUITestingUtil().waitForElementDisplayed(By.cssSelector(locationChangeIndicator),
                "location change indicator not found");
        assertEquals(Integer.toString(i), findByCssSelector(displayLocator).getText());
        findByCssSelector(nextLocator).click();
        i++;
        // 3
        getAuraUITestingUtil().waitForElementDisplayed(By.cssSelector(locationChangeIndicator),
                "location change indicator not found");
        assertEquals(Integer.toString(i), findByCssSelector(displayLocator).getText());
        findByCssSelector(backLocator).click();
        i--;
        // 2
        getAuraUITestingUtil().waitForElementDisplayed(By.cssSelector(locationChangeIndicator),
                "location change indicator not found");
        assertEquals(Integer.toString(i), findByCssSelector(displayLocator).getText());
        findByCssSelector(nextLocator).click();
        i++;
        // 3
        getAuraUITestingUtil().waitForElementDisplayed(By.cssSelector(locationChangeIndicator),
                "location change indicator not found");
        assertEquals(Integer.toString(i), findByCssSelector(displayLocator).getText());
        findByCssSelector(nextLocator).click();
        i++;
        // 4
        getAuraUITestingUtil().waitForElementDisplayed(By.cssSelector(locationChangeIndicator),
                "location change indicator not found");
        assertEquals(Integer.toString(i), findByCssSelector(displayLocator).getText());

        // The user hasn't navigated further. So clicking next should still be
        // at the same page
        findByCssSelector(nextLocator).click();
        // 4
        assertEquals(Integer.toString(i), findByCssSelector(displayLocator).getText());
        findByCssSelector(nextLocator).click();
        // 4
        assertEquals(Integer.toString(i), findByCssSelector(displayLocator).getText());

        // Let's go back 4 times and click one more back to make sure we can't
        // go any further(backwards)
        findByCssSelector(backLocator).click();
        i--;
        // 3
        getAuraUITestingUtil().waitForElementDisplayed(By.cssSelector(locationChangeIndicator),
                "location change indicator not found");
        assertEquals(Integer.toString(i), findByCssSelector(displayLocator).getText());
        findByCssSelector(backLocator).click();
        i--;
        // 2
        getAuraUITestingUtil().waitForElementDisplayed(By.cssSelector(locationChangeIndicator),
                "location change indicator not found");
        assertEquals(Integer.toString(i), findByCssSelector(displayLocator).getText());
        findByCssSelector(backLocator).click();
        i--;
        // 1
        getAuraUITestingUtil().waitForElementDisplayed(By.cssSelector(locationChangeIndicator),
                "location change indicator not found");
        assertEquals(Integer.toString(i), findByCssSelector(displayLocator).getText());
        // Pressing back now only changes the URL to the starting location, when
        // there was don't click me.
        // It actually doesn't change the button label because the client action
        // is not written that way. Don't worry
        // about it
        findByCssSelector(backLocator).click();
        // 1
        getAuraUITestingUtil().waitForElementDisplayed(By.cssSelector(locationChangeIndicator),
                "location change indicator not found");
        assertEquals(Integer.toString(i), findByCssSelector(displayLocator).getText());

        findByCssSelector(nextLocator).click();
        // 1
        getAuraUITestingUtil().waitForElementDisplayed(By.cssSelector(locationChangeIndicator),
                "location change indicator not found");
        assertEquals(Integer.toString(i), findByCssSelector(displayLocator).getText());
        findByCssSelector(nextLocator).click();
        i++;
        // 2
        getAuraUITestingUtil().waitForElementDisplayed(By.cssSelector(locationChangeIndicator),
                "location change indicator not found");
        assertEquals(Integer.toString(i), findByCssSelector(displayLocator).getText());
        // Using the browser's back button
        getAuraUITestingUtil().getEval("window.history.back()");
        i--;
        // 1
        getAuraUITestingUtil().waitForElementDisplayed(By.cssSelector(locationChangeIndicator),
                "location change indicator not found");
        assertEquals(Integer.toString(i), findByCssSelector(displayLocator).getText());
    }

    private WebElement findByCssSelector(String selector) {
        return findDomElement(By.cssSelector(selector));
    }

    private int navigateForwardWithClientActionAndVerify(int index) throws Exception {
        findByCssSelector(".SimpleComponent").click();
        index++;
        getAuraUITestingUtil().waitForElementDisplayed(By.cssSelector(".complete"),
                "location change indicator not found");
        assertEquals("ButtonClicked", getAuraUITestingUtil().getEval("return window.aura.historyService.get().token"));
        assertEquals(Integer.toString(index), getAuraUITestingUtil().getEval("return window.aura.historyService.get().num"));
        assertEquals(Integer.toString(index), findByCssSelector(".id").getText());
        return index;
    }
}
