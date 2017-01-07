import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityUnitTestCase;

import com.udacity.gradle.builditbigger.JokeFetcher;
import com.udacity.gradle.builditbigger.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

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

        final CountDownLatch mCountDownLatch = new CountDownLatch(1);
        JokeFetcher mJokeFetcher = new JokeFetcher();
        mJokeFetcher.setCountDownLatch(mCountDownLatch);
        mJokeFetcher.execute("http://" + MainActivity.host + ":" + MainActivity.port + "/_ah/api/myApi/v1/joke");

        try {
            mCountDownLatch.await();
            assertTrue(mJokeFetcher.get() != null || mJokeFetcher.get() != "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
