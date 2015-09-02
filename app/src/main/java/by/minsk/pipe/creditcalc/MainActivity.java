package by.minsk.pipe.creditcalc;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



import com.github.mikephil.charting.charts.PieChart;

import butterknife.ButterKnife;
import butterknife.InjectView;
import by.minsk.pipe.creditcalc.Fragments.CalcPay;
import by.minsk.pipe.creditcalc.Fragments.CreditList;
import by.minsk.pipe.creditcalc.Fragments.MakeCredit;
import by.minsk.pipe.creditcalc.Fragments.PieChartFr;
import by.minsk.pipe.creditcalc.Logic.Actual;


public class MainActivity extends FragmentActivity implements View.OnClickListener {


    private Actual actual = new Actual();

    @InjectView(R.id.button1) Button btnMake;
    @InjectView(R.id.button2) Button btnAdd;
    @InjectView(R.id.button3) Button btnList;
    @InjectView(R.id.button4) Button btnPie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        btnMake.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnList.setOnClickListener(this);
        btnPie.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button1:
                getFragmentManager().beginTransaction().replace(R.id.temp_container, new MakeCredit(),MakeCredit.TAG).commit();
                break;
            case R.id.button2:
                getFragmentManager().beginTransaction().replace(R.id.temp_container, CalcPay.newInstance(actual),CalcPay.TAG).commit();
                break;
            case R.id.button3:
                getFragmentManager().beginTransaction().replace(R.id.temp_container, new CreditList(),CreditList.TAG).commit();
                break;
            case R.id.button4:
                getFragmentManager().beginTransaction().replace(R.id.temp_container, new PieChartFr(),PieChartFr.TAG).commit();
                break;
        }
    }
}


