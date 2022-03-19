package com.example.a4350_project_1;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.a4350_project_1", appContext.getPackageName());
    }

    @Rule
    public ActivityScenarioRule<HeadActivity> activityRule =
            new ActivityScenarioRule<>(HeadActivity.class);

    @Test
    public void testingTextOnHeadActivity() {
        onView(withText("Lifestyle App")).check(matches(isDisplayed()));
        onView(withText("Estimated BMR:")).check(matches(isDisplayed()));
        onView(withText("Calories to hit goal:")).check(matches(isDisplayed()));
    }

    @Test
    public void testingPressingProfileModuleAndText() {
        onView(withText("Profile"))
                .perform(click());

        // Then the Profile screen is displayed
        onView(withText("Name:"))
                .check(matches(isDisplayed()));
        onView(withText("Age:"))
                .check(matches(isDisplayed()));
        onView(withText("Location:"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testingPressingWeatherModuleAndText() {
        onView(withText("Check Weather Today"))
                .perform(click());

        // Then the Weather screen is displayed
        onView(withText("Temperature:"))
                .check(matches(isDisplayed()));
        onView(withText("Humidity:"))
                .check(matches(isDisplayed()));
        onView(withText("Pressure:"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void goToBMIFragmentThenBack() {
        onView(withText("Lifestyle App")).check(matches(isDisplayed()));

        // go to bmi fragment
        onView(withText("Check BMI")).perform(click());
        pressBack();

        onView(withText("Lifestyle App")).check(matches(isDisplayed()));
    }

}