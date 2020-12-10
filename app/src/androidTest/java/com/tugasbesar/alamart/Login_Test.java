package com.tugasbesar.alamart;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Login_Test {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void login_Test() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.btnLogin), withText("Masuk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_layout),
                                        0),
                                5),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.inputEmail),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputEmailLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(click());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.inputEmail),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputEmailLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("evan"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btnLogin), withText("Masuk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_layout),
                                        0),
                                5),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.inputEmail), withText("evan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputEmailLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(click());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.inputEmail), withText("evan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputEmailLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("evan@gmail.com"));

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.inputEmail), withText("evan@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputEmailLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText5.perform(closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.btnLogin), withText("Masuk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_layout),
                                        0),
                                5),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.inputPassword),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputPasswordLayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText6.perform(replaceText("12312"), closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.btnLogin), withText("Masuk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_layout),
                                        0),
                                5),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.inputPassword), withText("12312"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputPasswordLayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText7.perform(replaceText("123123"));

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.inputPassword), withText("123123"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputPasswordLayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText8.perform(closeSoftKeyboard());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.btnLogin), withText("Masuk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_layout),
                                        0),
                                5),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction textInputEditText9 = onView(
                allOf(withId(R.id.inputEmail), withText("evan@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputEmailLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText9.perform(replaceText("evanrisky@gmail.com"));

        ViewInteraction textInputEditText10 = onView(
                allOf(withId(R.id.inputEmail), withText("evanrisky@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputEmailLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText10.perform(closeSoftKeyboard());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.btnLogin), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_layout),
                                        0),
                                5),
                        isDisplayed()));
        materialButton6.perform(click());

        ViewInteraction textInputEditText11 = onView(
                allOf(withId(R.id.inputPassword), withText("123123"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputPasswordLayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText11.perform(replaceText("12341234"));

        ViewInteraction textInputEditText12 = onView(
                allOf(withId(R.id.inputPassword), withText("12341234"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputPasswordLayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText12.perform(closeSoftKeyboard());

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.btnLogin), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_layout),
                                        0),
                                5),
                        isDisplayed()));
        materialButton7.perform(click());
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
