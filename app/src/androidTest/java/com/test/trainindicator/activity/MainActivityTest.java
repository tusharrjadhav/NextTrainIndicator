package com.test.trainindicator.activity;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.test.trainindicator.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerviewIndicators),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction wrapContentViewPager = onView(
                allOf(withId(R.id.wrap_content_viewpager),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.wcviewpager),
                                        0),
                                0),
                        isDisplayed()));
        wrapContentViewPager.perform(swipeLeft());

        ViewInteraction wrapContentViewPager2 = onView(
                allOf(withId(R.id.wrap_content_viewpager),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.wcviewpager),
                                        0),
                                0),
                        isDisplayed()));
        wrapContentViewPager2.perform(swipeLeft());

        ViewInteraction wrapContentViewPager3 = onView(
                allOf(withId(R.id.wrap_content_viewpager),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.wcviewpager),
                                        0),
                                0),
                        isDisplayed()));
        wrapContentViewPager3.perform(swipeRight());

    }

    @Test
    public void testTitle() {
        Assert.assertEquals("Next Train", mActivityTestRule.getActivity().getTitle());
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
