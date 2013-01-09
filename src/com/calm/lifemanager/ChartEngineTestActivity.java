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
		// 1, 构造显示用渲染图
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		// 2,进行显示
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		// 2.1, 构建数据
		Random r = new Random();
		for (int i = 0; i < 2; i++) {
			XYSeries series = new XYSeries("test" + (i + 1));
			// 填充数据
			for (int k = 0; k < 10; k++) {
				// 填x,y值
				series.add(k, 20 + r.nextInt() % 100);
			}
			// 需要绘制的点放进dataset中
			dataset.addSeries(series);
		}
		// 3, 对点的绘制进行设置
		XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
		// 3.1设置颜色
		xyRenderer.setColor(Color.BLUE);
		// 3.2设置点的样式
		xyRenderer.setPointStyle(PointStyle.SQUARE);
		// 3.3, 将要绘制的点添加到坐标绘制中
		renderer.addSeriesRenderer(xyRenderer);
		// 3.4,重复 1~3的步骤绘制第二个系列点
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
