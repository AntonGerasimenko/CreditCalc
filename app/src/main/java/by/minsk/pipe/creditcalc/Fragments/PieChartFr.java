package by.minsk.pipe.creditcalc.Fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;

import butterknife.ButterKnife;
import butterknife.InjectView;
import by.minsk.pipe.creditcalc.R;

/**
 * Created by gerasimenko on 02.09.2015.
 */
public class PieChartFr extends Fragment {

    public static final String TAG = "PieChartFr";
    private PieChart mChart;

    @InjectView(R.id.pie_title)    TextView title;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pie_chart, container, false);
        ButterKnife.inject(this,view);

        title.setText("Title");

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {


        pieChart();
        super.onActivityCreated(savedInstanceState);
    }

    private void pieChart(){

        View view = getView();
        if (view == null) return;

        mChart = (PieChart) view.findViewById(R.id.chart1);

        mChart.setUsePercentValues(true);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);

        mChart.setCenterText("Hello");
    }
}
