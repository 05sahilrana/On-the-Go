package  com.example.onthego;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        // Create and set up the adapter for ViewPager
        NewsPagerAdapter adapter = new NewsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // Connect ViewPager and TabLayout
        tabLayout.setupWithViewPager(viewPager);
    }
}
