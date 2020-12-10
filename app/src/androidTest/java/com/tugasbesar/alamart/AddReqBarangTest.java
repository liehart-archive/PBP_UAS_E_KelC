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
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddReqBarangTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addReqBarangTest() {
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.inputEmail),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputEmailLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("evanrisky@gmail.com"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.inputPassword),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputPasswordLayout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("12341234"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.btnLogin), withText("Masuk"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_layout),
                                        0),
                                5),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.request_page), withContentDescription("Request Barang"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation),
                                        0),
                                3),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab), withContentDescription("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_layout),
                                        0),
                                3),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btnEdit), withText("ADD"),
                        childAtPosition(
                                allOf(withId(R.id.footer),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.etNama),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.twNama),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(click());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.etNama),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.twNama),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("Ps"), closeSoftKeyboard());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.etHarga),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.twHarga),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText5.perform(replaceText("10000"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.btnEdit), withText("ADD"),
                        childAtPosition(
                                allOf(withId(R.id.footer),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.etKetrangan),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.twKeterangan),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText6.perform(replaceText("Kosong"), closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.btnEdit), withText("ADD"),
                        childAtPosition(
                                allOf(withId(R.id.footer),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        materialButton4.perform(click());

        pressBack();
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
