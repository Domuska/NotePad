package com.nononsenseapps.appium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TestAddNewNoteShouldShowNameInNotesScreen extends BaseTestClass{

    @Test
    public void testAddNewNoteShouldShowNameInNotesScreen(){

        AppiumHelper.closeDrawer(driver);
        AppiumHelper.createNewNoteWithName(driver, noteName1);
        AppiumHelper.navigateUp(driver);

        driverWait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//*[@text='"+ noteName1 + "']")
                )
        );

        //assert the element is visible
        AppiumHelper.assertVisibleText(noteName1);
    }
}
