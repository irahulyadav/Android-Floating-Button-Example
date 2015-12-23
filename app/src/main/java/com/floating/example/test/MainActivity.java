package com.floating.example.test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.floating.example.test.controller.SpeedDial;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SpeedDial.ExpandListener {
    private SpeedDial speedDial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speedDial = new SpeedDial((FloatingActionButton) findViewById(R.id.fab), (ViewGroup) findViewById(R.id.fab_container));
        speedDial.setExpandListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() != R.id.fab) {
            speedDial.getFab().performClick();
        }
    }


    @Override
    public void onExpand(FloatingActionButton button) {
        button.setImageResource(R.drawable.minus);
    }

    @Override
    public void onCollapse(FloatingActionButton button) {
        button.setImageResource(R.drawable.plus);
    }
}
