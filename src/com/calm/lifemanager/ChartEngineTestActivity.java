package com.calm.lifemanager;

import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

public class ChartEngineTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 1, ������ʾ����Ⱦͼ
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		// 2,������ʾ
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		// 2.1, ��������
		Random r = new Random();
		for (int i = 0; i < 2; i++) {
			XYSeries series = new XYSeries("test" + (i + 1));
			// �������
			for (int k = 0; k < 10; k++) {
				// ��x,yֵ
				series.add(k, 20 + r.nextInt() % 100);
			}
			// ��Ҫ���Ƶĵ�Ž�dataset��
			dataset.addSeries(series);
		}
		// 3, �Ե�Ļ��ƽ�������
		XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
		// 3.1������ɫ
		xyRenderer.setColor(Color.BLUE);
		// 3.2���õ����ʽ
		xyRenderer.setPointStyle(PointStyle.SQUARE);
		// 3.3, ��Ҫ���Ƶĵ���ӵ����������
		renderer.addSeriesRenderer(xyRenderer);
		// 3.4,�ظ� 1~3�Ĳ�����Ƶڶ���ϵ�е�
		xyRenderer = new XYSeriesRenderer();
		xyRenderer.setColor(Color.RED);
		xyRenderer.setPointStyle(PointStyle.CIRCLE);
		renderer.addSeriesRenderer(xyRenderer);

		// Intent intent = new LinChart().execute(this);
		Intent intent = ChartFactory
				.getLineChartIntent(this, dataset, renderer);
		startActivity(intent);

	}
}
