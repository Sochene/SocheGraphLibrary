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

package com.echo.holographlibrary;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PieGraph extends View {

	private static final int START_STEP_COUNT = 15;
	private static final int RO_STEP_COUNT = 10;
	private ArrayList<PieSlice> slices = new ArrayList<PieSlice>();
	private Paint paint = new Paint();
	private Path path = new Path();

	private int indexSelected = 0;
	private OnSliceClickedListener listener;
	private boolean drawCompleted = false;
	private boolean startAni = false;
	private int startCurStepCount = 0;
	private float newStartAngle = 0;
	private float curStartAngle = 0;
	private float roStep;//点击旋转动画步进
	private int roCurStepCount;//当前点击旋转动画步数
	private Bitmap fullImage;

	public PieGraph(Context context) {
		super(context);
		//thickness = (int) (75f * context.getResources().getDisplayMetrics().density);
	}

	public PieGraph(Context context, AttributeSet attrs) {
		super(context, attrs);
		//thickness = (int) (75f * context.getResources().getDisplayMetrics().density);
	}

	private void initAngle() {
		newStartAngle = 0;
		for (int i = 0; i < slices.size(); i++) {
			if (i == indexSelected) {
				newStartAngle += 0.5 * slices.get(i).getDesSweep();
				break;
			} else {
				newStartAngle += slices.get(i).getDesSweep();
			}
		}
		newStartAngle -= 90;
		newStartAngle = 360 - newStartAngle;
		newStartAngle = newStartAngle < 0 ? (newStartAngle + 360)
				: (newStartAngle % 360);
		curStartAngle = curStartAngle < 0 ? (curStartAngle + 360)
				: (curStartAngle % 360);
		if ((newStartAngle - curStartAngle) > 180) {
			roStep = (newStartAngle - curStartAngle - 360) / RO_STEP_COUNT;
		} else if ((newStartAngle - curStartAngle) < -180) {
			roStep = (newStartAngle - curStartAngle + 360) / RO_STEP_COUNT;
		} else {
			roStep = (newStartAngle - curStartAngle) / RO_STEP_COUNT;
		}
		roCurStepCount = 0;
	}

	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas ca) {
		if (fullImage == null || drawCompleted == false || startAni == true) {
			fullImage = Bitmap.createBitmap(getWidth(), getHeight(),
					Config.ARGB_8888);
			Canvas canvas = new Canvas(fullImage);
			canvas.drawColor(Color.TRANSPARENT);
			paint.reset();
			paint.setAntiAlias(true);
			float midX, midY, radius, innerRadius;
			path.reset();

			midX = getWidth() / 2;
			midY = getHeight() / 2;
			if (midX < midY) {
				radius = midX;
			} else {
				radius = midY;
			}
			radius -= 10; 
			innerRadius = radius/2;

			if(startAni != true){
				curStartAngle += roStep;
				roCurStepCount++;
			}
			float curAngle = curStartAngle;
			float currentSweep = 0;
			for (int i = 0; i < slices.size(); i++) {
				PieSlice slice = slices.get(i);
				currentSweep = slice.getSweep();
				Path p = new Path();
				paint.setColor(slice.getColor());

				if (indexSelected == i && roCurStepCount >= RO_STEP_COUNT && slice.getSweep() < 180 && startAni != true) {
					p.arcTo(new RectF(midX - radius, midY - radius+10, 
							midX + radius, midY + radius+10), curAngle ,
							currentSweep );
					p.arcTo(new RectF(midX - innerRadius, midY - innerRadius,
							midX + innerRadius, midY + innerRadius),
							curAngle  + currentSweep-1 ,
							-currentSweep +2);
					
					p.close();
				} else {
					p.arcTo(new RectF(midX - radius, midY - radius, midX
							+ radius, midY + radius), curAngle,
							currentSweep);
					p.arcTo(new RectF(midX - innerRadius, midY - innerRadius,
							midX + innerRadius, midY + innerRadius),
							curAngle + currentSweep,
							-currentSweep);
					p.close();
				}
				slice.setPath(p);
				slice.setRegion(new Region((int) (midX - radius),
						(int) (midY - radius), (int) (midX + radius),
						(int) (midY + radius)));
				canvas.drawPath(p, paint);
				curAngle = curAngle + currentSweep;
				if(startCurStepCount < START_STEP_COUNT){
					slice.setSweep(slice.getSweep() + slice.getStep());
				}else{
					startAni = false;
				}
			}

			float cirlceWidth = midX/16;
			paint.setColor(Color.BLACK);
			paint.setAlpha(80);
			paint.setAntiAlias(true);
			paint.setStrokeWidth(cirlceWidth);
			paint.setStyle(Style.STROKE);
			if(startAni == true){
				canvas.drawArc(new RectF(midX- (innerRadius+cirlceWidth/2), midY- (innerRadius+cirlceWidth/2), 
						midX+ innerRadius+cirlceWidth/2, midY+(innerRadius+cirlceWidth/2)),
						curStartAngle, 360/START_STEP_COUNT*startCurStepCount,
						false, paint);
			}else{
				canvas.drawCircle(midX, midY, innerRadius+cirlceWidth/2, paint);
			}
			
			startCurStepCount++;
		}
		ca.drawBitmap(fullImage, 0, 0, null);
		if (roCurStepCount >= RO_STEP_COUNT) {
			drawCompleted = true;
		} else {
			drawCompleted = false;
		}
		
		if(roCurStepCount < RO_STEP_COUNT || startAni == true){
			invalidate();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (drawCompleted) {

			Point point = new Point();
			point.x = (int) event.getX();
			point.y = (int) event.getY();

			int count = 0;
			for (PieSlice slice : slices) {
				Region r = new Region();
				r.setPath(slice.getPath(), slice.getRegion());
				if (r.contains((int) point.x, (int) point.y)
						&& event.getAction() == MotionEvent.ACTION_DOWN 
						&& indexSelected != count) {
					indexSelected = count;
					drawCompleted = false;
					initAngle();
					postInvalidate();
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					if (r.contains((int) point.x, (int) point.y)
							&& listener != null) {
						if (indexSelected > -1) {
							listener.onClick(indexSelected);
						}
						indexSelected = -1;
					}

				} else if (event.getAction() == MotionEvent.ACTION_CANCEL)
					indexSelected = -1;
				count++;
			}

			if (event.getAction() == MotionEvent.ACTION_DOWN
					|| event.getAction() == MotionEvent.ACTION_UP
					|| event.getAction() == MotionEvent.ACTION_CANCEL) {
				postInvalidate();
			}
		}

		return true;
	}

	public ArrayList<PieSlice> getSlices() {
		return slices;
	}

	public void setSlices(ArrayList<PieSlice> slices) {
		this.slices = slices;
		float totalValue = 0;
		for (PieSlice slice : slices) {
			totalValue += slice.getValue();
		}
		for (PieSlice slice : slices) {
			slice.setSweep(0);
			slice.setDesSweep(slice.getValue() / totalValue * 360);
			slice.setStep((slice.getDesSweep()-slice.getSweep())/START_STEP_COUNT);
		}
		startAni = true;
		startCurStepCount = 0;
		initAngle();
		postInvalidate();
	}

	public PieSlice getSlice(int index) {
		return slices.get(index);
	}

	public void setOnSliceClickedListener(OnSliceClickedListener listener) {
		this.listener = listener;
	}

	public void removeSlices() {
		for (int i = slices.size() - 1; i >= 0; i--) {
			slices.remove(i);
		}
		postInvalidate();
	}

	public static interface OnSliceClickedListener {
		public abstract void onClick(int index);
	}

}
