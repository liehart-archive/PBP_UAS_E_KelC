package com.tugasbesar.alamart;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.tugasbesar.alamart.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.inputEmail),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputEmailLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("vriyashartama@gmail.com"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.inputPassword),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputPasswordLayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("123123"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.btnLogin), withText("Masuk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_layout),
                                        0),
                                5),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.inputEmail), withText("vriyashartama@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputEmailLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("vriyashaa"));

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.inputEmail), withText("vriyashaa"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputEmailLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btnLogin), withText("Masuk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_layout),
                                        0),
                                5),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.inputEmail), withText("vriyashaa"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputEmailLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText5.perform(replaceText("vriyashaa@gmail.com"));

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.inputEmail), withText("vriyashaa@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputEmailLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText6.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.inputPassword), withText("123123"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputPasswordLayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText7.perform(replaceText("123"));

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.inputPassword), withText("123"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputPasswordLayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText8.perform(closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.btnLogin), withText("Masuk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_layout),
                                        0),
                                5),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction textInputEditText9 = onView(
                allOf(withId(R.id.inputEmail), withText("vriyashaa@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputEmailLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText9.perform(replaceText("vriyashartama@gmail.com"));

        ViewInteraction textInputEditText10 = onView(
                allOf(withId(R.id.inputEmail), withText("vriyashartama@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputEmailLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText10.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText11 = onView(
                allOf(withId(R.id.inputPassword), withText("123"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputPasswordLayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText11.perform(replaceText("123123"));

        ViewInteraction textInputEditText12 = onView(
                allOf(withId(R.id.inputPassword), withText("123123"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputPasswordLayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText12.perform(closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.btnLogin), withText("Masuk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_layout),
                                        0),
                                5),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction textInputEditText13 = onView(
                allOf(withId(R.id.inputPassword), withText("123123"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputPasswordLayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText13.perform(replaceText("1231231231231231231231231"));

        ViewInteraction textInputEditText14 = onView(
                allOf(withId(R.id.inputPassword), withText("1231231231231231231231231"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputPasswordLayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText14.perform(closeSoftKeyboard());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.btnLogin), withText("Masuk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_layout),
                                        0),
                                5),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction textInputEditText15 = onView(
                allOf(withId(R.id.inputEmail), withText("vriyashartama@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputEmailLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText15.perform(replaceText("vriyashartam22a@gmail.com"));

        ViewInteraction textInputEditText16 = onView(
                allOf(withId(R.id.inputEmail), withText("vriyashartam22a@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputEmailLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText16.perform(closeSoftKeyboard());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.btnLogin), withText("Masuk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_layout),
                                        0),
                                5),
                        isDisplayed()));
        materialButton6.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
