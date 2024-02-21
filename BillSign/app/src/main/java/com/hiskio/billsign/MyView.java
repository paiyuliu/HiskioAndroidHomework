package com.hiskio.billsign;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.LinkedList;

public class MyView extends View {
    //private LinkedList<HashMap<String,Float>> line=new LinkedList<HashMap<String, Float>>();
    private LinkedList<LinkedList<HashMap<String,Float>>> lines = new LinkedList<LinkedList<HashMap<String, Float>>>();
    private LinkedList<LinkedList<HashMap<String,Float>>> recycler = new LinkedList<LinkedList<HashMap<String, Float>>>();
    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.GREEN);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(8);


        for(LinkedList<HashMap<String,Float>> line:lines){
            //canvas.drawLine(0,0,200,300,paint);
            for(int i = 1;i<line.size();i++){
                HashMap<String,Float> p0 = line.get(i-1);
                HashMap<String,Float> p1 = line.get(i);

                canvas.drawLine(p0.get("x"),p0.get("y"),p1.get("x"),p1.get("y"),paint);
            }
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX(),y=event.getY();
        HashMap<String,Float> point = new HashMap<String,Float>();
        point.put("x",x);
        point.put("y",y);

        if(event.getAction() == MotionEvent.ACTION_DOWN ){
            LinkedList<HashMap<String,Float>> line = new LinkedList<>();
            line.add(point);
            lines.add(line);
            recycler.clear();

        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            lines.getLast().add(point);
        }





        /* call invalidate to fire onDraw */
        invalidate();



        Log.v("umec",x+":"+y);
        //return super.onTouchEvent(event);
        return true;
    }

    public void clear(){
        lines.clear();
        invalidate();
    }

    public void undo(){
        if(lines.size()>0){
            recycler.add(lines.removeLast());
            invalidate();
        }
    }

    public void redo(){
        if(recycler.size()>0){
            lines.add(recycler.removeLast());
            invalidate();
        }
    }
}
