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
import android.graphics.Color;
import android.os.Bundle;

import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieGraph.OnSliceClickedListener;
import com.echo.holographlibrary.PieSlice;

public class PieActvity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_piegraph);
        PieGraph pg = (PieGraph)findViewById(R.id.piegraph);
        ArrayList<PieSlice> sliceList = new ArrayList<PieSlice>();
        PieSlice slice = new PieSlice();
        slice.setColor(Color.rgb(197, 212, 27));
        slice.setValue(2);
        sliceList.add(slice);
        
        slice = new PieSlice();
        slice.setColor(Color.rgb(254, 152, 43));
        slice.setValue(2);
        sliceList.add(slice);
        
        slice = new PieSlice();
        slice.setColor(Color.rgb(253, 86, 53));
        slice.setValue(3);
        sliceList.add(slice);
        
        slice = new PieSlice();
        slice.setColor(Color.rgb(221, 77, 121));
        slice.setValue(8);
        sliceList.add(slice);
        pg.setSlices(sliceList);
        pg.setOnSliceClickedListener(new OnSliceClickedListener() {

            @Override
            public void onClick(int index) {

            }

        });
	}
}
