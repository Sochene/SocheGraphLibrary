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

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Point;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint("DrawAllocation")
public class LineGraph extends View {

	private static final int DEFAULT_PADDING = 10;
	private Line line = new Line();
	//private Line nline = new Line();
	Paint paint = new Paint();
	private float minY = 0, minX = 0;
	private float maxY = 0, maxX = 0;
	private double rangeYRatio = 0;
	private double rangeXRatio = 0;
	private boolean isMaxXUserSet = false;
	private int indexSelected = -1;
	private OnPointClickedListener listener;
	private Bitmap fullImage;
	private boolean shouldUpdate = false;
	private int gridYCounts = 10;
	private int gridXCounts = 9;
	// since this is a new addition, it has to default to false to be backwards
	// compatible
	private boolean isUsingDips = false;
	private int pixelPadding = DEFAULT_PADDING;
	private int dipPadding = DEFAULT_PADDING;

	public LineGraph(Context context) {
		super(context);
		dipPadding = getPixelForDip(DEFAULT_PADDING);
	}

	public LineGraph(Context context, AttributeSet attrs) {
		super(context, attrs);
		dipPadding = getPixelForDip(DEFAULT_PADDING);
	}

	public boolean isUsingDips() {
		return isUsingDips;
	}

	public void setUsingDips(boolean treatSizesAsDips) {
		this.isUsingDips = treatSizesAsDips;
	}

	public void addPointToLine(double x, double y) {
		addPointToLine((float) x, (float) y);
	}

	public void addPointToLine(float x, float y) {
		LinePoint p = new LinePoint(x, y);
		addPointToLine(p);
	}

	public double getRangeYRatio() {
		return rangeYRatio;
	}

	public void setRangeYRatio(double rr) {
		this.rangeYRatio = rr;
	}

	public double getRangeXRatio() {
		return rangeXRatio;
	}

	public void setRangeXRatio(double rr) {
		this.rangeXRatio = rr;
	}

	public void addPointToLine(LinePoint point) {
		line.addPoint(point);
		resetLimits();
		shouldUpdate = true;
		postInvalidate();
	}

	public void addPointsToLine(LinePoint[] points) {
		for (LinePoint point : points) {
			line.addPoint(point);
		}
		resetLimits();
		shouldUpdate = true;
		postInvalidate();
	}

	public void removeAllPointsAfter(double x) {
		removeAllPointsBetween(x, getMaxX());
	}

	public void removeAllPointsBefore(double x) {
		removeAllPointsBetween(getMinX(), x);
	}

	public void removeAllPointsBetween(double startX, double finishX) {
		LinePoint[] pts = new LinePoint[line.getPoints().size()];
		pts = line.getPoints().toArray(pts);
		for (LinePoint point : pts) {
			if (point.getX() >= startX && point.getX() <= finishX)
				line.removePoint(point);
		}
		resetLimits();
		shouldUpdate = true;
		postInvalidate();
	}

	public void removePointsFromLine(LinePoint[] points) {
		for (LinePoint point : points) {
			line.removePoint(point);
		}
		resetLimits();
		shouldUpdate = true;
		postInvalidate();
	}

	public void removePointFromLine(float x, float y) {
		LinePoint p = null;
		p = line.getPoint(x, y);
		removePointFromLine(p);
	}

	public void removePointFromLine(LinePoint point) {
		line.removePoint(point);
		resetLimits();
		shouldUpdate = true;
		postInvalidate();
	}

	public void resetYLimits() {
		float range = getMaxY() - getMinY();
		setRangeY(getMinY() - range * getRangeYRatio(), getMaxY() + range
				* getRangeYRatio());
	}

	public void resetXLimits() {
		float range = getMaxX() - getMinX();
		setRangeX(getMinX() - range * getRangeXRatio(), getMaxX() + range
				* getRangeXRatio());
	}

	public void resetLimits() {
		resetYLimits();
		resetXLimits();
	}

	public void setLine(Line line) {
		this.line = line;
		shouldUpdate = true;
		postInvalidate();
	}

	public Line getLine() {
		return line;
	}

	public void setRangeY(float min, float max) {
		minY = min;
		maxY = max;
	}

	private void setRangeY(double min, double max) {
		minY = (float) min;
		maxY = (float) max;
	}

	public void setRangeX(float min, float max) {
		minX = min;
		maxX = max;
		isMaxXUserSet = true;
	}

	private void setRangeX(double min, double max) {
		minX = (float) min;
		maxX = (float) max;
	}

	public float getMaxX() {
		float max = line.getPoint(0).getX();
		for (LinePoint point : line.getPoints()) {
			max = point.getX() > max ? point.getX() : max;
		}
		maxX = max;
		return maxX;

	}

	public float getMinX() {
		float min = line.getPoint(0).getX();
		for (LinePoint point : line.getPoints()) {
			min = point.getX() < min ? point.getX() : min;
		}
		minX = min;
		return minX;
	}

	public float getMaxY() {
		float max = line.getPoint(0).getY();
		for (LinePoint point : line.getPoints()) {
			max = point.getY() > max ? point.getY() : max;
		}

		maxY = max;
		return maxY;
	}

	public float getMinY() {
		float min = line.getPoint(0).getY();
		for (LinePoint point : line.getPoints()) {
			min = point.getY() < min ? point.getY() : min;
		}
		minY = min;
		return minY;
	}

	public float getMinLimY() {
		return minY;
	}

	public float getMaxLimY() {
		return maxY;
	}

	public float getMinLimX() {
		return minX;
	}

	public float getMaxLimX() {
		if (isMaxXUserSet) {
			return maxX;
		} else {
			return getMaxX();
		}
	}

	public int getGridXCounts() {
		return gridXCounts;
	}

	public void setGridXCounts(int gridXCounts) {
		this.gridXCounts = gridXCounts;
	}

	public int getGridYCounts() {
		return gridYCounts;
	}

	public void setGridYCounts(int girdYCounts) {
		this.gridYCounts = girdYCounts;
	}

	@Override
	public void onDraw(Canvas ca) {
		if (shouldUpdate) {
			fullImage = Bitmap.createBitmap(getWidth(), getHeight(),
					Config.ARGB_8888);
			Canvas canvas = new Canvas(fullImage);

			paint.reset();
			Path path = new Path();

			float bottomPadding = 10, topPadding = 10;
			float sidePadding = 10;
			if (isUsingDips) {
				bottomPadding = dipPadding;
				topPadding = dipPadding;
				sidePadding = dipPadding;
			}
			float usableHeight = getHeight() - bottomPadding - topPadding;
			float usableWidth = getWidth() - 2 * sidePadding;

			float maxY = getMaxLimY();
			float minY = getMinLimY();
			float maxX = getMaxLimX();
			float minX = getMinLimX();

			// 折线下方阴影
			int count = 0;
			float firstXPixels = 0, lastXPixels = 0, newYPixels = 0;
			float lastYPixels = 0, newXPixels = 0;

			paint.setColor(Color.rgb(250, 120, 38));
			paint.setAlpha(30);
			paint.setStrokeWidth(2);

			canvas.drawRect(0, 0, getWidth(), getHeight() - bottomPadding,
					paint);
			paint.reset();

			paint.setXfermode(new PorterDuffXfermode(
					android.graphics.PorterDuff.Mode.CLEAR));
			//TODO
			for (LinePoint p : line.getPoints()) {
				float yPercent = (p.getY() - minY) / (maxY - minY);
				float xPercent = (p.getX() - minX) / (maxX - minX);
				if (count == 0) {
					lastXPixels = sidePadding + (xPercent * usableWidth);
					lastYPixels = getHeight() - bottomPadding
							- (usableHeight * yPercent);
					firstXPixels = lastXPixels;
					path.moveTo(lastXPixels, lastYPixels);
				} else {
					newXPixels = sidePadding + (xPercent * usableWidth);
					newYPixels = getHeight() - bottomPadding
							- (usableHeight * yPercent);
					path.lineTo(newXPixels, newYPixels);
					Path pa = new Path();
					pa.moveTo(lastXPixels, lastYPixels);
					pa.lineTo(newXPixels, newYPixels);
					pa.lineTo(newXPixels, 0);
					pa.lineTo(lastXPixels, 0);
					pa.close();
					canvas.drawPath(pa, paint);
					lastXPixels = newXPixels;
					lastYPixels = newYPixels;
				}
				count++;
			}

			path.reset();

			path.moveTo(0, getHeight() - bottomPadding);
			path.lineTo(sidePadding, getHeight() - bottomPadding);
			path.lineTo(sidePadding, 0);
			path.lineTo(0, 0);
			path.close();
			canvas.drawPath(path, paint);

			path.reset();

			path.moveTo(getWidth(), getHeight() - bottomPadding);
			path.lineTo(getWidth() - sidePadding, getHeight() - bottomPadding);
			path.lineTo(getWidth() - sidePadding, 0);
			path.lineTo(getWidth(), 0);
			path.close();

			canvas.drawPath(path, paint);

			// 画出grid
			paint.reset();
			paint.setColor(Color.LTGRAY);
			paint.setAntiAlias(true);
			paint.setAlpha(120);
			paint.setStrokeWidth(1);
			float realWidth = (getWidth() - sidePadding * 2);
			float realHeight = (getHeight() - bottomPadding);
			int unitWid = (int) (realWidth / gridXCounts);
			int unitHei = (int) (realHeight / gridYCounts);
			for (int i = 1; i < gridYCounts; i++) {
				// heng
				canvas.drawLine(sidePadding, i * unitHei, getWidth()
						- sidePadding, i * unitHei, paint);
			}
			paint.setAlpha(255);
			canvas.drawLine(0 * unitWid + sidePadding, 0, 0 * unitWid
					+ sidePadding, getHeight() - bottomPadding, paint);

			
		/*	for (int i = 0; i < gridXCounts; i++) {
				canvas.drawLine(i * unitWid + sidePadding, 0, i * unitWid
						+ sidePadding, getHeight() - bottomPadding, paint);

			}*/
			// 画出text
			paint.reset();
			paint.setColor(Color.DKGRAY);
			paint.setAlpha(255);
			paint.setAntiAlias(true);
			paint.setStrokeWidth(1);
			paint.setFilterBitmap(true);
			paint.setTextSize(12);
			int unitYVal = (int) ((maxY - minY)/gridYCounts);
			for (int i = gridYCounts - 1; i >= 0; i -= 2) {
				canvas.drawText(unitYVal*(gridYCounts-i)+"", sidePadding + 5, i * unitHei, paint);
			}
			for (int i = 0; i < gridXCounts + 1; i++) {
				if(line.getPoint(i).getLabel() != null)
				canvas.drawText(line.getPoint(i).getLabel(), i * unitWid + sidePadding / 2,
						getHeight(), paint);
			}

			// 画出曲线
			paint.reset();
			paint.setColor(Color.BLACK);
			paint.setAlpha(50);
			paint.setAntiAlias(true);
			canvas.drawLine(sidePadding, getHeight() - bottomPadding,
					getWidth() - sidePadding, getHeight() - bottomPadding,
					paint);
			paint.setAlpha(255);

			count = 0;
			lastXPixels = 0;
			newYPixels = 0;
			lastYPixels = 0;
			newXPixels = 0;

			paint.setColor(line.getColor());
			paint.setStrokeWidth(getStrokeWidth(line));
			//TODO
			for (LinePoint p : line.getPoints()) {
				float yPercent = (p.getY() - minY) / (maxY - minY);
				float xPercent = (p.getX() - minX) / (maxX - minX);
				if (count == 0) {
					lastXPixels = sidePadding + (xPercent * usableWidth);
					lastYPixels = getHeight() - bottomPadding
							- (usableHeight * yPercent);
				} else {
					newXPixels = sidePadding + (xPercent * usableWidth);
					newYPixels = getHeight() - bottomPadding
							- (usableHeight * yPercent);
					canvas.drawLine(lastXPixels, lastYPixels, newXPixels,
							newYPixels, paint);
					lastXPixels = newXPixels;
					lastYPixels = newYPixels;
				}
				count++;
			}

			// 画出圆点
			int pointCount = 0;
			paint.setColor(line.getColor());
			paint.setStrokeWidth(getStrokeWidth(line));
			paint.setStrokeCap(Paint.Cap.ROUND);

			if (line.isShowingPoints()) {
				//TODO
				for (LinePoint p : line.getPoints()) {
					float yPercent = (p.getY() - minY) / (maxY - minY);
					float xPercent = (p.getX() - minX) / (maxX - minX);
					float xPixels = sidePadding + (xPercent * usableWidth);
					float yPixels = getHeight() - bottomPadding
							- (usableHeight * yPercent);

					int outerRadius;
					if (line.isUsingDips()) {
						outerRadius = getPixelForDip(line.getStrokeWidth() + 2);
					} else {
						outerRadius = line.getStrokeWidth() + 2;
					}
					int innerRadius = outerRadius / 2;

					paint.setColor(p.getColor());
					canvas.drawCircle(xPixels, yPixels, outerRadius, paint);
					paint.setColor(Color.WHITE);
					canvas.drawCircle(xPixels, yPixels, innerRadius, paint);

					Path path2 = new Path();
					path2.addCircle(xPixels, yPixels, 30, Direction.CW);
					p.setPath(path2);
					p.setRegion(new Region((int) (xPixels - 30),
							(int) (yPixels - 30), (int) (xPixels + 30),
							(int) (yPixels + 30)));

					if (indexSelected == pointCount && listener != null) {
						// ontouch命中此点
						paint.setColor(p.getColor());
						paint.setAlpha(100);
						canvas.drawPath(p.getPath(), paint);

						if (p.getTip() != null) {
							int bgWidth = 100;
							int bgHeight = 60;
							paint.setColor(Color.BLACK);
							paint.setAlpha(100);
							RectF rect = new RectF((realWidth - bgWidth) / 2,
									10, (realWidth + bgWidth) / 2,
									10 + bgHeight);
							canvas.drawRoundRect(rect, 5, 5, paint);

							paint.setColor(Color.WHITE);
							paint.setAlpha(255);
							paint.setAntiAlias(true);
							paint.setStrokeWidth(1);
							paint.setFilterBitmap(true);
							paint.setTextSize(20);
							float textLen = paint.measureText(p.getTip() + "");
							canvas.drawText(p.getTip() + "",
									(realWidth - textLen) / 2,
									10 + bgHeight / 2, paint);
						}
					}

					pointCount++;
				}
			}
		}
		ca.drawBitmap(fullImage, 0, 0, null);

		shouldUpdate = false;
		for(LinePoint p : line.getPoints()){
			if(p.getY() < p.getDesy()){
				p.setY(p.getY()+p.getStep());
				shouldUpdate = true;
			}
		}
		if(shouldUpdate){
			invalidate();
		}
	}

	private int getStrokeWidth(Line line) {
		int strokeWidth;
		if (line.isUsingDips()) {
			strokeWidth = getPixelForDip(line.getStrokeWidth());
		} else {
			strokeWidth = line.getStrokeWidth();
		}
		return strokeWidth;
	}

	private int getPixelForDip(int dipValue) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dipValue, getResources().getDisplayMetrics());
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(shouldUpdate == true)
			return true;
		Point point = new Point();
		point.x = (int) event.getX();
		point.y = (int) event.getY();

		int count = 0;
		int pointCount = 0;

		Region r = new Region();
		pointCount = 0;
		for (LinePoint p : line.getPoints()) {

			if (p.getPath() != null && p.getRegion() != null) {
				r.setPath(p.getPath(), p.getRegion());
				if (r.contains((int) point.x, (int) point.y)
						&& event.getAction() == MotionEvent.ACTION_DOWN) {
					indexSelected = count;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					if (r.contains((int) point.x, (int) point.y)
							&& listener != null) {
						listener.onClick(pointCount);
					}
					indexSelected = -1;
				}
			}

			pointCount++;
			count++;
		}

		if (event.getAction() == MotionEvent.ACTION_DOWN
				|| event.getAction() == MotionEvent.ACTION_UP) {
			shouldUpdate = true;
			postInvalidate();
		}

		return true;
	}

	public void setOnPointClickedListener(OnPointClickedListener listener) {
		this.listener = listener;
	}

	public interface OnPointClickedListener {
		abstract void onClick(int pointIndex);
	}
}
