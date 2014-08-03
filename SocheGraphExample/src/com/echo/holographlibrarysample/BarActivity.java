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

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.echo.holographlibrary.BarGraph.OnBarClickedListener;

public class BarActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_bargraph);
		
		final Resources resources = getResources();
        ArrayList<Bar> points = new ArrayList<Bar>();
        Bar d1 = new Bar();
        d1.setColor(resources.getColor(R.color.green_light));
        d1.setName("T1");
        d1.setValue(0);
        d1.setDesValue(1000);
        d1.setValueString("$1,000");
        
        Bar d2 = new Bar();
        d2.setColor(resources.getColor(R.color.orange));
        d2.setName("T2");
        d2.setValue(0);
        d2.setDesValue(2000);
        d2.setValueString("$2,000");
        Bar d3 = new Bar();
        d3.setColor(resources.getColor(R.color.orange));
        d3.setName("T3");
        d3.setValue(0);
        d3.setDesValue(2500);
        d3.setValueString("$2,500");
        Bar d4 = new Bar();
        d4.setColor(resources.getColor(R.color.green_light));
        d4.setName("T4");
        d4.setValue(0);
        d4.setDesValue(500);
        d4.setValueString("$500");
        
        points.add(d1);
        points.add(d2);
        points.add(d3);
        points.add(d4);

        BarGraph g = (BarGraph) findViewById(R.id.bargraph);
        g.setBars(points);

        g.setOnBarClickedListener(new OnBarClickedListener() {

            @Override
            public void onClick(int index) {

            }

        });
	}
}
