package com.example.dannygonzalez.mapmaker;
/*
Some code is from http://www.android-examples.com/android-simple-draw-canvas-finger-example-tutorial/
*/




import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    public ArrayList<DrawingClass> DrawingClassArrayList = new ArrayList<DrawingClass>();
    RelativeLayout relativeLayout;
    Paint paint;
    View view;
    Path path2;
    Bitmap bitmap;
    Canvas canvas;
    Button buttonClear;
    Button buttonColor;
    Button buttonUp;
    Button buttonDown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout1);

        buttonClear = (Button)findViewById(R.id.button);
        buttonColor = (Button)findViewById(R.id.Colorbutton);
        buttonUp = (Button)findViewById(R.id.Sizeup);
        buttonDown = (Button)findViewById(R.id.Sizedown);

        view = new SketchSheetView(MainActivity.this);

        paint = new Paint();

        path2 = new Path();

        relativeLayout.addView(view, new LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

        paint.setDither(true);

        paint.setColor(Color.parseColor("#000000"));

        paint.setStyle(Paint.Style.STROKE);

        paint.setStrokeJoin(Paint.Join.ROUND);

        paint.setStrokeCap(Paint.Cap.ROUND);

        paint.setStrokeWidth(2);

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path2.reset();
                view.invalidate();
            }
        });

        buttonColor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                paint.setColor(Color.parseColor("#ff0000"));
                view.invalidate();
            }
        });

        buttonUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               float x = paint.getStrokeWidth();
               if(x<=12) {
                   paint.setStrokeWidth(x + 0.5f);
               }
                view.invalidate();
            }
        });

        buttonDown.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                float x = paint.getStrokeWidth();
                if(x>=0.5) {
                    paint.setStrokeWidth(x - 0.5f);
                }
                view.invalidate();
            }
        });
    }

    class SketchSheetView extends View {

        public SketchSheetView(Context context) {

            super(context);

            bitmap = Bitmap.createBitmap(820, 480, Bitmap.Config.ARGB_4444);

            canvas = new Canvas(bitmap);

            this.setBackgroundColor(Color.WHITE);
        }



        @Override
        public boolean onTouchEvent(MotionEvent event) {

            DrawingClass pathWithPaint = new DrawingClass();

            canvas.drawPath(path2, paint);

            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                path2.moveTo(event.getX(), event.getY());

                path2.lineTo(event.getX(), event.getY());
            }
            else if (event.getAction() == MotionEvent.ACTION_MOVE) {

                path2.lineTo(event.getX(), event.getY());

                pathWithPaint.setPath(path2);

                pathWithPaint.setPaint(paint);

                DrawingClassArrayList.add(pathWithPaint);
            }

            invalidate();
            return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (DrawingClassArrayList.size() > 0) {

                canvas.drawPath(
                        DrawingClassArrayList.get(DrawingClassArrayList.size() - 1).getPath(),

                        DrawingClassArrayList.get(DrawingClassArrayList.size() - 1).getPaint());
            }
        }
    }

    public class DrawingClass {

        Path DrawingClassPath;
        Paint DrawingClassPaint;

        public Path getPath() {
            return DrawingClassPath;
        }

        public void setPath(Path path) {
            this.DrawingClassPath = path;
        }


        public Paint getPaint() {
            return DrawingClassPaint;
        }

        public void setPaint(Paint paint) {
            this.DrawingClassPaint = paint;
        }
    }

}
