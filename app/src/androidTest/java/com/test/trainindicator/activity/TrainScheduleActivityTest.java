package com.test.trainindicator.activity;


import android.os.SystemClock;
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
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TrainScheduleActivityTest {

    private ViewInteraction editText;

    private ViewInteraction refreshButton;

    private ViewInteraction emptyTextView;

    private ViewInteraction viewPager;

    @Rule
    public ActivityTestRule<TrainScheduleActivity> mActivityTestRule = new ActivityTestRule<>(TrainScheduleActivity.class);

    @Before
    public void setUp() {

        editText = onView(
                allOf(withId(R.id.editTimeFrame),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));


        refreshButton = onView(
                allOf(withId(R.id.refreshBt), withText("Refresh"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                2),
                        isDisplayed()));


        emptyTextView = onView(
                withId(R.id.empty_msg));


        viewPager = onView(
                withId(R.id.wcviewpager));
        SystemClock.sleep(200);
    }

    @Test
    public void trainScheduleForZeroTimeFrame() {

        editText.perform(replaceText("0"));

        refreshButton.perform(click());
        SystemClock.sleep(200);
        viewPager.check(matches(not(isDisplayed())));
        emptyTextView.check(matches(isDisplayed()));

    }

    @Test
    public void trainScheduleNonEmpty() {
        editText.perform(replaceText("60"));
        refreshButton.perform(click());
        SystemClock.sleep(200);
        viewPager.check(matches(isDisplayed()));
        emptyTextView.check(matches(not(isDisplayed())));
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
