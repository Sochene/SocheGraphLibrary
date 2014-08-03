/*
 * 	   Created by Sochene
 * 	   sochene.x@gmail.com
 * 
 * 	   Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.echo.holographlibrarysample;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

import com.echo.holographlibrary.Line;
import com.echo.holographlibrary.LineGraph;
import com.echo.holographlibrary.LineGraph.OnPointClickedListener;
import com.echo.holographlibrary.LinePoint;

public class LineActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_linegraph);
        LineGraph li = (LineGraph) findViewById(R.id.linegraph);
		final Resources resources = getResources();
        Line l = new Line();
        l.setUsingDips(true);
        LinePoint p = new LinePoint(0,0,5,2);
        p.setTip("5方");
        p.setLabel("09.30");
        p.setColor(resources.getColor(R.color.orange));
        l.addPoint(p);
        
        p = new LinePoint(1f,0f,2f,4);
        p.setTip("2方");
        p.setLabel("10.01");
        p.setColor(resources.getColor(R.color.orange));
        l.addPoint(p);
        
        p = new LinePoint(2,0,4,6);
        p.setTip("4方");
        p.setLabel("10.02");
        p.setColor(resources.getColor(R.color.orange));
        l.addPoint(p);
        
        p = new LinePoint(3,0,4,6);
        p.setTip("4方");
        p.setLabel("10.03");
        p.setColor(resources.getColor(R.color.orange));
        l.addPoint(p);
        
        p = new LinePoint(4,0,4,8);
        p.setTip("4方");
        p.setLabel("10.04");
        p.setColor(resources.getColor(R.color.orange));
        l.addPoint(p);
        
        p = new LinePoint(5,0,9,12);
        p.setTip("9方");
        p.setLabel("10.05");
        p.setColor(resources.getColor(R.color.orange));
        l.addPoint(p);
        
        p = new LinePoint(6,0,12,12);
        p.setTip("12方");
        p.setLabel("10.06");
        p.setColor(resources.getColor(R.color.orange));
        l.addPoint(p);
        
        p = new LinePoint(7,0,13,16);
        p.setTip("13方");
        p.setLabel("10.07");
        p.setColor(resources.getColor(R.color.orange));
        l.addPoint(p);
        
        p = new LinePoint(8,0,5,18);
        p.setTip("5方");
        p.setLabel("10.08");
        p.setColor(resources.getColor(R.color.orange));
        l.addPoint(p);
        
        p = new LinePoint(9,0,0.8f,30);
        p.setTip("0.8方");
        p.setLabel("10.09");
        p.setColor(resources.getColor(R.color.orange));
        l.addPoint(p);
        
        
        l.setColor(resources.getColor(R.color.orange));
        l.setStrokeWidth(3);
        li.setUsingDips(true);
        li.setLine(l);
        li.setRangeY(0, 20);
        
        li.setOnPointClickedListener(new OnPointClickedListener() {

            @Override
            public void onClick( int pointIndex) {

            }
        });
	}

    
}
