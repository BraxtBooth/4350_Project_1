package com.example.a4350_project_1;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.io.IOException;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
//    @Test
//    public void useAppContext() {
//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        assertEquals("com.example.a4350_project_1", appContext.getPackageName());
//    }
//
//    @Rule
//    public ActivityScenarioRule<HeadActivity> activityRule =
//            new ActivityScenarioRule<>(HeadActivity.class);
//
//    @Test
//    public void testingTextOnHeadActivity() {
//        onView(withText("Lifestyle App")).check(matches(isDisplayed()));
//        onView(withText("Estimated BMR:")).check(matches(isDisplayed()));
//        onView(withText("Calories to hit goal:")).check(matches(isDisplayed()));
//    }
//
////    @Test
////    public void testingPressingProfileModuleAndText() {
////        onView(withText("Profile"))
////                .perform(click());
////
////        // Then the Profile screen is displayed
////        onView(withText("Name:"))
////                .check(matches(isDisplayed()));
////        onView(withText("Age:"))
////                .check(matches(isDisplayed()));
////        onView(withText("Location:"))
////                .check(matches(isDisplayed()));
////    }
//
//    @Test
//    public void testingPressingWeatherModuleAndText() {
//        onView(withText("Check Weather Today"))
//                .perform(click());
//
//        // Then the Weather screen is displayed
//        onView(withText("Temperature:"))
//                .check(matches(isDisplayed()));
//        onView(withText("Humidity:"))
//                .check(matches(isDisplayed()));
//        onView(withText("Pressure:"))
//                .check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void goToBMIFragmentThenBack() {
//        onView(withText("Lifestyle App")).check(matches(isDisplayed()));
//
//        // go to bmi fragment
//        onView(withText("Check BMI")).perform(click());
//        pressBack();
//
//        onView(withText("Lifestyle App")).check(matches(isDisplayed()));
//    }

    /* Added Tests to check the functionality of the database */
//    private Repository repository;
    private Database db;
    private UserDao userDao;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, Database.class).build();
        userDao = db.userDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void insertUserAndRead() throws Exception{
        UserTable user = new UserTable("whatever@email.com", true, "Joe", 33,
                "Denver, US", 6, 1, 176, 0, 0, 0,
                0, "dummy_image_URI");

        Database.databaseExecutor.execute(() -> {
            userDao.insert(user);
            LiveData<UserTable> byName = userDao.findUserByName("Joe");
            assertEquals(user, byName);
        });
    }

    @Test
    public void insertUserAndDelete() throws Exception{
        UserTable user = new UserTable("whatever@email.com", true, "Joe", 33,
                "Denver, US", 6, 1, 176, 0, 0, 0,
                0, "dummy_image_URI");

        Database.databaseExecutor.execute(() -> {
            userDao.insert(user);
            userDao.delete(user);
            assertEquals(null, userDao.findUserByName("Joe"));
        });
    }

    @Test
    public void queryByName() throws Exception{
        UserTable user = new UserTable("whatever@email.com", true, "Joe", 33,
                "Denver, US", 6, 1, 176, 0, 0, 0,
                0, "dummy_image_URI");


        Database.databaseExecutor.execute(() -> {
            userDao.insert(user);
            LiveData<UserTable> byName = userDao.findUserByName("Joe");
            assertEquals(byName.getValue().getName(), "Joe");
        });
    }

    @Test
    public void queryByAge() throws Exception{
        UserTable user2 = new UserTable("maria@email.com", false, "Maria", 19,
                "Jacksonville, US", 5, 3, 120, 1, 0, 0,
                0, "dummy_image_URI2");

        Database.databaseExecutor.execute(() -> {
            userDao.insert(user2);
            LiveData<UserTable> byAge = userDao.findUserByAge(19);
            assertEquals(byAge.getValue().getAge(), 19);
        });
    }


    @Test
    public void queryByEmail() throws Exception{
        UserTable user3 = new UserTable("lol@email.com", false, "John", 23,
                "Salt Lake City, US", 6, 3, 167, 0, 1, 0,
                0, "dummy_image_URI3");

        Database.databaseExecutor.execute(() -> {
            userDao.insert(user3);
            LiveData<UserTable> byEmail = userDao.findUserByEmail("lol@email.com");
            assertEquals(byEmail.getValue().getEmail(), "lol@email.com");
        });
    }

}