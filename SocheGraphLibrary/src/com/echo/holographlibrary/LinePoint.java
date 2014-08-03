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

import android.graphics.Path;
import android.graphics.Region;

public class LinePoint {
	private float x = 0;
	private float y = 0;
	private float desy = 0;
	private float step = 0;
	private String tip;
	private String label;
	private Path path;
	private Region region;
    private int color = 0xFFffffff;

    public LinePoint(){
    }

	public LinePoint(double x, double y){
		this.x = (float)x;
		this.y = 0;
		this.desy = (float) y;
		this.setStep((this.desy - this.y)/20);
	}
	
	public LinePoint(float x, float y){
		this.x = x;
		this.y = 0;
		this.desy = y;
		this.setStep((this.desy - this.y)/20);
	}
	
	public LinePoint(float x, float y, float desy, int step){
		this.x = x;
		this.y = 0;
		this.desy = desy;
		this.setStep((this.desy - this.y)/step);
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	public void setX(double x){
		this.x = (float) x;
	}
	
	public void setY(double y){
		this.y = (float) y;
	}
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}
	public Path getPath() {
		return path;
	}
	public void setPath(Path path) {
		this.path = path;
	}
	
	@Override
	public String toString(){
		return "x= " + x + ", y= " + y;
	}

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public float getDesy() {
		return desy;
	}

	public void setDesy(float desy) {
		this.desy = desy;
	}

	public float getStep() {
		return step;
	}

	public void setStep(float step) {
		this.step = step;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
