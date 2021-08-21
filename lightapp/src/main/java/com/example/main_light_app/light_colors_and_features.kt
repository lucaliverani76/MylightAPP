package com.example.main_light_app

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat



class MyCanvasView: AppCompatImageView {

    public lateinit var extraBitmap: Bitmap
    public lateinit var otherimage: Bitmap
    public var unmodifiedcolor:Int=0
    public var modifiedcolor:Int=0
    public var V:Float=1f
    public var S:Float=1f
    public var RGBW : RGBtoRGBW.colorRgbw = RGBtoRGBW.colorRgbw()
    public var rrr = RGBtoRGBW()


    fun init()
    {
        extraBitmap = Bitmap.createBitmap(120, 120, Bitmap.Config.ARGB_8888)
    }

    public constructor (context: Context):super(context)
    {
        init()
    }

    public constructor (context: Context, attrs: AttributeSet?):super(context, attrs)
    {
        init()
    }

    public constructor (context: Context, attrs: AttributeSet, defStyleAttr: Int):super(
        context,
        attrs,
        defStyleAttr
    )
    {
        init()
    }

    private lateinit var extraCanvas: Canvas
    private val backgroundColor = ResourcesCompat.getColor(
        resources,
        android.R.color.holo_red_light,
        null
    )
    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f



    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)

        if (::extraBitmap.isInitialized) extraBitmap.recycle()

        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val getmin:Int=kotlin.math.min(height, width)

       val original = BitmapFactory.decodeResource(resources, R.drawable.pngegg)

        if (::otherimage.isInitialized) otherimage.recycle()

       otherimage = Bitmap.createScaledBitmap(original, getmin, getmin, false)
        var w_1= (extraBitmap.getWidth() - otherimage.getWidth()) / 2
        var h_1 = (extraBitmap.getHeight() - otherimage.getHeight()) / 2

        for (j in  0..otherimage.getHeight()-1)
        for (i in 0..otherimage.getWidth()-1)
        {
            var pixel = otherimage.getPixel(i, j)

            extraBitmap.setPixel(i + w_1, j + h_1, pixel);
        }
       extraCanvas = Canvas(extraBitmap)



        var paint = Paint()
        paint.setColor(Color.WHITE)
                //Color.parseColor("#FFFFFF"))
        //paint.setStrokeWidth(30F)
        //paint.setStyle(Paint.Style.STROKE)
        paint.setAntiAlias(false)
        paint.setDither(false)



        var center_x = (width/2).toFloat()
        var center_y = (height/2).toFloat()
        var radius = (otherimage.getWidth()/5).toFloat()

        // draw circle
        extraCanvas.drawCircle(center_x, center_y, radius, paint)


        //extraCanvas.drawColor(backgroundColor)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(extraBitmap, 0f, 0f, null)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y


        if (
                ((motionTouchEventX<extraBitmap.width)  and (motionTouchEventY<extraBitmap.height))
                and
                ((motionTouchEventX>0)  and (motionTouchEventY>0))
                and
                (((motionTouchEventX - extraBitmap.width/2) * (motionTouchEventX - extraBitmap.width/2) +
                        (motionTouchEventY - extraBitmap.height/2) * (motionTouchEventY - extraBitmap.height/2))>
                        (otherimage.height/4)*(otherimage.height/4))

                and
                (((motionTouchEventX - extraBitmap.width/2) * (motionTouchEventX - extraBitmap.width/2) +
                        (motionTouchEventY - extraBitmap.height/2) * (motionTouchEventY - extraBitmap.height/2))<
                        (otherimage.height/2.1)*(otherimage.height/2.1))

        ) {
            var pixel = extraBitmap.getPixel(motionTouchEventX.toInt(), motionTouchEventY.toInt());
            unmodifiedcolor = pixel
            drawstuff()
        }


        return true
    }

    public fun drawstuff(){

            var pixel=unmodifiedcolor

            //then do what you want with the pixel data, e.g
            var redValue = Color.red(pixel);
            var blueValue = Color.blue(pixel);
            var greenValue = Color.green(pixel)
            //RGBW=rrr.rgbToRgbw(redValue  as UInt,greenValue as UInt, blueValue as UInt)



           //(realActivity as MainActivity?)?.setdata(redValue, greenValue, blueValue)

            var  hsv = FloatArray(3)
            val RDBW=Color.RGBToHSV(redValue, greenValue, blueValue, hsv);

            hsv[1]=S
            hsv[2]=V

            modifiedcolor = Color.HSVToColor(hsv);



            var paint = Paint()
            paint.setColor(modifiedcolor)
            //Color.parseColor("#FFFFFF"))
            //paint.setStrokeWidth(30F)
            //paint.setStyle(Paint.Style.STROKE)
            paint.setAntiAlias(false)
            paint.setDither(false)


            var center_x = (width / 2).toFloat()
            var center_y = (height / 2).toFloat()
            var radius = (otherimage.getWidth() / 5).toFloat()

            // draw circle
            extraCanvas.drawCircle(center_x, center_y, radius, paint)

            invalidate()

        }


}

class light_colors_and_features () : AppCompatActivity() {

    lateinit var myCanvasView:MyCanvasView
    lateinit var warmwhite:TextView
    lateinit var brightness:TextView
    lateinit var seekBar_white:SeekBar
    lateinit var seekBar_brightness:SeekBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.light_colors_and_features)
        //val navView: BottomNavigationView = findViewById(R.id.nav_view)


        myCanvasView = findViewById(R.id.imageView_)

        warmwhite= findViewById(R.id.warmwhite)
        brightness= findViewById(R.id.brigthness)
        seekBar_white= findViewById(R.id.seekBar_white)
        seekBar_brightness= findViewById(R.id.seekBar2)



        seekBar_white.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar, progress: Int,
                fromUser: Boolean
            ) {
                var stringa: String = progress.toString()
                if (progress < 10)
                    stringa = "  " + stringa
                else
                    if ((progress < 100) and (progress >= 10))
                        stringa = " " + stringa
                warmwhite.setText("Warm white " + stringa + "%")
                myCanvasView.V = progress.toFloat() / 100f
                myCanvasView.drawstuff()
            }

            override fun onStartTrackingTouch(arg0: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }
        })

        seekBar_brightness.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar, progress: Int,
                fromUser: Boolean
            ) {
                var stringa: String = progress.toString()
                if (progress < 10)
                    stringa = "  " + stringa
                else
                    if ((progress < 100) and (progress >= 10))
                        stringa = " " + stringa
                brightness.setText("Brightness " + stringa + "%")
                myCanvasView.S = progress.toFloat() / 100f
                myCanvasView.drawstuff()
            }

            override fun onStartTrackingTouch(arg0: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }
        })
        //myCanvasView.setImageBitmap(myCanvasView.extraBitmap);
    }
}