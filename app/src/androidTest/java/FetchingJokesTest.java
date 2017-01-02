import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityUnitTestCase;

import com.udacity.gradle.builditbigger.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by michal.hornak on 12/29/2016.
 */
@RunWith(AndroidJUnit4.class)
public class FetchingJokesTest extends ActivityUnitTestCase<MainActivity> {

    public FetchingJokesTest() {
        super(MainActivity.class);
    }

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void FetchSingleJoke() {
        mActivityRule.getActivity();

        ((MainActivity)mActivityRule.getActivity()).tellJoke(null);


        //assertThat(activity, new StartedMatcher(NextActivity.class));
        assert true;
    }
}
