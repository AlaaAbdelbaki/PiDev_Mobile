/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kingpins.tunisiagottalent.GUI;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.PieChart;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.kingpins.tunisiagottalent.Entity.Product;
import com.kingpins.tunisiagottalent.Services.ProductServices;
import java.util.ArrayList;


/**
 *
 * @author paspo
 */
public class ProductChartForm extends Form {

     
    
     
     public ProductChartForm(Form previous){
         
         
         super(BoxLayout.y());
         
         ArrayList<Product> testarray = new ArrayList<>();
         
         getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
         Label top3 = new Label("Top 3 Best Selling Products");
         
         Container cnt =new Container(new LayeredLayout());
         LayeredLayout ll = (LayeredLayout)cnt.getLayout();
         
         cnt.add(top3);
         ll.setInsets(top3, "auto auto auto auto");
         
         ArrayList<Product> pd = ProductServices.getInstance().getAllChart();
         for(Product p : pd){

             ArrayList<Product> pdn = ProductServices.getInstance().getOneProduct(p.getId());
             for (Product pn : pdn){
             Product productnamewithquantity = new Product(pn.getProduct_name(),p.getQuantity());
             testarray.add(productnamewithquantity);
                }
   
           
         }
         
         add(cnt);
         add(createPieChartForm(testarray));
     }
       /***********************************************************************************************/
       
     private DefaultRenderer buildCategoryRenderer(int[] colors) {
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLabelsTextSize(40);
        renderer.setLegendTextSize(40);
        renderer.setLabelsColor(10000);
        renderer.setMargins(new int[]{20, 30, 15, 0});
        for (int color : colors) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }
     
     protected CategorySeries buildCategoryDataset(String title, double[] values,ArrayList<Product> onlyname) {
        CategorySeries series = new CategorySeries(title);
        ArrayList<Product> pd = ProductServices.getInstance().getAllChart();
         ArrayList<String> Name = new ArrayList();
         
         for(Product p : onlyname){
             Name.add(p.getProduct_name());
         }
                int i =0;
                for (double value : values) {
                    Container cnt1 = new Container(new LayeredLayout());
                    LayeredLayout ll2 = (LayeredLayout)cnt1.getLayout();
                    
                    
                    series.add(Name.get(i++), value);
                    
                    
                }

        
        return series;
    }
 
     public ChartComponent createPieChartForm(ArrayList<Product> pd) {

        double[] valuesF = null;
        int test1 = 0;
        int test2 = 0;
        String test1_string="" ;
        String test2_string="" ;
        String test3_string="" ;
        
        ArrayList<Product> onlyname = new ArrayList<>();
        for(Product ppp : pd){
            if(ppp.getQuantity()>test1){
                
                test1=ppp.getQuantity();
                test1_string=ppp.getProduct_name();
            }
            
        }
        for(Product ppp : pd){
            if(ppp.getQuantity()<test2){
                
                test2=ppp.getQuantity();
            }
            
        }
        int test2copy = test2;
        int test2copy2 = test2;
        
        for(Product ppp : pd){
            if(ppp.getQuantity()<test1 && ppp.getQuantity()>test2copy){
                test2copy=ppp.getQuantity();
                test2_string=ppp.getProduct_name();
            }
        }
        int test3 =test2copy; 
        
        for(Product ppp : pd){
            if(ppp.getQuantity()<test3 && ppp.getQuantity()>test2copy2){
                test2copy2=ppp.getQuantity();
                test3_string=ppp.getProduct_name();             
            }
        }
        
        
        Product pes1 = new Product(test1_string,test1);
        Product pes2 = new Product(test2_string,test2copy);
        Product pes3 = new Product(test3_string,test2copy2);
        
        onlyname.add(pes1);
        onlyname.add(pes2);
        onlyname.add(pes3);
        
        valuesF = new double[]{test1,test2copy,test2copy2};
        int[] colors = new int[]{ColorUtil.rgb(0, 100, 0), ColorUtil.rgb(255, 165, 0),ColorUtil.rgb(255, 0, 0)};
        DefaultRenderer renderer = buildCategoryRenderer(colors);
        renderer.setChartTitleTextSize(25);
        renderer.setDisplayValues(true);
        renderer.setShowLabels(true);
        SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
       
        PieChart chart = new PieChart(buildCategoryDataset("Project budget", valuesF,onlyname), renderer);

        ChartComponent c = new ChartComponent(chart);
        return c;
     } 
}
