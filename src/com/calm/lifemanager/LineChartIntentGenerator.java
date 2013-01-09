package com.calm.lifemanager;

import org.achartengine.ChartFactory;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class LineChartIntentGenerator {
	private String[] titles = new String[] { "心情" };

	//private List<double[]> xAxis;
	//private List<double[]> yAxis;
	
	private double[] xAxis;
	private double[] yAxis;

	public LineChartIntentGenerator() {

	}

	public LineChartIntentGenerator(double xData[], double yData[]) {
		//xAxis.add(xData);
		//yAxis.add(yData);
		
		for(int i = 0; i < xData.length; i++){
			xAxis[i] = xData[i];
		}
		
		for(int i = 0; i < yData.length; i++){
			yAxis[i] = yData[i];
		}
		
	}
	
	public Intent execute(Context context) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		
		return ChartFactory.getLineChartIntent(context, dataset, renderer, "趋势图");
	}
	
	private XYMultipleSeriesDataset getDataset() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		XYSeries series = new XYSeries(titles[0]);
		
		for(int i = 0; i < xAxis.length; i++){
			series.add(xAxis[i], yAxis[i]);
		}
		
		dataset.addSeries(series);
		
		return dataset;
	}

	/**
	 * 构建了XYMultipleSeriesRenderer，在其中加入了两个Series。
	 * 注意，这里的Series要用XYSeriesRenderer ，而不能使用 SimpleSeriesRenderer。
	 */
	public XYMultipleSeriesRenderer getRenderer() {
		// 新建一个xymultipleseries
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(16);// 设置坐标轴标题文本大小
		renderer.setChartTitleTextSize(20); // 设置图表标题文本大小
		renderer.setLabelsTextSize(15); // 设置轴标签文本大小
		renderer.setLegendTextSize(15); // 设置图例文本大小
		renderer.setMargins(new int[] { 20, 30, 15, 0 }); // 设置4边留白
		// 设置一个系列的颜色为蓝色
		XYSeriesRenderer r = new XYSeriesRenderer();
		r.setColor(Color.BLUE);
		// 往xymultiplerender中增加一个系列
		renderer.addSeriesRenderer(r);
		// 设置另一个系列的颜色为红色
		r = new XYSeriesRenderer();
		r.setColor(Color.GREEN);
		// 往xymultiplerender中增加另一个系列
		renderer.addSeriesRenderer(r);
		return renderer;
	}

}
