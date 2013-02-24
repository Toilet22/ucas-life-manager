package com.calm.lifemanager;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

public class PieChartViewGenerator {
private double[] value = {22,22,22};
    
    public PieChartViewGenerator(){
        value[0] = 1;
        value[1] = 2;
        value[2] = 3;
    }
    
    public PieChartViewGenerator(double values[]){
        for (int i = 0; i < values.length; i++){
            value[i] = values[i];
        }
    }
    
    public GraphicalView execute(Context context) {
        int[] colors = new int[] { Color.RED, Color.YELLOW, Color.BLUE };
        DefaultRenderer renderer = buildCategoryRenderer(colors);
        CategorySeries categorySeries = new CategorySeries("Vehicles Chart"); 
        categorySeries.add("学习 ", value[0]); 
        categorySeries.add("工作", value[1]); 
        categorySeries.add("娱乐 ", value[2]); 
        return ChartFactory.getPieChartView(context, categorySeries, renderer);
    } 
      
    protected DefaultRenderer buildCategoryRenderer(int[] colors) { 
        DefaultRenderer renderer = new DefaultRenderer(); 
        renderer.setBackgroundColor(Color.GRAY);
        renderer.setApplyBackgroundColor(true);
        renderer.setLabelsTextSize(20);
        renderer.setChartTitle("分布图");
        renderer.setChartTitleTextSize(30);
        renderer.setLegendTextSize(30);
        renderer.setLegendHeight(50);
        for (int color : colors) { 
            SimpleSeriesRenderer r = new SimpleSeriesRenderer(); 
            r.setColor(color); 
            renderer.addSeriesRenderer(r); 
        } 
        return renderer; 
    } 
}
