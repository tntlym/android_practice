package com.doradori.memorypower;

import java.util.ArrayList;
import java.util.Random;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends Activity {

	GameView gv;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        gv = new GameView(this);
        setContentView(gv);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    class Shape {
    	final static int RECT = 0;
    	final static int CIRCLE = 1;
    	final static int TRIANGLE = 2;
    	
    	int what;
    	int color;
    	Rect rt;
    }
    
    class GameView extends View {

    	final static int BLANK = 0;
    	final static int PLAY = 1;
    	
    	final static int DELAY = 1500;
    	
    	int status;
    	
    	ArrayList<Shape> arShape = new ArrayList<Shape>();
    	Random Rnd = new Random();
    	Activity mParent;
    	
    	
		public GameView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			
			mParent = (Activity)context;
			
			status = BLANK;
			mHandler.sendEmptyMessageDelayed(0, DELAY);
		}
		
		@SuppressLint("DrawAllocation") 
		public void onDraw (Canvas canvas) {
			canvas.drawColor(Color.BLACK);
			
			if (status == BLANK) {
				return;
			}
			
			int idx;
			for (idx = 0; idx < arShape.size(); idx++) {
				Paint Pnt = new Paint();
				Pnt.setAntiAlias(true);
				Pnt.setColor(arShape.get(idx).color);
				
				Rect rt = arShape.get(idx).rt;
				
				switch (arShape.get(idx).what){
				case Shape.RECT:
					canvas.drawRect(rt, Pnt);
					break;
				case Shape.CIRCLE:
					canvas.drawCircle((float)(rt.left+rt.width()/2), (float)(rt.top+rt.height()/2), (float)(rt.width()/2), Pnt);
					break;
				case Shape.TRIANGLE:
					Path path = new Path();
					path.moveTo(rt.left+rt.width()/2, rt.top);
					path.lineTo(rt.left, rt.bottom);
					path.lineTo(rt.right, rt.bottom);
					canvas.drawPath(path, Pnt);
					break;
				}
			}
		}
		
		public boolean onTouchEvent (MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				int sel;
				sel = FindShapeIdx((int) event.getX(), (int)event.getY());
				
				if (sel == -1) {
					return true;
				}
				
				if (sel == arShape.size()-1) {
					status = BLANK;
					invalidate();
					mHandler.sendEmptyMessageDelayed(0, DELAY);
				} else {
					new AlertDialog.Builder(getContext())
						.setMessage("is that fun? try again?")
						.setTitle("Game Over")
						.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								arShape.clear();
								status = BLANK;
								invalidate();
								mHandler.sendEmptyMessageDelayed(0, DELAY);
							}
						})
						
						.setNegativeButton("NO", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								mParent.finish();
							}
						})
						.show();
				}
				return true;
			}
			return false;
		}
		
		void AddNewShape() {
			Shape shape = new Shape();
			int idx;
			
			boolean bFindIntersect;
			Rect rt = new Rect();
			
			for (;;)  {
				int Size = 32 + 16 *Rnd.nextInt(3);
				
				rt.left = Rnd.nextInt(getWidth());
				rt.top = Rnd.nextInt(getHeight());
				rt.right = rt.left + Size;
				rt.bottom = rt.top + Size;
			
				if (rt.right > getWidth() || rt.bottom > getHeight()) {
					continue;
				}
				
				bFindIntersect = false;
				for (idx=0; idx<arShape.size(); idx++) {
					if (rt.intersect(arShape.get(idx).rt)== true ) {
						bFindIntersect = true;
					}
				}
				
				if (bFindIntersect == false) {
					break;
				}
			}
				
				shape.what = Rnd.nextInt(3);
				
				switch(Rnd.nextInt(5)) {
				case 0:
					shape.color = Color.WHITE;
					break;
				case 1:
					shape.color = Color.RED;
					break;
				case 2:
					shape.color = Color.GREEN;
					break;
				case 3:
					shape.color = Color.BLUE;
					break;
				case 4:
					shape.color = Color.YELLOW;
					break;
				}
				
				shape.rt = rt;
				arShape.add(shape);

		}
		
		int FindShapeIdx(int x, int y) {
			int idx;
			
			for (idx = 0; idx < arShape.size(); idx++) {
				if (arShape.get(idx).rt.contains(x, y)) {
					return idx;
				}
			}
			return -1;
		}
		
		Handler mHandler = new Handler() {
			public void handleMessage(Message msg) {
				AddNewShape();
				status = PLAY;
				invalidate();
				
				String title;
				title = "MemoryPower - " + arShape.size() + " Steps";
				mParent.setTitle(title);
			}
		};
		
    	
    }
    
}
