package com.test.trainindicator.activity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;

/**
 * Created by Tushar_temp on 11/12/17
 */
public class TrainSchedulePresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private TrainScheduleView view;

    private TrainSchedulePresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new TrainSchedulePresenter(view);
    }

    @Test
    public void testStartTrainScheduleUpdate() throws Exception {
        presenter.startTrainScheduleUpdate(15);
        //Adding delay for execute timer
        verify(view, Mockito.after(100)).updateUI(anyList());
    }

    @Test
    public void testRefreshTrainScheduleTimeFrame() throws Exception {
        presenter.refreshTrainScheduleTimeFrame(15);
        //Adding delay for execute timer
        verify(view, Mockito.after(100)).updateUI(anyList());
    }
}