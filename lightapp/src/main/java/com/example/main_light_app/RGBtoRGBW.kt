package com.example.main_light_app

class RGBtoRGBW {

    class colorRgbw{
        var red:UInt=0u
        public var green:UInt= 0u
        public var blue:UInt=0u
        public var white:UInt=0u
    }


public fun saturation(rgbw:colorRgbw ) : UInt {
    var low_:Float= (kotlin.math.min(rgbw.red, kotlin.math.min(rgbw.green, rgbw.blue))).toFloat()
    var high_:Float= (kotlin.math.max(rgbw.red, kotlin.math.max(rgbw.green, rgbw.blue))).toFloat()
    return (kotlin.math.round(100 * ((high_ - low_) / high_)).toUInt())
}


    public fun getWhite(rgbw:colorRgbw ) : UInt {
        return  (255u - saturation(rgbw)).toUInt() / 255u * (rgbw.red + rgbw.green + rgbw.blue) / 3u;
    }


    public fun getWhite(rgbw:colorRgbw, redMax:UInt, greenMax:UInt, blueMax:UInt ) : UInt {

        rgbw.red = (rgbw.red.toFloat() / 255.0 * redMax.toFloat()).toUInt();
        rgbw.green = (rgbw.green.toFloat() / 255.0 * greenMax.toFloat()).toUInt();
        rgbw.blue = (rgbw.blue.toFloat() / 255.0 * blueMax.toFloat()).toUInt();
        return  (255u - saturation(rgbw)).toUInt() / 255u * (rgbw.red + rgbw.green + rgbw.blue) / 3u;
    }


   // Example function.
     public  fun rgbToRgbw(red:UInt, green:UInt, blue:UInt):colorRgbw  {

           var white_:UInt  = 0u
           var rgbw: colorRgbw = colorRgbw()

            rgbw.red=red
            rgbw.green=green
            rgbw.blue=blue
            rgbw.white=white_

            rgbw.white = getWhite(rgbw);
           return rgbw;
       }

    public  fun rgbToRgbw(red:UInt, green:UInt, blue:UInt, redmax:UInt, greenmax:UInt, bluemax:UInt):colorRgbw  {

        var white_:UInt  = 0u
        var rgbw: colorRgbw = colorRgbw()

        rgbw.red=red
        rgbw.green=green
        rgbw.blue=blue
        rgbw.white=white_

        rgbw.white = getWhite(rgbw, redmax, greenmax, bluemax);
        return rgbw;
    }



}