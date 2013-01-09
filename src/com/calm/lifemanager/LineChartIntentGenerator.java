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
	private String[] titles = new String[] { "����" };

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
		
		return ChartFactory.getLineChartIntent(context, dataset, renderer, "����ͼ");
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
	 * ������XYMultipleSeriesRenderer�������м���������Series��
	 * ע�⣬�����SeriesҪ��XYSeriesRenderer ��������ʹ�� SimpleSeriesRenderer��
	 */
	public XYMultipleSeriesRenderer getRenderer() {
		// �½�һ��xymultipleseries
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(16);// ��������������ı���С
		renderer.setChartTitleTextSize(20); // ����ͼ������ı���С
		renderer.setLabelsTextSize(15); // �������ǩ�ı���С
		renderer.setLegendTextSize(15); // ����ͼ���ı���С
		renderer.setMargins(new int[] { 20, 30, 15, 0 }); // ����4������
		// ����һ��ϵ�е���ɫΪ��ɫ
		XYSeriesRenderer r = new XYSeriesRenderer();
		r.setColor(Color.BLUE);
		// ��xymultiplerender������һ��ϵ��
		renderer.addSeriesRenderer(r);
		// ������һ��ϵ�е���ɫΪ��ɫ
		r = new XYSeriesRenderer();
		r.setColor(Color.GREEN);
		// ��xymultiplerender��������һ��ϵ��
		renderer.addSeriesRenderer(r);
		return renderer;
	}

}
